package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.DetalleDescargos;

public class DetalleDescargosEliminarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idDescargo, idEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_descargos_eliminar);
        idDescargo = (EditText) findViewById(R.id.editIdDescargos);
        idEquipo = (EditText) findViewById(R.id.editIdEquipo);

        helper = ControlBD.getInstance(this);
    }

    public void eliminarDetalleDescargo(View v){
        String mensaje = "";
        try {
            DetalleDescargos detalle = new DetalleDescargos();
            detalle.idEquipo = Integer.parseInt(idEquipo.getText().toString());
            detalle.idDescargo = Integer.parseInt(idDescargo.getText().toString());

            int filasAfectadas = helper.detalleDescargosDao().eliminarDetalleDescargos(detalle);
            if(filasAfectadas<=0){
                mensaje = "Error al tratar de eliminar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de eliminar el registro.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        idEquipo.setText("");
        idDescargo.setText("");
    }
}
