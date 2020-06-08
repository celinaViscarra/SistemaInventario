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

public class CatalogoEquipoInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idCatalogo, modeloEquipo, memoria, cantEquipo;
    Spinner idMarca;

    List<Marca> marcas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_insertar);
        idCatalogo = (EditText) findViewById(R.id.editIdCatalogo);
        idMarca = (Spinner) findViewById(R.id.editIdMarca);
        modeloEquipo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantEquipo = (EditText) findViewById(R.id.editCantEquipo);

        helper = ControlBD.getInstance(this);
        llenarSpinner();
    }

    public void insertarCatalogoEquipo(View v){
        String mensaje = "";
        try {
            int posMarca = idMarca.getSelectedItemPosition();
            CatalogoEquipo nuevo = new CatalogoEquipo();
            if(posMarca>0) {
                nuevo.idMarca = marcas.get(posMarca - 1).idMarca;
                String idCata = idCatalogo.getText().toString();
                String modelo = modeloEquipo.getText().toString();
                int memo = Integer.parseInt(memoria.getText().toString());
                int cantidad = Integer.parseInt(cantEquipo.getText().toString());

                nuevo.idCatalogo = idCata;
                nuevo.modeloEquipo = modelo;
                nuevo.memoria = memo;
                nuevo.cantEquipo = cantidad;


                long catalogo = helper.catalogoEquipoDao().insertarCatalogoEquipo(nuevo);
                if (catalogo == 0 || catalogo == -1) {
                    mensaje = "ERROR AL INSERTAR CATALÓGO EQUIPO";
                } else {
                    mensaje = String.format("Registro insertado en la posicion %d", catalogo);
                }
            }else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }
        } catch (Exception e) {
            mensaje = "ERROR AL INSERTAR CATALÓGO EQUIPO";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        idCatalogo.setText("");
        cantEquipo.setText("");
        modeloEquipo.setText("");
        memoria.setText("");
    }


    public void llenarSpinner() {
        marcas = helper.marcaDao().obtenerMarcas();

        ArrayList<String> idMarcas = new ArrayList<>();

        idMarcas.add("** Seleccione una Marca **");

        for (Marca marca : marcas) {
            idMarcas.add(marca.idMarca);
        }

        ArrayAdapter<String> marcaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idMarcas);

        idMarca.setAdapter(marcaAdapter);

    }
}
