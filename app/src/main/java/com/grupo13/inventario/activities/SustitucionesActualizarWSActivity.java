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

public class SustitucionesActualizarWSActivity extends AppCompatActivity {
    @BindView(R.id.edtMotivoSustitucionActualizar)
    EditText edtMotivoSustitucionActualizar;
    @BindView(R.id.edtEquipObsoletoSustitucionActualizar)
    EditText edtEquipObsoletoSustitucionActualizar;
    @BindView(R.id.edtEquipReemplazoSustitucionActualizar)
    EditText edtEquipReemplazoSustitucionActualizar;
    @BindView(R.id.edtDocenteSustitucionActualizar)
    EditText edtDocenteSustitucionActualizar;

    @BindView(R.id.edtActualizarIDSustitucion)
    EditText edtActualizarIDSustitucion;

    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/sustituciones/actualizar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustituciones_actualizar_w_s);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void actualizarSustitucionWS(View v) {
        String mensaje = "";
        String sustitucion_id = edtActualizarIDSustitucion.getText().toString();
        String motivo_id = edtMotivoSustitucionActualizar.getText().toString();
        String idEqOb = edtEquipObsoletoSustitucionActualizar.getText().toString();
        String idEqRe = edtEquipReemplazoSustitucionActualizar.getText().toString();
        String idDocente = edtDocenteSustitucionActualizar.getText().toString();

        if(!sustitucion_id.isEmpty() || !motivo_id.isEmpty()|| !idEqOb.isEmpty()|| !idEqRe.isEmpty()|| !idDocente.isEmpty()) {
            try {
                JSONObject elementoActualizar = new JSONObject();
                elementoActualizar.put("sustitucion_id", sustitucion_id);
                elementoActualizar.put("motivo_id", motivo_id);
                elementoActualizar.put("equipo_obsoleto_id", idEqOb);
                elementoActualizar.put("equipo_reemplazo_id", idEqRe);
                elementoActualizar.put("docentes_id", idDocente);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoActualizar", elementoActualizar.toString()));

                String respuesta = ControlWS.post(url, params, this);
                JSONObject resp = new JSONObject(respuesta);
                int resultado = resp.getInt("resultado");
                if (resultado == 1) {
                    mensaje = "Actualizado con exito.";
                } else
                    mensaje = "El dato no existe.";

            } catch (JSONException e) {
                mensaje = "Error en el parseo.";
            } finally {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        edtActualizarIDSustitucion.setText("");
        edtMotivoSustitucionActualizar.setText("");
        edtEquipObsoletoSustitucionActualizar.setText("");
        edtEquipReemplazoSustitucionActualizar.setText("");
        edtDocenteSustitucionActualizar.setText("");
    }
}
