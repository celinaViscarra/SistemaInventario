package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.ControlWS;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Autor;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class AutorInsertarActivity extends AppCompatActivity {

    int modo_datos = 1;
    //URL de nuestra peticion.
    private final String urlAutor = "https://eisi.fia.ues.edu.sv/eisi13/inventariows/autor/insertar.php";

    @BindView(R.id.btnModo)
    Button btnModo;
    @BindViews({
            R.id.txt_autor_nombre,
            R.id.txt_autor_apellido,
    })
    List<EditText> editFieldList;

    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_insertar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //Metodo para cambiar modo
    public void cambiarModo(View v){
        if (modo_datos == 1) modo_datos = 2;
        else modo_datos = 1;
        btnModo.setText((modo_datos == 1)? R.string.cambiar_a_ws: R.string.cambiar_a_sqlite);
        limpiar(v);
    }
    public void insertarAutor(View v){
        String mensaje = "";
        try{
            boolean textosVacios = false;
            for(EditText pivote: editFieldList) textosVacios = pivote.getText().toString().isEmpty() || textosVacios;
            if(!textosVacios){
                Autor autor = new Autor();
                autor.nomAutor = editFieldList.get(0).getText().toString();
                autor.apeAutor = editFieldList.get(1).getText().toString();

                if(modo_datos == 1){
                    long posicion = helper.autorDao().insertarAutor(autor);
                    if(posicion == 0 || posicion == -1){
                        mensaje = getResources().getString(R.string.error_ingresar);
                    }
                    else{
                        mensaje = getResources().getString(R.string.exito_ingresar) + " " + posicion;
                    }
                } else{
                    JSONObject elementoInsertar = new JSONObject();
                    elementoInsertar.put("nomAutor", autor.nomAutor);
                    elementoInsertar.put("apeAutor", autor.apeAutor);

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("elementoInsertar", elementoInsertar.toString()));

                    String respuesta = ControlWS.post(urlAutor, params, this);
                    JSONObject resp = new JSONObject(respuesta);
                    int resultado = resp.getInt("resultado");
                    if(resultado == 1){
                        mensaje = getResources().getString(R.string.exito_ingresar_ws);
                    }else{
                        mensaje = getResources().getString(R.string.error_ingresar_ws);
                    }
                }
            }else if(textosVacios) {
                mensaje = getResources().getString(R.string.campos_vacios);
            } else{
                mensaje = "Seleccione una opcion por favor.";
            }
        }catch (SQLiteConstraintException e){
            mensaje = getResources().getString(R.string.error_ingresar);;
        } catch (JSONException e) {
            mensaje = "Error de parseo JSON";
        } finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(View v){
        for(EditText pivote: editFieldList) pivote.setText("");
    }
}