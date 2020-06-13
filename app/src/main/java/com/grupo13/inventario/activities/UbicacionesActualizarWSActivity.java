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

public class UbicacionesActualizarWSActivity extends AppCompatActivity {

    @BindView(R.id.edtUbicacionID)
    EditText edtUbicacionID;
    @BindView(R.id.edtNombreUbicacion)
    EditText edtNombreUbicacion;

    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/ubicaciones/actualizar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones_actualizar_w_s);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void actualizarUbicacionesWS(View v){
        String mensaje = "";
        String ubicacion_id = edtUbicacionID.getText().toString();
        String ubicacion_nombre = edtNombreUbicacion.getText().toString();

        if (!ubicacion_id.isEmpty() && !ubicacion_nombre.isEmpty()){
            try {
                JSONObject elementoActualizar = new JSONObject();
                elementoActualizar.put("ubicacion_id",ubicacion_id);
                elementoActualizar.put("ubicacion_nombre",ubicacion_nombre);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoActualizar",elementoActualizar.toString()));

                String respuesta = ControlWS.post(url,params,this);
                JSONObject resp = new JSONObject(respuesta);
                int resultado = resp.getInt("resultado");
                if (resultado == 1){
                    mensaje = "Actualizado con exito.";
                } else
                    mensaje = "El dato no existe.";
            } catch (JSONException e){
                mensaje = "Error en el parseo";
            } finally {
                Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        edtUbicacionID.setText("");
        edtNombreUbicacion.setText("");
    }
}