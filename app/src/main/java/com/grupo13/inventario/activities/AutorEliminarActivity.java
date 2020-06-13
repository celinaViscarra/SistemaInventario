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
import com.grupo13.inventario.modelo.Autor;
import com.grupo13.inventario.modelo.Documento;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutorEliminarActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlListado = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/autor/obtenerlista.php";
    private final String urlEliminarAutor = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/autor/eliminar.php";
    @BindView(R.id.btnModo)
    Button btnModo;
    List<Autor> autores;
    @BindView(R.id.sp_autor_id)
    Spinner spAutor;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_eliminar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        llenarLista();
    }
    //Metodo para cambiar modo
    public void cambiarModo(View v){
        if (modo_datos == 1) modo_datos = 2;
        else modo_datos = 1;
        //Si modo datos esta en 1, el texto sera cambias a WS, caso contrario en modo datos 2
        btnModo.setText((modo_datos == 1)? R.string.cambiar_a_ws: R.string.cambiar_a_sqlite);
        //Ejecutamos el metodo para llenar la lista.
        llenarLista();
    }

    public void llenarLista(){
        //Llamamos a nuestro asynctask para poder realizar la consulta.
        new LlenarLista(this).execute();
    }

    public void limpiarTexto(View v){
        spAutor.setSelection(0);
    }

    public void eliminarAutor(View v){
        String mensaje = "";
        try{
            int posicionEliminar = spAutor.getSelectedItemPosition();
            if(posicionEliminar > 0){
                Autor aEliminar = autores.get(posicionEliminar - 1);
                if(modo_datos == 1){
                    int filasAfectadas = helper.autorDao().eliminarAutor(aEliminar);
                    if(filasAfectadas<=0){
                        mensaje = "Error al tratar de eliminar el registro.";
                    }
                    else{
                        mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                    }
                }
                else{

                    JSONObject elementoEliminar = new JSONObject();
                    elementoEliminar.put("idAutor", Integer.toString(aEliminar.idAutor));

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("elementoEliminar", elementoEliminar.toString()));
                    String respuesta = ControlWS.post(urlEliminarAutor, params, this);
                    JSONObject resultado = new JSONObject(respuesta);
                    if(resultado.length() != 0){
                        if(resultado.getInt("resultado")==1)
                            mensaje = "Eliminado del WS con exito";
                        else
                            mensaje = "Error al tratar de eliminar el registro.";
                    }
                }
            } else{
                mensaje = "Tiene que seleccionar un elemento a eliminar.";
            }
        }catch (SQLiteConstraintException | JSONException e){
            mensaje = "Error al tratar de eliminar el registro.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
        llenarLista();
    }
    public class LlenarLista extends AsyncTask<String, String, String> {
        Context ctx;
        public LlenarLista(Context ctx){
            this.ctx = ctx;
        }
        ArrayList<String> nomAutores = new ArrayList<>();
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
                    autores = helper.autorDao().obtenerAutores();
                    break;
                }
                //Caso 2: Modo WebService
                case 2:{
                    //Primer paso del WS: Traemos el json que nos devuelve el WS
                    String jsonDocumentos = ControlWS.get(urlListado, ctx);
                    autores = ControlWS.obtenerListaAutor(jsonDocumentos, ctx);
                    break;
                }
            }
            //Despues de tener los datos tanto por sqlite o ws, hacemos la lista con titulos
            //de los documentos.

            nomAutores.add("** Selecciona un Autor **");
            for(Autor autor: autores){
                nomAutores.add(autor.nomAutor + " " + autor.apeAutor);
            }
            return null;
        }
        //paso 3: despues de la consulta
        @Override
        protected void onPostExecute(String result) {
            //Le asignamos un array adapter al listview, llena la lista con los documentos.
            spAutor.setAdapter(new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, nomAutores));
            //Activamos el boton otra vez para que pueda cambiar el modo.
            btnModo.setEnabled(true);
        }
    }
}