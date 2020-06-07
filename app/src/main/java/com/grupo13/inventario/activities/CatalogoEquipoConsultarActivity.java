package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.CatalogoEquipo;

public class CatalogoEquipoConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idCatalogo, idMarca, modelo, memoria, cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_consultar);
        idCatalogo = (EditText) findViewById(R.id.editIdCatalogo);
        idMarca = (EditText) findViewById(R.id.editIdMarca);
        modelo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantidad = (EditText) findViewById(R.id.editCantEquipo);

        helper = ControlBD.getInstance(this);
    }

    public void consultarCatalogoEquipo(View v){
        String mensaje = "";
        try{
            String idCatalogoEquipo = idCatalogo.getText().toString();
            CatalogoEquipo catalogo = helper.catalogoEquipoDao().consultarCatalogoEquipo(idCatalogoEquipo);
            if(catalogo == null){
                mensaje = "No se encontraron datos";
            }else{
                mensaje = "Se encontro el registro, mostrando datos...";
                idMarca.setText(catalogo.idMarca);
                modelo.setText(catalogo.modeloEquipo);
                memoria.setText(catalogo.memoria);
                cantidad.setText(catalogo.cantEquipo);
            }
        }catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
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
