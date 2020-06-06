package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.CatalogoEquipo;

public class CatalogoEquipoInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idCatalogo, idMarca, modeloEquipo, memoria, cantEquipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_insertar);
        idCatalogo = (EditText) findViewById(R.id.editIdCatalogo);
        idMarca = (EditText) findViewById(R.id.editIdMarca);
        modeloEquipo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantEquipo = (EditText) findViewById(R.id.editCantEquipo);

        helper = ControlBD.getInstance(this);
    }

    public void insertarCatalogoEquipo(View v){
        String mensaje = "";
        try {
            String idCata = idCatalogo.getText().toString();
            String idMark = idMarca.getText().toString();
            String modelo = modeloEquipo.getText().toString();
            int memo = Integer.parseInt(memoria.getText().toString());
            int cantidad = Integer.parseInt(cantEquipo.getText().toString());

            CatalogoEquipo nuevo = new CatalogoEquipo();
            nuevo.idCatalogo = idCata;
            nuevo.idMarca = idMark;
            nuevo.modeloEquipo = modelo;
            nuevo.memoria = memo;
            nuevo.cantEquipo = cantidad;


            long catalogo = helper.catalogoEquipoDao().insertarCatalogoEquipo(nuevo);
            if(catalogo == 0 || catalogo == -1){
                mensaje = "ERROR AL INSERTAR CATALÓGO EQUIPO";
            }else{
                mensaje = String.format("Registro insertado en la posicion %d", catalogo);
            }
        } catch (Exception e) {
            mensaje = "ERROR AL INSERTAR CATALÓGO EQUIPO";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        idMarca.setText("");
        idCatalogo.setText("");
        cantEquipo.setText("");
        modeloEquipo.setText("");
        memoria.setText("");
    }
}
