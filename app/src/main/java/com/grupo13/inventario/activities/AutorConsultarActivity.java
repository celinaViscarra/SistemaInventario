package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Autor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutorConsultarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.txt_autor_id)
    EditText txtIdAutor;
    @BindView(R.id.txt_autor_nombre_c)
    EditText txtNombre;
    @BindView(R.id.txt_autor_apellido_c)
    EditText txtApellido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_consultar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }
    public void consultarAutor(View v){

        String mensaje = "";
        try {
            Autor autor;
            int idAutor = Integer.parseInt(txtIdAutor.getText().toString());
            autor = helper.autorDao().consultarAutor(idAutor);
            if(autor == null){
                mensaje = "No hay autores con ese ID";
            }
            else{
                mensaje = "Se encontro el registro, mostrando datos...";
                txtNombre.setText(autor.nomAutor);
                txtApellido.setText(autor.apeAutor);
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