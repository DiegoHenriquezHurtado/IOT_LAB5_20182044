package com.example.appcalorias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcalorias.R;
import com.example.appcalorias.model.ComidaBebida;

import java.util.List;

public class ComidasAdapter extends RecyclerView.Adapter<ComidasAdapter.ComidasViewHolder> {
    private List<ComidaBebida> listaComidas;
    private Context context;

    public List<ComidaBebida> getListaComidas() {
        return listaComidas;
    }

    public void setListaComidas(List<ComidaBebida> listaComidas) {
        this.listaComidas = listaComidas;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ComidasViewHolder extends RecyclerView.ViewHolder{
        ComidaBebida comidaBebida;

        public ComidasViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }


    @NonNull
    @Override
    public ComidasAdapter.ComidasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comida_rv,parent,false);
        return new ComidasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComidasAdapter.ComidasViewHolder holder, int position) {
        ComidaBebida c = listaComidas.get(position);
        holder.comidaBebida = c;

        TextView textViewNombre = holder.itemView.findViewById(R.id.nombreComida);
        textViewNombre.setText(c.getNombre());

        TextView textViewCantCalorias = holder.itemView.findViewById(R.id.cantCalorias);
        textViewCantCalorias.setText(c.getCantidadCalorias());
    }

    @Override
    public int getItemCount() {
        return listaComidas.size();
    }
}
