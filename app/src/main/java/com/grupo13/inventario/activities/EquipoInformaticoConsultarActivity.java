package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.ControlWS;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.CatalogoEquipo;
import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.Idiomas;
import com.grupo13.inventario.modelo.TipoProducto;
import com.grupo13.inventario.modelo.Ubicaciones;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class EquipoInformaticoConsultarActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlCatalogo = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/obtenerlista.php?tabla=catalogoequipo";
    private final String urlTipoProducto = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/obtenerlista.php?tabla=tipoproducto";
    private final String urlUbicaciones = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/obtenerlista.php?tabla=ubicaciones";
    private final String urlEquipos = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/obtenerlista.php?tabla=equipoinformatico";
    private final String urlActualizarEquipo = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/equipoinformatico/actualizar.php";
    private final String urlConsulta = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/equipoinformatico/consultar.php";
    @BindView(R.id.btnModo)

    Button btnModo;
    @BindViews({
            R.id.txt_equipo_tipo_producto,
            R.id.txt_equipo_ubicacion,
            R.id.txt_equipo_catalogo,
            R.id.txt_equipo_codigo,
            R.id.txt_equipo_estado,
            R.id.txt_equipo_fecha
    })
    List<EditText> editTextList;

    @BindViews({
            R.id.sp_equipo_id
    })
    List<Spinner> spinners;

    List<EquipoInformatico> equipos;
    List<TipoProducto> tiposProducto;
    List<Ubicaciones> ubicaciones;
    List<CatalogoEquipo> catalogos;


    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_informatico_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        llenarSpinners();
    }
    public void cambiarModo(View v){
        if (modo_datos == 1) modo_datos = 2;
        else modo_datos = 1;
        //Si modo datos esta en 1, el texto sera cambias a WS, caso contrario en modo datos 2
        btnModo.setText((modo_datos == 1)? R.string.cambiar_a_ws: R.string.cambiar_a_sqlite);
        //Ejecutamos el metodo para llenar la lista.
        llenarSpinners();
        limpiarTexto(v);
    }
    public void limpiarTexto(View v){
        for(Spinner pivote: spinners) pivote.setSelection(0);
        for(EditText pivote: editTextList) pivote.setText("");
    }

    public void llenarSpinners(){
        new LlenarLista(this).execute();
    }

    public void consultarEquipoInformatico(View v){
        String mensaje = "";
        try{
            int posSeleccionada = spinners.get(0).getSelectedItemPosition();
            if(posSeleccionada > 0){
                int idEquipo = equipos.get(posSeleccionada - 1).idEquipo;
                EquipoInformatico equipo = null;

                if(modo_datos == 1){
                    equipo = helper.equipoInformaticoDao().consultarEquipoInformatico(idEquipo);
                }else{
                    JSONObject elementoConsulta = new JSONObject();
                    elementoConsulta.put("equipo_id",Integer.toString(idEquipo));

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("elementoConsulta",elementoConsulta.toString()));
                    String respuesta = ControlWS.post(urlConsulta,params, this);
                    JSONObject equipoConsulta = new JSONArray(respuesta).getJSONObject(0);
                    if(equipoConsulta.length() != 0){
                        equipo = new EquipoInformatico();
                        equipo.idEquipo = equipoConsulta.getInt("EQUIPO_ID");
                        equipo.tipo_producto_id = equipoConsulta.getInt("TIPO_PRODUCTO_ID");
                        equipo.ubicacion_id = equipoConsulta.getInt("UBICACION_ID");
                        equipo.catalogo_id = equipoConsulta.getString("CATALOGO_ID");
                        equipo.codEquipo = equipoConsulta.getString("CODIGO_EQUIPO");
                        equipo.fechaAdquisicion = Date.valueOf(equipoConsulta.getString("FECHA_ADQUISICION"));
                        equipo.estadoEquipo = equipoConsulta.getString("ESTADO_EQUIPO");
                    }
                }

                if(equipo == null){
                    mensaje = "No se encontraron datos";

                } else{
                    mensaje = "Se encontro el registro, mostrando datos...";

                    for (TipoProducto tipoProducto: tiposProducto) {
                        if (tipoProducto.idTipoProducto == equipo.tipo_producto_id)
                            editTextList.get(0).setText(tipoProducto.nomTipoProducto);
                    }

                    for (Ubicaciones ubicacion: ubicaciones) {
                        if (ubicacion.idUbicacion == equipo.ubicacion_id)
                            editTextList.get(1).setText(ubicacion.nomUbicacion);
                    }

                    editTextList.get(2).setText(equipo.catalogo_id);
                    editTextList.get(3).setText(equipo.codEquipo);
                    editTextList.get(4).setText(equipo.estadoEquipo);
                    editTextList.get(5).setText(equipo.fechaAdquisicion.toString());
                }
            }
            else{
                mensaje = "No se ha seleccionado ningun documento a consultar.";
            }
        }
        catch (SQLiteConstraintException e){
            mensaje = "Error en la base de datos.";
        }
        catch (NumberFormatException e) {
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        } catch (JSONException e) {
            mensaje = "Error de parseo JSON";
        } finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    public class LlenarLista extends AsyncTask<String, String, String> {
        Context ctx;
        public LlenarLista(Context ctx){
            this.ctx = ctx;
        }

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
                    equipos = helper.equipoInformaticoDao().obtenerEquiposInformaticos();
                    catalogos = helper.catalogoEquipoDao().obtenerCatalogoEquipo();
                    tiposProducto = helper.tipoProductoDao().obtenerTipos();
                    ubicaciones = helper.ubicacionesDao().obtenerUbicaciones();
                    break;
                }
                //Caso 2: Modo WebService
                case 2:{
                    String jsonEquipos = ControlWS.get(urlEquipos, ctx);
                    String jsonCatalogo = ControlWS.get(urlCatalogo, ctx);
                    String jsonTiposProducto = ControlWS.get(urlTipoProducto, ctx);
                    String jsonUbicaciones = ControlWS.get(urlUbicaciones, ctx);

                    equipos = ControlWS.obtenerEquipoInformatico(jsonEquipos, ctx);
                    catalogos = ControlWS.obtenerCatalogoEquipo(jsonCatalogo, ctx);
                    tiposProducto = ControlWS.obtenerListaTipoProducto(jsonTiposProducto, ctx);
                    ubicaciones = ControlWS.obtenerListaUbicaciones(jsonUbicaciones, ctx);
                    break;
                }
            }
            nomEquipos.add("** Selecciona un Equipo **");
            for(EquipoInformatico equipo: equipos){
                nomEquipos.add(String.valueOf(equipo.idEquipo));
            }
            return null;
        }
        //paso 3: despues de la consulta
        @Override
        protected void onPostExecute(String result) {
            ArrayAdapter<String> adapterEquipos = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomEquipos);

            spinners.get(0).setAdapter(adapterEquipos);

            btnModo.setEnabled(true);
        }
    }
}