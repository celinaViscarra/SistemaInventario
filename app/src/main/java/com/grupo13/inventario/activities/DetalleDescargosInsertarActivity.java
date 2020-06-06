package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;
import com.grupo13.inventario.modelo.DetalleDescargos;
import com.grupo13.inventario.modelo.DetalleReserva;
import com.grupo13.inventario.modelo.EquipoInformatico;

public class DetalleDescargosInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText editIdEquipo, editIdDescargos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_descargos_insertar);
        editIdDescargos = (EditText) findViewById(R.id.editIdDescargos);
        editIdEquipo = (EditText) findViewById(R.id.editIdEquipo);
        helper = ControlBD.getInstance(this);
    }

    public void insertarDetalleDescargos(View v){
        String mensaje = "";
        try {
            int idEquipo = Integer.parseInt(editIdEquipo.getText().toString());
            int idDescargos = Integer.parseInt(editIdDescargos.getText().toString());
            DetalleDescargos nuevo = new DetalleDescargos();
            nuevo.idDescargo = idDescargos;
            nuevo.idEquipo = idEquipo;
            long idDetalle = helper.detalleDescargosDao().insertarDetalleDescargos(nuevo);

            if(idDetalle == 0 || idDetalle == -1){
                mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
            }
            else{
                mensaje = String.format("DetalleDescargos registrado con ID %d",idDetalle);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        editIdEquipo.setText("");
        editIdDescargos.setText("");
    }
}
