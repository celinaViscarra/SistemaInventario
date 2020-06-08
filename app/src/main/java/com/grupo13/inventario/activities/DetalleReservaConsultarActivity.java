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

public class  DetalleReservaConsultarActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_detalle_reserva_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }
    public void consultarDetalleReserva(View v){

        int horarioSeleccionado = spHorario.getSelectedItemPosition();
        int movimientoSeleccionado = spMovimiento.getSelectedItemPosition();

        String diaCod = horarios.get(horarioSeleccionado).diaCod;
        int idHora = horarios.get(horarioSeleccionado).idHora;
        int idPrestamo = movimientos.get(movimientoSeleccionado).idPresatamo;

        DetalleReserva consulta = helper.detalleReservaDao().consultarDetalleReserva(idHora, diaCod, idPrestamo);

        if (consulta != null) {
            Toast.makeText(this, "Si existe ese prestamo", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "NO existe ese prestamo", Toast.LENGTH_SHORT).show();
        }

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