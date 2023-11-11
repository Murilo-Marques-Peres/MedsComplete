package com.example.medscomplete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private ArrayList<InfoRemedios> lista1;
    RecyclerView rvListagem;

    public static final String ARQUIVO_MEUS_DADOS = "MeusDados";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lista1 = new ArrayList<>();
        //lista1.add(new InfoRemedios("Axonium:", "Antes de tomar " +  String.valueOf(15) +  " depois " + String.valueOf(13.5)));

        SharedPreferences sharedPreferences =
                getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float tamanhoRemedio = sharedPreferences.getFloat("tamanhoRemedio", 0);
        int tamanhoRemedioInt = Math.round(tamanhoRemedio);
        for(int x = 1; x <= tamanhoRemedioInt; x++){
            String AdressnomeRemedio = "nome" + String.valueOf(x) + ".0";
            String nomeRemedio = sharedPreferences.getString(AdressnomeRemedio, null);
            String adressDose = "dose" + String.valueOf(x) + ".0";
            float dose = sharedPreferences.getFloat(adressDose, 0);
            if(nomeRemedio!= null){
                lista1.add(new InfoRemedios(nomeRemedio, "Antes de tomar " + String.valueOf(dose) + " e depois " + String.valueOf(dose - 1)));
            }
        }


        rvListagem = findViewById(R.id.rvListagem);
        rvListagem.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListagem.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvListagem.addItemDecoration(itemDecoration);

        Adaptador adaptador = new Adaptador();
        rvListagem.setAdapter(adaptador);
        adaptador.atualizarListagemCompleta(lista1);
    }
}