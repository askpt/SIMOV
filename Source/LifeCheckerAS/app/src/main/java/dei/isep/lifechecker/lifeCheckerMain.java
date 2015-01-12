package dei.isep.lifechecker;


import dei.isep.lifechecker.database.alertaBDD;
import dei.isep.lifechecker.database.estadoMarcacaoBDD;
import dei.isep.lifechecker.databaseonline.alertaHttp;
import dei.isep.lifechecker.databaseonline.estadoMarcacaoHttp;
import dei.isep.lifechecker.json.alertaJson;
import dei.isep.lifechecker.json.estadoMarcacaoJson;
import dei.isep.lifechecker.model.alerta;
import dei.isep.lifechecker.model.estadoMarcacao;
import dei.isep.lifechecker.other.preferenciasAplicacao;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class lifeCheckerMain extends Activity {
	
	Intent intent = null;
    ArrayList<estadoMarcacao> listaEstMar;
    ArrayList<alerta> listaAlerta;
    ProgressBar pbLoadingInicial;
    TextView txtView;

    TimerTask mTimerTask;
    Timer t = new Timer();
    final Handler handler = new Handler();


    @Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
        pbLoadingInicial =(ProgressBar) findViewById(R.id.loading_home_aplication_respconta_loading);
        txtView = (TextView) findViewById(R.id.home_txt_bem_vindo);

        inicialisar();
	}

    private void inicialisar()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        preferenciasAplicacao prefApp = new preferenciasAplicacao(getApplicationContext());
        int configuracao = prefApp.getTipoUser();

        if(configuracao == 1 || configuracao == 2 || networkInfo != null)
        {
            if(mTimerTask!=null){
                mTimerTask.cancel();
            }
            txtView.setText(getResources().getString(R.string.configuracao_inicial));
            configuracaoPrimeiro();
        }
        else
        {
            txtView.setText(getResources().getString(R.string.erro_sem_net));
            Toast toast  = Toast.makeText(this,getResources().getString(R.string.toat_des_sec_reverific),Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 250);
            toast.show();
            timerNoNet();
        }
    }

    protected void timerNoNet()
    {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        inicialisar();

                    }
                });

            }
        };
        t.schedule(mTimerTask,50000);

    }

    private void configuracaoPrimeiro()
    {
        Intent novaActivity;

        preferenciasAplicacao prefApp = new preferenciasAplicacao(getApplicationContext());
        int configuracao = prefApp.getTipoUser();

        //0 = configuração (após instalação da aplicação)
        //1 = vista resposnavel;
        //2 = vista paciente
        //3 = Depois de carregar em "sair" ou ter inserido estados de marcações
        switch (configuracao)
        {
            case 0:
                preencherEstadosMarcacoes();
                break;
            case 1:
                novaActivity = new Intent(lifeCheckerMain.this, responsavelMenu.class);
                startActivity(novaActivity);
                break;
            case 2:
                novaActivity = new Intent(lifeCheckerMain.this, pacienteMenu.class);
                startActivity(novaActivity);
                break;
            case 3:
                novaActivity = new Intent(lifeCheckerMain.this, configuracaoMenu.class);
                startActivity(novaActivity);
                break;
        }
    }

    public void preencherEstadosMarcacoes()
    {
        estadoMarcacaoHttp estMarHttp = new estadoMarcacaoHttp();
        estMarHttp.retornarEstadosMarcacoes(recuperarEstamarcacao);
    }

    public void preencherTiposAlertas()
    {
        alertaHttp alertHttp = new alertaHttp();
        alertHttp.retornarTiposAlertas(recuperarAlerta);
    }

    interfaceResultadoAsyncPost recuperarEstamarcacao = new interfaceResultadoAsyncPost()
    {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1 && conteudo.length() > 10) {
                        estadoMarcacaoJson estMarcJson = new estadoMarcacaoJson(conteudo);
                        listaEstMar = estMarcJson.transformJsonEstadoMarcacao();
                        estadoMarcacaoBDD estMarBDD = new estadoMarcacaoBDD(getApplicationContext());
                        for (int i = 0; i < listaEstMar.size(); i++) {
                            estMarBDD.inserirEstadoMarcacaoComId(listaEstMar.get(i));

                        }
                        preencherTiposAlertas();
                    }

                }
            });
        }

    };

    interfaceResultadoAsyncPost recuperarAlerta = new interfaceResultadoAsyncPost()
    {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1 && conteudo.length() > 10) {
                        alertaJson alertJson= new alertaJson(conteudo);
                        listaAlerta = alertJson.transformJsonAlertas();
                        alertaBDD alrtBdd = new alertaBDD(getApplicationContext());

                        for (int i = 0; i < listaAlerta.size(); i++) {
                            alrtBdd.inserirAlertaId(listaAlerta.get(i));
                        }
                        preferenciasAplicacao prefApp = new preferenciasAplicacao(getApplicationContext());
                        prefApp.setTipoUser(3);
                        Intent Activity = new Intent(lifeCheckerMain.this, configuracaoMenu.class);
                        startActivity(Activity);
                    }

                }
            });
        }

    };

}
