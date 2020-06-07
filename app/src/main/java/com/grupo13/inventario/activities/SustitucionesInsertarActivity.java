package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Docente;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.Motivo;
import com.grupo13.inventario.modelo.Sustituciones;
import com.grupo13.inventario.modelo.TipoProducto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class SustitucionesInsertarActivity extends AppCompatActivity {
    @BindViews({
            R.id.edtMotivoSustitucionInsertar,
            R.id.edtEquipObsoletoSustitucionInsertar,
            R.id.edtEquipReemplazoSustitucionInsertar,
            R.id.edtDocenteSustitucionInsertar
    })
    List<Spinner> spinners;

    List<Motivo> motivos;
    List<EquipoInformatico> equipoInformaticosObs;
    List<EquipoInformatico> equipoInformaticosRees;
    List<Docente> docentes;

    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustituciones_insertar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        llenarSpinners();
    }

    public void insertarSustitucion(View v){
        String mensaje = "";
        try{
            Sustituciones sus = new Sustituciones();
            sus.idMotivo = motivos.get(spinners.get(0).getSelectedItemPosition()).idMotivo;
            sus.idEquipoObsoleto = equipoInformaticosObs.get(spinners.get(1).getSelectedItemPosition()).idEquipo;
            sus.idEquipoReemplazo = equipoInformaticosRees.get(spinners.get(2).getSelectedItemPosition()).idEquipo;
            sus.idDocentes = docentes.get(spinners.get(3).getSelectedItemPosition()).idDocente;

            long posicion = helper.sustitucionesDao().insertarSustituciones(sus);
            if(posicion == 0 || posicion == -1){
                mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
            }
            else{
                
                mensaje = String.format("Registrado correctamente en la posicion: %d",posicion);
            }

        }catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de ingresar el registro a la Base de Datos.";
        }
        finally {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
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
            nomEquiposInfoObs.add(equipoInformaticob.estadoEquipo);
        }
        for(EquipoInformatico equipoInformaticor: equipoInformaticosRees){
            nomEquiposInfoRees.add(equipoInformaticor.estadoEquipo);
        }
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
