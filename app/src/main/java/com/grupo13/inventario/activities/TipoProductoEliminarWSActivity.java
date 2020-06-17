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

public class TipoProductoEliminarWSActivity extends AppCompatActivity {

    @BindView(R.id.edtTipoProductoID)
    EditText edtTipoProductoID;

    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/tipoproducto/eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_producto_eliminar_w_s);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void eliminarTipoProductoWS(View v){
        String mensaje = "";
        String tipoProducto_id = edtTipoProductoID.getText().toString();
        if (!tipoProducto_id.isEmpty()){
            try {
                JSONObject elementoEliminar = new JSONObject();
                elementoEliminar.put("tipoProducto_id",tipoProducto_id);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoEliminar",elementoEliminar.toString()));

                String respuesta = ControlWS.post(url,params,this);
                JSONObject resp = new JSONObject(respuesta);
                int resultado = resp.getInt("resultado");
                if (resultado == 1){
                    mensaje = "Eliminado con exito.";
                } else
                    mensaje = "El dato no existe.";
            } catch (JSONException e){
                mensaje = "Error en el parseo.";
            } finally {
                Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        edtTipoProductoID.setText("");
    }
}