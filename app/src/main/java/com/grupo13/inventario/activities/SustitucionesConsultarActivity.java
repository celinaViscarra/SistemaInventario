package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Docente;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.Motivo;
import com.grupo13.inventario.modelo.Sustituciones;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class SustitucionesConsultarActivity extends AppCompatActivity {
    @BindViews({
            R.id.edtMotivoIDSustitucionConsulta,
            R.id.edtEquipObsSustitucionConsulta,
            R.id.edtEquipReesSustitucionConsulta,
            R.id.edtDocenteIDSustitucionConsulta
    })
    List<Spinner> spinners;

    @BindView(R.id.edtIDSustitucionConsulta)
    EditText edtIDSustitucionConsulta;


    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustituciones_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);

        spinners.get(0).setEnabled(false);
        spinners.get(1).setEnabled(false);
        spinners.get(2).setEnabled(false);
        spinners.get(3).setEnabled(false);
    }

    public void consultarSustitucion(View v){
        String mensaje = "";
        try{
            int idSustitucion = Integer.parseInt(edtIDSustitucionConsulta.getText().toString());
            Sustituciones susConsultado = helper.sustitucionesDao().consultarSustituciones(idSustitucion);
            if(susConsultado == null){
                spinners.get(0).setAdapter(null);
                spinners.get(1).setAdapter(null);
                spinners.get(2).setAdapter(null);
                spinners.get(3).setAdapter(null);
                mensaje = "No se encontraro ID ingresado";

            } else{
                mensaje = "Se encontro el registro, mostrando datos...";
                ArrayList<String> nomMotivos = new ArrayList<>();
                ArrayList<String> nomEquiposInfoObs = new ArrayList<>();
                ArrayList<String> nomEquiposInfoRees = new ArrayList<>();
                ArrayList<String> nomDocentes = new ArrayList<>();

                Motivo motivoconsulta = helper.motivoDao().consultarMotivo(susConsultado.idMotivo);
                EquipoInformatico equipobsoletoconsulta = helper.equipoInformaticoDao().consultarEquipoInformatico(susConsultado.idEquipoObsoleto);
                EquipoInformatico equiporeesconsulta = helper.equipoInformaticoDao().consultarEquipoInformatico(susConsultado.idEquipoReemplazo);
                Docente docenteconsulta = helper.docenteDao().consultarDocente(susConsultado.idDocentes);


                nomMotivos.add(motivoconsulta.nomMotivo);
                nomEquiposInfoObs.add("ID: "+equipobsoletoconsulta.codEquipo+" Estado: "+equipobsoletoconsulta.estadoEquipo);
                nomEquiposInfoRees.add("ID: "+equiporeesconsulta.codEquipo+" Estado: "+equiporeesconsulta.estadoEquipo);
                nomDocentes.add(docenteconsulta.nomDocente);

                ArrayAdapter<String> adapterMotivo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomMotivos);
                ArrayAdapter<String> adapterEquiposInfoObs = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomEquiposInfoObs);
                ArrayAdapter<String> adapterEquiposInfoRees = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomEquiposInfoRees);
                ArrayAdapter<String> adapterDocente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomDocentes);

                spinners.get(0).setAdapter(adapterMotivo);
                spinners.get(1).setAdapter(adapterEquiposInfoObs);
                spinners.get(2).setAdapter(adapterEquiposInfoRees);
                spinners.get(3).setAdapter(adapterDocente);

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
