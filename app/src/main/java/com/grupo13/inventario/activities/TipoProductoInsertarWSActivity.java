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

public class TipoProductoInsertarWSActivity extends AppCompatActivity {

    @BindView(R.id.edtNombreProducto)
    EditText edtNombreProducto;
    @BindView(R.id.edtTipoProductoID)
    EditText edtTipoProductoID;
    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/tipoproducto/insertar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_producto_insertar_w_s);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void insertarTipoProductoWS(View v){
        String mensaje = "";

        String nomTipoProducto = edtNombreProducto.getText().toString();
        String idTipoProducto = edtTipoProductoID.getText().toString();

        if (!nomTipoProducto.isEmpty() && !idTipoProducto.isEmpty()){
            try {
                JSONObject elementoInsertar = new JSONObject();
                elementoInsertar.put("tipoProducto_nombre",nomTipoProducto);
                elementoInsertar.put("tipoProducto_id",idTipoProducto);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoInsertar",elementoInsertar.toString()));

                String respuesta = ControlWS.post(url,params,this);
                JSONObject resp = new JSONObject(respuesta);
                int resultado = resp.getInt("resultado");
                if (resultado == 1){
                    mensaje = "Insertado con exito.";
                } else
                    mensaje = "no se pudo ingresar el dato.";
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
        edtTipoProductoID.setText("");
        edtTipoProductoID.setText("");
    }
}