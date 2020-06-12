package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.grupo13.inventario.modelo.Marca;

import java.util.ArrayList;
import java.util.List;

public class CatalogoEquipoActualizarActivity extends AppCompatActivity {
    ControlBD helper;
    EditText  modelo, memoria, cantidad;
    Spinner idMarca, idCatalogo;

    List<CatalogoEquipo> catalogos;
    List<Marca> marcas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_equipo_actualizar);
        idCatalogo = (Spinner) findViewById(R.id.editIdCatalogo);
        idMarca = (Spinner) findViewById(R.id.editIdMarca);
        modelo = (EditText) findViewById(R.id.editModeloEquipo);
        memoria = (EditText) findViewById(R.id.editMemoria);
        cantidad = (EditText) findViewById(R.id.editCantEquipo);

        helper = ControlBD.getInstance(this);
        llenarSpiners();
    }

    public void actualizarCatalogoEquipo(View v){
        String mensaje = "";
        String modelos = modelo.getText().toString();
        String memorias = memoria.getText().toString();
        String cantidades = cantidad.getText().toString();
        if(!modelos.isEmpty() && !memorias.isEmpty() && !cantidades.isEmpty()) {
            try {
                int posCatalogo = idCatalogo.getSelectedItemPosition();
                int posMarca = idMarca.getSelectedItemPosition();
                CatalogoEquipo catalogo = new CatalogoEquipo();
                if (posCatalogo > 0 && posMarca > 0) {
                    catalogo.idCatalogo = catalogos.get(posCatalogo - 1).idCatalogo;
                    catalogo.idMarca = marcas.get(posMarca - 1).idMarca;
                    catalogo.modeloEquipo = modelos;
                    catalogo.memoria = Integer.parseInt(memorias);
                    catalogo.cantEquipo = Integer.parseInt(cantidades);

                    int filasAfectadas = helper.catalogoEquipoDao().actualizarCatalogoEquipo(catalogo);
                    if (filasAfectadas <= 0) {
                        mensaje = "Error al tratar de actualizar el registro.";
                    } else {
                        mensaje = String.format("Filas afectadas: %d", filasAfectadas);
                    }
                } else {
                    mensaje = "Por favor, seleccione una opcion valida.";
                }
            } catch (NumberFormatException e) {
                mensaje = "Error en la entrada de datos, revisa por favor los datos ingresados.";
            } catch (SQLiteConstraintException e) {
                mensaje = "Error al tratar de actualizar el registro.";

            } finally {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View v){
        modelo.setText("");
        memoria.setText("");
        cantidad.setText("");
    }

    public void llenarSpiners() {
        catalogos = helper.catalogoEquipoDao().obtenerCatalogoEquipo();
        marcas = helper.marcaDao().obtenerMarcas();

        ArrayList<String> idCatalogos = new ArrayList<>();
        ArrayList<String> idMarcas = new ArrayList<>();

        idCatalogos.add("** Seleccione un Catalogo **");
        idMarcas.add("** Seleccione una Marca **");

        for (CatalogoEquipo catalogoEquipo : catalogos) {
            idCatalogos.add(catalogoEquipo.idCatalogo);
        }

        for (Marca marca : marcas) {
            idMarcas.add(marca.idMarca);
        }

        ArrayAdapter<String> catalogoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idCatalogos);
        ArrayAdapter<String> marcaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idMarcas);

        idCatalogo.setAdapter(catalogoAdapter);
        idMarca.setAdapter(marcaAdapter);

    }
}
