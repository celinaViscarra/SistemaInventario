package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Ubicaciones;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UbicacionesEliminarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtUbicacionID)
    EditText edtUbicacionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones_eliminar);

        ButterKnife.bind(this);
    }

    public void eliminarUbicaciones(View v){
        String mensaje = "";
        try {
            Ubicaciones ubiEliminar = new Ubicaciones();
            ubiEliminar.idUbicacion = Integer.parseInt(edtUbicacionID.getText().toString());

            int filaAfectada = helper.ubicacionesDao().eliminarUbicaciones(ubiEliminar);
            if (filaAfectada<=0){
                mensaje = "Error al tratar de eliminar el registro.";
            }
            else{
                mensaje = String.format("Fila afectada: %d",filaAfectada);
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