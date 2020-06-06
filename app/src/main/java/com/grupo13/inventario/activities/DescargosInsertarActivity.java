package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;

import java.sql.Date;

public class DescargosInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idOrigen, idDestino, descargoFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_insertar);
        idOrigen = (EditText) findViewById(R.id.editIdOrigen);
        idDestino = (EditText) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);

        helper = ControlBD.getInstance(this);
    }

    public void insertarDescargos(View v){
        String mensaje = "";
        int idOrige = Integer.parseInt(idOrigen.getText().toString());
        int idDest = Integer.parseInt(idDestino.getText().toString());
        Date descargoF = Date.valueOf(descargoFecha.getText().toString());

        Descargos nuevo = new Descargos();
        nuevo.ubicacion_origen_id = idOrige;
        nuevo.ubicacion_destino_id = idDest;
        nuevo.fechaDescargos = descargoF;
        try {
            long posicion = helper.descargosDao().insertarDescargos(nuevo);
            if(posicion == 0 || posicion == -1){
                mensaje = "ERROR AL INSERTAR DESCARGO";
            }else{
                mensaje = String.format("Descargo insertado en la posicion %d", posicion);
            }
        } catch (Exception e) {
            mensaje = "ERROR AL INSERTAR DESCARGO";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        idOrigen.setText("");
        idDestino.setText("");
        descargoFecha.setText("");
    }
}
