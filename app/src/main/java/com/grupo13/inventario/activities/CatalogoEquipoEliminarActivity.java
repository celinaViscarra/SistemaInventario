package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.CatalogoEquipo;

public class CatalogoEquipoEliminarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idCatalogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_eliminar);
        idCatalogo = (EditText) findViewById(R.id.editIdCatalogo);

        helper = ControlBD.getInstance(this);
    }

    public void eliminarCatalogoEquipo(View v){
        String mensaje = "";
        try{
            CatalogoEquipo catalogo = new CatalogoEquipo();
            catalogo.idCatalogo = idCatalogo.getText().toString();

            int filasAfectadas = helper.catalogoEquipoDao().eliminarCatalogoEquipo(catalogo);
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

    public void limpiarTexto(){
        idCatalogo.setText("");
    }
}
