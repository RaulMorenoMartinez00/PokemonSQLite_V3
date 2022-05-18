package es.android.pokemon.servicio.interfaz;

import androidx.appcompat.app.AppCompatActivity;
import es.android.pokemon.R;
import es.android.pokemon.entidad.Pokemon;
import es.android.pokemon.repositorio.implementacion.RepositorioSQLiteImpl;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class FormActivity extends AppCompatActivity {

    private EditText np;
    private EditText dp;
    private int dbL;
    RepositorioSQLiteImpl pokemondb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_form);
    }
}
