package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Autor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutorInsertarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.txt_autor_nombre)
    EditText txtNombre;
    @BindView(R.id.txt_autor_apellido)
    EditText txtApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_insertar);
        helper = ControlBD.getInstance(this);

        ButterKnife.bind(this);
    }

    public void insertarAutorAutor(View v){
        String mensaje = "";
        try {
            String nombre = txtNombre.getText().toString();
            String apellido = txtApellido.getText().toString();

            if (!nombre.isEmpty() && !apellido.isEmpty()) {
                Autor nuevoAutor = new Autor();
                nuevoAutor.nomAutor = nombre;
                nuevoAutor.apeAutor = apellido;

                long idAutor = helper.autorDao().insertarAutor(nuevoAutor);
                if(idAutor == 0 || idAutor == -1){
                    mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
                }
                else{
                    mensaje = String.format("Autor registrado con ID %d", idAutor);
                }
            }else {
                mensaje = "Complete todos los campos";
            }

        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        }
        finally{
            Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show();
        }
    }
}