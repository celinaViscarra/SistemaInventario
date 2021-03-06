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
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
        version = 4
)
@TypeConverters({Conversor.class})
public abstract class ControlBD extends RoomDatabase {
    private static final String DB_NAME = "grupo13_proyecto1.db";
    private static ControlBD instance;

    public static synchronized ControlBD getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ControlBD.class,DB_NAME)
            .setJournalMode(JournalMode.TRUNCATE).fallbackToDestructiveMigration().allowMainThreadQueries().build();
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


        Autor AV12013 = new Autor();
        AV12013.nomAutor = "Celina";
        AV12013.apeAutor = "Vizcarra";
        autorDao().insertarAutor(AV12013);
        Autor AH13019 = new Autor();
        AH13019.nomAutor = "Marvin";
        AH13019.apeAutor = "Alvarenga";
        autorDao().insertarAutor(AH13019);
        Autor AB15002 = new Autor();
        AB15002.nomAutor = "Josue";
        AB15002.apeAutor = "Aquino";
        autorDao().insertarAutor(AB15002);
        Autor AC13002 = new Autor();
        AC13002.nomAutor = "Rafael";
        AC13002.apeAutor = "Avilés";
        autorDao().insertarAutor(AC13002);
        Autor MD13017 = new Autor();
        MD13017.nomAutor = "Bryan";
        MD13017.apeAutor = "Marín";
        autorDao().insertarAutor(MD13017);

        Autor pruebaAutor = new Autor();
        pruebaAutor.nomAutor = "Autor";
        pruebaAutor.apeAutor = "Prueba";
        autorDao().insertarAutor(pruebaAutor);


        Docente docente1 = new Docente();
        docente1.nomDocente = "José Montiel";
        docenteDao().insertarDocente(docente1);
        Docente docente2 = new Docente();
        docente2.nomDocente = "Juan Valdéz";
        docenteDao().insertarDocente(docente2);
        Docente docente3 = new Docente();
        docente3.nomDocente = "Alberto López";
        long idDoce = docenteDao().insertarDocente(docente3);

        TipoParticipacion participacion1 = new TipoParticipacion();
        participacion1.nomParticipacion = "100%";
        tipoParticipacionDao().instertarTipoParticipacion(participacion1);

        TipoParticipacion participacion2 = new TipoParticipacion();
        participacion2.nomParticipacion = "75%";
        tipoParticipacionDao().instertarTipoParticipacion(participacion2);

        TipoParticipacion participacion3 = new TipoParticipacion();
        participacion3.nomParticipacion = "50%";
        tipoParticipacionDao().instertarTipoParticipacion(participacion3);

        Categorias cat1 = new Categorias();
        cat1.nomCategoria = "Escritos";
        long idCat1 = categoriasDao().insertarCategoria(cat1);

        Categorias cat2 = new Categorias();
        cat2.nomCategoria = "Equipo de LCOMP";
        long idCat2 = categoriasDao().insertarCategoria(cat2);

        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.categoria_id = (int) idCat1;
        tipoProducto.nomTipoProducto = "Libro";
        long idTipoProducto = tipoProductoDao().insertarTipoProducto(tipoProducto);

        TipoProducto tipoProducto2 = new TipoProducto();
        tipoProducto2.categoria_id = (int) idCat1;
        tipoProducto2.nomTipoProducto = "Perifericos";
        long idTipoProducto2 = tipoProductoDao().insertarTipoProducto(tipoProducto2);



        Idiomas spa = new Idiomas();
        spa.nombreIdioma = "Español";
        long idSpa = idiomasDao().insertarIdioma(spa);

        Idiomas eng = new Idiomas();
        eng.nombreIdioma = "Inglés";
        long idEng = idiomasDao().insertarIdioma(eng);

        Documento doc = new Documento();
        doc.tipo_producto_id = (int) idTipoProducto;
        doc.idioma_id = (int) idSpa;
        doc.isbn = "8403094264";
        doc.edicion = "1era";
        doc.editorial = "UES";
        doc.titulo = "Sistema informático de monitoreo y control de los proyectos en la Fundación para la Cooperación y Desarrollo Comunal en El Salvador";

        Documento doc2 = new Documento();
        doc2.tipo_producto_id = (int) idTipoProducto;
        doc2.idioma_id = (int) idEng;
        doc2.isbn = "1337-239-1923";
        doc2.edicion = "2da";
        doc2.editorial = "UES";
        doc2.titulo = "Sistema de información para el área de hospitalización en el Hospital Nacional Rosales.\n";

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
        c1.modeloEquipo = "Compaq";
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
        Ubicaciones u2 = new Ubicaciones();
        u2.nomUbicacion = "EII";
        long idU2 = ubicacionesDao().insertarUbicaciones(u2);

        Ubicaciones u3 = new Ubicaciones();
        u3.nomUbicacion = "LCOMP-1";
        ubicacionesDao().insertarUbicaciones(u3);

        Categorias cate = new Categorias();
        cate.nomCategoria = "Equipo informatico";
        long idC = categoriasDao().insertarCategoria(cate);

        TipoProducto tp1 = new TipoProducto();
        tp1.categoria_id = (int) idC;
        tp1.nomTipoProducto = "Computadora";
        long idTp = tipoProductoDao().insertarTipoProducto(tp1);

        //Datos de Descargos
        Descargos descargo = new Descargos();
        descargo.ubicacion_destino_id = (int) idU2;
        descargo.ubicacion_origen_id = (int) idU;
        descargo.fechaDescargos = Date.valueOf("2020-07-07");
        long idDescargo = descargosDao().insertarDescargos(descargo);

        // *****Datos para detalle de reserva de equipo ****

        // Datos para el horarios

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

        // Datos de un movimiento
        EquipoInformatico equipoInformatico = new EquipoInformatico();
        equipoInformatico.fechaAdquisicion = Date.valueOf("2015-01-01");
        equipoInformatico.estadoEquipo = "BUENO";
        equipoInformatico.codEquipo = "EQ0001";
        equipoInformatico.ubicacion_id = (int) idU;
        equipoInformatico.catalogo_id = c1.idCatalogo;
        equipoInformatico.tipo_producto_id = (int) idTp;
        long idEquipoInfor = equipoInformaticoDao().insertarEquipoInformatico(equipoInformatico);

        EquipoInformatico equipoInformatico2 = new EquipoInformatico();
        equipoInformatico2.fechaAdquisicion = Date.valueOf("2019-09-24");
        equipoInformatico2.estadoEquipo = "NUEVO";
        equipoInformatico2.codEquipo = "EQ0002";
        equipoInformatico2.ubicacion_id = (int) idU;
        equipoInformatico2.catalogo_id = c1.idCatalogo;
        equipoInformatico2.tipo_producto_id = (int) idTp;
        equipoInformaticoDao().insertarEquipoInformatico(equipoInformatico2);


        TipoMovimiento tipoMov1 = new TipoMovimiento();
        tipoMov1.nombreTipoMoviento = "Prestamo de equipo";
        long idTMov = tipoMovimientoDao().insertarTipoMovimiento(tipoMov1);

        TipoMovimiento tipoMov2 = new TipoMovimiento();
        tipoMov2.nombreTipoMoviento = "Asignacion";
        tipoMovimientoDao().insertarTipoMovimiento(tipoMov2);

        MovimientoInventario movInventa = new MovimientoInventario();
        movInventa.descripcion = "Prestamo de una compu";
        movInventa.prestamoActivo =  Boolean.TRUE;
        movInventa.prestamoFechaInicio = Date.valueOf("2020-06-01");
        movInventa.prestamoFechaFin = Date.valueOf("2020-06-01");
        movInventa.prestamoPermanente = Boolean.FALSE;
        movInventa.tipo_movimiento_id = (int) idTMov;
        movInventa.docentes_id = (int) idDoce;
        movInventa.equipo_id = (int) idEquipoInfor;
        long idMovInv = movimientoInventarioDao().insertarMovimientoInventario(movInventa);

        MovimientoInventario movInventa2 = new MovimientoInventario();
        movInventa2.descripcion = "Prestar otra vez compu";
        movInventa2.prestamoActivo =  Boolean.TRUE;
        movInventa2.prestamoFechaInicio = Date.valueOf("2020-06-01");
        movInventa2.prestamoFechaFin = Date.valueOf("2020-06-01");
        movInventa2.prestamoPermanente = Boolean.FALSE;
        movInventa2.tipo_movimiento_id = (int) idTMov;
        movInventa2.docentes_id = (int) idDoce;
        movInventa2.equipo_id = (int) idEquipoInfor;
        long idMovInv2 = movimientoInventarioDao().insertarMovimientoInventario(movInventa2);

        Motivo motivoPrueba = new Motivo();
        motivoPrueba.nomMotivo = "Motivo prueba sustitucion";
        motivoDao().insertarMotivo(motivoPrueba);

        // Datos para el login y los permisos

        Usuario admin = new Usuario("admin", "admin", "Usuario Administrador");
        Usuario normal = new Usuario("user", "user", "Usuario Normal");

        usuarioDao().insertarUsuario(admin);
        usuarioDao().insertarUsuario(normal);

        OpcionCrud o1 = new OpcionCrud("001", "MovimientoInventarioActualizarActivity", 1);
        OpcionCrud o2 = new OpcionCrud("002", "MovimientoInventarioConsultarActivity", 1);
        OpcionCrud o3 = new OpcionCrud("003", "MovimientoInventarioEliminarActivity", 1);
        OpcionCrud o4 = new OpcionCrud("004", "MovimientoInventarioInsertarActivity", 1);

        OpcionCrud o5 = new OpcionCrud("005", "DetalleReservaConsultarActivity", 2);
        OpcionCrud o6 = new OpcionCrud("006", "DetalleReservaEliminarActivity", 2);
        OpcionCrud o7 = new OpcionCrud("007", "DetalleReservaActualizarActivity", 2);
        OpcionCrud o8 = new OpcionCrud("008", "DetalleReservaInsertarActivity", 2);

        OpcionCrud o9 = new OpcionCrud("009", "EquipoInformaticoActualizarActivity", 3);
        OpcionCrud o10 = new OpcionCrud("010", "EquipoInformaticoEliminarActivity", 3);
        OpcionCrud o11 = new OpcionCrud("011", "EquipoInformaticoConsultarActivity", 3);
        OpcionCrud o12 = new OpcionCrud("012", "EquipoInformaticoInsertarActivity", 3);

        OpcionCrud o13 = new OpcionCrud("013", "AutorEliminarActivity", 4);
        OpcionCrud o14 = new OpcionCrud("014", "AutorActualizarActivity", 4);
        OpcionCrud o15 = new OpcionCrud("015", "AutorInsertarActivity", 4);
        OpcionCrud o16 = new OpcionCrud("016", "AutorConsultarActivity", 4);

        OpcionCrud o17 = new OpcionCrud("017", "SustitucionesActualizarActivity", 5);
        OpcionCrud o18 = new OpcionCrud("018", "SustitucionesConsultarActivity", 5);
        OpcionCrud o19 = new OpcionCrud("019", "SustitucionesEliminarActivity", 5);
        OpcionCrud o20 = new OpcionCrud("020", "SustitucionesInsertarActivity", 5);

        OpcionCrud o21 = new OpcionCrud("021", "UbicacionesActualizarActivity", 6);
        OpcionCrud o22 = new OpcionCrud("022", "UbicacionesConsultarActivity", 6);
        OpcionCrud o23 = new OpcionCrud("023", "UbicacionesEliminarActivity", 6);
        OpcionCrud o24 = new OpcionCrud("024", "UbicacionesInsertarActivity", 6);

        OpcionCrud o25 = new OpcionCrud("025", "TipoProductoActualizarActivity", 7);
        OpcionCrud o26 = new OpcionCrud("026", "TipoProductoConsultarActivity", 7);
        OpcionCrud o27 = new OpcionCrud("027", "TipoProductoEliminarActivity", 7);
        OpcionCrud o28 = new OpcionCrud("028", "TipoProductoInsertarActivity", 7);

        OpcionCrud o29 = new OpcionCrud("029", "DocumentoEliminarActivity", 8);
        OpcionCrud o30 = new OpcionCrud("030", "DocumentoActualizarActivity", 8);
        OpcionCrud o31 = new OpcionCrud("031", "DocumentoConsultarActivity", 8);
        OpcionCrud o32 = new OpcionCrud("032", "DocumentoInsertarActivity", 8);

        OpcionCrud o33 = new OpcionCrud("033", "ParticipacionDocenteEliminarActivity", 9);
        OpcionCrud o34 = new OpcionCrud("034", "ParticipacionDocenteActualizarActivity", 9);
        OpcionCrud o35 = new OpcionCrud("035", "ParticipacionDocenteConsultarActivity", 9);
        OpcionCrud o36 = new OpcionCrud("036", "ParticipacionDocenteInsertarActivity", 9);

        OpcionCrud o37 = new OpcionCrud("037", "DetalleAutorEliminarActivity", 10);
        OpcionCrud o38 = new OpcionCrud("038", "DetalleAutorActualizarActivity", 10);
        OpcionCrud o39 = new OpcionCrud("039", "DetalleAutorConsultarActivity", 10);
        OpcionCrud o40 = new OpcionCrud("040", "DetalleAutorInsertarActivity", 10);

        OpcionCrud o41 = new OpcionCrud("041", "CatalogoEquipoActualizarActivity", 11);
        OpcionCrud o42 = new OpcionCrud("042", "CatalogoEquipoEliminarActivity", 11);
        OpcionCrud o43 = new OpcionCrud("043", "CatalogoEquipoConsultarActivity", 11);
        OpcionCrud o44 = new OpcionCrud("044", "CatalogoEquipoInsertarActivity", 11);

        OpcionCrud o45 = new OpcionCrud("045", "DetalleDescargosActualizarActivity", 12);
        OpcionCrud o46 = new OpcionCrud("046", "DetalleDescargosConsultarActivity", 12);
        OpcionCrud o47 = new OpcionCrud("047", "DetalleDescargosEliminarActivity", 12);
        OpcionCrud o48 = new OpcionCrud("048", "DetalleDescargosInsertarActivity", 12);

        OpcionCrud o49 = new OpcionCrud("049", "DescargosEliminarActivity", 13);
        OpcionCrud o50 = new OpcionCrud("050", "DescargosConsultarActivity", 13);
        OpcionCrud o51 = new OpcionCrud("051", "DescargosActualizarActivity", 13);
        OpcionCrud o52 = new OpcionCrud("052", "DescargosInsertarActivity", 13);

        OpcionCrud o53 = new OpcionCrud("053", "MotivoActualizarActivity", 14);
        OpcionCrud o54 = new OpcionCrud("054", "MotivoInsertarActivity", 14);
        OpcionCrud o55 = new OpcionCrud("055", "MotivoEliminarActivity", 14);
        OpcionCrud o56 = new OpcionCrud("056", "MotivoConsultarActivity", 14);

        //Las voy a agregar al final por si acaso. El numero 10 corresponde al crud de DetalleAutor.
        OpcionCrud o57 = new OpcionCrud("057", "DetalleAutorListaActivity",10);
        //Y el 8 al crud de Documento.
        OpcionCrud o58 = new OpcionCrud("058","DocumentoListaActivity", 8);

        //Para los nuevos webservice
        OpcionCrud o59 = new OpcionCrud("059", "CatalogoEquipoActualizarWSActivity", 15);
        OpcionCrud o60 = new OpcionCrud("060", "CatalogoEquipoEliminarWSActivity", 15);
        OpcionCrud o61 = new OpcionCrud("061", "CatalogoEquipoConsultarWSActivity", 15);
        OpcionCrud o62 = new OpcionCrud("062", "CatalogoEquipoInsertarWSActivity", 15);

        OpcionCrud o63 = new OpcionCrud("063", "DescargosEliminarWSActivity", 16);
        OpcionCrud o64 = new OpcionCrud("064", "DescargosConsultarWSActivity", 16);
        OpcionCrud o65 = new OpcionCrud("065", "DescargosActualizarWSActivity", 16);
        OpcionCrud o66 = new OpcionCrud("066", "DescargosInsertarWSActivity", 16);

        //Docente se nos habia olvidado xd
        OpcionCrud o67 = new OpcionCrud("067","DocenteMenuActivity",17);
        OpcionCrud o68 = new OpcionCrud("068","DocenteInsertarActivity", 17);
        OpcionCrud o69 = new OpcionCrud("069","DocenteConsultarActivity", 17);
        OpcionCrud o70 = new OpcionCrud("070","DocenteActualizarActivity",17);
        OpcionCrud o71 = new OpcionCrud("071","DocenteEliminarActivity", 17);

        //motivo ws
        OpcionCrud o72 = new OpcionCrud("072","MotivoInsertarWSActivity", 18);
        OpcionCrud o73 = new OpcionCrud("073","MotivoEliminarWSActivity", 18);
        OpcionCrud o74 = new OpcionCrud("074","MotivoConsultarWSActivity", 18);
        OpcionCrud o75 = new OpcionCrud("075","MotivoActualizarWSActivity", 18);

        //Agregado lista de ubicaciones para que tenga correlación con los permisos de arriba :v
        OpcionCrud o76 = new OpcionCrud("076","UbicacionesListaActivity",6);

        //Ubicaciones ws
        OpcionCrud o77 = new OpcionCrud("077","UbicacionesInsertarWSActivity",19);
        OpcionCrud o78 = new OpcionCrud("078","UbicacionesEliminarWSActivity",19);
        OpcionCrud o79 = new OpcionCrud("079","UbicacionesConsultarWSActivity",19);
        OpcionCrud o80 = new OpcionCrud("080","UbicacionesActualizarWSActivity",19);

        //Lista de TipoProducto
        OpcionCrud o81 = new OpcionCrud("081","TipoProductoListaActivity",7);

        //TipoProducto ws
        OpcionCrud o82 = new OpcionCrud("082","TipoProductoInsertarWSActivity",20);
        OpcionCrud o83 = new OpcionCrud("083","TipoProductoEliminarWSActivity",20);
        OpcionCrud o84 = new OpcionCrud("084","TipoProductoConsultarWSActivity",20);
        OpcionCrud o85 = new OpcionCrud("085","TipoProductoActualizarWSActivity",20);

        //Sustituciones ws
        OpcionCrud o86 = new OpcionCrud("086","SustitucionesInsertarWSActivity",21);
        OpcionCrud o87 = new OpcionCrud("087","SustitucionesEliminarWSActivity",21);
        OpcionCrud o88 = new OpcionCrud("088","SustitucionesConsultarWSActivity",21);
        OpcionCrud o89 = new OpcionCrud("089","SustitucionesActualizarWSActivity",21);

        // Agregar aqui las TODAS las opciones disponibles
        OpcionCrud opciones[] = {
                o1, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11, o12, o13, o14, o15, o16, o17, o18, o19, o20,
                o21, o22, o23, o24, o25, o26, o27, o28, o29, o30, o31, o32, o33, o34, o35, o36, o37, o38, o39, o40,
                o41, o42, o43, o44, o45, o46, o47, o48, o49, o50, o51, o52, o53, o54, o55, o56, o57, o58, o59, o60,
                o61, o62, o63, o64, o65, o66, o67, o68, o69, o70, o71, o72, o73, o74, o75, o76, o77, o78, o79, o80,
                o81, o82, o83, o84, o85, o86, o87, o88, o89
            };

        // Guarda todas las opciones disponibles
        for (OpcionCrud opcion: opciones)
            opcionCrudDao().insertarOpcionCrud(opcion);

        // Agregar permisos al usuario administrador
        for(OpcionCrud opcion: opciones) {
            AccesoUsuario permiso = new AccesoUsuario();
            permiso.idOpcion = opcion.idOpcion;
            permiso.usuario = admin.usuario;
            accesoUsuarioDao().insertarAccesoUsuario(permiso);
        }

        // Esto se pudo hacer en el for anterior, pero para que sea más legible se hizo aparte
        for(OpcionCrud opcion: opciones) {
            if (!opcion.desOpcion.contains("Eliminar")) {
                // NO dar permiso de eliminar
                AccesoUsuario permiso = new AccesoUsuario();
                permiso.idOpcion = opcion.idOpcion;
                permiso.usuario = normal.usuario;
                accesoUsuarioDao().insertarAccesoUsuario(permiso);
            }
        }
    }
    //Aqui se declaran los DAOs
    public abstract AccesoUsuarioDao accesoUsuarioDao();
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
