package com.example.medscomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    Button buttonAdd;
    Button buttonRemove;

    TextView campoNome;
    TextView campoDose;
    TextView campoNomeDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        buttonAdd = findViewById(R.id.button);
        buttonRemove = findViewById(R.id.button3);
        campoNome = findViewById(R.id.editTextTextPersonName);
        campoDose = findViewById(R.id.editTextTextPersonName2);
        campoNomeDelete = findViewById(R.id.editTextTextPersonName3);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = campoNome.getText().toString();
                float dose = Float.parseFloat(campoDose.getText().toString());
                adicionarRemedio(dose, nome);
            }
        });
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeDelete = campoNomeDelete.getText().toString();
                excluirRemedio(nomeDelete);
            }
        });
    }
    public void adicionarRemedio(float dose, String nome){
        SharedPreferences sharedPreferences =
                getSharedPreferences(MainActivity2.ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
                float tamanhoRemedio = sharedPreferences.getFloat("tamanhoRemedio", 0);
                tamanhoRemedio = tamanhoRemedio + 1;
                sharedPreferences.edit().putFloat("tamanhoRemedio", tamanhoRemedio).apply();
                String adressDose = "dose" + String.valueOf(tamanhoRemedio);
                String adressNome = "nome" + String.valueOf(tamanhoRemedio);
                sharedPreferences.edit().putFloat(adressDose, dose).apply();
                sharedPreferences.edit().putString(adressNome, nome).apply();
        Toast.makeText(MainActivity3.this, "Ação de Adicionar Ativado", Toast.LENGTH_LONG).show();
    }
    public void excluirRemedio(String nome){
        SharedPreferences sharedPreferences =
                getSharedPreferences(MainActivity2.ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float tamanhoRemedio = sharedPreferences.getFloat("tamanhoRemedio", 0);
        int tamanhoRemedioInt = Math.round(tamanhoRemedio);
        for(int x = 0;x <= tamanhoRemedioInt; x++){
            String adressDose = "dose" + String.valueOf(x) + ".0";
            String adressNome = "nome" + String.valueOf(x) + ".0";
            if(nome.equals(sharedPreferences.getString(adressNome, null))){
                sharedPreferences.edit()
                        .remove(adressNome).apply();
                sharedPreferences.edit()
                        .remove(adressDose).apply();
            }
        }



        /*SharedPreferences sharedPreferences =
                getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float valorAtual = sharedPreferences.getFloat(String.
                valueOf(ano), 0);
        float novoValor = valorAtual - valor;
        if(novoValor < 0){
            novoValor = 0;
        }
        sharedPreferences.edit()
                .putFloat(String.valueOf(ano), novoValor)
                .apply();
                */

    }
}