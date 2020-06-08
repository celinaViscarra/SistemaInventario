package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    @BindView(R.id.txtTipoParticipacionID)
    EditText txtTipoParticipacionID;

    List<Docente> docentes;
    List<Documento> documentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participacion_docente_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        //Importante poner este metodo, de lo contrario nunca se llenaran los spinners.
        llenarSpinners();
        txtTipoParticipacionID.setText("");
    }

    public void limpiarTexto(View v){
        edtDocenteID.setSelection(0);
        edtEscritoID.setSelection(0);
        txtTipoParticipacionID.setText("");
    }
    public void consultarParticipacionDocente(View v){
        String mensaje = "";
        //Para traerse los datos de los spinners
        //De esta forma, cualquier entrada de datos es valida, por lo cual ya no se necesita el
        //catch de NumberFormatException.
        int posDocenteSelected = edtDocenteID.getSelectedItemPosition();
        int posEscritoSelected = edtEscritoID.getSelectedItemPosition();
        if(posDocenteSelected > 0 && posEscritoSelected > 0){
            int idDocente = docentes.get(posDocenteSelected - 1).idDocente;
            int idEscrito = documentos.get(posEscritoSelected - 1).idEscrito;

            ParticipacionDocente participacionDocente = helper.participacionDocenteDao()
                    .consultarParticipacionDocente(idEscrito,idDocente);

            if(participacionDocente != null){
                mensaje = "Se encontro el registro, mostrando datos...";
                TipoParticipacion tipoParticipacion = helper.tipoParticipacionDao()
                        .consultarTipoParticipacion(participacionDocente.idParticipacion);

                txtTipoParticipacionID.setText(tipoParticipacion.nomParticipacion);

            }else{
                mensaje = "No se encontraron datos";
            }
        }
        else{
            mensaje = "Por favor seleccione los campos para poder consultar.";
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

    }

    public void llenarSpinners(){
        //Primer paso, traerme la lista de docentes, documentos y tipoParticipaciones
        docentes = helper.docenteDao().obtenerDocentes();
        documentos = helper.documentoDao().obtenerDocumentos();

        //Segundo paso, hacer los arraylist que ocupare en los spinner
        ArrayList<String> nombresDocentes = new ArrayList<>();
        ArrayList<String> nombresDocumentos = new ArrayList<>();
        nombresDocentes.add("** Ningun elemento seleccionado **");
        nombresDocumentos.add("** Ningun elemento seleccionado **");
        for(Documento pivote: documentos){
            nombresDocumentos.add(pivote.titulo);
        }
        for(Docente pivote: docentes){
            nombresDocentes.add(pivote.nomDocente);
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

        //Cuarto paso, poner los array adapters en los spinners correspondientes
        edtEscritoID.setAdapter(documentoArrayAdapter);
        edtDocenteID.setAdapter(docenteArrayAdapter);
    }
}