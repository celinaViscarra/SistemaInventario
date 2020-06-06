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
        setContentView(R.layout.activity_documento_actualizar);
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
        spinners.get(0).setAdapter(adapterTiposProducto);
        spinners.get(1).setAdapter(adapterIdiomas);
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
            Documento doc = new Documento();
            doc.idEscrito = Integer.parseInt(editTextList.get(0).getText().toString());
            doc.tipo_producto_id = tiposProducto.get(spinners.get(0).getSelectedItemPosition()).idTipoProducto;
            doc.idioma_id = idiomas.get(spinners.get(1).getSelectedItemPosition()).idIdioma;
            doc.isbn = editTextList.get(1).getText().toString();
            doc.edicion = editTextList.get(2).getText().toString();
            doc.editorial = editTextList.get(3).getText().toString();
            doc.titulo = editTextList.get(4).getText().toString();

            int filasAfectadas = helper.documentoDao().actualizarDocumento(doc);
            if(filasAfectadas <= 0){
                mensaje = "Error al tratar de actualizar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
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