package com.example.appcalorias;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appcalorias.adapter.ComidasAdapter;
import com.example.appcalorias.databinding.ActivityComidasBinding;
import com.example.appcalorias.model.ComidaBebida;

import com.example.appcalorias.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ComidasActivity extends AppCompatActivity {

    ActivityComidasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Usuario usuario = new Usuario();
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        ArrayList<ComidaBebida> listaComidas = new ArrayList<>();

        super.onCreate(savedInstanceState);
        binding = ActivityComidasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        Intent intent = getIntent();

        if(intent.hasExtra("tmb")){
            String tmb = intent.getStringExtra("tmb");
            String nombreUsuario = intent.getStringExtra("nombreUsuario");
            binding.cantRecomendada.setText(tmb + " cal");
            binding.nombreUsuario.setText(nombreUsuario);

            //Creamos el usuario
            usuario.setNombre(nombreUsuario);
            usuario.setTmb(tmb);
            usuario.setListaComidasBebidas(listaComidas);

            //Seteamos en la lista de usuarios del sistema
            listaUsuarios.add(usuario);
            guardarArchivoComoJsonUsuario(listaUsuarios);
        }

        if(intent.hasExtra("comidaBebida")){
            ComidaBebida comidaBebida = (ComidaBebida) intent.getSerializableExtra("comidaBebida");
            //listaComidas = leerArchivoTexto();
            //listaComidas.add(comidaBebida);
            //guardarArchivoComoJson(listaComidas);
            listaUsuarios = leerArchivoTextoUsuario();
            listaUsuarios.get(listaUsuarios.size()-1).getListaComidasBebidas().add(comidaBebida);
            guardarArchivoComoJsonUsuario(listaUsuarios);
        }

        //Mostrar nombre y calorias cuando se regrese a este activity
        listaUsuarios = leerArchivoTextoUsuario();
        binding.nombreUsuario.setText(listaUsuarios.get(listaUsuarios.size()-1).getNombre());
        String calRecomendada = listaUsuarios.get(listaUsuarios.size()-1).getTmb();
        binding.cantRecomendada.setText(calRecomendada);

        //Recepcionar las comidas nuevas y listarlas con recycler view
        ComidasAdapter adapter = new ComidasAdapter();
        adapter.setContext(ComidasActivity.this);
        //listaComidas = leerArchivoTexto();
        listaComidas = listaUsuarios.get(listaUsuarios.size()-1).getListaComidasBebidas();
        adapter.setListaComidas(listaComidas);
        binding.rvComidas.setAdapter(adapter);
        binding.rvComidas.setLayoutManager(new LinearLayoutManager(ComidasActivity.this));

        String calConsumidas = calculoCalConsumidas(listaComidas);
        binding.cantConsumida.setText(calConsumidas);

        //Notificacion para cuando se excede las calorias
        if(Float.parseFloat(calConsumidas) > Float.parseFloat(calRecomendada)){
            createNotificationChannel();
            lanzarNotificacion();
        }

        //Boton para agregar comida o bebida
        binding.btnAddProduct.setOnClickListener(view -> {
            Intent nuevaComida = new Intent(this,NuevaComidaActivity.class);
            startActivity(nuevaComida);
        });
    }

    public String calculoCalConsumidas(ArrayList<ComidaBebida> listaComidas){
        float calConsumidas = 0f;
        for (ComidaBebida comidaBebida:listaComidas){
            calConsumidas = calConsumidas+ Float.parseFloat(comidaBebida.getCantidadCalorias());
        }
        return Float.toString(calConsumidas);
    }

    public void guardarArchivoComoJson(ArrayList<ComidaBebida> listaComidas){
        //Convertimos el arreglo a un String (para guardarlo como json)
        Gson gson = new Gson();
        String listaComidasJson = gson.toJson(listaComidas);

        //nombre del archivo a guardar
        String fileNameJson = "listaComidasJson";

        //Se utiliza la clase FileOutPutStream para poder almacenar en android
        try(FileOutputStream fileOutputStream = this.openFileOutput(fileNameJson, Context.MODE_PRIVATE);
            FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())){
            fileWriter.write(listaComidasJson);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<ComidaBebida> leerArchivoTexto(){
        String fileName = "listaComidasJson";
        ArrayList<ComidaBebida> listaComidas = new ArrayList<>();

        try (FileInputStream fileInputStream = openFileInput(fileName);
             FileReader fileReader = new FileReader(fileInputStream.getFD());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String jsonData = bufferedReader.readLine();
            Gson gson = new Gson();

            // Utilizamos TypeToken para especificar que queremos un ArrayList de ComidaBebida
            Type listType = new TypeToken<ArrayList<ComidaBebida>>() {}.getType();

            listaComidas = gson.fromJson(jsonData,listType);
        } catch (IOException e){
            e.printStackTrace();
        }

        return listaComidas;
    }

    public void guardarArchivoComoJsonUsuario(ArrayList<Usuario> listaUsuarios){
        //Convertimos el arreglo a un String (para guardarlo como json)
        Gson gson = new Gson();
        String listaUsuariosJson = gson.toJson(listaUsuarios);

        //nombre del archivo a guardar
        String fileNameJson = "listaUsuariosJson";

        //Se utiliza la clase FileOutPutStream para poder almacenar en android
        try(FileOutputStream fileOutputStream = this.openFileOutput(fileNameJson, Context.MODE_PRIVATE);
            FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())){
            fileWriter.write(listaUsuariosJson);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Usuario> leerArchivoTextoUsuario(){
        String fileName = "listaUsuariosJson";
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        try (FileInputStream fileInputStream = openFileInput(fileName);
             FileReader fileReader = new FileReader(fileInputStream.getFD());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String jsonData = bufferedReader.readLine();
            Gson gson = new Gson();

            // Utilizamos TypeToken para especificar que queremos un ArrayList de ComidaBebida
            Type listType = new TypeToken<ArrayList<Usuario>>() {}.getType();

            listaUsuarios = gson.fromJson(jsonData,listType);
        } catch (IOException e){
            e.printStackTrace();
        }

        return listaUsuarios;
    }

    String channelId = "channelDefaultPri";
    public void createNotificationChannel() {
        //android.os.Build.VERSION_CODES.O == 26
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Canal notificaciones default",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canal para notificaciones con prioridad default");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            askPermission();
        }
    }
    public void askPermission(){
        //android.os.Build.VERSION_CODES.TIRAMISU == 33
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ComidasActivity.this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }

    public void lanzarNotificacion() {
        Intent intent = new Intent(this, ComidasActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Exceso de consumo de calorias")
                .setContentText("Se recomienda hacer mas ejercicio o disminuir su consumo de calorias para la proxima comida")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }
}