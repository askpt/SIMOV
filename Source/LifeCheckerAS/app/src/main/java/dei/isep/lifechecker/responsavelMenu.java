package dei.isep.lifechecker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.alarme.localizacaoAlarm;
import dei.isep.lifechecker.alarme.notificacaoResp;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelMenu extends Activity {

    Intent intent = null;
    ListView listaHoje = null;
    ArrayList<marcacao> listaMarcacoes;
    final String EXIT_MESSAGE = "Tem a certeza que pretende sair?";
    int idResp = -1;

    ProgressBar PBLoadMarcaHojeResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_menu);

        //lifeCheckerManager.getInstance().setEnviarLocalizacao(true);95
        Log.i("notificacao", "antes");
        if (lifeCheckerManager.getInstance().getaVerificarNotificacao() == false)
        {
            lifeCheckerManager.getInstance().setaVerificarNotificacao(true);
            Intent intentLocalization = new Intent(responsavelMenu.this, notificacaoResp.class);
            startService(intentLocalization);
        }
        Log.i("notificacao", " passou");

        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.lifechecker);
        Context context = getApplicationContext();

        PBLoadMarcaHojeResp = (ProgressBar)findViewById(R.id.progressBar_action_bar);

        PBLoadMarcaHojeResp.setVisibility(View.VISIBLE);

        listaHoje = (ListView) findViewById(R.id.listview_responsavel_menu_listahoje);

        findViewById(R.id.bt_responsavel_menu_agendar).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_responsavel_menu_consultar).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_responsavel_menu_validar).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_responsavel_menu_localizar).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_responsavel_menu_alertas).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_responsavel_menu_pacientes).setOnClickListener(btnCarregado);

        listaMarcacoes = new ArrayList<marcacao>();

        preencherListaMarcacoes();

    }

    private void preencherListaMarcacoes() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null) {
            responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
            idResp = respBDD.getIdResponsavel();
            lifeCheckerManager.getInstance().setIdResponsavel(idResp);
            marcacaoHttp marcaHttp = new marcacaoHttp();
            marcaHttp.retornarMarcacoesEstado(idResp, 1, marcacaoGetAllValidasHoje);
        }
        else
        {
            Toast.makeText(this,getResources().getString(R.string.erro_sem_net_info),Toast.LENGTH_LONG).show();
            PBLoadMarcaHojeResp.setVisibility(View.INVISIBLE);
        }
    }

    interfaceResultadoAsyncPost marcacaoGetAllValidasHoje = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dformate = new SimpleDateFormat("yyyy-MM-dd");
                        String dataFormatada = dformate.format(c.getTime());

                        SimpleDateFormat dformateTime = new SimpleDateFormat("HH:mm");
                        String dataFormatadaTime = dformateTime.format(c.getTime());
                        //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                        marcacaoJson marcJ = new marcacaoJson(conteudo);
                        listaMarcacoes = marcJ.transformJsonMarcacao();
                        ArrayList<marcacao> listaMarcacoesHoje = new ArrayList<marcacao>();
                        //Calendar c = Calendar.getInstance();
                        //String dataHoje = c.get(Calendar.YEAR) + "-" + String.valueOf(c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
                        for(Iterator<marcacao> i = listaMarcacoes.iterator(); i.hasNext(); ) {
                            marcacao tmp = i.next();
                            if (tmp.getDataMarc().contentEquals(dataFormatada) && tmp.getHoraMarc().compareTo(dataFormatadaTime) > 0)
                                listaMarcacoesHoje.add(tmp);
                        }
                        listaMarcacoes = listaMarcacoesHoje;
                        preencherListaHoje();
                    }
                    else {
                        PBLoadMarcaHojeResp.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    };

    private void preencherListaHoje()
    {
        itemResponsavelHoje adapter = new itemResponsavelHoje(getApplicationContext(), R.layout.responsavel_itemtipo_hoje, listaMarcacoes);
        listaHoje.setAdapter(adapter);
        PBLoadMarcaHojeResp.setVisibility(View.INVISIBLE);
    }

    final OnClickListener btnCarregado = new OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.bt_responsavel_menu_agendar:
                    intent = new Intent(responsavelMenu.this, responsavelAgendar.class);
                    break;
                case R.id.bt_responsavel_menu_consultar:
                    intent = new Intent(responsavelMenu.this, responsavelConsultar.class);
                    break;
                case R.id.bt_responsavel_menu_validar:
                    intent = new Intent(responsavelMenu.this, responsavelValidar.class);
                    break;
                case R.id.bt_responsavel_menu_localizar:
                    intent = new Intent(responsavelMenu.this, responsavelLocalizar.class);
                    break;
                case R.id.bt_responsavel_menu_alertas:
                    intent = new Intent(responsavelMenu.this, responsavelAlertas.class);
                    break;
                case R.id.bt_responsavel_menu_pacientes:
                    intent = new Intent(responsavelMenu.this, responsavelPacientes.class);
                    break;
            }
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo != null) {
                    intent.putExtra("idResponsavel",idResp);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.erro_sem_net), Toast.LENGTH_LONG).show();
                }



        }
    };

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(EXIT_MESSAGE)
                .setCancelable(false)
                .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


}
