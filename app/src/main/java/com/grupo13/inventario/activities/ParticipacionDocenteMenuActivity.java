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

import java.util.ArrayList;

public class ParticipacionDocenteMenuActivity extends ListActivity {
    int[] menu = {
            R.string.menu_item_insertar,
            R.string.menu_item_consultar,
            R.string.menu_item_actualizar,
            R.string.menu_item_eliminar
    };
    String[] activities = {
            "ParticipacionDocenteInsertarActivity",
            "ParticipacionDocenteConsultarActivity",
            "ParticipacionDocenteActualizarActivity",
            "ParticipacionDocenteEliminarActivity"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_participacion_docente_menu);
        ArrayList<String> menuStrings = new ArrayList<>();
        for(int pivote: menu){
            //Esto formatea el string del elemento del menu,
            //por ejemplo, si el string que devuelve getString(pivote) fuera "Insertar %s",
            //getString(R.string.detalle_autor) devolvera el nombre de esta clase, ejemplo
            //"Insertar DetalleAutor"
            String elementoMenu = String.format(getString(pivote),getString(R.string.participacion_docente));
            menuStrings.add(elementoMenu);
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,menuStrings));
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id){
        super.onListItemClick(lv, v, position, id);
        try {
            if (Permisos.getInstance(v.getContext()).has_permission(activities[position])) {
                Class<?> clase = Class.forName("com.grupo13.inventario.activities." + activities[position]);
                Intent inte = new Intent(this,clase);
                startActivity(inte);
            } else {
                Toast.makeText(v.getContext(), R.string.no_permiso, Toast.LENGTH_SHORT).show();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}