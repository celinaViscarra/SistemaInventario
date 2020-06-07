package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.R;
import com.grupo13.inventario.modelo.CatalogoEquipo;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.TipoProducto;
import com.grupo13.inventario.modelo.Ubicaciones;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipoInformaticoConsultarActivity extends AppCompatActivity {
    ControlBD helper;
    @BindView(R.id.txt_equipo_id)
    EditText txtId;
    @BindView(R.id.txt_equipo_tipo_producto)
    EditText txtTipoProducto;
    @BindView(R.id.txt_equipo_catalogo)
    EditText txtCatalogo;
    @BindView(R.id.txt_equipo_ubicacion)
    EditText txtUbicacion;
    @BindView(R.id.txt_equipo_codigo)
    EditText txtCodigo;
    @BindView(R.id.txt_equipo_estado)
    EditText txtEstado;
    @BindView(R.id.txt_equipo_fecha)
    EditText txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_informatico_consultar);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
    }

    public void consultarEquipoInformatico(View v){

        try {
            int idEquipo = Integer.parseInt(txtId.getText().toString());

            EquipoInformatico equipo = helper.equipoInformaticoDao().consultarEquipoInformatico(idEquipo);
            String mensaje = "";
            if(equipo != null){

                // Recuperar datos de las tablas de las que depende
                TipoProducto tipoProducto = helper.tipoProductoDao().consultarTipoProducto(equipo.tipo_producto_id);
                CatalogoEquipo catalogoEquipo = helper.catalogoEquipoDao().consultarCatalogoEquipo(equipo.catalogo_id);
                Ubicaciones ubicaciones = helper.ubicacionesDao().consultarUbicaciones(equipo.ubicacion_id);

                if (tipoProducto != null && catalogoEquipo != null && ubicaciones != null) {
                    mensaje = "Se encontro el registro, mostrando datos...";
                    txtTipoProducto.setText(tipoProducto.nomTipoProducto);
                    txtCatalogo.setText(catalogoEquipo.idCatalogo);
                    txtUbicacion.setText(ubicaciones.nomUbicacion);
                    txtCodigo.setText(equipo.codEquipo);
                    txtEstado.setText(equipo.estadoEquipo);
                    txtFecha.setText(equipo.fechaAdquisicion.toString());
                } else {
                    Toast.makeText(this, "No se pudieron recuperar las dependencias.", Toast.LENGTH_SHORT).show();
                }

            }else{
                mensaje = "No se encontraron datos";
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }catch (NumberFormatException e) {
            Toast.makeText(this, "El ID debe ser un numero entero", Toast.LENGTH_SHORT).show();
        }catch (SQLiteConstraintException e) {
            Toast.makeText(this, "Error leyendo los datos", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}