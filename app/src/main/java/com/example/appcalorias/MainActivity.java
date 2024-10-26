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

import java.util.Objects;

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
            String genero = String.valueOf(binding.spinnerGenero.getText());
            String peso = String.valueOf(binding.peso.getText());
            String altura = String.valueOf(binding.altura.getText());
            String edad = String.valueOf(binding.edad.getText());
            String meta = String.valueOf(binding.spinnerMeta.getText());
            String nivel = String.valueOf(binding.spinnerNivelFisico.getText());
            String nombreUsuario = String.valueOf(binding.nombre.getText());

            float tmb = calculoTMB(genero,peso,altura,edad,meta,nivel);

            Intent comidas = new Intent(this,ComidasActivity.class);
            comidas.putExtra("tmb",Float.toString(tmb));
            comidas.putExtra("nombreUsuario",nombreUsuario);
            startActivity(comidas);
        });
    }

    private float calculoTMB(String genero, String peso, String altura, String edad,String meta , String nivel){
        float tmb;
        if(Objects.equals(genero, "Hombre")){
            tmb = 10*Integer.parseInt(peso) + 6.25f*Integer.parseInt(altura) - 5*Integer.parseInt(edad) + 5;
        } else if (Objects.equals(genero, "Mujer")) {
            tmb = 10*Integer.parseInt(peso) + 6.25f*Integer.parseInt(altura) - 5*Integer.parseInt(edad) -161;
        }else {
            tmb = 0;
        }

        if(Objects.equals(nivel,"Poco o Ningun Ejercicio")){
            tmb = tmb*1.2f;
        } else if (Objects.equals(nivel,"Ejercicio ligero(1–3 dias por semana)")) {
            tmb = tmb*1.375f;
        } else if (Objects.equals(nivel,"Ejercicio moderado(3–5 dias por semana)")) {
            tmb = tmb*1.55f;
        } else if (Objects.equals(nivel,"Ejercicio fuerte(6–7 dias por semana)")) {
            tmb = tmb*1.725f;
        } else if (Objects.equals(nivel,"Ejercicio muy fuerte(dos veces al dia,entrenamientos muy duros)")) {
            tmb = tmb*1.9f;
        }

        if(Objects.equals(meta, "Bajar")){
            tmb = tmb - 300;
        } else if (Objects.equals(meta, "Subir")) {
            tmb = tmb + 500;
        }

        return tmb;
    }


    private void setupSpinnerGenero() {

        // Configurar AutoCompleteTextView para genero
        String[] genero = getResources().getStringArray(R.array.generos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,genero);
        binding.spinnerGenero.setAdapter(adapter);

        // Establecer el primer genero por defecto
        /*if (genero.length > 0) {
            binding.spinnerGenero.setText(genero[0], false);
        }*/
    }

    private void setupSpinnerActividadFisica() {

        // Configurar AutoCompleteTextView para nivel fisico
        String[] nivelesFisico = getResources().getStringArray(R.array.niveles_actividad_fisica);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,nivelesFisico);
        binding.spinnerNivelFisico.setAdapter(adapter);

        // Establecer el primera nivel por defecto
        /*if (nivelesFisico.length > 0) {
            binding.spinnerNivelFisico.setText(nivelesFisico[0], false);
        }*/
    }

    private void setupSpinnerMetas() {

        // Configurar AutoCompleteTextView para metas
        String[] metas = getResources().getStringArray(R.array.metas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,metas);
        binding.spinnerMeta.setAdapter(adapter);

        // Establecer la primera meta por defecto
        /*if (metas.length > 0) {
            binding.spinnerMeta.setText(metas[0], false);
        }*/
    }
}