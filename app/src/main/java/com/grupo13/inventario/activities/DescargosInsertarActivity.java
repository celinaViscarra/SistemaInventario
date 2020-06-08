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
import com.grupo13.inventario.modelo.CatalogoEquipo;
import com.grupo13.inventario.modelo.Descargos;
import com.grupo13.inventario.modelo.Ubicaciones;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DescargosInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText descargoFecha;
    Spinner idOrigen, idDestino;

    List<Ubicaciones> listaOrigen;
    List<Ubicaciones> listaDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargos_insertar);
        idOrigen = (Spinner) findViewById(R.id.editIdOrigen);
        idDestino = (Spinner) findViewById(R.id.editIdDestino);
        descargoFecha = (EditText) findViewById(R.id.editIdDescargoFecha);

        helper = ControlBD.getInstance(this);
        llenarSpiners();
    }

    public void insertarDescargos(View v) {
        String mensaje = "";
        try {Descargos descargos = new Descargos();
            int posOrigen = idOrigen.getSelectedItemPosition();
            int posDestino = idDestino.getSelectedItemPosition();
            if(posOrigen>0 && posDestino>0) {
                descargos.ubicacion_origen_id = Integer.parseInt(listaOrigen.get(posOrigen - 1).nomUbicacion);
                descargos.ubicacion_destino_id = Integer.parseInt(listaDestino.get(posDestino - 1).nomUbicacion);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd—mm—yyyy");
                LocalDate fecha = LocalDate.parse(descargoFecha.getText().toString(), formatter);
                descargos.fechaDescargos = Date.valueOf(fecha.toString());
                long posicion = helper.descargosDao().insertarDescargos(descargos);
                if (posicion == 0 || posicion == -1) {
                    mensaje = "ERROR AL INSERTAR DESCARGO";
                } else {
                    mensaje = String.format("Descargo insertado en la posicion %d", posicion);
                }
            }else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }
        }catch(DateTimeParseException e){
            mensaje = "El formato correcto para insertar fecha es: dd—mm—yyyy";
        }catch (Exception e) {
            mensaje = "ERROR AL INSERTAR DESCARGO";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        descargoFecha.setText("");
    }

    public void llenarSpiners() {
        listaOrigen = helper.ubicacionesDao().obtenerUbicaciones();
        listaDestino = helper.ubicacionesDao().obtenerUbicaciones();

        ArrayList<String> ubicacionOrigen = new ArrayList<>();
        ArrayList<String> ubicacionDestino = new ArrayList<>();

        ubicacionDestino.add("** Seleccione una Ubicación de Origen**");
        ubicacionOrigen.add("** Seleccione una Ubicación de Destino **");

        for (Ubicaciones ubicacionO : listaOrigen) {
            ubicacionOrigen.add(ubicacionO.nomUbicacion);
        }

        for (Ubicaciones ubicacionD : listaDestino) {
            ubicacionDestino.add(ubicacionD.nomUbicacion);
        }

        ArrayAdapter<String> origenAdaptaer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ubicacionOrigen);
        ArrayAdapter<String> destinoAdaptaer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ubicacionDestino);

        idOrigen.setAdapter(origenAdaptaer);
        idDestino.setAdapter(destinoAdaptaer);
    }

}
