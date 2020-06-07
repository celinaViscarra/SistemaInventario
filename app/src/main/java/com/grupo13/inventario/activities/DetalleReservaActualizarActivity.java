package com.grupo13.inventario.activities;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.DetalleReserva;
import com.grupo13.inventario.modelo.HoraClase;
import com.grupo13.inventario.modelo.MovimientoInventario;
import com.grupo13.inventario.modelo.Horarios;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  DetalleReservaActualizarActivity extends AppCompatActivity {

    ControlBD helper;
    @BindView(R.id.sp_reserva_movimiento)
    Spinner spMovimiento;
    @BindView(R.id.sp_reserva_horario)
    Spinner spHorario;
    @BindView(R.id.sp_reserva_horario_actualizar)
    Spinner spHorarioActualizado;

    List<MovimientoInventario> movimientos;
    List<Horarios> horarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reserva_actualizar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }
    public void actualizarDetalleReserva(View v){

        int horarioSeleccionado = spHorario.getSelectedItemPosition();
        int movimientoSeleccionado = spMovimiento.getSelectedItemPosition();
        int nuevoHorario = spHorarioActualizado.getSelectedItemPosition();

        String diaCod = horarios.get(horarioSeleccionado).diaCod;
        int idHora = horarios.get(horarioSeleccionado).idHora;
        int idPrestamo = movimientos.get(movimientoSeleccionado).idPresatamo;

        String diaCodActu = horarios.get(nuevoHorario).diaCod;
        int idHoraActu = horarios.get(nuevoHorario).idHora;


        DetalleReserva aEditar = helper.detalleReservaDao().consultarDetalleReserva(idHora, diaCod, idPrestamo);

        if (aEditar != null) {
            // VALIDAD QUE NO ESTE YA RESERVADO
            if (verificarReserva(movimientoSeleccionado, nuevoHorario)) {


                String mensaje = "";
                try{
                    helper.detalleReservaDao().eliminarDetalleReserva(aEditar);

                    aEditar.diaCod = diaCodActu;
                    aEditar.idHora = idHoraActu;
                    int filasAfectadas =  (int) helper.detalleReservaDao().insertarDetalleReserva(aEditar);

                    if(filasAfectadas <= 0){
                        mensaje = "Error al tratar de actualizar el registro.";
                    } else{
                        mensaje = String.format("Actualizado");
                    }
                }
                catch (SQLiteConstraintException e){
                    mensaje = "Error al tratar de registrar el actualizar de reserva";
                }
                finally {
                    Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No disponible, ya esta reservado a esa hora",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No hay ninguna reserva que coincidente",Toast.LENGTH_SHORT).show();
        }


    }

    public boolean verificarReserva(int movimiento, int horario) {
        boolean disponible = true;

        MovimientoInventario nuevoPrestamo = movimientos.get(movimiento); //Prestamo seleccionado
        Horarios hora = horarios.get(horario);
        List<DetalleReserva> detalles = helper.detalleReservaDao().obtenerDetallesReserva();

        if (detalles != null && detalles.size() > 0) {
            int equipoId = nuevoPrestamo.equipo_id;

            for (MovimientoInventario mov: movimientos){
                // Verificar solo para el equipo que se planea prestar
                if (mov.equipo_id == equipoId) {
                    int prestamoID = mov.idPresatamo;

                    //Buscar en los detalles
                    for (DetalleReserva detalle: detalles){
                        if (detalle.idPrestamos == prestamoID) { // Los detalles del prestamo que se encontró
                            if (detalle.idHora == hora.idHora && detalle.diaCod.equals(hora.diaCod)){ // Reservado mismo dia y hora
                                //Verificar si es la misma fecha
                                if (mov.prestamoFechaInicio.toString().equals(nuevoPrestamo.prestamoFechaInicio.toString())) {
                                    disponible = false;
                                    break;
                                }
                            }
                        }
                    }

                }
            }

        }
        return disponible;
    }

    public void llenarSpinners(){

        movimientos = helper.movimientoInventarioDao().obtenerMovimientosInventario();
        horarios = helper.horariosDao().obtenerHorarios();

        ArrayList<String> nombresMovimientos = new ArrayList<>();
        ArrayList<String> nombresHorarios = new ArrayList<>();
        ArrayList<String> nombresHorariosActualizado = new ArrayList<>();

        for(MovimientoInventario pivote: movimientos){
            nombresMovimientos.add("ID Equipo: " + pivote.equipo_id + " - " + pivote.descripcion);
        }
        for(Horarios pivote: horarios){
            HoraClase hora = helper.horaClaseDao().consultarHoraClase(pivote.idHora);
            nombresHorarios.add(pivote.diaCod + " | " + hora.horaInicio + " - " + hora.horaFin);
            nombresHorariosActualizado.add(pivote.diaCod + " | " + hora.horaInicio + " - " + hora.horaFin);
        }

        ArrayAdapter movimientosArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresMovimientos);
        ArrayAdapter horariosArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresHorarios);
        ArrayAdapter horariosActualizadoArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresHorarios);

        spMovimiento.setAdapter(movimientosArrayAdapter);
        spHorario.setAdapter(horariosArrayAdapter);
        spHorarioActualizado.setAdapter(horariosActualizadoArrayAdapter);
    }
}