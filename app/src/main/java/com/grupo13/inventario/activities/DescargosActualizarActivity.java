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

import java.sql.Date;

public class DescargosActualizarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idDescargo, idOrigen, idDestino, descargoFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_actualizar);
        idDescargo = (EditText) findViewById(R.id.editIdDescargos);
        idOrigen = (EditText) findViewById(R.id.editIdOrigen);
        idDestino = (EditText) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);

        helper = ControlBD.getInstance(this);
    }

    public void actualizarDescargos(View v) {
        String mensaje = "";
        try {
            Descargos descargos = new Descargos();
            descargos.idDescargos = Integer.parseInt(idDescargo.getText().toString());
            descargos.ubicacion_origen_id = Integer.parseInt(idOrigen.getText().toString());
            descargos.ubicacion_destino_id = Integer.parseInt(idDestino.getText().toString());
            descargos.fechaDescargos = Date.valueOf(descargoFecha.getText().toString());

            int filasAfectadas = helper.descargosDao().actualizarDescargos(descargos);

            if (filasAfectadas <= 0) {
                mensaje = "Error al tratar de actualizar el registro.";
            } else {
                mensaje = String.format("Filas afectadas: %d", filasAfectadas);
            }
        }catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }catch(SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        }finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        idDescargo.setText("");
        idOrigen.setText("");
        idDestino.setText("");
        descargoFecha.setText("");
    }
}
