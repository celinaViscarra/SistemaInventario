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

public class TipoProductoActualizarWSActivity extends AppCompatActivity {

    @BindView(R.id.edtTipoProductoID)
    EditText edtTipoProductoID;
    @BindView(R.id.edtCategoriaID)
    EditText edtCategoriaID;
    @BindView(R.id.edtNombreProducto)
    EditText edtNombreProducto;

    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/tipoproducto/actualizar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_producto_actualizar_w_s);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void actualizarTipoProductoWS(View v){
        String mensaje = "";
        String tipo_producto_id = edtTipoProductoID.getText().toString();
        String categoria_id = edtCategoriaID.getText().toString();
        String nombre_tipo_producto = edtNombreProducto.getText().toString();

        if (!tipo_producto_id.isEmpty() && !categoria_id.isEmpty() && !nombre_tipo_producto.isEmpty()){
            try {
                JSONObject elementoActualizar = new JSONObject();
                elementoActualizar.put("tipo_producto_id",tipo_producto_id);
                elementoActualizar.put("categoria_id",categoria_id);
                elementoActualizar.put("nombre_tipo_producto",nombre_tipo_producto);

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
                mensaje = "Error en el parseo.";
            } finally {
                Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        edtTipoProductoID.setText("");
        edtCategoriaID.setText("");
        edtNombreProducto.setText("");
    }
}