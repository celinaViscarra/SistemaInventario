package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;
import com.grupo13.inventario.modelo.Ubicaciones;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DescargosActualizarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText descargoFecha;
    Spinner idDescargo, idOrigen, idDestino;

    List<Descargos> listaDescargos;
    List<Ubicaciones> listaOrigen;
    List<Ubicaciones> listaDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_actualizar);
        idDescargo = (Spinner) findViewById(R.id.editIdDescargos);
        idOrigen = (Spinner) findViewById(R.id.editIdOrigen);
        idDestino = (Spinner) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);

        helper = ControlBD.getInstance(this);
        llenarSpiners();
    }

    public void actualizarDescargos(View v) {
        String mensaje = "";
        try {
            Descargos descargos = new Descargos();
            descargos.idDescargos = listaDescargos.get(idDescargo.getSelectedItemPosition()).idDescargos;
            descargos.ubicacion_origen_id = Integer.parseInt(listaOrigen.get(idOrigen.getSelectedItemPosition()).nomUbicacion);
            descargos.ubicacion_destino_id = Integer.parseInt(listaDestino.get(idDestino.getSelectedItemPosition()).nomUbicacion);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd—mm—yyyy");
            LocalDate fecha = LocalDate.parse(descargoFecha.getText().toString(), formatter);
            descargos.fechaDescargos = Date.valueOf(fecha.toString());

            int filasAfectadas = helper.descargosDao().actualizarDescargos(descargos);

            if (filasAfectadas <= 0) {
                mensaje = "Error al tratar de actualizar el registro.";
            } else {
                mensaje = String.format("Filas afectadas: %d", filasAfectadas);
            }
        }catch(DateTimeParseException e){
            mensaje = "El formato correcto para insertar fecha es: dd—mm—yyyy";
        }catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }catch(SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        }finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        descargoFecha.setText("");
    }

    public void llenarSpiners() {
        listaDescargos = helper.descargosDao().obtenerDescargos();
        listaOrigen = helper.ubicacionesDao().obtenerUbicaciones();
        listaDestino = helper.ubicacionesDao().obtenerUbicaciones();

        ArrayList<String> idDescargos = new ArrayList<>();
        ArrayList<String> ubicacionOrigen = new ArrayList<>();
        ArrayList<String> ubicacionDestino = new ArrayList<>();


        idDescargos.add("** Seleccione un ID Descargos **");
        ubicacionDestino.add("** Seleccione una Ubicación de Origen**");
        ubicacionOrigen.add("** Seleccione una Ubicación de Destino **");

        for (Descargos descargo: listaDescargos) {
            idDescargos.add(String.valueOf(descargo.idDescargos));
        }

        for(Ubicaciones ubicacionO : listaOrigen){
            ubicacionOrigen.add(ubicacionO.nomUbicacion);
        }

        for(Ubicaciones ubicacionD : listaDestino){
            ubicacionDestino.add(ubicacionD.nomUbicacion);
        }

        ArrayAdapter<String> descargoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idDescargos);
        ArrayAdapter<String> origenAdaptaer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ubicacionOrigen);
        ArrayAdapter<String> destinoAdaptaer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ubicacionDestino);

        idDescargo.setAdapter(descargoAdapter);
        idOrigen.setAdapter(origenAdaptaer);
        idDestino.setAdapter(descargoAdapter);
    }
}
