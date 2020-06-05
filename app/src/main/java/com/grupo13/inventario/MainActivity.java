package com.grupo13.inventario;

import androidx.appcompat.app.AppCompatActivity;

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
            "Llenar Base de Datos(aun no sirve xd)"
    };

    String[] activities={};

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
                if(position == 0){
                    Toast.makeText(getApplicationContext(),"Que aun no sirve we :v",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
