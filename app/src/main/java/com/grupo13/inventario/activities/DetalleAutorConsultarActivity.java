package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.DetalleAutor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleAutorConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    //Usando butterknife no es necesario hacer findViewById
    @BindView(R.id.edtEscritoID)
    EditText edtEscritoID;
    @BindView(R.id.edtAutorID)
    EditText edtAutorID;
    @BindView(R.id.edtEsPrincipal)
    CheckBox edtEsPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_autor_consultar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }
    public void consultarDetalleAutor(View v){

        String mensaje = "";
        try {
            DetalleAutor consulta;
            int escrito_id = Integer.parseInt(edtEscritoID.getText().toString());
            int idAutor = Integer.parseInt(edtAutorID.getText().toString());
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
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}