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
import com.grupo13.inventario.modelo.TipoProducto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoProductoListaActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;

    private final String url = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/tipoproducto/obtenerlista.php";
    @BindView(R.id.btnModo)
    Button btnModo;
    List<TipoProducto> tipoProductos;
    ControlBD helper;
    @BindView(R.id.lvTipoProducto)
    ListView lvTipoProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_producto_lista);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        llenarLista();
    }

    public void cambiarModo(View v){
        if (modo_datos == 1) modo_datos = 2;
        else modo_datos = 1;

        btnModo.setText((modo_datos == 1)? R.string.cambiar_a_ws: R.string.cambiar_a_sqlite);

        llenarLista();
    }
    public void llenarLista(){

        new LlenarLista(this).execute();
    }


    public class LlenarLista extends AsyncTask<String, String, String>{
        Context ctx;
        public LlenarLista(Context ctx){
            this.ctx = ctx;
        }
        ArrayList<String> nomTipoProducto = new ArrayList<>();

        @Override
        protected void onPreExecute(){
            btnModo.setEnabled(false);
        }
        @Override
        protected String doInBackground(String... strings){
            tipoProductos = new ArrayList<>();
            switch (modo_datos){
                case 1:{
                    tipoProductos.addAll(helper.tipoProductoDao().obtenerTipos());
                    break;
                }
                case 2:{

                    String jsonTipoProducto = ControlWS.get(url,ctx);
                    if(!jsonTipoProducto.isEmpty())
                    tipoProductos.addAll(ControlWS.obtenerListaTipoProducto(jsonTipoProducto,ctx));
                    break;
                }
            }
            for (TipoProducto tipoProducto: tipoProductos){
                nomTipoProducto.add(tipoProducto.nomTipoProducto);
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){

            lvTipoProducto.setAdapter(new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1,nomTipoProducto));

            btnModo.setEnabled(true);
        }
    }
}