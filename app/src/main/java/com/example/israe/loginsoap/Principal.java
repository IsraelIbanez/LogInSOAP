package com.example.israe.loginsoap;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Principal extends Activity {

    TextView usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        usuario = (TextView) findViewById(R.id.lblNombre);
        Bundle recibe = new Bundle();
        recibe = this.getIntent().getExtras();
        usuario.setText("¡Bienvenido " + recibe.getString("usuario") +"!");
    }

    public void salir (View v){
        Intent envia = new Intent (this, MainActivity.class);
        finish();
        startActivity(envia);
    }

}
