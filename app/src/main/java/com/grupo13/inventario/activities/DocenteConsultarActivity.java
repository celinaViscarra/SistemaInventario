package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Docente;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocenteConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    @BindView(R.id.edtNombreDocente)
    EditText edtNombreDocente;
    @BindView(R.id.edtDocenteID)
    EditText edtDocenteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_consultar);
        //IMPORTANTE, si no dara errores el codigo
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
    }

    public void consultarDocente(View v){
        String mensaje = "";
        try{
            int docenteID = Integer.parseInt(edtDocenteID.getText().toString());
            Docente consulta = helper.docenteDao().consultarDocente(docenteID);
            if(consulta == null){
                mensaje = "No se encontraron datos";
            }else{
                mensaje = "Se encontro el registro, mostrando datos...";
                edtNombreDocente.setText(consulta.nomDocente);
            }
        }
        catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}