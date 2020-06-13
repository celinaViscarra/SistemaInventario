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

import butterknife.BindView;
import butterknife.ButterKnife;

public class UbicacionesInsertarWSActivity extends AppCompatActivity {

    @BindView(R.id.edtNombreUbicacion)
    EditText edtNombreUbicacion;

    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/ubicaciones/insertar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones_insertar_w_s);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void insertarUbicacionesWS(View v){
        String mensaje = "";

        String nomUbicacion = edtNombreUbicacion.getText().toString();

        if (!nomUbicacion.isEmpty()){
            try {
                JSONObject elementoInsertar = new JSONObject();
                elementoInsertar.put("ubicacion_nombre",nomUbicacion);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoInsertar",elementoInsertar.toString()));

                String respuesta = ControlWS.post(url,params,this);
                JSONObject resp = new JSONObject(respuesta);
                int resultado = resp.getInt("resultado");
                if (resultado == 1){
                    mensaje = "Insertado con exito.";
                } else
                    mensaje = "No se pudo ingresar el dato.";
            } catch (JSONException e){
                mensaje = "Error en el parseo.";
            } finally {
                Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Complete el campo vacio",Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        edtNombreUbicacion.setText("");
    }
}