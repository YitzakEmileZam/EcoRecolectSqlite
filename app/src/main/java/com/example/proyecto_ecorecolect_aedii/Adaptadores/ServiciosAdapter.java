package com.example.proyecto_ecorecolect_aedii.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_ecorecolect_aedii.Entidades.Cliente;
import com.example.proyecto_ecorecolect_aedii.Entidades.Servicios;
import com.example.proyecto_ecorecolect_aedii.FragmentsMuestra.DetalleServicioFragment;
import com.example.proyecto_ecorecolect_aedii.R;

import java.util.ArrayList;

public class ServiciosAdapter extends RecyclerView.Adapter<ServiciosAdapter.ViewHolder> {
    ArrayList<Servicios> ListaDeServicios;

    private Context context;

    public ServiciosAdapter(ArrayList<Servicios> ListaDeServicios, Context context) {
        this.ListaDeServicios = ListaDeServicios;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_servicios,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.nombre.setText(ListaDeServicios.get(position).getNombreServicio());
        holder.precio.setText("S/"+String.valueOf(ListaDeServicios.get(position).getPrecio()));

        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(ListaDeServicios.get(position).getImagen(),
                        "drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.imagen);

        holder.addBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reemplazar el fragmento actual con el fragmento DetalleFragment
                // Realizar la transición al otro fragmento
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                Fragment newFragment = new DetalleServicioFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", ListaDeServicios.get(position));
                newFragment.setArguments(bundle);
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {   return ListaDeServicios.size();   }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, precio;
        ImageView imagen;
        ImageView addBtn2;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.title);
            imagen = itemView.findViewById(R.id.pic);
            precio = itemView.findViewById(R.id.fee);
            addBtn2 = itemView.findViewById(R.id.addBtn2);
        }
    }
}
