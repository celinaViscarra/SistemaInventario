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

public class DocumentoInsertarActivity extends AppCompatActivity {
    @BindViews({
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
        setContentView(R.layout.activity_documento_insertar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }

    public void insertarDocumento(View v){
        String mensaje = "";
        try{
            Documento doc = new Documento();
            doc.tipo_producto_id = tiposProducto.get(spinners.get(0).getSelectedItemPosition()).idTipoProducto;
            doc.idioma_id = idiomas.get(spinners.get(1).getSelectedItemPosition()).idIdioma;
            doc.isbn = editTextList.get(0).getText().toString();
            doc.edicion = editTextList.get(1).getText().toString();
            doc.editorial = editTextList.get(2).getText().toString();
            doc.titulo = editTextList.get(3).getText().toString();

            long posicion = helper.documentoDao().insertarDocumento(doc);
            if(posicion == 0 || posicion == -1){
                mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
            }
            else{
                mensaje = String.format("Registrado correctamente en la posicion: %d",posicion);
            }

        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        }
        finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
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
}