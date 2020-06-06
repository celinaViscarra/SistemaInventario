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

public class DocenteInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    @BindView(R.id.edtNombreDocente)
    EditText edtNombreDocente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_insertar);
        //IMPORTANTE, si no dara errores el codigo
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
    }

    public void insertarDocente(View v){
        String mensaje = "";
        Docente nuevo = new Docente();
        nuevo.nomDocente = edtNombreDocente.getText().toString();
        try{
            long posicion = helper.docenteDao().insertarDocente(nuevo);
            if(posicion == 0 || posicion == -1){
                mensaje = "Error al insertar Docente";
            }
            else{
                mensaje = String.format("Registro insertado en la posicion %d", posicion);
            }
        }
        catch (SQLiteConstraintException e){
            mensaje = "Error al insertar Docente";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}