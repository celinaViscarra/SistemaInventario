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
import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.Idiomas;
import com.grupo13.inventario.modelo.TipoProducto;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DocumentoConsultarActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlIdioma = "http://grupo13pdm.ml/inventariows/obtenerlista.php?tabla=idiomas";
    private final String urlTipoProducto = "http://grupo13pdm.ml/inventariows/obtenerlista.php?tabla=tipoproducto";
    private final String urlDocumento = "http://grupo13pdm.ml/inventariows/documento/obtenerlista.php";
    private final String urlConsulta = "http://grupo13pdm.ml/inventariows/documento/consultar.php";
    @BindView(R.id.btnModo)
    Button btnModo;
    @BindViews({
            R.id.edtISBN,
            R.id.edtEdicion,
            R.id.edtEditorial,
            R.id.edtTitulo
    })
    List<EditText> editTextList;

    @BindViews({
            R.id.edtTipoProductoID,
            R.id.edtIdiomaID,
            R.id.edtEscritoID
    })
    List<Spinner> spinners;

    List<Idiomas> idiomas;
    List<TipoProducto> tiposProducto;
    List<Documento> documentos;

    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento_consultar);
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

    public void consultarDocumento(View v){
        String mensaje = "";
        try{
            int posSeleccionada = spinners.get(2).getSelectedItemPosition();
            if(posSeleccionada > 0){
                int idEscrito = documentos.get(posSeleccionada - 1).idEscrito;
                Documento docConsultado = null;

                if(modo_datos == 1){
                    docConsultado = helper.documentoDao().consultarDocumento(idEscrito);
                }else{
                    JSONObject elementoConsulta = new JSONObject();
                    elementoConsulta.put("escrito_id",Integer.toString(idEscrito));

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("elementoConsulta",elementoConsulta.toString()));
                    String respuesta = ControlWS.post(urlConsulta,params, this);
                    JSONObject documentoConsulta = new JSONArray(respuesta).getJSONObject(0);
                    if(documentoConsulta.length() != 0){
                        docConsultado = new Documento();
                        docConsultado.idEscrito = documentoConsulta.getInt("ESCRITO_ID");
                        docConsultado.tipo_producto_id = documentoConsulta.getInt("TIPO_PRODUCTO_ID");
                        docConsultado.idioma_id = documentoConsulta.getInt("IDIOMA_ID");
                        docConsultado.isbn = documentoConsulta.getString("ISBN");
                        docConsultado.edicion = documentoConsulta.getString("EDICION");
                        docConsultado.editorial = documentoConsulta.getString("EDITORIAL");
                        docConsultado.titulo = documentoConsulta.getString("TITULO");
                    }
                }

                if(docConsultado == null){
                    mensaje = "No se encontraron datos";

                } else{
                    mensaje = "Se encontro el registro, mostrando datos...";
                    editTextList.get(0).setText(docConsultado.isbn);
                    editTextList.get(1).setText(docConsultado.edicion);
                    editTextList.get(2).setText(docConsultado.editorial);
                    editTextList.get(3).setText(docConsultado.titulo);

                    int pos = -1;
                    for(TipoProducto tipoProducto: tiposProducto){
                        if(tipoProducto.idTipoProducto == docConsultado.tipo_producto_id){
                            pos = tiposProducto.indexOf(tipoProducto);
                        }
                    }
                    spinners.get(0).setSelection(pos+1);
                    for(Idiomas idioma: idiomas){
                        if(idioma.idIdioma == docConsultado.idioma_id){
                            pos = idiomas.indexOf(idioma);
                        }
                    }
                    spinners.get(1).setSelection(pos+1);
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
        ArrayList<String> nomIdiomas = new ArrayList<>();
        ArrayList<String> nomTiposProducto = new ArrayList<>();
        ArrayList<String> nomDocumentos = new ArrayList<>();
        //Primer paso, antes de ejecutar nuestra consulta
        @Override
        protected void onPreExecute() {
            //Desactivamos el boton para que no le den click hasta que termine
            btnModo.setEnabled(false);
        }
        //Paso 2: la consulta que se hace en otro hilo.
        @Override
        protected String doInBackground(String... strings) {
            //Empieza consulta, revisamos en que modo tenemos los datos.
            switch (modo_datos){
                //Caso 1: Modo SQLite
                case 1:{
                    idiomas = helper.idiomasDao().obtenerIdiomas();
                    tiposProducto = helper.tipoProductoDao().obtenerTipos();
                    documentos = helper.documentoDao().obtenerDocumentos();
                    break;
                }
                //Caso 2: Modo WebService
                case 2:{
                    String jsonIdiomas = ControlWS.get(urlIdioma, ctx);
                    String jsonTiposProducto = ControlWS.get(urlTipoProducto, ctx);
                    String jsonDocumento = ControlWS.get(urlDocumento, ctx);

                    idiomas = ControlWS.obtenerListaIdioma(jsonIdiomas, ctx);
                    tiposProducto = ControlWS.obtenerListaTipoProducto(jsonTiposProducto, ctx);
                    documentos = ControlWS.obtenerListaDocumento(jsonDocumento, ctx);
                    break;
                }
            }

            nomIdiomas.add("** Selecciona un idioma **");
            nomTiposProducto.add("** Selecciona un Tipo de Producto **");
            nomDocumentos.add("** Selecciona un Documento **");
            for(Idiomas idioma: idiomas){
                nomIdiomas.add(idioma.nombreIdioma);
            }
            for(TipoProducto tipoProducto: tiposProducto){
                nomTiposProducto.add(tipoProducto.nomTipoProducto);
            }

            for(Documento pivote: documentos) nomDocumentos.add(pivote.titulo);
            return null;
        }
        //paso 3: despues de la consulta
        @Override
        protected void onPostExecute(String result) {
            ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomIdiomas);
            ArrayAdapter<String> adapterTiposProducto = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomTiposProducto);
            ArrayAdapter<String> adapterDocumentos = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomDocumentos);

            spinners.get(0).setEnabled(false);
            spinners.get(0).setClickable(false);
            spinners.get(1).setEnabled(false);
            spinners.get(1).setClickable(false);

            spinners.get(0).setAdapter(adapterTiposProducto);
            spinners.get(1).setAdapter(adapterIdiomas);
            spinners.get(2).setAdapter(adapterDocumentos);

            btnModo.setEnabled(true);
        }
    }
}