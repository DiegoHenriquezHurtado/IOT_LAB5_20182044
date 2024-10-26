package com.example.appcalorias;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcalorias.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        setupSpinnerGenero();
        setupSpinnerActividadFisica();
        setupSpinnerMetas();

        binding.btnRegistrar.setOnClickListener(view -> {
            Intent comidas = new Intent(this,ComidasActivity.class);
            startActivity(comidas);
        });
    }


    private void setupSpinnerGenero() {

        // Configurar AutoCompleteTextView para genero
        String[] genero = getResources().getStringArray(R.array.generos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,genero);
        binding.spinnerGenero.setAdapter(adapter);

        // Establecer el primer genero por defecto
        if (genero.length > 0) {
            binding.spinnerGenero.setText(genero[0], false);
        }
    }

    private void setupSpinnerActividadFisica() {

        // Configurar AutoCompleteTextView para nivel fisico
        String[] nivelesFisico = getResources().getStringArray(R.array.niveles_actividad_fisica);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,nivelesFisico);
        binding.spinnerNivelFisico.setAdapter(adapter);

        // Establecer el primera nivel por defecto
        if (nivelesFisico.length > 0) {
            binding.spinnerNivelFisico.setText(nivelesFisico[0], false);
        }
    }

    private void setupSpinnerMetas() {

        // Configurar AutoCompleteTextView para metas
        String[] metas = getResources().getStringArray(R.array.metas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,metas);
        binding.spinnerMeta.setAdapter(adapter);

        // Establecer la primera meta por defecto
        if (metas.length > 0) {
            binding.spinnerMeta.setText(metas[0], false);
        }
    }
}