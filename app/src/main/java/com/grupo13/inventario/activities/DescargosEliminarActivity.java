package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.CatalogoEquipo;
import com.grupo13.inventario.modelo.Descargos;

import java.util.ArrayList;
import java.util.List;

public class DescargosEliminarActivity extends AppCompatActivity {
    ControlBD helper;
    Spinner idDescargo;

    List<Descargos> des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_eliminar);
        idDescargo = (Spinner) findViewById(R.id.editIdDescargos);

        helper = ControlBD.getInstance(this);
        llenarSpiners();
    }

    public void eliminarDescargos(View v){
        String mensaje = "";
        try{
            int posDescargo = idDescargo.getSelectedItemPosition();
            if (posDescargo > 0) {
                Descargos descargos = new Descargos();
                descargos.idDescargos = des.get(posDescargo - 1).idDescargos;

                int filasAfectadas = helper.descargosDao().eliminarDescargos(descargos);

                if (filasAfectadas <= 0) {
                    mensaje = "Error al tratar de eliminar el registro.";
                } else {
                    mensaje = String.format("Filas afectadas: %d", filasAfectadas);
                    llenarSpiners();
                }
            }else {
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
    public void llenarSpiners() {
        des = helper.descargosDao().obtenerDescargos();

        ArrayList<String> idDescargos = new ArrayList<>();

        idDescargos.add("** Seleccione un ID Descargos **");

        for (Descargos descargo: des) {
            idDescargos.add(String.valueOf(descargo.idDescargos));
        }

        ArrayAdapter<String> descargoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idDescargos);

        idDescargo.setAdapter(descargoAdapter);
    }
}
