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

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Autor;
import com.grupo13.inventario.modelo.DetalleAutor;
import com.grupo13.inventario.modelo.Documento;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleAutorConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    //Usando butterknife no es necesario hacer findViewById
    @BindView(R.id.edtEscritoID)
    Spinner edtEscritoID;
    @BindView(R.id.edtAutorID)
    Spinner edtAutorID;
    @BindView(R.id.edtEsPrincipal)
    CheckBox edtEsPrincipal;

    List<Documento> documentos;
    List<Autor> autores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_autor_consultar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
        llenarSpinners();
    }
    public void consultarDetalleAutor(View v){

        String mensaje = "";
        try {
            DetalleAutor consulta;

            int posDoc = edtEscritoID.getSelectedItemPosition();
            int posAutor = edtAutorID.getSelectedItemPosition();
            if(posAutor > 0 && posDoc > 0){
                int idAutor = autores.get(posAutor - 1).idAutor;
                int escrito_id = documentos.get(posDoc - 1).idEscrito;
                consulta = helper.detalleAutorDao().consultarDetalle(idAutor, escrito_id);
                if(consulta == null){
                    mensaje = "No se encontraron datos";
                }
                else{
                    mensaje = "Se encontro el registro, mostrando datos...";
                    //En el caso de esta Clase (DetalleAutor) el unico dato que no es llave primaria
                    //Es "esPrincipal", entonces mostramos el resultado de la consulta.
                    edtEsPrincipal.setChecked(consulta.esPrincipal);
                }
            }
            else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }
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