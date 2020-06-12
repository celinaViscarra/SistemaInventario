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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DescargosConsultarWSActivity extends AppCompatActivity {
    EditText idDescargo, descargoFecha, idOrigen, idDestino;
    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/descargos/consultar.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_consultar_ws);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        idDescargo = (EditText) findViewById(R.id.editIdDescargos);
        idOrigen = (EditText) findViewById(R.id.editIdOrigen);
        idDestino = (EditText) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);
    }

    public void consultarDescargosWS(View v){
        String mensaje = "";
        String descargo = idDescargo.getText().toString();
        if(!descargo.isEmpty()) {
            try {
                JSONObject elementoConsulta = new JSONObject();
                elementoConsulta.put("descargo_id", descargo);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoConsulta", elementoConsulta.toString()));

                String respuesta = ControlWS.post(url, params, this);
                JSONObject resp = new JSONArray(respuesta).getJSONObject(0);
                if (resp.length() != 0) {
                    idOrigen.setText(resp.getString("UBICACION__ORIGEN_ID"));
                    idDestino.setText(resp.getString("UBICACION__DESTINO_ID"));
                    descargoFecha.setText(resp.getString("DESCARGO_FECHA"));
                    mensaje = "Elemento encontrado";
                } else {
                    mensaje = "Elemento vacio";
                }
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
