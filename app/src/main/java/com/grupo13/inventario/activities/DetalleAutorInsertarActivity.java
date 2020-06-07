package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
//Importante!!!
import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Autor;
import com.grupo13.inventario.modelo.DetalleAutor;
import com.grupo13.inventario.modelo.Documento;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleAutorInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    //Usando butterknife no es necesario hacer findViewById
    @BindView(R.id.edtEscritoID)
    Spinner edtEscritoID;
    @BindView(R.id.edtAutorID)
    Spinner edtAutorID;
    @BindView(R.id.edtEsPrincipal)
    CheckBox edtEspPrincipal;

    List<Documento> documentos;
    List<Autor> autores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_autor_insertar);
        helper = ControlBD.getInstance(this);

        //IMPORTANTE SI NO NO SE PUEDE USAR BUTTERKNIFE!!!
        ButterKnife.bind(this);
        llenarSpinners();
    }

    public void insertarDetalleAutor(View v){
        String mensaje = "";
        try {
            DetalleAutor nuevo = new DetalleAutor();
            int posDoc = edtEscritoID.getSelectedItemPosition();
            int posAutor = edtAutorID.getSelectedItemPosition();
            if(posAutor > 0 && posDoc > 0){
                nuevo.idAutor = autores.get(posAutor - 1).idAutor;
                nuevo.escrito_id = documentos.get(posDoc - 1).idEscrito;
                nuevo.esPrincipal = edtEspPrincipal.isChecked();

                long idDetalle = helper.detalleAutorDao().insertarDetalleAutor(nuevo);
                if(idDetalle == 0 || idDetalle == -1){
                    mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
                }
                else{
                    mensaje = String.format("DetalleAutor registrado con ID %d",idDetalle);
                }
            }
            else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public void llenarSpinners(){
        documentos = helper.documentoDao().obtenerDocumentos();
        autores = helper.autorDao().obtenerAutores();

        ArrayList<String> nombreDocumentos = new ArrayList<>();
        ArrayList<String> nombreAutores = new ArrayList<>();
        nombreDocumentos.add("**Seleccione un documento**");
        nombreAutores.add("**Seleccione un autor**");
        for(Documento documento: documentos){
            nombreDocumentos.add(documento.titulo);
        }
        for(Autor autor: autores){
            nombreAutores.add(String.format("%s %s",autor.nomAutor,autor.apeAutor));
        }

        ArrayAdapter<String> docAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombreDocumentos);
        ArrayAdapter<String> autorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombreAutores);

        edtEscritoID.setAdapter(docAdapter);
        edtAutorID.setAdapter(autorAdapter);
    }
}