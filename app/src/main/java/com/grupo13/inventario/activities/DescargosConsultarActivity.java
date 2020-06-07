package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;
import com.grupo13.inventario.modelo.Docente;

public class DescargosConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idDescargo, idOrigen, idDestino, descargoFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_consultar);
        idDescargo = (EditText) findViewById(R.id.editIdDescargos);
        idOrigen = (EditText) findViewById(R.id.editIdOrigen);
        idDestino = (EditText) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);

        helper = ControlBD.getInstance(this);
    }

    public void consultarDescargos(View v){
        String mensaje = "";
        try{
            int descargosId = Integer.parseInt(idDescargo.getText().toString());
            Descargos descargos = helper.descargosDao().consultarDescargos(descargosId);

            if(descargos == null){
                mensaje = "No se encontraron datos";
            }else {
                mensaje = "Se encontro el registro, mostrando datos...";
                idOrigen.setText(descargos.ubicacion_origen_id);
                idDestino.setText(descargos.ubicacion_destino_id);
                int dia, mes, anio;
                dia = descargos.fechaDescargos.getDay();
                mes = descargos.fechaDescargos.getMonth();
                anio = descargos.fechaDescargos.getYear();
                descargoFecha.setText(String.format("%d—%d—%d", dia, mes, anio));
            }
        }catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
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
