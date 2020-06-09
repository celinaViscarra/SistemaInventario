package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Docente;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.MovimientoInventario;
import com.grupo13.inventario.modelo.TipoMovimiento;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MovimientoInventarioConsultarActivity extends AppCompatActivity {
    @BindViews({
            R.id.edtTipoMoviIDMovInvConsulta,
            R.id.edtDocenteMovInvConsulta,
            R.id.edtEquipoMovInvConsulta,
    })
    List<Spinner> spinners;

    @BindView(R.id.edtIDMovInvConsulta)
    EditText edtIDMovInvConsulta;

    @BindView(R.id.edtDescripcionMovInvConsultar)
    EditText edtDescripcionMovInvConsultar;

    @BindView(R.id.edtFechaIniMovInvConsultar)
    EditText edtFechaIniMovInvConsultar;

    @BindView(R.id.edtFechaFinMovInvConsultar)
    EditText edtFechaFinMovInvConsultar;

    @BindView(R.id.edtPrestamoPermanenteConsultar)
    CheckBox edtPrestamoPermanenteConsultar;

    @BindView(R.id.edtPrestamoActivoConsultar)
    CheckBox edtPrestamoActivoConsultar;

    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_inventario_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);

        spinners.get(0).setEnabled(false);
        spinners.get(1).setEnabled(false);
        spinners.get(2).setEnabled(false);
    }

    public void consultarMovimientoInventario(View v){
        String mensaje = "";
        try{
            int idMoviminetoInventario = Integer.parseInt(edtIDMovInvConsulta.getText().toString());
            MovimientoInventario movConsultado = helper.movimientoInventarioDao().consultarMovimientoInventario(idMoviminetoInventario);
            if(movConsultado == null){
                spinners.get(0).setAdapter(null);
                spinners.get(1).setAdapter(null);
                spinners.get(2).setAdapter(null);
                edtDescripcionMovInvConsultar.setText("");
                edtFechaIniMovInvConsultar.setText("");
                edtFechaFinMovInvConsultar.setText("");
                edtPrestamoPermanenteConsultar.setChecked(false);
                edtPrestamoActivoConsultar.setChecked(false);
                mensaje = "No se encontro ID ingresado";

            } else{
                mensaje = "Se encontro el registro, mostrando datos...";
                ArrayList<String> nomTipoMovimiento = new ArrayList<>();
                ArrayList<String> nomDocentes = new ArrayList<>();
                ArrayList<String> nomEquiposInfo = new ArrayList<>();

                TipoMovimiento tipomovimientoconsulta = helper.tipoMovimientoDao().consultarTipoMovimiento(movConsultado.tipo_movimiento_id);
                Docente docenteconsulta = helper.docenteDao().consultarDocente(movConsultado.docentes_id);
                EquipoInformatico equipoinfoconsulta = helper.equipoInformaticoDao().consultarEquipoInformatico(movConsultado.equipo_id);

                nomTipoMovimiento.add(tipomovimientoconsulta.nombreTipoMoviento);
                nomDocentes.add(docenteconsulta.nomDocente);
                nomEquiposInfo.add(equipoinfoconsulta.codEquipo);

                ArrayAdapter<String> adapterTipoMovimiento = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomTipoMovimiento);
                ArrayAdapter<String> adapterDocente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomDocentes);
                ArrayAdapter<String> adapterEquiposInfo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomEquiposInfo);

                spinners.get(0).setAdapter(adapterTipoMovimiento);
                spinners.get(1).setAdapter(adapterDocente);
                spinners.get(2).setAdapter(adapterEquiposInfo);
                edtDescripcionMovInvConsultar.setText(movConsultado.descripcion);
                edtFechaIniMovInvConsultar.setText(String.valueOf(movConsultado.prestamoFechaInicio));
                edtFechaFinMovInvConsultar.setText(String.valueOf(movConsultado.prestamoFechaFin));
                edtPrestamoPermanenteConsultar.setChecked(movConsultado.prestamoPermanente);
                edtPrestamoActivoConsultar.setChecked(movConsultado.prestamoActivo);
            }
        }
        catch (SQLiteConstraintException e){
            mensaje = "Error en la base de datos.";
        }
        catch (NumberFormatException e) {
            mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
        }
        finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

        }
    }
}
