package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Docente;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocenteActualizarActivity extends AppCompatActivity {
    ControlBD helper;
    @BindView(R.id.edtNombreDocente)
    EditText edtNombreDocente;
    @BindView(R.id.edtDocenteID)
    EditText edtDocenteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_actualizar);
        //IMPORTANTE, si no dara errores el codigo
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
    }

    public void actualizarDocente(View v){
        String mensaje = "";
        try{
            Docente docenteAct = new Docente();
            docenteAct.idDocente = Integer.parseInt(edtDocenteID.getText().toString());
            docenteAct.nomDocente = edtNombreDocente.getText().toString();

            int filasAfectadas = helper.docenteDao().actualizarDocente(docenteAct);
            if(filasAfectadas <= 0){
                mensaje = "Error al tratar de actualizar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
            }

        }
        catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";

        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}