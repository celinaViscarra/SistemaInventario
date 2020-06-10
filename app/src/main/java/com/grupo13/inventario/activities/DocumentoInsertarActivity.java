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

public class DocumentoInsertarActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlIdioma = "http://grupo13pdm.ml/inventariows/obtenerlista.php?tabla=idiomas";
    private final String urlTipoProducto = "http://grupo13pdm.ml/inventariows/obtenerlista.php?tabla=tipoproducto";
    private final String urlInsertDocumento = "http://grupo13pdm.ml/inventariows/documento/insertar.php";
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
            R.id.edtIdiomaID
    })
    List<Spinner> spinners;

    List<Idiomas> idiomas;
    List<TipoProducto> tiposProducto;

    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento_insertar);
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
        limpiar(v);
    }
    public void insertarDocumento(View v){
        String mensaje = "";
        try{
            Documento doc = new Documento();
            doc.tipo_producto_id = tiposProducto.get(spinners.get(0).getSelectedItemPosition()).idTipoProducto;
            doc.idioma_id = idiomas.get(spinners.get(1).getSelectedItemPosition()).idIdioma;
            doc.isbn = editTextList.get(0).getText().toString();
            doc.edicion = editTextList.get(1).getText().toString();
            doc.editorial = editTextList.get(2).getText().toString();
            doc.titulo = editTextList.get(3).getText().toString();

            if(modo_datos == 1){
                long posicion = helper.documentoDao().insertarDocumento(doc);
                if(posicion == 0 || posicion == -1){
                    mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
                }
                else{
                    mensaje = String.format("Registrado correctamente en la posicion: %d",posicion);
                }
            } else{
                JSONObject elementoInsertar = new JSONObject();
                //TODO: IMPORTANTE!!!
                //Si en el nombre en put ponen el nombre como esta en la base de datos
                //Asi con las mayusculas que salgan en la base, por ejemplo si saliera 'idAutor'
                //asi tienen que ponerlo.
                elementoInsertar.put("tipo_producto_id",doc.tipo_producto_id);
                elementoInsertar.put("idioma_id", doc.idioma_id);
                elementoInsertar.put("isbn", doc.isbn);
                elementoInsertar.put("edicion", doc.edicion);
                elementoInsertar.put("editorial", doc.editorial);
                elementoInsertar.put("titulo", doc.titulo);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("elementoInsertar",elementoInsertar.toString()));

                String respuesta = ControlWS.post(urlInsertDocumento, params, this);
                //Traemos el objeto json de la respuesta
                JSONObject resp = new JSONObject(respuesta);
                //Sacamos el resultado como un entero
                int resultado = resp.getInt("resultado");
                if(resultado == 1){
                    mensaje = "Insertado con exito.";
                }else{
                    mensaje = "No se pudo ingresar el dato.";
                }
            }

        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        } catch (JSONException e) {
            mensaje = "Error de parseo JSON";
        } finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        for(EditText pivote: editTextList) pivote.setText("");
        for(Spinner pivote: spinners) pivote.setSelection(0);
    }
    public void llenarSpinners(){
        new LlenarLista(this).execute();
    }
    public class LlenarLista extends AsyncTask<String, String, String> {
        Context ctx;
        public LlenarLista(Context ctx){
            this.ctx = ctx;
        }
        ArrayList<String> nomIdiomas = new ArrayList<>();
        ArrayList<String> nomTiposProducto = new ArrayList<>();
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
                    idiomas = helper.idiomasDao().obtenerIdiomas();
                    tiposProducto = helper.tipoProductoDao().obtenerTipos();
                    break;
                }
                //Caso 2: Modo WebService
                case 2:{
                    String jsonIdiomas = ControlWS.get(urlIdioma, ctx);
                    String jsonTiposProducto = ControlWS.get(urlTipoProducto, ctx);

                    idiomas = ControlWS.obtenerListaIdioma(jsonIdiomas, ctx);
                    tiposProducto = ControlWS.obtenerListaTipoProducto(jsonTiposProducto, ctx);
                    break;
                }
            }
            for(Idiomas idioma: idiomas){
                nomIdiomas.add(idioma.nombreIdioma);
            }
            for(TipoProducto tipoProducto: tiposProducto){
                nomTiposProducto.add(tipoProducto.nomTipoProducto);
            }
            return null;
        }
        //paso 3: despues de la consulta
        @Override
        protected void onPostExecute(String result) {
            ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomIdiomas);
            ArrayAdapter<String> adapterTiposProducto = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomTiposProducto);

            spinners.get(0).setAdapter(adapterTiposProducto);
            spinners.get(1).setAdapter(adapterIdiomas);

            btnModo.setEnabled(true);
        }
    }
}