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
import com.grupo13.inventario.modelo.CatalogoEquipo;

import java.util.ArrayList;
import java.util.List;

public class CatalogoEquipoEliminarActivity extends AppCompatActivity {
    ControlBD helper;
    Spinner idCatalogo;

    List<CatalogoEquipo> catalogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_eliminar);
        idCatalogo = (Spinner) findViewById(R.id.editIdCatalogo);

        helper = ControlBD.getInstance(this);
        llenarSpiners();
    }

    public void eliminarCatalogoEquipo(View v){
        String mensaje = "";
        try {
            int posCatalogo = idCatalogo.getSelectedItemPosition();
            if (posCatalogo > 0) {
                CatalogoEquipo catalogo = new CatalogoEquipo();
                catalogo.idCatalogo = catalogos.get(posCatalogo - 1).idCatalogo;

                int filasAfectadas = helper.catalogoEquipoDao().eliminarCatalogoEquipo(catalogo);
                if (filasAfectadas <= 0) {
                    mensaje = "Error al tratar de eliminar el registro.";
                } else {
                    mensaje = String.format("Filas afectadas: %d", filasAfectadas);
                    llenarSpiners();
                }
            } else {
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
        catalogos = helper.catalogoEquipoDao().obtenerCatalogoEquipo();

        ArrayList<String> idCatalogos = new ArrayList<>();

        idCatalogos.add("** Seleccione un Catalogo **");

        for (CatalogoEquipo catalogoEquipo : catalogos) {
            idCatalogos.add(catalogoEquipo.idCatalogo);
        }

        ArrayAdapter<String> catalogoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idCatalogos);

        idCatalogo.setAdapter(catalogoAdapter);
    }
}
