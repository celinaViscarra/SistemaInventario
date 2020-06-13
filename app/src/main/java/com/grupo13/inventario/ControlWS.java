package com.grupo13.inventario;

import android.content.Context;
import android.content.Entity;
import android.util.Log;
import android.widget.Toast;

import com.grupo13.inventario.modelo.Autor;
import com.grupo13.inventario.modelo.CatalogoEquipo;
import com.grupo13.inventario.modelo.DetalleAutor;
import com.grupo13.inventario.modelo.Documento;
import com.grupo13.inventario.modelo.EquipoInformatico;
import com.grupo13.inventario.modelo.Idiomas;
import com.grupo13.inventario.modelo.TipoProducto;
import com.grupo13.inventario.modelo.Ubicaciones;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ControlWS {
    public static String get(String url, Context ctx){
        String respuesta = "";
        //Estableciendo tiempo de espera del servicio
        HttpParams parametros = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parametros, 300);
        HttpConnectionParams.setSoTimeout(parametros, 5000);

        //Creando objetos de conexion
        HttpClient cliente = new DefaultHttpClient(parametros);
        HttpGet httpGet = new HttpGet(url);
        try{
            HttpResponse httpResponse = cliente.execute(httpGet);
            StatusLine estado = httpResponse.getStatusLine();
            int codigoEstado = estado.getStatusCode();
            if(codigoEstado == 200){
                System.out.println("Llegue");
                HttpEntity entidad = httpResponse.getEntity();
                respuesta = EntityUtils.toString(entidad);
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_SHORT).show();
            Log.v("Error de conexion: ", e.toString());
        }
        return respuesta;
    }

    public static String post(String url, List<NameValuePair> params, Context ctx){
        String respuesta = "";
        try{
            //Estableciendo tiempo de espera del servicio
            HttpParams parametros = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(parametros, 300);
            HttpConnectionParams.setSoTimeout(parametros, 5000);

            //Creando objetos de conexion
            HttpClient cliente = new DefaultHttpClient(parametros);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            Log.v("Peticion",url);
            Log.v("POST", httpPost.toString());
            HttpResponse response = cliente.execute(httpPost);
            StatusLine estado = response.getStatusLine();
            int codigoEstado = estado.getStatusCode();
            if(codigoEstado == 200){
                //Diferencia con la guia, usaremos post para traer datos.
                HttpEntity entidad = response.getEntity();
                respuesta = EntityUtils.toString(entidad);
            }
        }
        catch(Exception e){
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_SHORT).show();
            Log.v("Error de conexion: ", e.toString());
        }
        return respuesta;
    }

    public static List<Documento> obtenerListaDocumento(String json, Context ctx){
        List<Documento> documentos = new ArrayList<Documento>();

        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                Documento doc = new Documento();
                doc.idEscrito = obj.getInt("ESCRITO_ID");
                doc.tipo_producto_id = obj.getInt("TIPO_PRODUCTO_ID");
                doc.idioma_id = obj.getInt("IDIOMA_ID");
                doc.isbn = obj.getString("ISBN");
                doc.edicion = obj.getString("EDICION");
                doc.editorial = obj.getString("EDITORIAL");
                doc.titulo = obj.getString("TITULO");
                documentos.add(doc);
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG).show();
            return null;
        }
        return documentos;
    }

    public static List<Idiomas> obtenerListaIdioma(String json, Context ctx){
        List<Idiomas> idiomas = new ArrayList<>();

        try{
            //Primer paso, nos traemos el array de json que sacamos del WS
            JSONArray jsonArray = new JSONArray(json);
            //Realizamos una iteracion por el arreglo.
            for(int i = 0; i < jsonArray.length(); i++){
                //Recuperamos un objeto JSON del arreglo.
                JSONObject obj = jsonArray.getJSONObject(i);
                Idiomas idioma = new Idiomas();
                //TODO: siempre que extraigan un valor del json ponganlo en mayuscula
                //porque la base de datos del WS asi lo devuelve.
                idioma.idIdioma = obj.getInt("IDIOMA_ID");
                idioma.nombreIdioma = obj.getString("IDIOMA_NOMBRE");
                //Lo agregamos a la lista de idiomas.
                idiomas.add(idioma);
            }
        }  catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG).show();
            return null;
        }
        //Devolvemos los idiomas.
        return idiomas;
    }

    public static List<TipoProducto> obtenerListaTipoProducto(String json, Context ctx){
        List<TipoProducto> lista = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                TipoProducto tipo = new TipoProducto();
                tipo.idTipoProducto = obj.getInt("TIPO_PRODUCTO_ID");
                tipo.categoria_id = obj.getInt("CATEGORIA_ID");
                tipo.nomTipoProducto = obj.getString("NOMBRE_TIPO_PRODUCTO");
                lista.add(tipo);
            }
        }  catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG).show();
            return null;
        }
        return lista;
    }

    public static List<DetalleAutor> obtenerListaDetalleAutor(String json, Context ctx){
        List<DetalleAutor> lista = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                DetalleAutor detalle = new DetalleAutor();
                detalle.escrito_id = obj.getInt("ESCRITO_ID");
                detalle.idAutor = obj.getInt("IDAUTOR");
                //Para obtener booleano, la verdad no se si lo necesito, pero igua por si las diules
                detalle.esPrincipal = (obj.getInt("ESPRINCIPAL") == 1);
                lista.add(detalle);
            }
        }  catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG).show();
            return null;
        }
        return lista;
    }

    public static List<Autor> obtenerListaAutor(String json, Context ctx){
        List<Autor> lista = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                Autor autor = new Autor();
                autor.idAutor = obj.getInt("IDAUTOR");
                autor.nomAutor = obj.getString("NOMAUTOR");
                autor.apeAutor = obj.getString("APEAUTOR");
                lista.add(autor);
            }
        }  catch (Exception e) {
            Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG).show();
            return null;
        }
        return lista;
    }

    public static List<Ubicaciones> obtenerListaUbicaciones(String json, Context ctx){
        List<Ubicaciones> lista = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                Ubicaciones ubicaciones = new Ubicaciones();
                ubicaciones.idUbicacion = obj.getInt("UBICACION_ID");
                ubicaciones.nomUbicacion = obj.getString("UBICACION_NOMBRE");
                lista.add(ubicaciones);
            }
        } catch (Exception e){
            Toast.makeText(ctx,"Error en parseo de JSON",Toast.LENGTH_LONG).show();
            return null;
        }
        return lista;
    }

    public static List<CatalogoEquipo> obtenerCatalogoEquipo(String json, Context ctx) {
        List<CatalogoEquipo> lista = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                CatalogoEquipo catalogoEquipo = new CatalogoEquipo();
                catalogoEquipo.idCatalogo = obj.getString("CATALOGO_ID");
                catalogoEquipo.idMarca = obj.getString("MARCA_ID");
                catalogoEquipo.modeloEquipo = obj.getString("MODELO_EQUIPO_GENERICO");
                catalogoEquipo.memoria = obj.getInt("MEMORIA");
                catalogoEquipo.cantEquipo = obj.getInt("CANTIDAD_EQUIPO");
                lista.add(catalogoEquipo);
            }
        } catch (Exception e){
            Toast.makeText(ctx,"Error en parseo de JSON",Toast.LENGTH_LONG).show();
            return null;
        }
        return lista;
    }

    public static List<EquipoInformatico> obtenerEquipoInformatico(String json, Context ctx) {
        List<EquipoInformatico> lista = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                EquipoInformatico equipoInformatico = new EquipoInformatico();
                equipoInformatico.idEquipo = obj.getInt("EQUIPO_ID");
                equipoInformatico.tipo_producto_id = obj.getInt("TIPO_PRODUCTO_ID");
                equipoInformatico.ubicacion_id = obj.getInt("UBICACION_ID");
                equipoInformatico.catalogo_id = obj.getString("CATALOGO_ID");
                equipoInformatico.codEquipo = obj.getString("CODIGO_EQUIPO");
                equipoInformatico.fechaAdquisicion = Date.valueOf(obj.getString("FECHA_ADQUISICION"));
                equipoInformatico.estadoEquipo = obj.getString("ESTADO_EQUIPO");
                lista.add(equipoInformatico);
            }
        } catch (Exception e){
            Toast.makeText(ctx,"Error en parseo de JSON",Toast.LENGTH_LONG).show();
            return null;
        }
        return lista;
    }
}
