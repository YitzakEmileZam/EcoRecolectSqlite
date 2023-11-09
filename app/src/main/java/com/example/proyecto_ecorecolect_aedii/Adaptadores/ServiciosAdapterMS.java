package com.example.proyecto_ecorecolect_aedii.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto_ecorecolect_aedii.Entidades.ServiciosMS;
import com.example.proyecto_ecorecolect_aedii.R;

import java.util.ArrayList;

public class ServiciosAdapterMS extends BaseAdapter {
    Activity activity;

    ArrayList<ServiciosMS> listaSrvEco;

    public ServiciosAdapterMS(Activity activity, ArrayList<ServiciosMS>listaSrvEco) {
        this.activity = activity;
        this.listaSrvEco = listaSrvEco;
    }

    @Override
    public int getCount() {
        return listaSrvEco.size();
    }

    @Override
    public Object getItem(int position) {
        return listaSrvEco.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        //txtDescrip es => razon
        TextView txtServic,txtDescrip,txtPrec;
        ImageView imgFoto;

        if(v==null) {
            LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
            v=inflater.inflate(R.layout.lyt_item_servicio, null);
        }
        //es del item_servicio
        txtServic=v.findViewById(R.id.txtServicioMS_item);
        txtPrec=v.findViewById(R.id.txtPrecioMS_item);
        txtDescrip=v.findViewById(R.id.txtRazonMS_item);

        imgFoto=v.findViewById(R.id.imgFoto);
        ServiciosMS a=listaSrvEco.get(position);

        txtServic.setText(a.getNomSer());
        txtPrec.setText(a.getPrec());
        txtDescrip.setText(a.getRazon());
        imgFoto.setImageResource(a.getIdFoto());
        return v;

    }
}
