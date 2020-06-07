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

public class DetalleAutorEliminarActivity extends AppCompatActivity {
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
                int filasAfectadas = helper.detalleAutorDao().eliminarDetalleAutor(aEliminar);
                if(filasAfectadas<=0){
                    mensaje = "Error al tratar de eliminar el registro.";
                }
                else{
                    mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                }
            }
            else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }

        }catch (SQLiteConstraintException e){
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