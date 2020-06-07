package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.TipoProducto;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoProductoConsultarActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_tipo_producto_consultar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }
    public void consultarTipoProducto(View v){
        String mensaje = "";
        try {
            TipoProducto consulta;
            int tipoProducto_id = Integer.parseInt(edtTipoProductoID.getText().toString());
            consulta = helper.tipoProductoDao().consultarTipoProducto(tipoProducto_id);
            if (consulta == null){
                mensaje = "No se encontraron datos";
            }
            else{
                mensaje = "Se encontro el registro, mostrando datos...";
                edtCategoriaID.setText(consulta.categoria_id);
                edtNombreProducto.setText(consulta.nomTipoProducto);
            }
        } catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor el dato ingresado.";
        }
        finally {
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}