package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.EquipoInformatico;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipoInformaticoEliminarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.txt_equipo_id)
    EditText txtEquipoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_informatico_eliminar);

        ButterKnife.bind(this);

        helper = ControlBD.getInstance(this);
    }

    public void eliminarEquipoInformatico(View v){
        String mensaje = "";
        try {
            EquipoInformatico aEliminar = new EquipoInformatico();
            aEliminar.idEquipo = Integer.parseInt(txtEquipoId.getText().toString());

            int filasAfectadas = helper.equipoInformaticoDao().eliminarEquipoInformatico(aEliminar);
            if(filasAfectadas <= 0){
                mensaje = "Error al tratar de eliminar el Equipo Informatico. No existe";
            }
            else{
                mensaje = String.format("Filas afectadas: %d", filasAfectadas);
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de eliminar el registro.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}