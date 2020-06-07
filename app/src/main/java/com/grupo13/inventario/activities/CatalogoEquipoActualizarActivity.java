package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.CatalogoEquipo;

import java.util.List;

public class CatalogoEquipoActualizarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idMarca, modelo, memoria, cantidad;
    Spinner idCatalogo;
    List<CatalogoEquipo> catalogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_actualizar);
        idCatalogo = (Spinner) findViewById(R.id.editIdCatalogo);
        idMarca = (EditText) findViewById(R.id.editIdMarca);
        modelo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantidad = (EditText) findViewById(R.id.editCantEquipo);

        helper = ControlBD.getInstance(this);
    }

    public void actualizarCatalogoEquipo(View v){
        String mensaje = "";
        try{
            int posCatalogo = idCatalogo.getSelectedItemPosition();
            CatalogoEquipo catalogo = new CatalogoEquipo();
            if(posCatalogo>0) {
                catalogo.idCatalogo = catalogos.get(posCatalogo - 1).idCatalogo;
                catalogo.idMarca = idMarca.getText().toString();
                catalogo.modeloEquipo = modelo.getText().toString();
                catalogo.memoria = Integer.parseInt(memoria.getText().toString());
                catalogo.cantEquipo = Integer.parseInt(cantidad.getText().toString());

                int filasAfectadas = helper.catalogoEquipoDao().actualizarCatalogoEquipo(catalogo);
                if (filasAfectadas <= 0) {
                    mensaje = "Error al tratar de actualizar el registro.";
                } else {
                    mensaje = String.format("Filas afectadas: %d", filasAfectadas);
                }
            }else{
                mensaje = "Por favor, seleccione una opcion valida.";
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
        idMarca.setText("");
        modelo.setText("");
        memoria.setText("");
        cantidad.setText("");
    }
}
