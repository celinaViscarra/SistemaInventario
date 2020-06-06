package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
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
        String mensaje = "";
        try {
            DetalleAutor nuevo = new DetalleAutor();
            nuevo.escrito_id = Integer.parseInt(edtEscritoID.getText().toString());
            nuevo.idAutor = Integer.parseInt(edtAutorID.getText().toString());
            nuevo.esPrincipal = edtEspPrincipal.isChecked();

            long idDetalle = helper.detalleAutorDao().insertarDetalleAutor(nuevo);
            if(idDetalle == 0 || idDetalle == -1){
                mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
            }
            else{
                mensaje = String.format("DetalleAutor registrado con ID %d",idDetalle);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}