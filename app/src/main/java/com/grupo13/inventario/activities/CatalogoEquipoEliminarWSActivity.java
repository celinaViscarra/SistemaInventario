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

import java.util.ArrayList;
import java.util.List;

public class CatalogoEquipoEliminarWSActivity extends AppCompatActivity {
    EditText idCatalogo;
    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/catalogoequipo/eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_eliminar_ws);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        idCatalogo = (EditText) findViewById(R.id.editIdCatalogo);
    }

    public void limpiarTexto(View v) {
        idCatalogo.setText("");
    }


    public void eliminarCatalogoEquipoWS(View v){
        String mensaje = "";
        String catalogo = idCatalogo.getText().toString();
        if(!catalogo.isEmpty()) {
            try {
                JSONObject elementoEliminar = new JSONObject();
                elementoEliminar.put("catalogo_id", catalogo);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoEliminar", elementoEliminar.toString()));

                String respuesta = ControlWS.post(url, params, this);
                JSONObject resp = new JSONObject(respuesta);
                int resultado = resp.getInt("resultado");
                if (resultado == 1) {
                    mensaje = "Eliminado con exito.";
                } else
                    mensaje = "No se pudo eliminar el dato.";

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
