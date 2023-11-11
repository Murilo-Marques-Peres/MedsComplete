package com.example.medscomplete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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




            String adressHoje = "hoje" + String.valueOf(x) + ".0";
            float hoje = sharedPreferences.getFloat(adressHoje, 0);
            String adressNumeroDez = "numerodez" + String.valueOf(x) + ".0";
            float numeroDez = sharedPreferences.getFloat(adressNumeroDez, 0);
            float doseCalculada = numeroDez - calcularAtualNovo() * dose;
            while(doseCalculada <= 0){
                doseCalculada += 30;
            }
            if(nomeRemedio!= null){
                String doseChange1 = (String.valueOf(doseCalculada)).replace(".",",");
                String[] doseCorte = doseChange1.split(",0",-2);
                String doseChange2 = doseCorte[0];

                String dose2Change1 = (String.valueOf(doseCalculada - dose)).replace(".",",");
                String[] dose2Corte = dose2Change1.split(",0",-2);
                String dose2Change2 = dose2Corte[0];
                lista1.add(new InfoRemedios((nomeRemedio + ":"), "Antes de tomar " + doseChange2 + " e depois " + dose2Change2));
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
    public float calcularAtualNovo() {
        Calendar cal = Calendar.getInstance();
        Date data = (Date) cal.getTime();
        String a1 = String.valueOf(data);
        String mes_atual = a1.substring(4, 7);
        int soma_meses_completos = 0;
        int soma_dias_atuais = 0;
        int mJan = 31;
        int mFeb = 28;
        int mMar = 31;
        int mApril = 30;
        int mMay = 31;
        int mJune = 30;
        int mJuly = 31;
        int mAug = 31;
        int mSept = 30;
        int mOct = 31;
        int mNov = 30;
        int mDec = 31;
        ArrayList<Integer> lista_meses = new ArrayList<Integer>();
        lista_meses.add(mJan);
        lista_meses.add(mFeb);
        lista_meses.add(mMar);
        lista_meses.add(mApril);
        lista_meses.add(mMay);
        lista_meses.add(mJune);
        lista_meses.add(mJuly);
        lista_meses.add(mAug);
        lista_meses.add(mSept);
        lista_meses.add(mOct);
        lista_meses.add(mNov);
        lista_meses.add(mDec);
        ArrayList<String> lista_string_meses = new ArrayList<String>();
        lista_string_meses.add("Jan");
        lista_string_meses.add("Feb");
        lista_string_meses.add("Mar");
        lista_string_meses.add("Apri");
        lista_string_meses.add("May");
        lista_string_meses.add("Jun");
        lista_string_meses.add("Jul");
        lista_string_meses.add("Aug");
        lista_string_meses.add("Sep");
        lista_string_meses.add("Oct");
        lista_string_meses.add("Nov");
        lista_string_meses.add("Dec");
        int index_mes_atual = 0;
        String dia_atual_string = a1.substring(8, 10);
        int dia_atual_integer = Integer.parseInt(dia_atual_string);
        for (int x = 0; x < 12; x++) {
            if ((mes_atual).equals(lista_string_meses.get(x))) {
                index_mes_atual = x;
            }
        }
        for (int x2 = 0; x2 < index_mes_atual; x2++) {
            soma_meses_completos = soma_meses_completos + lista_meses.get(x2);
        }
        soma_dias_atuais = soma_meses_completos + dia_atual_integer;


        return soma_dias_atuais;
    }
}