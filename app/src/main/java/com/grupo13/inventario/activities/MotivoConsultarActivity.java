package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Motivo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MotivoConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    @BindView(R.id.edtIdMotivo)
    EditText edtIdMotivo;
    @BindView(R.id.edtNombreMotivo)
    EditText edtNombreMotivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_consultar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }
    public void consultarMotivo(View v){

        String mensaje = "";
        try {
            Motivo consulta;
            int motivo_id = Integer.parseInt(edtIdMotivo.getText().toString());

            consulta = helper.motivoDao().consultarMotivo(motivo_id);
            if(consulta == null){
                mensaje = "No se encontraron datos";
                edtNombreMotivo.setText("");
            }
            else{
                mensaje = "Se encontro el registro, mostrando datos...";

                edtNombreMotivo.setText(consulta.nomMotivo);
            }
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}
