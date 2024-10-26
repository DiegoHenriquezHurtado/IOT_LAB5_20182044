package com.example.appcalorias;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcalorias.databinding.ActivityNuevaComidaBinding;
import com.example.appcalorias.model.ComidaBebida;

public class NuevaComidaActivity extends AppCompatActivity {

    ActivityNuevaComidaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNuevaComidaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_comida);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        //Funcionalidad del boton regresar
        binding.btnBack.setOnClickListener(view -> {
            Intent comidas = new Intent(this,ComidasActivity.class);
            startActivity(comidas);
        });

        //Envio de comidaBebida con sus respectivos datos a comidasActivity
        binding.btnCrearProducto.setOnClickListener(view -> {
            String nombreComida = String.valueOf(binding.etNombre.getText());
            String cantidadCalorias = String.valueOf(binding.etCantidadCalorias.getText());

            ComidaBebida comidaBebida = new ComidaBebida();
            comidaBebida.setNombre(nombreComida);
            comidaBebida.setCantidadCalorias(cantidadCalorias);

            Intent comidas = new Intent(this, ComidasActivity.class);
            comidas.putExtra("comidaBebida",comidaBebida);
            startActivity(comidas);
        });
    }
}