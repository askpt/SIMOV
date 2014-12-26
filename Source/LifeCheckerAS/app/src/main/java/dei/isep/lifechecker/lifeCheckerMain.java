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
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class lifeCheckerMain extends Activity {
	
	Intent intent = null;
    ArrayList<estadoMarcacao> listaEstMar;
    ArrayList<alerta> listaAlerta;
    ProgressBar pbLoadingInicial;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
        pbLoadingInicial =(ProgressBar) findViewById(R.id.loading_home_aplication_respconta_loading);

		
		Intent novaActivity;
		/*pacienteBDD pacienteBDD = new pacienteBDD(getApplicationContext());
		responsavelBDD responsavelBDD = new responsavelBDD(getApplicationContext());*/

        preferenciasAplicacao prefApp = new preferenciasAplicacao(getApplicationContext());
        int configuracao = prefApp.getTipoUser();

        //0 = configuração (após instalação da aplicação)
        //1 = vista resposnavel;
        //2 = vista paciente
        //3 = Depois de carregar em "sair" ou ter inserido estados de marcações
        switch (configuracao)
        {
            case 0:
                preencherEstadosmarcacoes();
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

    public void preencherEstadosmarcacoes()
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
                            estMarBDD.inserirEstadoMarcacao(listaEstMar.get(i));

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
                            alrtBdd.inserirAlerta(listaAlerta.get(i));
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
