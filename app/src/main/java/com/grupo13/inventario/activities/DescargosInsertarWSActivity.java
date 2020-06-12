package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlWS;
import com.grupo13.inventario.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.List;

public class DescargosInsertarWSActivity extends AppCompatActivity {
    EditText descargoFecha, idOrigen, idDestino;
    private final String url = "http://grupo13pdm.ml/inventariows/descargos/insertar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_insertar_ws);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        idOrigen = (EditText) findViewById(R.id.editIdOrigen);
        idDestino = (EditText) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);
    }

    public void insertarDescargosWS(View v) {
        String mensaje = "";
        String fecha = descargoFecha.getText().toString();
        String destino = idDestino.getText().toString();
        String origen = idOrigen.getText().toString();
        if(!fecha.isEmpty() && !destino.isEmpty() && !origen.isEmpty()) {
            try {
                //Para verificar formato de fecha
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate fechas = LocalDate.parse(fecha, formatter);


                JSONObject elementoInsertar = new JSONObject();
                elementoInsertar.put("ubicacion_destino_id", destino);
                elementoInsertar.put("ubicacion_origen_id", origen);
                elementoInsertar.put("descargo_fecha", fechas.toString());


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoInsertar", elementoInsertar.toString()));

                String respuesta = ControlWS.post(url, params, this);
                JSONObject resp = new JSONObject(respuesta);
                int resultado = resp.getInt("resultado");
                if (resultado == 1) {
                    mensaje = "Insertado con exito.";
                } else
                    mensaje = "No se pudo ingresar el dato.";
            }catch(DateTimeParseException e){ //Para validar el formato de fecha
                mensaje = "El formato correcto para insertar fecha es: dd—mm—yyyy";
            } catch (JSONException e) {
                mensaje = "Error en el parseo.";
            } finally {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }else { //Si algun campo está vacío da esta excepción.
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        idOrigen.setText("");
        idDestino.setText("");
        descargoFecha.setText("");
    }
}
