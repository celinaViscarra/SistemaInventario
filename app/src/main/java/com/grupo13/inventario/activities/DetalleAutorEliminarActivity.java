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

public class DetalleAutorEliminarActivity extends AppCompatActivity {
    ControlBD helper;
    //Usando butterknife no es necesario hacer findViewById
    @BindView(R.id.edtEscritoID)
    EditText edtEscritoID;
    @BindView(R.id.edtAutorID)
    EditText edtAutorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_autor_eliminar);

        //USO DE BUTTERKNIFE, SI NO PONEN ESTA LINEA NO SIRVE
        ButterKnife.bind(this);

        helper = ControlBD.getInstance(this);
    }

    public void eliminarDetalleAutor(View v){
        String mensaje = "";
        try {
            DetalleAutor aEliminar = new DetalleAutor();
            aEliminar.escrito_id = Integer.parseInt(edtEscritoID.getText().toString());
            aEliminar.idAutor = Integer.parseInt(edtAutorID.getText().toString());

            int filasAfectadas = helper.detalleAutorDao().eliminarDetalleAutor(aEliminar);
            if(filasAfectadas<=0){
                mensaje = "Error al tratar de eliminar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
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
}