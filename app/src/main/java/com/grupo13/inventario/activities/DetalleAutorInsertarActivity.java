package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
//Importante!!!
import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.DetalleAutor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleAutorInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    //Usando butterknife no es necesario hacer findViewById
    @BindView(R.id.edtEscritoID)
    EditText edtEscritoID;
    @BindView(R.id.edtAutorID)
    EditText edtAutorID;
    @BindView(R.id.edtEsPrincipal)
    CheckBox edtEspPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_autor_insertar);
        helper = ControlBD.getInstance(this);

        //IMPORTANTE SI NO NO SE PUEDE USAR BUTTERKNIFE!!!
        ButterKnife.bind(this);
    }

    public void insertarDetalle(View v){
        DetalleAutor nuevo = new DetalleAutor();
        nuevo.escrito_id = Integer.parseInt(edtEscritoID.getText().toString());
        nuevo.idAutor = Integer.parseInt(edtAutorID.getText().toString());
        nuevo.esPrincipal = edtEspPrincipal.isChecked();

        long idDetalle = helper.detalleAutorDao().insertarDetalleAutor(nuevo);

        String mensaje = "";
        if(idDetalle == 0 || idDetalle == -1){
            mensaje = "El DetalleAutor no se pudo registrar con exito";
        }
        else{
            mensaje = String.format("DetalleAutor registrado con ID %d",idDetalle);
        }
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}