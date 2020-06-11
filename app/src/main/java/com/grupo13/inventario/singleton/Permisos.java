package com.grupo13.inventario.singleton;

import android.content.Context;
import android.content.SharedPreferences;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.MainActivity;
import com.grupo13.inventario.modelo.AccesoUsuario;
import com.grupo13.inventario.modelo.OpcionCrud;

import java.util.ArrayList;
import java.util.List;

public class Permisos {

    private static Permisos INSTANCE;

    private List<AccesoUsuario> accesoUsuarios;

    private List<OpcionCrud> permisos = new ArrayList<>();

    private Permisos(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(MainActivity.USER_KEY, Context.MODE_PRIVATE);
        String username = preferences.getString(MainActivity.USERNAME, null);


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

}
