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

public class CatalogoEquipoActualizarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idCatalogo, idMarca, modelo, memoria, cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_actualizar);
        idCatalogo = (EditText) findViewById(R.id.editIdCatalogo);
        idMarca = (EditText) findViewById(R.id.editIdMarca);
        modelo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantidad = (EditText) findViewById(R.id.editCantEquipo);

        helper = ControlBD.getInstance(this);
    }

    public void actualizarCatalogoEquipo(View v){
        String mensaje = "";
        try{
            CatalogoEquipo catalogo = new CatalogoEquipo();
            catalogo.idCatalogo = idCatalogo.getText().toString();
            catalogo.idMarca = idMarca.getText().toString();
            catalogo.modeloEquipo = modelo.getText().toString();
            catalogo.memoria = Integer.parseInt(memoria.getText().toString());
            catalogo.cantEquipo = Integer.parseInt(cantidad.getText().toString());

            int filasAfectadas = helper.catalogoEquipoDao().actualizarCatalogoEquipo(catalogo);
            if(filasAfectadas <= 0){
                mensaje = "Error al tratar de actualizar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
            }
        }catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";

        }finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        idCatalogo.setText("");
        idMarca.setText("");
        modelo.setText("");
        memoria.setText("");
        cantidad.setText("");
    }
}
