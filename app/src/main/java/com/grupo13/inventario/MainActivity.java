package com.grupo13.inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.grupo13.inventario.activities.LoginActivity;
import com.grupo13.inventario.singleton.Permisos;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String[] menu = {
            "Autor",
            "DetalleAutor",
            "ParticipacionDocente",
            "Documento",
            "Equipo Informatico",
            "Catalogo Equipo",
            "Motivo",
            "Sustituciones",
            "Detalle Reserva Equipo",
            "Descargos",
            "Detalle Descargos",
            "Movimiento Inventario",
            "Llenar Base de Datos(sirve pero solo llena datos mios xd)",
            "Cerrar sesion"
    };

    String[] activities={
            "AutorMenuActivity",
            "DetalleAutorMenuActivity",
            "ParticipacionDocenteMenuActivity",
            "DocumentoMenuActivity",
            "EquipoInformaticoMenuActivity",
            "CatalogoEquipoMenuActivity",
            "MotivoMenuActivity",
            "SustitucionesMenuActivity",
            "DetalleReservaMenuActivity",
            "DescargosMenuActivity",
            "DetalleDescargosMenuActivity",
            "MovimientoInventarioMenuActivity"
    };

    public static final String USER_KEY = "USER_KEY";
    public static final String USERNAME = "USERNAME";

    //Se puede usar este para no usar findViewByID
    @BindView(R.id.listaOpciones)
    ListView listaOpciones;
    @BindView(R.id.bar)
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(USERNAME, null);

        if (username == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                Permisos.getInstance(getApplicationContext());
            }
        });
        hilo.start();

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
                if(position == menu.length-2){

                    // Llenar la base de datos en un hilo separado para no congelar el hilo principal
                    new LlenarBase().execute(helper);

                }else if (position == menu.length-1){
                    // Cerrar la sesión
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USERNAME, null);
                    editor.commit();

                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    view.getContext().startActivity(intent);
                    finish();

                }else {
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

    class LlenarBase extends AsyncTask<ControlBD, String, Void>{

        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
            listaOpciones.setEnabled(false);
        }

        @Override
        protected Void doInBackground(ControlBD... controlBDS) {
            //getApplicationContext().deleteDatabase("grupo13_proyecto1.db");
            ControlBD helper = controlBDS[0];
            String mensaje = "";
            try{
                helper.llenarBD();
                mensaje = "Los datos se llenaron correctamente";
            }
            catch(SQLiteConstraintException e){
                mensaje = "Error al insertar registros a la Base de Datos";
            }
            finally {
                publishProgress(mensaje);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... mensajes) {
            String mensaje = mensajes[0];
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            bar.setVisibility(View.GONE);
            listaOpciones.setEnabled(true);

            //Cargar los permisos
            Permisos.getInstance(getApplicationContext());
        }
    }
}
