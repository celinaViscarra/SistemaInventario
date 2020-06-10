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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DocumentoActualizarActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlIdioma = "http://grupo13pdm.ml/inventariows/obtenerlista.php?tabla=idiomas";
    private final String urlTipoProducto = "http://grupo13pdm.ml/inventariows/obtenerlista.php?tabla=tipoproducto";
    private final String urlDocumento = "http://grupo13pdm.ml/inventariows/documento/obtenerlista.php";
    private final String urlActualizar = "http://grupo13pdm.ml/inventariows/documento/actualizar.php";
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
        setContentView(R.layout.activity_documento_actualizar);
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
    public void llenarSpinners(){
        new LlenarLista(this).execute();
    }
    public void limpiarTexto(View v){
        for(Spinner spinner: spinners)
            spinner.setSelection(0);
        for(EditText editText: editTextList)
            editText.setText("");
    }

    public void actualizarDocumento(View v){
        String mensaje = "";
        try{
            int posDocumento = spinners.get(2).getSelectedItemPosition();
            int posIdioma = spinners.get(1).getSelectedItemPosition();
            int posTipoproducto = spinners.get(0).getSelectedItemPosition();
            boolean textosVacios = false;
            for(EditText pivote: editTextList){
                textosVacios = pivote.getText().toString().equals("") || textosVacios;
            }
            if(posDocumento > 0 && posIdioma >0 && posTipoproducto > 0 && !textosVacios){
                Documento doc = new Documento();
                doc.idEscrito = documentos.get(posDocumento - 1).idEscrito;
                doc.tipo_producto_id = tiposProducto.get(posTipoproducto - 1).idTipoProducto;
                doc.idioma_id = idiomas.get(posIdioma - 1).idIdioma;
                doc.isbn = editTextList.get(0).getText().toString();
                doc.edicion = editTextList.get(1).getText().toString();
                doc.editorial = editTextList.get(2).getText().toString();
                doc.titulo = editTextList.get(3).getText().toString();

                if(modo_datos == 1){
                    int filasAfectadas = helper.documentoDao().actualizarDocumento(doc);
                    if(filasAfectadas <= 0){
                        mensaje = "Error al tratar de actualizar el registro.";
                    }
                    else{
                        llenarSpinners();
                        mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                    }
                }
                else{

                    JSONObject elementoActualizar = new JSONObject();
                    //TODO: IMPORTANTE!!!
                    //Si en el nombre en put ponen el nombre como esta en la base de datos
                    //Asi con las mayusculas que salgan en la base, por ejemplo si saliera 'idAutor'
                    //asi tienen que ponerlo.
                    elementoActualizar.put("escrito_id",Integer.toString(doc.idEscrito));
                    elementoActualizar.put("tipo_producto_id",Integer.toString(doc.tipo_producto_id));
                    elementoActualizar.put("idioma_id", Integer.toString(doc.idioma_id));
                    elementoActualizar.put("isbn", doc.isbn);
                    elementoActualizar.put("edicion", doc.edicion);
                    elementoActualizar.put("editorial", doc.editorial);
                    elementoActualizar.put("titulo", doc.titulo);

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("elementoActualizar",elementoActualizar.toString()));

                    System.out.println(elementoActualizar.toString());
                    String respuesta = ControlWS.post(urlActualizar, params, this);
                    //Traemos el objeto json de la respuesta
                    JSONObject resp = new JSONObject(respuesta);
                    System.out.println(resp.toString());
                    //Sacamos el resultado como un entero
                    int resultado = resp.getInt("resultado");
                    if(resultado == 1){
                        mensaje = "Actualizado con exito.";
                        llenarSpinners();
                    }else if(resultado == 0){
                        mensaje = "No se pudo actualizar el dato.";
                    }
                }
            } else if(textosVacios){
                mensaje = "Revisar que ningun campo de texto vaya vacío.";
            }
            else{
                mensaje = "Seleccione una opcion por favor.";
            }

        } catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        } catch(NumberFormatException e){
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

            spinners.get(0).setAdapter(adapterTiposProducto);
            spinners.get(1).setAdapter(adapterIdiomas);
            spinners.get(2).setAdapter(adapterDocumentos);

            btnModo.setEnabled(true);
        }
    }
}