package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.dialog.DatePickerFragment;
import com.grupo13.inventario.modelo.CatalogoEquipo;
import com.grupo13.inventario.modelo.Docente;
import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.ParticipacionDocente;
import com.grupo13.inventario.modelo.TipoParticipacion;
import com.grupo13.inventario.modelo.TipoProducto;
import com.grupo13.inventario.modelo.Ubicaciones;

import java.lang.reflect.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  EquipoInformaticoInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    @BindView(R.id.sp_equipo_tipo_producto)
    Spinner spTipoProducto;
    @BindView(R.id.sp_equipo_catalogo)
    Spinner spCatalogo;
    @BindView(R.id.sp_equipo_ubicacion)
    Spinner spUbicacion;
    @BindView(R.id.txt_equipo_codigo)
    EditText txtCodigo;
    @BindView(R.id.txt_equipo_estado)
    EditText txtEstado;
    @BindView(R.id.dp_equipo_fecha)
    EditText txtFecha;

    List<TipoProducto> tipoProductos;
    List<CatalogoEquipo> catalogoEquipos;
    List<Ubicaciones> ubicaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_informatico_insertar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }

    public void insertarEquipoInformatico(View v){
        if (tipoProductos.size() > 0 && catalogoEquipos.size() > 0 && ubicaciones.size() > 0){
            EquipoInformatico equipo = new EquipoInformatico();
            equipo.tipo_producto_id = tipoProductos.get(spTipoProducto.getSelectedItemPosition()).idTipoProducto;
            equipo.catalogo_id = catalogoEquipos.get(spCatalogo.getSelectedItemPosition()).idCatalogo;
            equipo.ubicacion_id = ubicaciones.get(spUbicacion.getSelectedItemPosition()).idUbicacion;
            equipo.codEquipo = txtCodigo.getText().toString();
            equipo.estadoEquipo = txtEstado.getText().toString();
            String fecha = txtFecha.getText().toString();

            if(!equipo.codEquipo.isEmpty() && !equipo.estadoEquipo.isEmpty() && !fecha.isEmpty()){
                String mensaje = "";
                equipo.fechaAdquisicion = Date.valueOf(fecha);
                try{
                    long posicion = helper.equipoInformaticoDao().insertarEquipoInformatico(equipo);
                    if(posicion == 0 || posicion == -1){
                        mensaje = "Error al tratar de registrar el Equipo";
                    } else{
                        mensaje = String.format("Registrado correctamente en la posicion: %d", posicion);
                    }
                }
                catch (SQLiteConstraintException e){
                    mensaje = "Error al tratar de registrar El equipo.";
                }
                finally {
                    Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "No estan todos los campos completos", Toast.LENGTH_SHORT).show();
        }

    }

    public void llenarSpinners(){
        tipoProductos = helper.tipoProductoDao().obtenerTipos();
        catalogoEquipos = helper.catalogoEquipoDao().obtenerCatalogoEquipo();
        ubicaciones = helper.ubicacionesDao().obtenerUbicaciones();

        //Segundo paso, hacer los arraylist que ocupare en los spinner
        ArrayList<String> nombreTipos = new ArrayList<>();
        ArrayList<String> nombreCatalogo = new ArrayList<>();
        ArrayList<String> nombreUbicacion = new ArrayList<>();

        for(TipoProducto pivote: tipoProductos){
            nombreTipos.add(pivote.nomTipoProducto);
        }
        for(CatalogoEquipo pivote: catalogoEquipos){
            nombreCatalogo.add(pivote.modeloEquipo);
        }
        for(Ubicaciones pivote: ubicaciones){
            nombreUbicacion.add(pivote.nomUbicacion);
        }

        ArrayAdapter tipoProductoArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombreTipos);
        ArrayAdapter catalogoEquipoArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombreCatalogo);
        ArrayAdapter ubicacionesArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombreUbicacion);

        spTipoProducto.setAdapter(tipoProductoArrayAdapter);
        spCatalogo.setAdapter(catalogoEquipoArrayAdapter);
        spUbicacion.setAdapter(ubicacionesArrayAdapter);
    }

    public void seleccionarFecha(View v) {
        DatePickerFragment fragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                txtFecha.setText(selectedDate);
            }
        });
        fragment.show(this.getSupportFragmentManager(), "datePicker");
    }
}