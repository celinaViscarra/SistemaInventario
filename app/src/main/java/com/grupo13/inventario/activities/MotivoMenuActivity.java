package com.grupo13.inventario.activities;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MotivoMenuActivity extends ListActivity {
    String[] menu = {
            "Insertar Motivo",
            "Consultar Motivo",
            "Actualizar Motivo",
            "Eliminar Motivo"
    };
    String[] activities = {
            "MotivoInsertarActivity",
            "MotivoConsultarActivity",
            "MotivoActualizarActivity",
            "MotivoEliminarActivity"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,menu));
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        try {
            Class<?> clase = Class.forName("com.grupo13.inventario.activities."+activities[position]);
            Intent inte = new Intent(this,clase);
            startActivity(inte);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
