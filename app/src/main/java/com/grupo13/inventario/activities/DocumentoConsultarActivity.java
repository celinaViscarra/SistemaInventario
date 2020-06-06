package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
            R.id.edtEscritoID,
            R.id.edtISBN,
            R.id.edtEdicion,
            R.id.edtEditorial,
            R.id.edtTitulo
    })
    List<EditText> editTextList;

    @BindViews({
            R.id.edtTipoProductoID,
            R.id.edtIdiomaID
    })
    List<Spinner> spinners;

    List<Idiomas> idiomas;
    List<TipoProducto> tiposProducto;

    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }

    public void llenarSpinners(){
        idiomas = helper.idiomasDao().obtenerIdiomas();
        tiposProducto = helper.tipoProductoDao().obtenerTipos();

        ArrayList<String> nomIdiomas = new ArrayList<>();
        ArrayList<String> nomTiposProducto = new ArrayList<>();
        for(Idiomas idioma: idiomas){
            nomIdiomas.add(idioma.nombreIdioma);
        }
        for(TipoProducto tipoProducto: tiposProducto){
            nomTiposProducto.add(tipoProducto.nomTipoProducto);
        }
        ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomIdiomas);
        ArrayAdapter<String> adapterTiposProducto = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomTiposProducto);
        spinners.get(0).setEnabled(false);
        spinners.get(0).setClickable(false);
        spinners.get(1).setEnabled(false);
        spinners.get(1).setClickable(false);
        spinners.get(0).setAdapter(adapterTiposProducto);
        spinners.get(1).setAdapter(adapterIdiomas);
    }

    public void consultarDocumento(View v){
        String mensaje = "";
        try{
            int idEscrito = Integer.parseInt(editTextList.get(0).getText().toString());
            Documento docConsultado = helper.documentoDao().consultarDocumento(idEscrito);
            if(docConsultado == null){
                mensaje = "No se encontraron datos";

            } else{
                mensaje = "Se encontro el registro, mostrando datos...";
                editTextList.get(1).setText(docConsultado.isbn);
                editTextList.get(2).setText(docConsultado.edicion);
                editTextList.get(3).setText(docConsultado.editorial);
                editTextList.get(4).setText(docConsultado.titulo);

                int pos = -1;
                for(TipoProducto tipoProducto: tiposProducto){
                    if(tipoProducto.idTipoProducto == docConsultado.tipo_producto_id){
                        pos = tiposProducto.indexOf(tipoProducto);
                    }
                }
                spinners.get(0).setSelection(pos);
                for(Idiomas idioma: idiomas){
                    if(idioma.idIdioma == docConsultado.idioma_id){
                        pos = idiomas.indexOf(idioma);
                    }
                }
                spinners.get(1).setSelection(pos);
            }
        }
        catch (SQLiteConstraintException e){
            mensaje = "Error en la base de datos.";
        }
        catch (NumberFormatException e) {
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally {

        }
    }
}