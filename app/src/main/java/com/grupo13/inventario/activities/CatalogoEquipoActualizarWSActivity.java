package com.grupo13.inventario.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.grupo13.inventario.ControlWS;
import com.grupo13.inventario.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CatalogoEquipoActualizarWSActivity extends AppCompatActivity {
    EditText modelo, memoria, cantidad, idMarca, idCatalogo;
    private final String url = "http://grupo13pdm.ml/inventariows/catalogoequipo/actualizar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_actualizar_ws);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        idCatalogo = (EditText) findViewById(R.id.editIdCatalogo);
        idMarca = (EditText) findViewById(R.id.editIdMarca);
        modelo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantidad = (EditText) findViewById(R.id.editCantEquipo);
    }

    public void actualizarCatalogoEquipoWS(View v) {
        String mensaje = "";
        try {
            JSONObject elementoActualizar = new JSONObject();
            elementoActualizar.put("catalogo_id", idCatalogo.getText().toString());
            elementoActualizar.put("marca_id", idMarca.getText().toString());
            elementoActualizar.put("modelo_equipo_generico", modelo.getText().toString());
            elementoActualizar.put("memoria", memoria.getText().toString());
            elementoActualizar.put("cantidad_equipo", cantidad.getText().toString());

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("elementoActualizar",elementoActualizar.toString()));

            String respuesta = ControlWS.post(url,params,this);
            JSONObject resp = new JSONObject(respuesta);
            int resultado = resp.getInt("resultado");
            if(resultado == 1){
                mensaje = "Actualizado con exito.";
            }else
                mensaje = "No se pudo actualizar el dato.";

        }catch (JSONException e) {
            mensaje = "Error en el parseo.";
        } finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        idCatalogo.setText("");
        idMarca.setText("");
        modelo.setText("");
        memoria.setText("");
        cantidad.setText("");
    }
}