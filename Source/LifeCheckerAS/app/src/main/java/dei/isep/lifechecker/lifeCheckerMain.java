package dei.isep.lifechecker;


import dei.isep.lifechecker.database.estadoMarcacaoBDD;
import dei.isep.lifechecker.databaseonline.estadoMarcacaoHttp;
import dei.isep.lifechecker.json.estadoMarcacaoJson;
import dei.isep.lifechecker.model.estadoMarcacao;
import dei.isep.lifechecker.other.preferenciasAplicacao;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class lifeCheckerMain extends Activity {
	
	Intent intent = null;
    ArrayList<estadoMarcacao> listaEstMar;
    ProgressBar pbLoadingInicial;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
        pbLoadingInicial =(ProgressBar) findViewById(R.id.loading_home_aplication_respconta_loading);
		/*
		responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
		responsavel resp = new responsavel("Diogo", "Leite", "912955395", true, true, 10, 10, "diogo@hotmail.com", "1234", "13:13:13", "12-12-12");
		
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		
		pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
		paciente paciente = new paciente(1, "Maria", "tera", "andr@hotmail.com", "912542525", true, "13:13:13", "12-12-12");

		paciBDD.inserirPaciente(paciente);
		paciBDD.inserirPaciente(paciente);
		paciente pacienteB = new paciente(2654, "Maria", "Leit�o", "andr@hotmail.com", "912542525", true, "13:13:13", "12-12-12");
		paciBDD.inserirPaciente(pacienteB);
		
		alertaBDD alerBDD = new alertaBDD(getApplicationContext());
		alerta alerta = new alerta("tetetete","13:13:13", "12-12-12");
		
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);*/
		
		
		Intent novaActivity = null;
		/*pacienteBDD pacienteBDD = new pacienteBDD(getApplicationContext());
		responsavelBDD responsavelBDD = new responsavelBDD(getApplicationContext());*/

        preferenciasAplicacao prefApp = new preferenciasAplicacao(getApplicationContext());
        int configuracao = prefApp.getTipoUser();


        //0 = configuração (após instalação da aplicação)
        //1 = vista resposnavel;
        //2 = vista paciente
        //3= Depois de carregar em "sair" ou ter inserido estados de marcações
        switch (configuracao)
        {
            case 0:
                preencherEstadosmarcacoes();
                break;
            case 1:
                novaActivity = new Intent(lifeCheckerMain.this, responsavelMenu.class);
                break;
            case 2:
                novaActivity = new Intent(lifeCheckerMain.this, pacienteMenu.class);
                break;
            case 3:
                novaActivity = new Intent(lifeCheckerMain.this, configuracaoMenu.class);
                break;
        }
		if(novaActivity != null) {
            startActivity(novaActivity);
        }

	}

    public void preencherEstadosmarcacoes()
    {
        estadoMarcacaoHttp estMarHttp = new estadoMarcacaoHttp();
        estMarHttp.retornarEstadosMarcacoes(recuperarEstamarcacao);
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
