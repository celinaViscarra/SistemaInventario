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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class AutorActualizarActivity extends AppCompatActivity {
    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlAutorActualizar = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/autor/actualizar.php";
    private final String urlAutorListar = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/autor/obtenerlista.php";
    @BindView(R.id.btnModo)
    Button btnModo;
    @BindViews({
            R.id.txt_autor_nombre,
            R.id.txt_autor_apellido,
    })
    List<EditText> editTextList;

    @BindViews({
            R.id.sp_autor_id,
    })
    List<Spinner> spinners;

    List<Autor> autores;

    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_actualizar);
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
        btnModo.setText((modo_datos == 1)? R.string.cambiar_a_ws: R.string.cambiar_a_sqlite);

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

    public void actualizarAutor(View v){
        String mensaje = "";
        try{
            int posAutor = spinners.get(0).getSelectedItemPosition();
            boolean textosVacios = false;
            for(EditText pivote: editTextList){
                textosVacios = pivote.getText().toString().isEmpty() || textosVacios;
            }
            if(posAutor > 0 && !textosVacios){
                Autor autor = new Autor();
                autor.idAutor = autores.get(posAutor - 1).idAutor;
                autor.nomAutor = editTextList.get(0).getText().toString();
                autor.apeAutor = editTextList.get(1).getText().toString();

                if(modo_datos == 1){
                    int filasAfectadas = helper.autorDao().actualizarAutor(autor);
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
                    elementoActualizar.put("idAutor", autor.idAutor);
                    elementoActualizar.put("nomAutor", autor.nomAutor);
                    elementoActualizar.put("apeAutor", autor.apeAutor);

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("elementoActualizar", elementoActualizar.toString()));

                    String respuesta = ControlWS.post(urlAutorActualizar, params, this);
                    JSONObject resp = new JSONObject(respuesta);

                    int resultado = resp.getInt("resultado");
                    if(resultado == 1){
                        mensaje = "Actualizado con exito.";
                        llenarSpinners();
                    }else if(resultado == 0){
                        mensaje = "No se pudo actualizar el dato.";
                    }
                }
            } else if(textosVacios){
                mensaje = getResources().getString(R.string.campos_vacios);
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
        ArrayList<String> nomAutores = new ArrayList<>();
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
                    String jsonAutores = ControlWS.get(urlAutorListar, ctx);

                    autores = ControlWS.obtenerListaAutor(jsonAutores, ctx);
                    break;
                }
            }

            nomAutores.add("** Selecciona un autor **");
            for(Autor pivote: autores) nomAutores.add(pivote.nomAutor + " " + pivote.apeAutor);
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            ArrayAdapter<String> adapterAutores = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nomAutores);

            spinners.get(0).setAdapter(adapterAutores);

            btnModo.setEnabled(true);
        }
    }
}