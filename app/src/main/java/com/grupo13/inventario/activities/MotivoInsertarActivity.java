package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Motivo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MotivoInsertarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtMotivo)
    EditText edtMotivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_insertar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }
    public void insertarMotivo(View v){
        String mensaje = "";
        try {
            Motivo nuevo = new Motivo();
            nuevo.nomMotivo = edtMotivo.getText().toString();

            if (!nuevo.nomMotivo.isEmpty()){
                long idDetalle = helper.motivoDao().insertarMotivo(nuevo);
                if(idDetalle == 0 || idDetalle == -1){
                    mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
                }
                else{
                    mensaje = String.format("Motivo registrado con ID %d",idDetalle);
                }
            }
            else {
                mensaje="Complete el campo vacio";
            }


        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            edtMotivo.setText("");
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}
