package com.example.medscomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {
    Button buttonAdd;
    Button buttonRemove;

    TextView campoNome;
    TextView campoDose;
    TextView campoNomeDelete;
    TextView campoHoje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        buttonAdd = findViewById(R.id.button);
        buttonRemove = findViewById(R.id.button3);
        campoNome = findViewById(R.id.editTextTextPersonName);
        campoDose = findViewById(R.id.editTextTextPersonName2);
        campoHoje = findViewById(R.id.editTextTextPersonName5);
        campoNomeDelete = findViewById(R.id.editTextTextPersonName3);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = campoNome.getText().toString();
                float dose = Float.parseFloat(campoDose.getText().toString());
                float hoje = Float.parseFloat(campoHoje.getText().toString());

                adicionarRemedio(dose, hoje, nome);
                campoNome.setText("");
                campoDose.setText("");
                campoHoje.setText("");
            }
        });
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeDelete = campoNomeDelete.getText().toString();
                excluirRemedio(nomeDelete);
                campoNomeDelete.setText("");
            }
        });
    }
    public void adicionarRemedio(float dose, float hoje, String nome){
        SharedPreferences sharedPreferences =
                getSharedPreferences(MainActivity2.ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
                float tamanhoRemedio = sharedPreferences.getFloat("tamanhoRemedio", 0);
                tamanhoRemedio = tamanhoRemedio + 1;
                sharedPreferences.edit().putFloat("tamanhoRemedio", tamanhoRemedio).apply();
                String adressDose = "dose" + String.valueOf(tamanhoRemedio);
                String adressNome = "nome" + String.valueOf(tamanhoRemedio);
                String adressHoje = "hoje" + String.valueOf(tamanhoRemedio);
                //////////////////////////////////////////////////////////////
                MainActivity2 mainActivity2 = new MainActivity2();
                float diasAtuais = mainActivity2.calcularAtualNovo();
                float numeroDez = hoje + diasAtuais * dose;
                while(numeroDez > 30){
                    numeroDez -= 30;
                }
                String adressNumeroDez = "numerodez" + String.valueOf(tamanhoRemedio);
                        sharedPreferences.edit().putFloat(adressNumeroDez, numeroDez).apply();
                ////////////////////////////////////////////////////////////////
                sharedPreferences.edit().putString(adressNome, nome).apply();
                sharedPreferences.edit().putFloat(adressHoje, hoje).apply();
                sharedPreferences.edit().putFloat(adressDose, dose).apply();


        Toast.makeText(MainActivity3.this, "Ação de Adicionar Ativado", Toast.LENGTH_LONG).show();
    }
    public void excluirRemedio(String nome) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(MainActivity2.ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float tamanhoRemedio = sharedPreferences.getFloat("tamanhoRemedio", 0);
        int tamanhoRemedioInt = Math.round(tamanhoRemedio);
        for (int x = 0; x <= tamanhoRemedioInt; x++) {
            String adressDose = "dose" + String.valueOf(x) + ".0";
            String adressNome = "nome" + String.valueOf(x) + ".0";
            String adressHoje = "hoje" + String.valueOf(x) + ".0";
            String adressNumeroDez = "numerodez" + String.valueOf(x) + ".0";
            String nomePesquisado = sharedPreferences.getString(adressNome, null);
            if (nome.equals(nomePesquisado)) {
                sharedPreferences.edit()
                        .remove(adressNome).apply();
                sharedPreferences.edit()
                        .remove(adressDose).apply();
                sharedPreferences.edit()
                        .remove(adressNumeroDez).apply();
            }
        }
    }
}