package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;
import com.grupo13.inventario.modelo.Docente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DescargosConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText idOrigen, idDestino, descargoFecha;
    Spinner idDescargo;

    List<Descargos> des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_consultar);
        idDescargo = (Spinner) findViewById(R.id.editIdDescargos);
        idOrigen = (EditText) findViewById(R.id.editIdOrigen);
        idDestino = (EditText) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);


        helper = ControlBD.getInstance(this);
        llenarSpiners();
    }

    public void consultarDescargos(View v){
        String mensaje = "";
        try{
            int posID = idDescargo.getSelectedItemPosition();
            if(posID>0) {
                int idDescargos = des.get(posID - 1).idDescargos;
                Descargos descargos = helper.descargosDao().consultarDescargos(idDescargos);

                if (descargos == null) {
                    mensaje = "No se encontraron datos";
                } else {
                    mensaje = "Se encontro el registro, mostrando datos...";
                    idOrigen.setText(descargos.ubicacion_origen_id+"");
                    idDestino.setText(descargos.ubicacion_destino_id+"");
                    int dia, mes, anio;
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(descargos.fechaDescargos);
                    dia = cal.get(Calendar.DAY_OF_MONTH);
                    mes = cal.get(Calendar.MONTH) + 1;
                    anio = cal.get(Calendar.YEAR);
                    descargoFecha.setText(String.format("%d-%d-%d",dia,mes,anio));
                }
            }else {
                mensaje = "Por favor seleccione los campos para poder consultar.";
            }
        }catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        idOrigen.setText("");
        idDestino.setText("");
        descargoFecha.setText("");
    }


    public void llenarSpiners() {
        des = helper.descargosDao().obtenerDescargos();

        ArrayList<String> idDescargos = new ArrayList<>();

        idDescargos.add("** Seleccione un ID Descargos **");

        for (Descargos descargo: des) {
            idDescargos.add(String.valueOf(descargo.idDescargos));
        }

        ArrayAdapter<String> descargoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idDescargos);

        idDescargo.setAdapter(descargoAdapter);
    }

}
