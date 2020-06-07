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

public class MotivoActualizarActivity extends AppCompatActivity {
    ControlBD helper;

    @BindView(R.id.edtIDConsultaMotivo)
    EditText edtIDConsultaMotivo;
    @BindView(R.id.edtNombreConsultaMotivo)
    EditText edtNombreConsultaMotivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_actualizar);
        helper = ControlBD.getInstance(this);
        ButterKnife.bind(this);
    }

    public void actualizarMotivo(View v){
        String mensaje = "";
        try {
            Motivo motivo = new Motivo();
            motivo.idMotivo = Integer.parseInt(edtIDConsultaMotivo.getText().toString());
            motivo.nomMotivo = edtNombreConsultaMotivo.getText().toString();


            int filasAfectadas = helper.motivoDao().actualizarMotivo(motivo);
            if(filasAfectadas<=0){
                mensaje = "Error al tratar de actualizar el registro.";
            }
            else{
                mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                edtIDConsultaMotivo.setText("");
                edtNombreConsultaMotivo.setText("");
            }
        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        }
        catch (NumberFormatException e){
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally{
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();

        }

    }
}
