package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grupo13.inventario.R;

public class DetalleAutorMenuActivity extends ListActivity {
    String[] menu = {
            "Insertar DetalleAutor",
            "Lista DetalleAutor",
            "Consultar DetalleAutor",
            "Actualizar DetalleAutor",
            "Eliminar DetalleAutor"
    };
    String[] activities = {
            "DetalleAutorInsertarActivity",
            "DetalleAutorListaActivity",
            "DetalleAutorConsultarActivity",
            "DetalleAutorActualizarActivity",
            "DetalleAutorEliminarActivity"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detalle_autor_menu);
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