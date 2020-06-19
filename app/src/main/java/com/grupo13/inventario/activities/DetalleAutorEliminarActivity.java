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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.ControlWS;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Autor;
import com.grupo13.inventario.modelo.DetalleAutor;
import com.grupo13.inventario.modelo.Documento;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleAutorEliminarActivity extends AppCompatActivity {
    int modo_datos = 1;
    private final String urlDocumentos = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/documento/obtenerlista.php";
    private final String urlAutores = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/autor/obtenerlista.php";
    private final String urlDelete = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/detalleautor/eliminar.php";
    @BindView(R.id.btnModo)
    Button btnModo;

    ControlBD helper;
    //Usando butterknife no es necesario hacer findViewById
    @BindView(R.id.edtEscritoID)
    Spinner edtEscritoID;
    @BindView(R.id.edtAutorID)
    Spinner edtAutorID;

    List<Documento> documentos;
    List<Autor> autores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_autor_eliminar);

        //USO DE BUTTERKNIFE, SI NO PONEN ESTA LINEA NO SIRVE
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void eliminarDetalleAutor(View v){
        String mensaje = "";
        try {
            DetalleAutor aEliminar = new DetalleAutor();

            int posDoc = edtEscritoID.getSelectedItemPosition();
            int posAutor = edtAutorID.getSelectedItemPosition();
            if(posAutor > 0 && posDoc > 0){
                aEliminar.idAutor = autores.get(posAutor - 1).idAutor;
                aEliminar.escrito_id = documentos.get(posDoc - 1).idEscrito;
                if(modo_datos == 1){
                    int filasAfectadas = helper.detalleAutorDao().eliminarDetalleAutor(aEliminar);
                    if(filasAfectadas<=0){
                        mensaje = "Error al tratar de eliminar el registro.";
                    }
                    else{
                        mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                    }
                }else{
                    JSONObject elementoEliminar = new JSONObject();
                    elementoEliminar.put("escrito_id",aEliminar.escrito_id);
                    elementoEliminar.put("idAutor",aEliminar.idAutor);

                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("elementoEliminar",elementoEliminar.toString()));

                    String respuesta = ControlWS.post(urlDelete,params,this);
                    JSONObject resp = new JSONObject(respuesta);
                    if(resp.length() != 0){
                        if(resp.getInt("resultado")==1)
                            mensaje = "Eliminado con exito";
                        else
                            mensaje = "No se pudo eliminar el dato.";
                    }

                }
            }
            else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }

        }catch (SQLiteConstraintException | JSONException e){
            mensaje = "Error al tratar de eliminar el registro.";
        }
        //Este catch se usa para la conversion de int a String, verificar que se ponga un
        //entero en la entrada de ID
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void cambiarModo(View v){
        if(modo_datos == 1) modo_datos = 2;
        else modo_datos = 1;

        btnModo.setText((modo_datos == 1) ? R.string.cambiar_a_ws: R.string.cambiar_a_sqlite);
        llenarSpinners();
        limpiar(v);
    }

    public void limpiar(View v){
        edtEscritoID.setSelection(0);
        edtAutorID.setSelection(0);
    }

    public void llenarSpinners(){
        new LlenarLista(this).execute();
    }

    public class LlenarLista extends AsyncTask<String, String, String> {
        Context ctx;

        ArrayList<String> nombreDocumentos = new ArrayList<>();
        ArrayList<String> nombreAutores = new ArrayList<>();

        public LlenarLista(Context ctx){
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute(){
            btnModo.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings){
            documentos = new ArrayList<>();
            autores = new ArrayList<>();
            switch (modo_datos){
                case 1:{
                    documentos.addAll(helper.documentoDao().obtenerDocumentos());
                    autores.addAll(helper.autorDao().obtenerAutores());
                    break;
                }
                case 2:{
                    String jsonDocumentos = ControlWS.get(urlDocumentos, ctx);
                    String jsonAutores = ControlWS.get(urlAutores, ctx);
                    if(!(jsonDocumentos.isEmpty()&&jsonAutores.isEmpty())){
                        documentos = ControlWS.obtenerListaDocumento(jsonDocumentos, ctx);
                        autores = ControlWS.obtenerListaAutor(jsonAutores, ctx);
                    }
                    break;
                }
            }
            nombreDocumentos.add("** Seleccione un documento, por favor **");
            nombreAutores.add("** Seleccione un autor, por favor **");
            for(Documento pivote: documentos) nombreDocumentos.add(pivote.titulo);
            for(Autor pivote: autores) nombreAutores.add(String.format("%s %s", pivote.nomAutor, pivote.apeAutor));
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            ArrayAdapter<String> docAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nombreDocumentos);
            ArrayAdapter<String> autorAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, nombreAutores);

            edtEscritoID.setAdapter(docAdapter);
            edtAutorID.setAdapter(autorAdapter);
            btnModo.setEnabled(true);
        }
    }
}