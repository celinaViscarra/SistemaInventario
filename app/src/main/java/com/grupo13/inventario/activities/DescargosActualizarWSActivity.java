package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
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

public class DescargosActualizarWSActivity extends AppCompatActivity {
    EditText idDescargo, descargoFecha, idOrigen, idDestino;
    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/descargos/actualizar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_actualizar_ws);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        idDescargo = (EditText) findViewById(R.id.editIdDescargos);
        idOrigen = (EditText) findViewById(R.id.editIdOrigen);
        idDestino = (EditText) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);
    }

    public void actualizarDescargosWS(View v){
        String mensaje = "";
        String descargo = idDescargo.getText().toString();
        String fecha = descargoFecha.getText().toString();
        String destino = idDestino.getText().toString();
        String origen = idOrigen.getText().toString();
        if(!fecha.isEmpty() && !destino.isEmpty() && !origen.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate fechas = LocalDate.parse(descargoFecha.getText().toString(), formatter);

                JSONObject elementoActualizar = new JSONObject();
                elementoActualizar.put("descargo_id", descargo);
                elementoActualizar.put("ubicacion_destino_id", destino);
                elementoActualizar.put("ubicacion_origen_id", origen);
                elementoActualizar.put("descargo_fecha", fechas.toString());

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoActualizar", elementoActualizar.toString()));

                String respuesta = ControlWS.post(url, params, this);
                JSONObject resp = new JSONObject(respuesta);
                int resultado = resp.getInt("resultado");
                if (resultado == 1) {
                    mensaje = "Actualizado con exito.";
                } else
                    mensaje = "No se pudo actualizar el dato.";
            }catch(DateTimeParseException e){ //Para validar el formato de fecha
                mensaje = "El formato correcto para insertar fecha es: dd—mm—yyyy";
            } catch (JSONException e) {
                mensaje = "Error en el parseo.";
            } finally {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        idDescargo.setText("");
        idOrigen.setText("");
        idDestino.setText("");
        descargoFecha.setText("");
    }
}
