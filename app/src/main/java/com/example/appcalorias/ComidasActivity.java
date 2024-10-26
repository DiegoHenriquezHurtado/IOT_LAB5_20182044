package com.example.appcalorias;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcalorias.databinding.ActivityComidasBinding;

public class ComidasActivity extends AppCompatActivity {

    ActivityComidasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComidasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comidas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        binding.btnAddProduct.setOnClickListener(view -> {
            Intent nuevaComida = new Intent(this,NuevaComidaActivity.class);
            startActivity(nuevaComida);
        });
    }
}