package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.CatalogoEquipo;
import com.grupo13.inventario.modelo.Marca;

import java.util.ArrayList;
import java.util.List;

public class CatalogoEquipoConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText  idMarca, modelo, memoria, cantidad;
    Spinner idCatalogo;

    List<CatalogoEquipo> catalogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_consultar);
        idCatalogo = (Spinner) findViewById(R.id.editIdCatalogo);
        idMarca = (EditText) findViewById(R.id.editIdMarca);
        modelo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantidad = (EditText) findViewById(R.id.editCantEquipo);

        helper = ControlBD.getInstance(this);
        llenarSpiners();
    }

    public void consultarCatalogoEquipo(View v){
        String mensaje = "";
        try{
            int posCatalogo = idCatalogo.getSelectedItemPosition();
            if(posCatalogo>0) {
                String idCatalogo = catalogos.get(posCatalogo - 1).idCatalogo;
                CatalogoEquipo catalogo = helper.catalogoEquipoDao().consultarCatalogoEquipo(idCatalogo);
                if(catalogo == null){
                    mensaje = "No se encontraron datos";
                }else{
                    mensaje = "Se encontro el registro, mostrando datos...";
                    idMarca.setText(catalogo.idMarca);
                    modelo.setText(catalogo.modeloEquipo);
                    memoria.setText(catalogo.memoria+"");
                    cantidad.setText(catalogo.cantEquipo+"");
                }
            }else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }
        }catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        modelo.setText("");
        memoria.setText("");
        cantidad.setText("");
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
