package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Ubicaciones;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UbicacionesConsultarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtUbicacionID)
    EditText edtUbicacionID;
    @BindView(R.id.edtNombreUbicacion)
    EditText edtNombreUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones_consultar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }
    public void consultarUbicaciones(View v){
        String mensaje = "";
        try {
            Ubicaciones ubiConsulta;
            int ubicacion_id = Integer.parseInt(edtUbicacionID.getText().toString());
            ubiConsulta = helper.ubicacionesDao().consultarUbicaciones(ubiConsulta);
            if (ubiConsulta == null){
                mensaje = "No se encontraron datos";
            }
            else {
                mensaje = "Se encontro el registro, mostrando datos...";
                edtNombreUbicacion.setText(ubiConsulta.nomUbicacion);
            }
        }catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor el dato ingresado.";
        }
        finally {
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}