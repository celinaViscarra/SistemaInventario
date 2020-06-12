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

public class CatalogoEquipoConsultarWSActivity extends AppCompatActivity {
    EditText modelo, memoria, cantidad, idMarca, idCatalogo;
    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/catalogoequipo/consultar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_consultar_ws);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        idCatalogo = (EditText) findViewById(R.id.editIdCatalogo);
        idMarca = (EditText) findViewById(R.id.editIdMarca);
        modelo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantidad = (EditText) findViewById(R.id.editCantEquipo);
    }

    public void limpiarTexto(View v){
        idCatalogo.setText("");
        idMarca.setText("");
        modelo.setText("");
        memoria.setText("");
        cantidad.setText("");
    }

    public void consultarCatalogoEquipoWS(View v){
        String mensaje = "";
        String catalogo = idCatalogo.getText().toString();
        if(!catalogo.isEmpty()) {
            try {
                JSONObject elementoConsulta = new JSONObject();
                elementoConsulta.put("catalogo_id", catalogo);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoConsulta", elementoConsulta.toString()));

                String respuesta = ControlWS.post(url, params, this);
                JSONObject resp = new JSONArray(respuesta).getJSONObject(0);
                if (resp.length() != 0) {
                    idMarca.setText(resp.getString("MARCA_ID"));
                    modelo.setText(resp.getString("MODELO_EQUIPO_GENERICO"));
                    memoria.setText(resp.getString("MEMORIA"));
                    cantidad.setText(resp.getString("CANTIDAD_EQUIPO"));
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

}
