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

public class SustitucionesConsultarWSActivity extends AppCompatActivity {
    @BindView(R.id.edtMotivoIDSustitucionConsulta)
    EditText edtMotivoIDSustitucionConsulta;
    @BindView(R.id.edtEquipObsSustitucionConsulta)
    EditText edtEquipObsSustitucionConsulta;
    @BindView(R.id.edtEquipReesSustitucionConsulta)
    EditText edtEquipReemplazoSustitucionInsertar;
    @BindView(R.id.edtDocenteIDSustitucionConsulta)
    EditText edtDocenteIDSustitucionConsulta;

    @BindView(R.id.edtIDSustitucionConsulta)
    EditText edtIDSustitucionConsulta;

    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/sustituciones/consultar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustituciones_consultar_w_s);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void consultarSustitucionWS(View v){
        String mensaje = "";
        String sustitucion_id = edtIDSustitucionConsulta.getText().toString();
        if(!sustitucion_id.isEmpty()) {
            try {
                JSONObject elementoConsulta = new JSONObject();
                elementoConsulta.put("sustitucion_id", sustitucion_id);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoConsulta", elementoConsulta.toString()));

                String respuesta = ControlWS.post(url, params, this);
                JSONObject resp = new JSONArray(respuesta).getJSONObject(0);
                if (resp.length() != 0) {
                    edtMotivoIDSustitucionConsulta.setText(resp.getString("MOTIVO_ID"));
                    edtEquipObsSustitucionConsulta.setText(resp.getString("EQUIPO_OBSOLETO_ID"));
                    edtEquipReemplazoSustitucionInsertar.setText(resp.getString("EQUIPO_REEMPLAZO_ID"));
                    edtDocenteIDSustitucionConsulta.setText(resp.getString("DOCENTES_ID"));
                    mensaje = "Elemento encontrado";
                } else {
                    mensaje = "No existe el elemento";
                    edtMotivoIDSustitucionConsulta.setText("");
                    edtEquipObsSustitucionConsulta.setText("");
                    edtEquipReemplazoSustitucionInsertar.setText("");
                    edtDocenteIDSustitucionConsulta.setText("");
                }

            } catch (JSONException e) {
                mensaje = "No existe el elemento";
                edtMotivoIDSustitucionConsulta.setText("");
                edtEquipObsSustitucionConsulta.setText("");
                edtEquipReemplazoSustitucionInsertar.setText("");
                edtDocenteIDSustitucionConsulta.setText("");
            } finally {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        edtIDSustitucionConsulta.setText("");
        edtMotivoIDSustitucionConsulta.setText("");
        edtEquipObsSustitucionConsulta.setText("");
        edtEquipReemplazoSustitucionInsertar.setText("");
        edtDocenteIDSustitucionConsulta.setText("");
    }
}
