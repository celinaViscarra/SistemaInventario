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

public class MotivoEliminarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtIDEliminarMotivo)
    EditText edtIDEliminarMotivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_eliminar);
        ButterKnife.bind(this);

        helper = ControlBD.getInstance(this);
    }

    public void eliminarMotivo(View v){
        String mensaje = "";
        try {
            Motivo consulta;
            Motivo aEliminar = new Motivo();
            aEliminar.idMotivo = Integer.parseInt(edtIDEliminarMotivo.getText().toString());
            consulta = helper.motivoDao().consultarMotivo(Integer.parseInt(edtIDEliminarMotivo.getText().toString()));
            int filasAfectadas = helper.motivoDao().eliminarMotivo(aEliminar);
            if(filasAfectadas<=0){
                mensaje = "Error al tratar de eliminar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                mensaje+=" Motivo: "+consulta.nomMotivo;
                edtIDEliminarMotivo.setText("");
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
