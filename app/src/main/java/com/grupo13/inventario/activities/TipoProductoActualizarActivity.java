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

public class TipoProductoActualizarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtTipoProductoID)
    EditText edtTipoProductoID;
    @BindView(R.id.edtCategoriaID)
    EditText edtCategoriaID;
    @BindView(R.id.edtNombreProducto)
    EditText edtNombreProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_producto_actualizar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }

    public void actualizarTipoProducto(View v){
        String mensaje = "";
        try {
            TipoProducto tipoProducto = new TipoProducto();
            tipoProducto.idTipoProducto = Integer.parseInt(edtTipoProductoID.getText().toString());
            tipoProducto.categoria_id = Integer.parseInt(edtCategoriaID.getText().toString());
            tipoProducto.nomTipoProducto = edtNombreProducto.getText().toString();

            int filasAfectadas = helper.tipoProductoDao().actualizarTipoProducto(tipoProducto);
            if (filasAfectadas<=0){
                mensaje = "Error al tratar de actualizar el registro.";
            }
            else {
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisar por favor los datos ingresados.";
        }
        finally {
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}