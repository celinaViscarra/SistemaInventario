package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.ControlWS;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Documento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentoListaActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/documento/obtenerlista.php";
    @BindView(R.id.btnModo)
    Button btnModo;
    List<Documento> documentos;
    ControlBD helper;
    @BindView(R.id.lvDocumentos)
    ListView lvDocumentos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento_lista);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
        //Importante usar esto si se hacen las consultas del WS sin usar AsyncTask
        //Si se usa asynctask, se puede obviar esto
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Llenamos la lista por primera vez.
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
    //AsyncTask para poder correr en otro hilo la consulta
    //Debido a que la consulta del webservice se traba.
    public class LlenarLista extends AsyncTask<String, String, String>{
        Context ctx;
        public LlenarLista(Context ctx){
            this.ctx = ctx;
        }
        ArrayList<String> nomDocumentos = new ArrayList<>();
        //Primer paso, antes de ejecutar nuestra consulta
        @Override
        protected void onPreExecute() {
            //Desactivamos el boton para que no le den click hasta que termine
            btnModo.setEnabled(false);
        }
        @Override
        protected String doInBackground(String... strings) {
            documentos = new ArrayList<>();
            //Empieza consulta, revisamos en que modo tenemos los datos.
            switch (modo_datos){
                //Caso 1: Modo SQLite
                case 1:{
                    documentos.addAll(helper.documentoDao().obtenerDocumentos());
                    break;
                }
                //Caso 2: Modo WebService
                case 2:{
                    //Primer paso del WS: Traemos el json que nos devuelve el WS
                    String jsonDocumentos = ControlWS.get(url, ctx);
                    //Segundo paso: convertimos a una lista el json, finalmente tenemos
                    //los documentos que estaban guardados en el WS.
                    if(!jsonDocumentos.equals(""))
                        documentos.addAll(ControlWS.obtenerListaDocumento(jsonDocumentos, ctx));
                    break;
                }
            }
            //Despues de tener los datos tanto por sqlite o ws, hacemos la lista con titulos
            //de los documentos.
            for(Documento documento: documentos){
                nomDocumentos.add(documento.titulo);
            }
            return null;
        }
        //paso 3: despues de la consulta
        @Override
        protected void onPostExecute(String result) {
            //Le asignamos un array adapter al listview, llena la lista con los documentos.
            lvDocumentos.setAdapter(new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, nomDocumentos));
            //Activamos el boton otra vez para que pueda cambiar el modo.
            btnModo.setEnabled(true);
        }
    }
}


