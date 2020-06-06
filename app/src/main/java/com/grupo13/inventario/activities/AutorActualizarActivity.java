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

public class AutorActualizarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.txt_autor_id)
    EditText txtId;
    @BindView(R.id.txt_autor_nombre)
    EditText txtNombre;
    @BindView(R.id.txt_autor_apellido)
    EditText txtApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_actualizar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }

    public void actualizarAutorAutor(View v){
        String mensaje = "";
        try {
            Autor autor = new Autor();
            autor.idAutor = Integer.parseInt(txtId.getText().toString());
            autor.nomAutor = txtNombre.getText().toString();
            autor.apeAutor = txtApellido.getText().toString();

            int filasAfectadas = helper.autorDao().actualizarAutor(autor);
            if(filasAfectadas <= 0){
                mensaje = "Error al tratar de actualizar el Autor. No existe";
            }
            else{
                mensaje = String.format("Filas afectadas: %d", filasAfectadas);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }

    }
}