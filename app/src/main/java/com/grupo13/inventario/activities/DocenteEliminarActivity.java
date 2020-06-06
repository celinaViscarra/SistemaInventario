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

public class DocenteEliminarActivity extends AppCompatActivity {
    @BindView(R.id.edtDocenteID)
    EditText edtDocenteID;

    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_eliminar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
    }

    public void eliminarDocente(View v){
        String mensaje = "";
        try{
            Docente docente = new Docente();
            docente.idDocente = Integer.parseInt(edtDocenteID.getText().toString());

            int filasAfectadas = helper.docenteDao().eliminarDocente(docente);
            if(filasAfectadas<=0){
                mensaje = "Error al tratar de eliminar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
            }
        }
        catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de eliminar el registro.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}