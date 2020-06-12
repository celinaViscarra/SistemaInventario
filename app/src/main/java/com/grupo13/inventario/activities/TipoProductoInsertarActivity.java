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

public class TipoProductoInsertarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtCategoriaID)
    EditText edtCategoriaID;
    @BindView(R.id.edtNombreProducto)
    EditText edtNombreProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_producto_insertar);
        helper = ControlBD.getInstance(this);

        ButterKnife.bind(this);
    }

    public void insertarTipoProducto(View v){
        String mensaje = "";
        try {
            TipoProducto tp = new TipoProducto();
            tp.categoria_id = Integer.parseInt(edtCategoriaID.getText().toString());
            tp.nomTipoProducto = edtNombreProducto.getText().toString();

            long idTipoProd = helper.tipoProductoDao().insertarTipoProducto(tp);
            if (idTipoProd == 0 || idTipoProd == -1){
                mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
            }
            else{
                mensaje = String.format("TipoProducto registrado con ID %d",idTipoProd);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally {
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}