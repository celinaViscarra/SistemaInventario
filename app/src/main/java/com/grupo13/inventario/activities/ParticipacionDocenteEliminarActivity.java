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

public class ParticipacionDocenteEliminarActivity extends AppCompatActivity {
    ControlBD helper;
    @BindView(R.id.edtEscritoID)
    Spinner edtEscritoID;
    @BindView(R.id.edtDocenteID)
    Spinner edtDocenteID;

    List<Docente> docentes;
    List<Documento> documentos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participacion_docente_eliminar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        //Importante poner este metodo, de lo contrario nunca se llenaran los spinners.
        llenarSpinners();
    }

    public void eliminarParticipacionDocente(View v){
        String mensaje = "";
        try{
            //Para traerse los datos de los spinners
            //De esta forma, cualquier entrada de datos es valida, por lo cual ya no se necesita el
            //catch de NumberFormatException.
            int posDocente = edtDocenteID.getSelectedItemPosition();
            int posEscrito = edtEscritoID.getSelectedItemPosition();
            if(posDocente > 0 && posEscrito > 0){
                ParticipacionDocente aEliminar = new ParticipacionDocente();
                aEliminar.idDocentes = docentes.get(posDocente - 1).idDocente;
                aEliminar.idEscritos = documentos.get(posEscrito - 1).idEscrito;

                int filasAfectadas = helper.participacionDocenteDao().eliminarParticipacionDocente(aEliminar);
                if(filasAfectadas<=0){
                    mensaje = "Error al tratar de eliminar el registro.";
                }
                else{
                    mensaje = String.format("Filas afectadas: %d",filasAfectadas);
                }
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }else{
                mensaje = "Por favor, seleccione una opcion valida.";
            }
        }
        catch (SQLiteConstraintException e){
            mensaje = "Error al tratar de actualizar el registro.";
        }
        finally {
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }

    }
    public void limpiar(View v){
        edtDocenteID.setSelection(0);
        edtEscritoID.setSelection(0);
    }
    public void llenarSpinners(){
        //Primer paso, traerme la lista de docentes, documentos y tipoParticipaciones
        docentes = helper.docenteDao().obtenerDocentes();
        documentos = helper.documentoDao().obtenerDocumentos();

        //Segundo paso, hacer los arraylist que ocupare en los spinner
        ArrayList<String> nombresDocentes = new ArrayList<>();
        ArrayList<String> nombresDocumentos = new ArrayList<>();
        nombresDocentes.add("** Seleccione un docente **");
        nombresDocumentos.add("** Seleccione un documento **");

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