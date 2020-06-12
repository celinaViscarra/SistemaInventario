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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MotivoConsultarWSActivity extends AppCompatActivity {

    @BindView(R.id.edtIdMotivo)
    EditText edtIdMotivo;
    @BindView(R.id.edtNombreMotivo)
    EditText edtNombreMotivo;

    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/motivo/consultar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_consultar_w_s);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void consultarMotivoWS(View v){
        String mensaje = "";
        String motivo_id = edtIdMotivo.getText().toString();
        if(!motivo_id.isEmpty()) {
            try {
                JSONObject elementoConsulta = new JSONObject();
                elementoConsulta.put("motivo_id", motivo_id);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoConsulta", elementoConsulta.toString()));

                String respuesta = ControlWS.post(url, params, this);
                JSONObject resp = new JSONArray(respuesta).getJSONObject(0);
                if (resp.length() != 0) {
                    edtNombreMotivo.setText(resp.getString("MOTIVO_NOMBRE"));
                    mensaje = "Elemento encontrado";
                } else {
                    mensaje = "No existe el elemento";
                    edtNombreMotivo.setText("");
                }

            } catch (JSONException e) {
                mensaje = "No existe el elemento";
                edtNombreMotivo.setText("");
            } finally {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        edtIdMotivo.setText("");
        edtNombreMotivo.setText("");
    }
}
