package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.Descargos;
import com.grupo13.inventario.modelo.DetalleDescargos;
import com.grupo13.inventario.modelo.DetalleReserva;
import com.grupo13.inventario.modelo.EquipoInformatico;

public class DetalleDescargosInsertarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText editIdEquipo, editIdDescargos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_descargos_insertar);
        editIdDescargos = (EditText) findViewById(R.id.editIdDescargos);
        editIdEquipo = (EditText) findViewById(R.id.editIdEquipo);
        helper = ControlBD.getInstance(this);
    }

    public void insertarDetalleDescargos(View v){
        int idEquipo = Integer.parseInt(editIdEquipo.getText().toString());
        int idDescargos = Integer.parseInt(editIdDescargos.getText().toString());
        DetalleDescargos nuevo = new DetalleDescargos();
        nuevo.idDescargo = idDescargos;
        nuevo.idEquipo = idEquipo;
        helper.detalleDescargosDao().insertarDetalleDescargos(nuevo);
    }

    public void limpiarTexto(View v){
        editIdEquipo.setText("");
        editIdDescargos.setText("");
    }
}
