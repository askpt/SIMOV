package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;

public class responsavelConsultar extends Activity {
	
	ListView listviewMarcacoes = null;
    ArrayList<marcacao> listaMarcacoes;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_consultarmarcacoes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.consultarMarcacao);

		
		listviewMarcacoes = (ListView)findViewById(R.id.list_responsavel_marcacoes);
		
		listaMarcacoes = new ArrayList<marcacao>();

        preencherListaMarcacoes();
	}

    private void preencherListaMarcacoes()
    {
        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        int idResp = respBDD.getIdResponsavel();
        marcacaoHttp marcaHttp = new marcacaoHttp();
        marcaHttp.retornarMarcacoes(idResp,marcacaoGetAllValidas);
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
                        /*
                        pacienteJson paciJson = new pacienteJson(conteudo);
                        listaPacientes = paciJson.transformJsonPaciente();
                        preencherLista();
                        pbLoadingList.setVisibility(View.INVISIBLE);*/
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

                startActivity(new Intent(responsavelConsultar.this, responsavelDetalhesMarcacao.class));

            }
        });
    }



}
