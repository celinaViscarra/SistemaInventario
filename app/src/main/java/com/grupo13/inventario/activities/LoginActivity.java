package com.grupo13.inventario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo13.inventario.ControlBD;
import com.grupo13.inventario.MainActivity;
import com.grupo13.inventario.R;
import com.grupo13.inventario.activities.AppLanguage.CountryAdapter;
import com.grupo13.inventario.activities.AppLanguage.CountryItem;
import com.grupo13.inventario.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txt_username)
    EditText txtUsername;
    @BindView(R.id.txt_password)
    EditText txtPassword;
    @BindView(R.id.spinnerIdiomas)
    Spinner spinnerIdiomas;

    ControlBD helper;
    private ArrayList<CountryItem> listaIdiomas;
    private CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        helper = ControlBD.getInstance(getApplicationContext());

        // Comprobar si es primera vez de apertura
        List<Usuario> usuarios = helper.usuarioDao().obtenerUsuarios();

        iniciarListaIdiomas();

        if (usuarios.size() == 0) {
            Usuario admin = new Usuario("admin", "admin", "Usuario Administrador");
            Usuario normal = new Usuario("user", "user", "Usuario Normal");

            helper.usuarioDao().insertarUsuario(admin);
            helper.usuarioDao().insertarUsuario(normal);
        }

    }

    public void iniciarListaIdiomas(){
        listaIdiomas = new ArrayList<>();
        listaIdiomas.add(new CountryItem("Español",R.drawable.es));
        listaIdiomas.add(new CountryItem("Inglés", R.drawable.en));

        adapter = new CountryAdapter(this, listaIdiomas);
        spinnerIdiomas.setAdapter(adapter);
    }

    public void login(View view) {

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {
            Usuario usuario = helper.usuarioDao().consultarUsuario(username);

            if (usuario != null && usuario.contra.equals(password)) {
                Log.d("TOTAL", "" + helper.usuarioDao().obtenerUsuarios().size());

                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.USER_KEY, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MainActivity.USERNAME, usuario.usuario);
                editor.commit();

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
                finish();

            }else {
                Toast.makeText(view.getContext(), R.string.singing_error, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(view.getContext(), R.string.singing_error_vacios, Toast.LENGTH_SHORT).show();
        }



    }
}
