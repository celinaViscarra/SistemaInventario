package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;
import com.grupo13.inventario.modelo.DetalleDescargos;
import com.grupo13.inventario.modelo.EquipoInformatico;

import java.util.ArrayList;
import java.util.List;

public class DetalleDescargosEliminarActivity extends AppCompatActivity {
    ControlBD helper;
    Spinner idDescargo, idEquipo;

    List<Descargos> listaDescargos;
    List<EquipoInformatico> listaEquipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_descargos_eliminar);
        idDescargo = (Spinner) findViewById(R.id.editIdDescargos);
        idEquipo = (Spinner) findViewById(R.id.editIdEquipo);

        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }

    public void eliminarDetalleDescargo(View v){
        String mensaje = "";
        try {
            DetalleDescargos detalle = new DetalleDescargos();
            int posDescargos = idDescargo.getSelectedItemPosition();
            int posEquipo = idEquipo.getSelectedItemPosition();
            if(posDescargos>0 && posEquipo>0) {
                detalle.idDescargo = listaDescargos.get(posDescargos - 1).idDescargos;
                detalle.idEquipo = listaEquipos.get(posEquipo - 1).idEquipo;

                int filasAfectadas = helper.detalleDescargosDao().eliminarDetalleDescargos(detalle);
                if (filasAfectadas <= 0) {
                    mensaje = "Error al tratar de eliminar el registro.";
                } else {
                    mensaje = String.format("Filas afectadas: %d", filasAfectadas);
                }
            }else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de eliminar el registro.";
        }catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void llenarSpinners(){
        listaDescargos = helper.descargosDao().obtenerDescargos();
        listaEquipos = helper.equipoInformaticoDao().obtenerEquiposInformaticos();

        ArrayList<String> idDescargos = new ArrayList<>();
        ArrayList<String> idEquipos = new ArrayList<>();
        idDescargos.add("** Seleccione un Descargo **");
        idEquipos.add("** Seleccione un Equipo Informático **");

        for(Descargos pivote: listaDescargos){
            idDescargos.add(String.valueOf(pivote.idDescargos));
        }

        for(EquipoInformatico pivote: listaEquipos){
            idEquipos.add(String.valueOf(pivote.idEquipo));
        }

        ArrayAdapter descargosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, idDescargos);
        ArrayAdapter equipoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, idEquipos);

        idDescargo.setAdapter(descargosAdapter);
        idEquipo.setAdapter(equipoAdapter);
    }
}
