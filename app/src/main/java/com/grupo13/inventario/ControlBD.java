package com.grupo13.inventario;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.grupo13.inventario.modelo.*;
import com.grupo13.inventario.dao.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;

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
            .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

    public void vaciarBD(){
        Class controlBD = this.getClass();
        //Obtenemos la lista de todos los metodos declarados en esta clase
        Method[] metodos = controlBD.getDeclaredMethods();
        for(Method metodo: metodos){
            //Si el metodo contiene la palabra Dao
            if(metodo.toString().contains("Dao")){
                try {
                    //Devolver el dao en una variable
                    Object dao = metodo.invoke(this);
                    //Obtener la clase del dao
                    Class claseDao = dao.getClass();
                    //Buscando el metodo limpiarTabla
                    Method metodoLimpiarTabla = claseDao.getMethod("limpiarTabla",null);
                    //Ejecutar el metodo limpiar tabla
                    metodoLimpiarTabla.invoke(dao);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void llenarBD(){
        //Primero que nada, vaciamos la tabla.
        vaciarBD();
        //Generando datos para probar mis clases xd
        Autor nuevo = new Autor();
        nuevo.nomAutor = "Josue";
        nuevo.apeAutor = "Aquino";
        autorDao().insertarAutor(nuevo);

        Docente aquisi = new Docente();
        aquisi.nomDocente = "Ayasi aquino";
        docenteDao().insertarDocente(aquisi);
        Docente monge = new Docente();
        monge.nomDocente = "Ing. Mario Monge";
        docenteDao().insertarDocente(monge);
        Docente otroing = new Docente();
        otroing.nomDocente = "Ing. Algotro";
        docenteDao().insertarDocente(otroing);

        TipoParticipacion tipoParticipacion = new TipoParticipacion();
        tipoParticipacion.nomParticipacion = "completamente";
        tipoParticipacionDao().instertarTipoParticipacion(tipoParticipacion);

        TipoParticipacion otraparticipacion = new TipoParticipacion();
        otraparticipacion.nomParticipacion = "50%";
        tipoParticipacionDao().instertarTipoParticipacion(otraparticipacion);

        Categorias cat = new Categorias();
        cat.nomCategoria = "Escritos";
        long idCat = categoriasDao().insertarCategoria(cat);

        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.categoria_id = (int) idCat;
        tipoProducto.nomTipoProducto = "Libro";
        long idTipoProducto = tipoProductoDao().insertarTipoProducto(tipoProducto);

        Idiomas spa = new Idiomas();
        spa.nombreIdioma = "Español";
        long idSpa = idiomasDao().insertarIdioma(spa);

        Idiomas eng = new Idiomas();
        eng.nombreIdioma = "Inglés";
        long idEng = idiomasDao().insertarIdioma(eng);

        Documento doc = new Documento();
        doc.tipo_producto_id = (int) idTipoProducto;
        doc.idioma_id = (int) idSpa;
        doc.isbn = "1337";
        doc.edicion = "1era";
        doc.editorial = "Una editorial seria";
        doc.titulo = "Un titulo mas serio para el libro";

        Documento doc2 = new Documento();
        doc2.tipo_producto_id = (int) idTipoProducto;
        doc2.idioma_id = (int) idEng;
        doc2.isbn = "0001-1";
        doc2.edicion = "1era";
        doc2.editorial = "Otra editorial seria";
        doc2.titulo = "Algotro libro";

        documentoDao().insertarDocumento(doc);
        documentoDao().insertarDocumento(doc2);

        // Datos para Equipo informatico
        Marca m1 = new Marca("M01", "HP");
        Marca m2 = new Marca("M02", "DELL");
        Marca m3 = new Marca("M03", "Lenovo");

        marcaDao().insertarMarca(m1);
        marcaDao().insertarMarca(m2);
        marcaDao().insertarMarca(m3);

        CatalogoEquipo c1 = new CatalogoEquipo();
        c1.idCatalogo = "000001";
        c1.idMarca = m1.idMarca;
        c1.modeloEquipo = "Compact";
        c1.memoria = 1024;
        c1.cantEquipo = 5;
        CatalogoEquipo c2 = new CatalogoEquipo();
        c2.idCatalogo = "000002";
        c2.idMarca = m2.idMarca;
        c2.modeloEquipo = "Infinium";
        c2.memoria = 1024;
        c2.cantEquipo = 7;

        catalogoEquipoDao().insertarCatalogoEquipo(c1);
        catalogoEquipoDao().insertarCatalogoEquipo(c2);

        Ubicaciones u1 = new Ubicaciones();
        u1.nomUbicacion = "EISI";
        long idU = ubicacionesDao().insertarUbicaciones(u1);

        Categorias cate = new Categorias();
        cate.nomCategoria = "Equipo informatico";
        long idC = categoriasDao().insertarCategoria(cate);

        TipoProducto tp1 = new TipoProducto();
        tp1.categoria_id = (int) idC;
        tp1.nomTipoProducto = "Computadora";
        tipoProductoDao().insertarTipoProducto(tp1);

        // Datos para detalle de reserva de equipo

        Dias lun = new Dias("LUN", "LUNES");
        Dias mar = new Dias("MAR", "MARTES");
        Dias mie = new Dias("MIE", "MIERCOLES");
        Dias jue = new Dias("JUE", "JUEVES");
        Dias vie = new Dias("VIE", "VIERNES");
        Dias sab = new Dias("SAB", "SABADO");
        Dias dom = new Dias("DOM", "DOMINGO");

        diasDao().insertarDia(lun);
        diasDao().insertarDia(mar);
        diasDao().insertarDia(mie);
        diasDao().insertarDia(jue);
        diasDao().insertarDia(vie);
        diasDao().insertarDia(sab);
        diasDao().insertarDia(dom);

        HoraClase h1 = new HoraClase();
        HoraClase h2 = new HoraClase();
        HoraClase h3 = new HoraClase();
        HoraClase h4 = new HoraClase();
        HoraClase h5 = new HoraClase();
        HoraClase h6 = new HoraClase();
        HoraClase h7 = new HoraClase();
        HoraClase h8 = new HoraClase();

        h1.horaInicio = Time.valueOf("06:20:00");
        h1.horaFin = Time.valueOf("08:00:00");
        h2.horaInicio = Time.valueOf("08:05:00");
        h2.horaFin = Time.valueOf("09:45:00");
        h3.horaInicio = Time.valueOf("09:50:00");
        h3.horaFin = Time.valueOf("11:30:00");
        h4.horaInicio = Time.valueOf("11:35:00");
        h4.horaFin = Time.valueOf("13:15:00");
        h5.horaInicio = Time.valueOf("13:20:00");
        h5.horaFin = Time.valueOf("15:00:00");
        h6.horaInicio = Time.valueOf("15:05:00");
        h6.horaFin = Time.valueOf("16:45:00");
        h7.horaInicio = Time.valueOf("16:50:00");
        h7.horaFin = Time.valueOf("18:30:00");
        h8.horaInicio = Time.valueOf("18:35:00");
        h8.horaFin = Time.valueOf("20:15:00");

        h1.idHora = (int) horaClaseDao().insertarHoraClase(h1);
        h2.idHora = (int) horaClaseDao().insertarHoraClase(h2);
        h3.idHora = (int) horaClaseDao().insertarHoraClase(h3);
        h4.idHora = (int) horaClaseDao().insertarHoraClase(h4);
        h5.idHora = (int) horaClaseDao().insertarHoraClase(h5);
        h6.idHora = (int) horaClaseDao().insertarHoraClase(h6);
        h7.idHora = (int) horaClaseDao().insertarHoraClase(h7);
        h8.idHora = (int) horaClaseDao().insertarHoraClase(h8);

        Dias[] dias = {lun, mar, mie, jue, vie, sab, dom};
        HoraClase[] horas = {h1, h2, h3, h4, h5, h6, h7, h8};

        for (Dias dia: dias) {
            for (HoraClase clase: horas) {
                Horarios horario = new Horarios(clase.idHora, dia.diaCod);
                horariosDao().insertarHorario(horario);
            }
        }



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
