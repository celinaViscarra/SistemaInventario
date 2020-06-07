package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.TipoProducto;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoProductoEliminarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtTipoProductoID)
    EditText edtTipoProductoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_producto_eliminar);

        ButterKnife.bind(this);
    }

    public void eliminarTipoProducto(View v){
        String mensaje = "";
        try {
            {
                TipoProducto tpEliminar = new TipoProducto();
                tpEliminar.idTipoProducto = Integer.parseInt(edtTipoProductoID.getText().toString());

                int filaAfectada = helper.tipoProductoDao().eliminarTipoProducto(tpEliminar);
                if (filaAfectada<=0){
                    mensaje = "Error al tratar de eliminar el registro.";
                }
                else {
                    mensaje = String.format("Fila afectada: %d",filaAfectada);
                }
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de eliminar el registro.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor el dato ingresado.";
        }
        finally {
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}