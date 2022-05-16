package es.android.pokemon.servicio.interfaz;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import es.android.pokemon.R;
import es.android.pokemon.databinding.FragmentFormBinding;
import es.android.pokemon.entidad.Pokemon;
import es.android.pokemon.repositorio.implementacion.RepositorioSQLiteImpl;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {
            binding = FragmentFormBinding.inflate(inflater, container, false);
            return binding.getRoot();
    }
}
