package com.grupo13.inventario.singleton;

import android.content.Context;
import android.os.Handler;

public class MessageHandlerSingleton {

    private static MessageHandlerSingleton INSTANCE;

    private MessageHandlerSingleton() {}

    // Inicializador
    public static MessageHandlerSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageHandlerSingleton();
            INSTANCE.handler = new Handler();
        }
        return INSTANCE;
    }

    private Handler handler;

    public Handler getHandler() {
        return handler;
    }
}
