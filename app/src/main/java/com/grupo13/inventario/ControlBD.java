package com.grupo13.inventario;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.grupo13.inventario.modelo.*;
import com.grupo13.inventario.dao.*;

@Database(
        entities = {
                Categorias.class,
                TipoProducto.class,
                Idiomas.class,
                Documento.class,
                DetalleAutor.class,
                Autor.class,
                ParticipacionDocente.class,
                TipoParticipacion.class,
                Docente.class,
                MovimientoInventario.class,
                DetalleReserva.class,
                Horarios.class,
                Dias.class,
                HoraClase.class,
                TipoMovimiento.class,
                Sustituciones.class,
                Motivo.class,
                EquipoInformatico.class,
                DetalleDescargos.class,
                Descargos.class,
                Ubicaciones.class,
                CatalogoEquipo.class,
                TomaFisica.class,
                Marca.class,
                Observacion.class,
                OpcionCrud.class,
                AccesoUsuario.class,
                Usuario.class
        },
        exportSchema = false,
        version = 1
)
@TypeConverters({Conversor.class})
public abstract class ControlBD extends RoomDatabase {
    private static final String DB_NAME = "grupo13_proyecto1.db";
    private static ControlBD instance;

    public static synchronized ControlBD getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ControlBD.class,DB_NAME)
            .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    //Aqui se declaran los DAOs
    public abstract AutorDao autorDao();
    public abstract CatalogoEquipoDao catalogoEquipoDao();
    public abstract CategoriasDao categoriasDao();
    public abstract DescargosDao descargosDao();
    public abstract DetalleAutorDao detalleAutorDao();
    public abstract DetalleDescargosDao detalleDescargosDao();
    public abstract DetalleReservaDao detalleReservaDao();
    public abstract DiasDao diasDao();
    public abstract DocenteDao docenteDao();
    public abstract DocumentoDao documentoDao();
    public abstract EquipoInformaticoDao equipoInformaticoDao();
    public abstract HoraClaseDao horaClaseDao();
    public abstract HorariosDao horariosDao();
    public abstract IdiomasDao idiomasDao();
    public abstract MarcaDao marcaDao();
    public abstract MotivoDao motivoDao();
    public abstract MovimientoInventarioDao movimientoInventarioDao();
    public abstract ObservacionDao observacionDao();
    public abstract OpcionCrudDao opcionCrudDao();
    public abstract ParticipacionDocenteDao participacionDocenteDao();
    public abstract SustitucionesDao sustitucionesDao();
    public abstract TipoMovimientoDao tipoMovimientoDao();
    public abstract TipoParticipacionDao tipoParticipacionDao();
    public abstract TipoProductoDao tipoProductoDao();
    public abstract TomaFisicaDao tomaFisicaDao();
    public abstract UbicacionesDao ubicacionesDao();
    public abstract UsuarioDao usuarioDao();

}
