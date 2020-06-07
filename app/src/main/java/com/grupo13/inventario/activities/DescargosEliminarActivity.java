package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;

public class DescargosEliminarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idDescargo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_eliminar);
        idDescargo = (EditText) findViewById(R.id.editIdDescargos);

        helper = ControlBD.getInstance(this);
    }

    public void eliminarDescargos(View v){
        String mensaje = "";
        try{
            Descargos descargos = new Descargos();
            descargos.idDescargos = Integer.parseInt(idDescargo.getText().toString());
            int filasAfectadas = helper.descargosDao().eliminarDescargos(descargos);

            if(filasAfectadas<=0){
                mensaje = "Error al tratar de eliminar el registro.";
            }else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de eliminar el registro.";
        }catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v) {
        idDescargo.setText("");
    }
}
