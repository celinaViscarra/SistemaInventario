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

import java.util.ArrayList;
import java.util.List;

public class DescargosInsertarWSActivity extends AppCompatActivity {
    EditText idDescargo, descargoFecha, idOrigen, idDestino;
    private final String url = "http://grupo13pdm.ml/inventariows/descargos/insertar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_insertar_ws);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        idDescargo = (EditText) findViewById(R.id.editIdDescargos);
        idOrigen = (EditText) findViewById(R.id.editIdOrigen);
        idDestino = (EditText) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);
    }

    public void insertarDescargosWS(View v) {
        String mensaje = "";
        try {
            JSONObject elementoInsertar = new JSONObject();
            elementoInsertar.put("descargo_id", idDescargo.getText().toString());
            elementoInsertar.put("ubicacion_destino_id", idDestino.getText().toString());
            elementoInsertar.put("ubicacion_origen_id", idOrigen.getText().toString());
            elementoInsertar.put("descargo_fecha", descargoFecha.getText().toString());

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("elementoInsertar",elementoInsertar.toString()));

            String respuesta = ControlWS.post(url,params,this);
            JSONObject resp = new JSONObject(respuesta);
            int resultado = resp.getInt("resultado");
            if(resultado == 1){
                mensaje = "Insertado con exito.";
            }else
                mensaje = "No se pudo ingresar el dato.";
        } catch (JSONException e) {
            mensaje = "Error en el parseo.";
        } finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        idDescargo.setText("");
        idOrigen.setText("");
        idDestino.setText("");
        descargoFecha.setText("");
    }
}
