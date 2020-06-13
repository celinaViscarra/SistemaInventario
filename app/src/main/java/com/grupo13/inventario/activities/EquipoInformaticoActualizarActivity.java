package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.ControlWS;
import com.grupo13.inventario.R;
import com.grupo13.inventario.dialog.DatePickerFragment;
import com.grupo13.inventario.modelo.CatalogoEquipo;
import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.Idiomas;
import com.grupo13.inventario.modelo.TipoProducto;
import com.grupo13.inventario.modelo.Ubicaciones;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class EquipoInformaticoActualizarActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlCatalogo = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/obtenerlista.php?tabla=catalogoequipo";
    private final String urlTipoProducto = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/obtenerlista.php?tabla=tipoproducto";
    private final String urlUbicaciones = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/obtenerlista.php?tabla=ubicaciones";
    private final String urlEquipos = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/obtenerlista.php?tabla=equipoinformatico";
    private final String urlActualizarEquipo = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/equipoinformatico/actualizar.php";
    @BindView(R.id.btnModo)
    Button btnModo;
    @BindViews({
            R.id.txt_equipo_codigo,
            R.id.txt_equipo_estado,
            R.id.dp_equipo_fecha,
    })
    List<EditText> editTextList;

    @BindViews({
            R.id.sp_equipo_tipo_producto,
            R.id.sp_equipo_ubicacion,
            R.id.sp_equipo_catalogo,
            R.id.sp_equipo_id,
    })
    List<Spinner> spinners;

    List<TipoProducto> tiposProducto;
    List<Ubicaciones> ubicaciones;
    List<CatalogoEquipo> catalogos;
    List<EquipoInformatico> equipos;

    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_informatico_actualizar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        llenarSpinners();
    }

    //Metodo para cambiar modo
    public void cambiarModo(View v){
        if (modo_datos == 1) modo_datos = 2;
        else modo_datos = 1;
        //Si modo datos esta en 1, el texto sera cambias a WS, caso contrario en modo datos 2
        btnModo.setText((modo_datos == 1)? R.string.cambiar_a_ws: R.string.cambiar_a_sqlite);
        //Ejecutamos el metodo para llenar la lista.
        llenarSpinners();
        limpiarTexto(v);
    }
    public void actualizarEquipoInformatico(View v){
        String mensaje = "";
        try{
            int posTP = spinners.get(0).getSelectedItemPosition();
            int posUbi = spinners.get(1).getSelectedItemPosition();
            int posCata = spinners.get(2).getSelectedItemPosition();
            int posEquipo = spinners.get(3).getSelectedItemPosition();
            boolean textosVacios = false;
            for(EditText pivote: editTextList) textosVacios = pivote.getText().toString().isEmpty() || textosVacios;
            if(posTP > 0 && posUbi > 0 && posCata > 0 && posEquipo > 0 && !textosVacios){
                EquipoInformatico equipo = new EquipoInformatico();
                equipo.idEquipo = equipos.get(posEquipo - 1).idEquipo;
                equipo.tipo_producto_id = tiposProducto.get(posTP - 1).idTipoProducto;
                equipo.ubicacion_id = ubicaciones.get(posUbi - 1).idUbicacion;
                equipo.catalogo_id = catalogos.get(posCata - 1).idCatalogo;
                equipo.codEquipo = editTextList.get(0).getText().toString();
                equipo.estadoEquipo = editTextList.get(1).getText().toString();
                equipo.fechaAdquisicion = Date.valueOf(editTextList.get(2).getText().toString());

                if(modo_datos == 1){
                    int filasafectadas = helper.equipoInformaticoDao().actualizarEquipoInformatico(equipo);
                    if(filasafectadas <= 0){
                        mensaje = "Error al tratar de actualizar el registro a la Base de Datos.";
                    }
                    else{
                        llenarSpinners();
                        mensaje = String.format("Filas afectadas: %d", filasafectadas);
                    }
                } else{
                    JSONObject elementoActualizar = new JSONObject();
                    elementoActualizar.put("equipo_id", equipo.idEquipo);
                    elementoActualizar.put("tipo_producto_id",equipo.tipo_producto_id);
                    elementoActualizar.put("ubicacion_id", equipo.ubicacion_id);
                    elementoActualizar.put("catalogo_id", equipo.catalogo_id);
                    elementoActualizar.put("codigo_equipo", equipo.codEquipo);
                    elementoActualizar.put("estado_equipo", equipo.estadoEquipo);
                    elementoActualizar.put("fecha_adquisicion", equipo.fechaAdquisicion);

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("elementoActualizar",elementoActualizar.toString()));

                    String respuesta = ControlWS.post(urlActualizarEquipo, params, this);
                    //Traemos el objeto json de la respuesta
                    JSONObject resp = new JSONObject(respuesta);
                    //Sacamos el resultado como un entero
                    int resultado = resp.getInt("resultado");
                    if(resultado == 1){
                        mensaje = "Actualizado con exito.";
                    }else{
                        mensaje = "No se pudo actualizar el dato.";
                    }
                }
            }else if(textosVacios) {
                mensaje = "Revisar que ningun campo de texto vaya vacío.";
            } else{
                mensaje = "Seleccione una opcion por favor.";
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        } catch (JSONException e) {
            mensaje = "Error de parseo JSON";
        } finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiarTexto(View v){
        for(EditText pivote: editTextList) pivote.setText("");
        for(Spinner pivote: spinners) pivote.setSelection(0);
    }
    public void llenarSpinners(){
        new LlenarLista(this).execute();
    }

    public void seleccionarFecha(View v) {
        DatePickerFragment fragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                editTextList.get(2).setText(selectedDate);
            }
        });
        fragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    public class LlenarLista extends AsyncTask<String, String, String> {
        Context ctx;
        public LlenarLista(Context ctx){
            this.ctx = ctx;
        }
        ArrayList<String> nomCatalogos = new ArrayList<>();
        ArrayList<String> nomTiposProducto = new ArrayList<>();
        ArrayList<String> nomUbicaciones = new ArrayList<>();
        ArrayList<String> nomEquipos = new ArrayList<>();
        //Primer paso, antes de ejecutar nuestra consulta
        @Override
        protected void onPreExecute() {
            //Desactivamos el boton para que no le den click hasta que termine
            btnModo.setEnabled(false);
        }
        @Override
        protected String doInBackground(String... strings) {
            //Empieza consulta, revisamos en que modo tenemos los datos.
            switch (modo_datos){
                //Caso 1: Modo SQLite
                case 1:{
                    catalogos = helper.catalogoEquipoDao().obtenerCatalogoEquipo();
                    tiposProducto = helper.tipoProductoDao().obtenerTipos();
                    ubicaciones = helper.ubicacionesDao().obtenerUbicaciones();
                    equipos = helper.equipoInformaticoDao().obtenerEquiposInformaticos();
                    break;
                }
                //Caso 2: Modo WebService
                case 2:{
                    String jsonCatalogo = ControlWS.get(urlCatalogo, ctx);
                    String jsonTiposProducto = ControlWS.get(urlTipoProducto, ctx);
                    String jsonUbicaciones = ControlWS.get(urlUbicaciones, ctx);
                    String jsonEquipos = ControlWS.get(urlEquipos, ctx);


                    catalogos = ControlWS.obtenerCatalogoEquipo(jsonCatalogo, ctx);
                    tiposProducto = ControlWS.obtenerListaTipoProducto(jsonTiposProducto, ctx);
                    ubicaciones = ControlWS.obtenerListaUbicaciones(jsonUbicaciones, ctx);
                    equipos = ControlWS.obtenerEquipoInformatico(jsonEquipos, ctx);
                    break;
                }
            }
            nomCatalogos.add("** Selecciona un Catalogo **");
            for(CatalogoEquipo catalogo: catalogos){
                nomCatalogos.add(catalogo.idCatalogo);
            }
            nomTiposProducto.add("** Selecciona un Tipo Producto **");
            for(TipoProducto tipoProducto: tiposProducto){
                nomTiposProducto.add(tipoProducto.nomTipoProducto);
            }
            nomUbicaciones.add("** Selecciona una ubicacion **");
            for(Ubicaciones ubicaciones: ubicaciones){
                nomUbicaciones.add(ubicaciones.nomUbicacion);
            }
            nomEquipos.add("** Selecciona un Equipo **");
            for(EquipoInformatico equipo: equipos){
                nomEquipos.add(String.valueOf(equipo.idEquipo) + " - " + equipo.codEquipo);
            }
            return null;
        }
        //paso 3: despues de la consulta
        @Override
        protected void onPostExecute(String result) {
            ArrayAdapter<String> adapterCatalogo = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomCatalogos);
            ArrayAdapter<String> adapterTiposProducto = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomTiposProducto);
            ArrayAdapter<String> adapterUbicaciones = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomUbicaciones);
            ArrayAdapter<String> adapterEquipos = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomEquipos);

            spinners.get(0).setAdapter(adapterTiposProducto);
            spinners.get(1).setAdapter(adapterUbicaciones);
            spinners.get(2).setAdapter(adapterCatalogo);
            spinners.get(3).setAdapter(adapterEquipos);

            btnModo.setEnabled(true);
        }
    }
}