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

public class SustitucionesActualizarActivity extends AppCompatActivity {

    @BindViews({
            R.id.edtMotivoSustitucionActualizar,
            R.id.edtEquipObsoletoSustitucionActualizar,
            R.id.edtEquipReemplazoSustitucionActualizar,
            R.id.edtDocenteSustitucionActualizar
    })
    List<Spinner> spinners;


    @BindView(R.id.edtActualizarIDSustitucion)
    EditText edtActualizarIDSustitucion;

    List<Motivo> motivos;
    List<EquipoInformatico> equipoInformaticosObs;
    List<EquipoInformatico> equipoInformaticosRees;
    List<Docente> docentes;

    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustituciones_actualizar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }

    public void actualizarSustitucion(View v){
        String mensaje = "";
        if(spinners.get(0).getSelectedItem()==null||spinners.get(1).getSelectedItem()==null||spinners.get(2).getSelectedItem()==null||spinners.get(3).getSelectedItem()==null){
            Toast.makeText(this, "Complete los campos obligarotios", Toast.LENGTH_SHORT).show();
        }
        else{
            try{
                Sustituciones sus = new Sustituciones();
                sus.idSustituciones=Integer.parseInt(edtActualizarIDSustitucion.getText().toString());
                sus.idMotivo = motivos.get(spinners.get(0).getSelectedItemPosition()).idMotivo;
                sus.idEquipoObsoleto = equipoInformaticosObs.get(spinners.get(1).getSelectedItemPosition()).idEquipo;
                sus.idEquipoReemplazo = equipoInformaticosRees.get(spinners.get(2).getSelectedItemPosition()).idEquipo;
                sus.idDocentes = docentes.get(spinners.get(3).getSelectedItemPosition()).idDocente;

                int filasAfectadas = helper.sustitucionesDao().actualizarSustituciones(sus);
                if(filasAfectadas <= 0){
                    mensaje = "Error al tratar de actualizar el registro.";
                }
                else{
                    mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                    mensaje+=" Registro Actualizado";
                }

            } catch (SQLiteConstraintException e){
                mensaje = "Error al tratar de actualizar el registro.";
            } catch(NumberFormatException e){
                mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
            }
            finally {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void llenarSpinners(){
        motivos = helper.motivoDao().obtenerMotivos();
        equipoInformaticosObs = helper.equipoInformaticoDao().obtenerEquiposInformaticos();
        equipoInformaticosRees = helper.equipoInformaticoDao().obtenerEquiposInformaticos();
        docentes = helper.docenteDao().obtenerDocentes();

        ArrayList<String> nomMotivos = new ArrayList<>();
        ArrayList<String> nomEquiposInfoObs = new ArrayList<>();
        ArrayList<String> nomEquiposInfoRees = new ArrayList<>();
        ArrayList<String> nomDocentes = new ArrayList<>();

        for(Motivo motivo: motivos){
            nomMotivos.add(motivo.nomMotivo);
        }

        for(EquipoInformatico equipoInformaticob: equipoInformaticosObs){
            nomEquiposInfoObs.add("ID: "+equipoInformaticob.codEquipo+" Estado: "+equipoInformaticob.estadoEquipo);
        }

        for(EquipoInformatico equipoInformaticor: equipoInformaticosRees){
            nomEquiposInfoRees.add("ID: "+equipoInformaticor.codEquipo+" Estado: "+equipoInformaticor.estadoEquipo);
        }
        nomEquiposInfoRees.add(null);
        for(Docente docente: docentes){
            nomDocentes.add(docente.nomDocente);
        }
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
