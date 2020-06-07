package com.grupo13.inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String[] menu = {
            "Autor",
            "DetalleAutor",
            "ParticipacionDocente",
            "Documento",
            "Equipo Informatico",
            "Motivo",
            "Sustituciones",
            "Llenar Base de Datos(sirve pero solo llena datos mios xd)"
    };

    String[] activities={
            "AutorMenuActivity",
            "DetalleAutorMenuActivity",
            "ParticipacionDocenteMenuActivity",
            "DocumentoMenuActivity",
            "EquipoInformaticoMenuActivity"
            "MotivoMenuActivity",
            "SustitucionesMenuActivity"
    };

    //Se puede usar este para no usar findViewByID
    @BindView(R.id.listaOpciones)
    ListView listaOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ControlBD helper;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Inventario EISI FIA - Grupo 13");

        helper = ControlBD.getInstance(getApplicationContext());

        //IMPORTANTE! Si usan Butterknife tienen que poner esta linea, de lo contrario no servira.
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);
        listaOpciones.setAdapter(adapter);

        listaOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == menu.length-1){
                    //getApplicationContext().deleteDatabase("grupo13_proyecto1.db");
                    String mensaje = "";
                    try{
                        helper.llenarBD();
                        mensaje = "Los datos se llenaron correctamente";
                    }
                    catch(SQLiteConstraintException e){
                        mensaje = "Error al insertar registros a la Base de Datos";
                    }
                    finally {
                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        Class clase = Class.forName("com.grupo13.inventario.activities."+activities[position]);
                        Intent inte = new Intent(getApplicationContext(), clase);
                        startActivity(inte);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
