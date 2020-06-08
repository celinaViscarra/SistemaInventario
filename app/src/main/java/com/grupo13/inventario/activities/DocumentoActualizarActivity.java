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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class DocumentoActualizarActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_documento_actualizar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }


    public void llenarSpinners(){
        idiomas = helper.idiomasDao().obtenerIdiomas();
        tiposProducto = helper.tipoProductoDao().obtenerTipos();
        documentos = helper.documentoDao().obtenerDocumentos();

        ArrayList<String> nomIdiomas = new ArrayList<>();
        ArrayList<String> nomTiposProducto = new ArrayList<>();
        ArrayList<String> nomDocumentos = new ArrayList<>();
        nomIdiomas.add("** Selecciona un idioma **");
        nomTiposProducto.add("** Selecciona un Tipo de Producto **");
        nomDocumentos.add("** Selecciona un Documento **");
        for(Idiomas idioma: idiomas){
            nomIdiomas.add(idioma.nombreIdioma);
        }
        for(TipoProducto tipoProducto: tiposProducto){
            nomTiposProducto.add(tipoProducto.nomTipoProducto);
        }
        for(Documento pivote: documentos) nomDocumentos.add(pivote.titulo);

        ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomIdiomas);
        ArrayAdapter<String> adapterTiposProducto = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomTiposProducto);
        ArrayAdapter<String> adapterDocumentos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomDocumentos);

        spinners.get(0).setAdapter(adapterTiposProducto);
        spinners.get(1).setAdapter(adapterIdiomas);
        spinners.get(2).setAdapter(adapterDocumentos);
    }
    public void limpiarTexto(View v){
        for(Spinner spinner: spinners)
            spinner.setSelection(0);
        for(EditText editText: editTextList)
            editText.setText("");
    }

    public void actualizarDocumento(View v){
        String mensaje = "";
        try{
            int posDocumento = spinners.get(2).getSelectedItemPosition();
            int posIdioma = spinners.get(1).getSelectedItemPosition();
            int posTipoproducto = spinners.get(0).getSelectedItemPosition();
            boolean textosVacios = false;
            for(EditText pivote: editTextList){
                textosVacios = pivote.getText().toString().equals("") || textosVacios;
            }
            if(posDocumento > 0 && posIdioma >0 && posTipoproducto > 0 && !textosVacios){
                Documento doc = new Documento();
                doc.idEscrito = documentos.get(posDocumento - 1).idEscrito;
                doc.tipo_producto_id = tiposProducto.get(posTipoproducto - 1).idTipoProducto;
                doc.idioma_id = idiomas.get(posIdioma - 1).idIdioma;
                doc.isbn = editTextList.get(0).getText().toString();
                doc.edicion = editTextList.get(1).getText().toString();
                doc.editorial = editTextList.get(2).getText().toString();
                doc.titulo = editTextList.get(3).getText().toString();

                int filasAfectadas = helper.documentoDao().actualizarDocumento(doc);
                if(filasAfectadas <= 0){
                    mensaje = "Error al tratar de actualizar el registro.";
                }
                else{
                    mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                }
            } else if(textosVacios){
                mensaje = "Revisar que ningun campo de texto vaya vacío.";
            }
            else{
                mensaje = "Seleccione una opcion por favor.";
            }

        } catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        } catch(NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

    }
}