package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo13.inventario.R;
import com.grupo13.inventario.singleton.Permisos;

public class TipoProductoMenuActivity extends ListActivity {
    String[] menu = {
            "Insertar TipoProducto",
            "Consultar TipoProducto",
            "Actualizar TipoProducto",
            "Eliminar TipoProducto"
    };
    String[] activities = {
            "TipoProductoInsertarActivity",
            "TipoProductoConsultarActivity",
            "TipoProductoActualizarActivity",
            "TipoProductoEliminarActivity"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tipo_producto_menu);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        try {
            if (Permisos.getInstance(v.getContext()).has_permission(activities[position])) {
                Class<?> clase = Class.forName("com.grupo13.inventario.activities." + activities[position]);
                Intent inte = new Intent(this,clase);
                startActivity(inte);
            } else {
                Toast.makeText(v.getContext(), R.string.no_permiso, Toast.LENGTH_SHORT).show();
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}