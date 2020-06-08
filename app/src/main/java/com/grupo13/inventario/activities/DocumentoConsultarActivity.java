package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.Idiomas;
import com.grupo13.inventario.modelo.TipoProducto;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class DocumentoConsultarActivity extends AppCompatActivity {
    @BindViews({
            R.id.edtISBN,
            R.id.edtEdicion,
            R.id.edtEditorial,
            R.id.edtTitulo
    })
    List<EditText> editTextList;

    @BindViews({
            R.id.edtTipoProductoID,
            R.id.edtIdiomaID,
            R.id.edtEscritoID
    })
    List<Spinner> spinners;

    List<Idiomas> idiomas;
    List<TipoProducto> tiposProducto;
    List<Documento> documentos;

    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }

    public void limpiarTexto(View v){
        for(Spinner pivote: spinners) pivote.setSelection(0);
        for(EditText pivote: editTextList) pivote.setText("");
    }

    public void llenarSpinners(){
        documentos = helper.documentoDao().obtenerDocumentos();
        idiomas = helper.idiomasDao().obtenerIdiomas();
        tiposProducto = helper.tipoProductoDao().obtenerTipos();

        ArrayList<String> nomIdiomas = new ArrayList<>();
        nomIdiomas.add("Ningun elemento seleccionado");
        ArrayList<String> nomTiposProducto = new ArrayList<>();
        nomTiposProducto.add("Ningun elemento seleccionado");
        ArrayList<String> nomDocumentos = new ArrayList<>();
        nomDocumentos.add("Ningun elemento seleccionado");

        for(Documento documento: documentos) nomDocumentos.add(documento.titulo);
        for(Idiomas idioma: idiomas) nomIdiomas.add(idioma.nombreIdioma);
        for(TipoProducto tipoProducto: tiposProducto) nomTiposProducto.add(tipoProducto.nomTipoProducto);

        ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomIdiomas);
        ArrayAdapter<String> adapterTiposProducto = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomTiposProducto);
        ArrayAdapter<String> adapterDocumentos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomDocumentos);

        spinners.get(0).setEnabled(false);
        spinners.get(0).setClickable(false);
        spinners.get(1).setEnabled(false);
        spinners.get(1).setClickable(false);
        spinners.get(0).setAdapter(adapterTiposProducto);
        spinners.get(1).setAdapter(adapterIdiomas);
        spinners.get(2).setAdapter(adapterDocumentos);
    }

    public void consultarDocumento(View v){
        String mensaje = "";
        try{
            int posSeleccionada = spinners.get(2).getSelectedItemPosition();
            if(posSeleccionada > 0){
                int idEscrito = documentos.get(posSeleccionada - 1).idEscrito;
                Documento docConsultado = helper.documentoDao().consultarDocumento(idEscrito);
                if(docConsultado == null){
                    mensaje = "No se encontraron datos";

                } else{
                    mensaje = "Se encontro el registro, mostrando datos...";
                    editTextList.get(0).setText(docConsultado.isbn);
                    editTextList.get(1).setText(docConsultado.edicion);
                    editTextList.get(2).setText(docConsultado.editorial);
                    editTextList.get(3).setText(docConsultado.titulo);

                    int pos = -1;
                    for(TipoProducto tipoProducto: tiposProducto){
                        if(tipoProducto.idTipoProducto == docConsultado.tipo_producto_id){
                            pos = tiposProducto.indexOf(tipoProducto);
                        }
                    }
                    spinners.get(0).setSelection(pos+1);
                    for(Idiomas idioma: idiomas){
                        if(idioma.idIdioma == docConsultado.idioma_id){
                            pos = idiomas.indexOf(idioma);
                        }
                    }
                    spinners.get(1).setSelection(pos+1);
                }

            }
            else{
                mensaje = "No se ha seleccionado ningun documento a consultar.";
            }
        }
        catch (SQLiteConstraintException e){
            mensaje = "Error en la base de datos.";
        }
        catch (NumberFormatException e) {
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }
}