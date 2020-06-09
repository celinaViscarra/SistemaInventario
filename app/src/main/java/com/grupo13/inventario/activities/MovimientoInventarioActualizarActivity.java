package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.dialog.DatePickerFragment;
import com.grupo13.inventario.modelo.Docente;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.MovimientoInventario;
import com.grupo13.inventario.modelo.TipoMovimiento;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MovimientoInventarioActualizarActivity extends AppCompatActivity {

    @BindViews({
            R.id.edtTipoMovimientoMovInvActualizar,
            R.id.edtDocenteoMovInvActualizar,
            R.id.edtEquipoInfoMovInvActualizar
    })
    List<Spinner> spinners;

    @BindView(R.id.edtIDMovInvActualizar)
    EditText edtIDMovInvActualizar;

    @BindView(R.id.edtDescripcionMovInvActualizar)
    EditText edtDescripcionMovInvActualizar;

    @BindView(R.id.edtFechaIniMovInvActualizar)
    EditText edtFechaIniMovInvActualizar;

    @BindView(R.id.edtFechaFinMovInvActualizar)
    EditText edtFechaFinMovInvActualizar;

    @BindView(R.id.edtPrestamoPermanenteActualizar)
    CheckBox edtPrestamoPermanenteActualizar;

    @BindView(R.id.edtPrestamoActivoActualizar)
    CheckBox edtPrestamoActivoActualizar;

    List<TipoMovimiento> tipoMovimientos;
    List<Docente> docentes;
    List<EquipoInformatico> equipoInformaticos;

    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_inventario_actualizar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }

    public void actualizarMovimientoInventario(View v){
        String mensaje = "";
        if(spinners.get(0).getSelectedItem()==null||spinners.get(1).getSelectedItem()==null||spinners.get(2).getSelectedItem()==null|| edtIDMovInvActualizar.getText().toString().equals("")
        || edtDescripcionMovInvActualizar.getText().toString().equals("") || edtFechaIniMovInvActualizar.getText().toString().equals("") || edtFechaFinMovInvActualizar.getText().toString().equals("")) {
            Toast.makeText(this, "Complete los campos obligarotios", Toast.LENGTH_SHORT).show();
        }
        else{
            try{
                MovimientoInventario mov = new MovimientoInventario();
                mov.idPresatamo=Integer.parseInt(edtIDMovInvActualizar.getText().toString());
                mov.tipo_movimiento_id = tipoMovimientos.get(spinners.get(0).getSelectedItemPosition()).idTipoMovimiento;
                mov.docentes_id = docentes.get(spinners.get(1).getSelectedItemPosition()).idDocente;
                mov.equipo_id = equipoInformaticos.get(spinners.get(2).getSelectedItemPosition()).idEquipo;
                mov.descripcion=edtDescripcionMovInvActualizar.getText().toString();
                mov.prestamoFechaInicio= Date.valueOf(edtFechaIniMovInvActualizar.getText().toString());
                mov.prestamoFechaFin= Date.valueOf(edtFechaFinMovInvActualizar.getText().toString());
                mov.prestamoPermanente=edtPrestamoPermanenteActualizar.isChecked();
                mov.prestamoActivo=edtPrestamoActivoActualizar.isChecked();

                if (mov.prestamoFechaFin.after(mov.prestamoFechaInicio)){
                int filasAfectadas = helper.movimientoInventarioDao().actualizarMovimientoInventario(mov);
                    if(filasAfectadas <= 0){
                        mensaje = "Error el Registro no existe";
                    }
                    else{
                        mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                        mensaje+=" Registro Actualizado";
                    }
                }
                else {
                    mensaje="La fecha final debe ser mayor a la inicial";
                }

            }catch (SQLiteConstraintException e){
                mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
            }
            catch(NumberFormatException e){
                mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
            }
            finally {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void llenarSpinners(){
        tipoMovimientos=helper.tipoMovimientoDao().obtenerTipoMoviento();
        docentes=helper.docenteDao().obtenerDocentes();
        equipoInformaticos=helper.equipoInformaticoDao().obtenerEquiposInformaticos();

        ArrayList<String> nomtipoMovimientos = new ArrayList<>();
        ArrayList<String> nomDocentes = new ArrayList<>();
        ArrayList<String> nomEquiposInfo = new ArrayList<>();

        for(TipoMovimiento tipo: tipoMovimientos){
            nomtipoMovimientos.add(tipo.nombreTipoMoviento);
        }

        for(Docente docente: docentes){
            nomDocentes.add(docente.nomDocente);
        }

        for(EquipoInformatico equipo: equipoInformaticos){
            nomEquiposInfo.add(equipo.codEquipo);
        }

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomtipoMovimientos);
        ArrayAdapter<String> adapterDocente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomDocentes);
        ArrayAdapter<String> adapterEquipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomEquiposInfo);

        spinners.get(0).setAdapter(adapterTipo);
        spinners.get(1).setAdapter(adapterDocente);
        spinners.get(2).setAdapter(adapterEquipo);
    }
    public void seleccionarFecha1(View v) {
        DatePickerFragment fragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                edtFechaIniMovInvActualizar.setText(selectedDate);
            }
        });
        fragment.show(this.getSupportFragmentManager(), "datePicker");
    }
    public void seleccionarFecha2(View v) {
        DatePickerFragment fragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                edtFechaFinMovInvActualizar.setText(selectedDate);
            }
        });
        fragment.show(this.getSupportFragmentManager(), "datePicker");
    }
}
