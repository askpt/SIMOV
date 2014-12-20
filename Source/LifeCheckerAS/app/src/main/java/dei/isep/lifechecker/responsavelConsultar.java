package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

public class responsavelConsultar extends Activity {
	
	ListView listviewMarcacoes = null;
    ArrayList<marcacao> listaMarcacoes;
    ProgressBar PBloadingActionBar;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_consultarmarcacoes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.consultarMarcacao);
        PBloadingActionBar = (ProgressBar)findViewById(R.id.progressBar_action_bar);

		
		listviewMarcacoes = (ListView)findViewById(R.id.list_responsavel_marcacoes);
		
		listaMarcacoes = new ArrayList<marcacao>();

        preencherListaMarcacoes();
	}

    private void preencherListaMarcacoes()
    {
        PBloadingActionBar.setVisibility(View.VISIBLE);
        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        int idResp = respBDD.getIdResponsavel();
        marcacaoHttp marcaHttp = new marcacaoHttp();
        marcaHttp.retornarMarcacoesEstado(idResp, 1, marcacaoGetAllValidas);
    }

    interfaceResultadoAsyncPost marcacaoGetAllValidas = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        marcacaoJson marcJ = new marcacaoJson(conteudo);
                        listaMarcacoes = marcJ.transformJsonMarcacao();
                        preencherListaConsultar();


                        marcacaoBDD marcBDD = new marcacaoBDD(getApplicationContext());
                        marcBDD.deleteMarcacoesByEstado(1);
                        for (int i = 0; i < listaMarcacoes.size(); i++) {
                            marcBDD.inserirMarcacaoComId(listaMarcacoes.get(i));
                        }
                        /*
                        pacienteJson paciJson = new pacienteJson(conteudo);
                        listaPacientes = paciJson.transformJsonPaciente();
                        preencherLista();
                        pbLoadingList.setVisibility(View.INVISIBLE);*/
                    }
                    else
                    {
                        PBloadingActionBar.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }
    };

    private void preencherListaConsultar()
    {
        itemResponsavelConsultar adapter = new itemResponsavelConsultar(getApplicationContext(), R.layout.responsavel_itemtipo_listaconsultas, listaMarcacoes);
        listviewMarcacoes.setAdapter(adapter);

        listviewMarcacoes.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                pacienteBDD paciBdd = new pacienteBDD(getApplicationContext());
                int idPaciente = listaMarcacoes.get(position).getIdPacienteMarc();
                String nomePaciente = paciBdd.getNomeApelidoPacienteById(idPaciente);

                int idMarcacao = listaMarcacoes.get(position).getIdMarcacaoMarc();

                Intent intent = new Intent(responsavelConsultar.this, responsavelDetalhesMarcacao.class);
                intent.putExtra("paciente", nomePaciente);
                intent.putExtra("idMacarcao",idMarcacao);
                startActivity(intent);

            }
        });

        PBloadingActionBar.setVisibility(View.INVISIBLE);
    }



}
