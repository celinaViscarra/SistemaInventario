package com.grupo13.inventario.singleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.MainActivity;
import com.grupo13.inventario.modelo.AccesoUsuario;
import com.grupo13.inventario.modelo.OpcionCrud;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Permisos {

    private static Permisos INSTANCE;

    private List<AccesoUsuario> accesoUsuarios;

    private List<OpcionCrud> permisos = new ArrayList<>();
    private String idioma;
    private Context ctx;

    private Permisos(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(MainActivity.USER_KEY, Context.MODE_PRIVATE);
        String username = preferences.getString(MainActivity.USERNAME, null);
        idioma = preferences.getString("idioma", "es");
        ctx = context;

        if (username != null) {
            ControlBD helper = ControlBD.getInstance(context);
            accesoUsuarios =  helper.accesoUsuarioDao().accesoByUser(username);

            for (AccesoUsuario accesoUsuario: accesoUsuarios) {
                OpcionCrud opcion = helper.opcionCrudDao().consultarOpcionCrud(accesoUsuario.idOpcion);
                permisos.add(opcion);
            }
        }

    }

    public boolean has_permission(String activityName) {

        boolean has = false;
        for (OpcionCrud opcion: permisos) {
            if (opcion.desOpcion.equals(activityName)) {
                has = true;
                break;
            }
        }

        return has;
    }

    public synchronized static Permisos getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Permisos(context);
        }
        return INSTANCE;
    }

    public synchronized static void destroy() {
        INSTANCE = null;
    }

    public String getidioma(){
        return this.idioma;
    }

    public void configurarIdioma(){
        Locale locale = new Locale(idioma);
        Locale.setDefault(locale);
        Configuration config = ctx.getResources().getConfiguration();
        config.locale = locale;
        ctx.getResources().updateConfiguration(config,
                ctx.getResources().getDisplayMetrics());
    }
}
