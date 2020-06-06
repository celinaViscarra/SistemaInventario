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

public class DetalleAutorActualizarActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_detalle_autor_actualizar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }

    public void actualizarDetalleAutor(View v){
        String mensaje = "";
        try {
            DetalleAutor detalleAutor = new DetalleAutor();
            detalleAutor.escrito_id = Integer.parseInt(edtEscritoID.getText().toString());
            detalleAutor.idAutor = Integer.parseInt(edtAutorID.getText().toString());
            detalleAutor.esPrincipal = edtEspPrincipal.isChecked();

            int filasAfectadas = helper.detalleAutorDao().actualizarDetalleAutor(detalleAutor);
            if(filasAfectadas<=0){
                mensaje = "Error al tratar de actualizar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }

    }
}