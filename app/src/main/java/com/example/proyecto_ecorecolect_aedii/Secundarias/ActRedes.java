package com.example.proyecto_ecorecolect_aedii.Secundarias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_ecorecolect_aedii.R;

public class ActRedes extends AppCompatActivity {
    ImageView imgTwitter,imgWhatsApp,imgFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_redes);

        imgTwitter = findViewById(R.id.imgTwitter);
        imgFacebook = findViewById(R.id.imgFacebook);
        imgWhatsApp = findViewById(R.id.imgWhatsApp);
        click();
    }

    private void click() {
        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://twitter.com/Eco_Recolect");
            }
        });
        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.facebook.com/profile.php?id=61551245050149");
            }
        });
        imgWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://w.app/EcoRecolect");
            }
        });
    }

    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW));
    }
}
