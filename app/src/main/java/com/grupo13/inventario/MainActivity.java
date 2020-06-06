package com.grupo13.inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
            "DetalleAutor",
            "Llenar Base de Datos(aun no sirve xd)"
    };

    String[] activities={
            "DetalleAutorMenuActivity"
    };

    //Se puede usar este para no usar findViewByID
    @BindView(R.id.listaOpciones)
    ListView listaOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //IMPORTANTE! Si usan Butterknife tienen que poner esta linea, de lo contrario no servira.
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);
        listaOpciones.setAdapter(adapter);

        listaOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    getApplicationContext().deleteDatabase("grupo13_proyecto1.db");
                    ControlBD helper = ControlBD.getInstance(getApplicationContext());
                    helper.llenarBD();
                }else{
                    try {
                        Class clase = Class.forName("com.grupo13.inventario.activities."+activities[position]);
                        Intent inte = new Intent(getApplicationContext(),clase);
                        startActivity(inte);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
