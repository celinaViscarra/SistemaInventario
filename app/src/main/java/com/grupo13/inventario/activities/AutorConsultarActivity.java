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

public class AutorConsultarActivity extends AppCompatActivity {
    //Modo datos 1 = SQLITE
    //2 = WebService
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlAutorLista = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/autor/obtenerlista.php";
    private final String urlConsulta = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/autor/consultar.php";
    @BindView(R.id.btnModo)
    Button btnModo;
    @BindViews({
            R.id.txt_autor_nombre_c,
            R.id.txt_autor_apellido_c
    })
    List<EditText> editTextList;

    @BindViews({
            R.id.sp_autor_id
    })
    List<Spinner> spinners;

    List<Autor> autores;

    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_consultar);
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

    public void consultarAutor(View v){
        String mensaje = "";
        try{
            int posSeleccionada = spinners.get(0).getSelectedItemPosition();
            if(posSeleccionada > 0){
                int idautor= autores.get(posSeleccionada - 1).idAutor;
                Autor autorConsultado = null;

                if(modo_datos == 1){
                    autorConsultado = helper.autorDao().consultarAutor(idautor);
                }else{
                    JSONObject elementoConsulta = new JSONObject();
                    elementoConsulta.put("idAutor",Integer.toString(idautor));

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("elementoConsulta",elementoConsulta.toString()));
                    String respuesta = ControlWS.post(urlConsulta, params, this);
                    JSONObject documentoConsulta = new JSONArray(respuesta).getJSONObject(0);
                    if(documentoConsulta.length() != 0){
                        autorConsultado = new Autor();
                        autorConsultado.idAutor = documentoConsulta.getInt("IDAUTOR");
                        autorConsultado.nomAutor = documentoConsulta.getString("NOMAUTOR");
                        autorConsultado.apeAutor = documentoConsulta.getString("APEAUTOR");
                    }
                }

                if(autorConsultado == null){
                    mensaje = "No se encontraron datos";

                } else{
                    mensaje = "Se encontro el registro, mostrando datos...";
                    editTextList.get(0).setText(autorConsultado.nomAutor);
                    editTextList.get(1).setText(autorConsultado.apeAutor);
                }
            }
            else{
                mensaje = "No se ha seleccionado ningun Autor a consultar.";
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
        ArrayList<String> nomAutores = new ArrayList<>();
        //Primer paso, antes de ejecutar nuestra consulta
        @Override
        protected void onPreExecute() {
            //Desactivamos el boton para que no le den click hasta que termine
            btnModo.setEnabled(false);
        }
        //Paso 2: la consulta que se hace en otro hilo.
        @Override
        protected String doInBackground(String... strings) {
            autores = new ArrayList<>();
            //Empieza consulta, revisamos en que modo tenemos los datos.
            switch (modo_datos){
                //Caso 1: Modo SQLite
                case 1:{
                    autores.addAll(helper.autorDao().obtenerAutores());
                    break;
                }
                //Caso 2: Modo WebService
                case 2:{
                    String jsonAutores = ControlWS.get(urlAutorLista, ctx);
                    if(!jsonAutores.isEmpty())
                        autores.addAll(ControlWS.obtenerListaAutor(jsonAutores, ctx));
                    break;
                }
            }

            nomAutores.add("** Selecciona un Autor **");

            for(Autor pivote: autores) nomAutores.add(String.valueOf(pivote.idAutor));
            return null;
        }
        //paso 3: despues de la consulta
        @Override
        protected void onPostExecute(String result) {
            ArrayAdapter<String> adapterAutores = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomAutores);
            spinners.get(0).setAdapter(adapterAutores);

            btnModo.setEnabled(true);
        }
    }
}