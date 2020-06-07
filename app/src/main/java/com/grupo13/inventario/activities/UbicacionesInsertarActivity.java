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

public class UbicacionesInsertarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtUbicacionID)
    EditText edtUbicacionID;
    @BindView(R.id.edtNombreUbicacion)
    EditText edtNombreUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones_insertar);
        helper = ControlBD.getInstance(this);

        ButterKnife.bind(this);
    }

    public void insertarUbicaciones(View v){
        String mensaje = "";
        try {
            Ubicaciones ubicaciones = new Ubicaciones();
            ubicaciones.idUbicacion = Integer.parseInt(edtUbicacionID.getText().toString());
            ubicaciones.nomUbicacion = edtNombreUbicacion.getText().toString();

            long idUbicacion = helper.ubicacionesDao().insertarUbicaciones(ubicaciones);
            if (idUbicacion == 0 || idUbicacion == -1){
                mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
            }
            else {
                mensaje = String.format("Ubicacion registrado con ID %d",idUbicacion);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally {
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}