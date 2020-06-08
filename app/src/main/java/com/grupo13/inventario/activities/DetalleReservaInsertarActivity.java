package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

public class  DetalleReservaInsertarActivity extends AppCompatActivity {

    ControlBD helper;
    @BindView(R.id.sp_reserva_movimiento)
    Spinner spMovimiento;
    @BindView(R.id.sp_reserva_horario)
    Spinner spHorario;

    List<MovimientoInventario> movimientos;
    List<Horarios> horarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reserva_insertar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }
    public void insertarDetalleReserva(View v){

        int horarioSeleccionado = spHorario.getSelectedItemPosition();
        int movimientoSeleccionado = spMovimiento.getSelectedItemPosition();

        String diaCod = horarios.get(horarioSeleccionado).diaCod;
        int idHora = horarios.get(horarioSeleccionado).idHora;
        int idPrestamo = movimientos.get(movimientoSeleccionado).idPresatamo;

        // VALIDAD QUE NO ESTE YA RESERVADO
        if (verificarReserva(movimientoSeleccionado, horarioSeleccionado)) {
            DetalleReserva detalleReserva = new DetalleReserva();
            detalleReserva.diaCod = diaCod;
            detalleReserva.idHora = idHora;
            detalleReserva.idPrestamos = idPrestamo;

            String mensaje = "";
            try{
                long posicion = helper.detalleReservaDao().insertarDetalleReserva(detalleReserva);
                if(posicion == 0 || posicion == -1){
                    mensaje = "Error al tratar de registrar el Detalle de reserva";
                } else{
                    mensaje = String.format("Registrado correctamente en la posicion: %d", posicion);
                }
            }
            catch (SQLiteConstraintException e){
                mensaje = "Error al tratar de registrar el detalle de reserva";
            }
            finally {
                Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No disponible, ya esta reservado a esa hora",Toast.LENGTH_SHORT).show();
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

        for(MovimientoInventario pivote: movimientos){
            nombresMovimientos.add("ID Equipo: " + pivote.equipo_id + " - " + pivote.descripcion);
        }
        for(Horarios pivote: horarios){
            HoraClase hora = helper.horaClaseDao().consultarHoraClase(pivote.idHora);
            nombresHorarios.add(pivote.diaCod + " | " + hora.horaInicio + " - " + hora.horaFin);
        }

        ArrayAdapter movimientosArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresMovimientos);
        ArrayAdapter horariosArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresHorarios);

        spMovimiento.setAdapter(movimientosArrayAdapter);
        spHorario.setAdapter(horariosArrayAdapter);
    }
}