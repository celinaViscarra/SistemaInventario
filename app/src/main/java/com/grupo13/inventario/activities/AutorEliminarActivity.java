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

public class AutorEliminarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.txt_autor_id_e)
    EditText autorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_eliminar);

        ButterKnife.bind(this);

        helper = ControlBD.getInstance(this);
    }

    public void eliminarAutor(View v){
        String mensaje = "";
        try {
            Autor aEliminar = new Autor();
            aEliminar.idAutor = Integer.parseInt(autorId.getText().toString());

            int filasAfectadas = helper.autorDao().eliminarAutor(aEliminar);
            if(filasAfectadas <= 0){
                mensaje = "Error al tratar de eliminar el Autor.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d", filasAfectadas);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de eliminar el registro. No existe";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}