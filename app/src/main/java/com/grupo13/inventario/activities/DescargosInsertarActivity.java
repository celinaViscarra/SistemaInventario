package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;
import java.sql.Date;
import java.util.ArrayList;

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

    public void insertarDescargos(View v) {
        String mensaje = "";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd—mm—yyyy");
            LocalDate fecha = LocalDate.parse(descargoFecha.getText().toString(), formatter);
            Date descargoF = Date.valueOf(fecha.toString());
            int idOrige = Integer.parseInt(idOrigen.getText().toString());
            int idDest = Integer.parseInt(idDestino.getText().toString());


            Descargos nuevo = new Descargos();
            nuevo.ubicacion_origen_id = idOrige;
            nuevo.ubicacion_destino_id = idDest;
            nuevo.fechaDescargos = descargoF;

            long posicion = helper.descargosDao().insertarDescargos(nuevo);
            if (posicion == 0 || posicion == -1) {
                mensaje = "ERROR AL INSERTAR DESCARGO";
            } else {
                mensaje = String.format("Descargo insertado en la posicion %d", posicion);
            }
        }catch(DateTimeParseException e){
            mensaje = "El formato correcto para insertar fecha es: dd—mm—yyyy";
        }catch (Exception e) {
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
