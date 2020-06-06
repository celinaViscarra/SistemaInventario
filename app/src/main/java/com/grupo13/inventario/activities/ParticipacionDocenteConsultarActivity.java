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
import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.ParticipacionDocente;
import com.grupo13.inventario.modelo.TipoParticipacion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParticipacionDocenteConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    @BindView(R.id.edtEscritoID)
    Spinner edtEscritoID;
    @BindView(R.id.edtDocenteID)
    Spinner edtDocenteID;
    @BindView(R.id.edtTipoParticipacionID)
    Spinner edtTipoParticipacionID;

    List<Docente> docentes;
    List<Documento> documentos;
    List<TipoParticipacion> tipoParticipaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participacion_docente_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        //Importante poner este metodo, de lo contrario nunca se llenaran los spinners.
        llenarSpinners();
    }

    public void consultarParticipacionDocente(View v){
        //Para traerse los datos de los spinners
        //De esta forma, cualquier entrada de datos es valida, por lo cual ya no se necesita el
        //catch de NumberFormatException.
        int idDocente = docentes.get(edtDocenteID.getSelectedItemPosition()).idDocente;
        int idEscrito = documentos.get(edtEscritoID.getSelectedItemPosition()).idEscrito;


        ParticipacionDocente participacionDocente = helper.participacionDocenteDao().consultarParticipacionDocente(idEscrito,idDocente);
        String mensaje = "";
        if(participacionDocente != null){
            mensaje = "Se encontro el registro, mostrando datos...";
            //Por si acaso, la forma que buscare el tipo de participacion lo hare recorriendo el
            //arreglo.
            //La diferencia es que la posicion en el arreglo puede ser una, y el ID del
            //TipoParticipacion puede ser otra. De esta forma nos aseguramos hallar la posicion.
            int posicion = -1;
            for(TipoParticipacion pivote: tipoParticipaciones){
                if(pivote.idParticipacion == participacionDocente.idParticipacion){
                    posicion = tipoParticipaciones.indexOf(pivote) + 1;
                }
            }
            //Finalmente, seleccionamos la posicion en el spinner
            edtTipoParticipacionID.setSelection(posicion);

        }else{
            mensaje = "No se encontraron datos";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

    }

    public void llenarSpinners(){
        //Primer paso, traerme la lista de docentes, documentos y tipoParticipaciones
        docentes = helper.docenteDao().obtenerDocentes();
        documentos = helper.documentoDao().obtenerDocumentos();
        tipoParticipaciones = helper.tipoParticipacionDao().obtenerTipoParticipaciones();

        //Segundo paso, hacer los arraylist que ocupare en los spinner
        ArrayList<String> nombresDocentes = new ArrayList<>();
        ArrayList<String> nombresDocumentos = new ArrayList<>();
        ArrayList<String> nombresTipoParticiones = new ArrayList<>();

        for(Documento pivote: documentos){
            nombresDocumentos.add(pivote.titulo);
        }
        for(Docente pivote: docentes){
            nombresDocentes.add(pivote.nomDocente);
        }
        nombresTipoParticiones.add("NO SELECCIONADO");
        for(TipoParticipacion pivote: tipoParticipaciones){
            nombresTipoParticiones.add(pivote.nomParticipacion);
        }

        //Tercer paso, hacer los arrayadapters
        ArrayAdapter docenteArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresDocentes);
        ArrayAdapter documentoArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresDocumentos);
        ArrayAdapter tipoParticipacionArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombresTipoParticiones);

        //Cuarto paso, poner los array adapters en los spinners correspondientes
        edtEscritoID.setAdapter(documentoArrayAdapter);
        edtDocenteID.setAdapter(docenteArrayAdapter);

        //Importante, si se desea desactivar el spinner se tiene que hacer ANTES de ponerle
        //el ArrayAdapter.
        edtTipoParticipacionID.setEnabled(false);
        edtTipoParticipacionID.setClickable(false);
        edtTipoParticipacionID.setAdapter(tipoParticipacionArrayAdapter);
    }
}