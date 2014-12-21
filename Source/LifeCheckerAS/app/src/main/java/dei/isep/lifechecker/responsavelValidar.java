package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelValidar;
import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class responsavelValidar extends Activity {
	
	ListView listviewMarcacoes = null;
    ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();
    ProgressBar PBloadingValidMarcacoesLista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_validarmarcacoes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.validarMarcacao);
		Context context = getApplicationContext();
		
		listviewMarcacoes = (ListView)findViewById(R.id.list_responsavel_validar_listamarcacoes);

        PBloadingValidMarcacoesLista = (ProgressBar)findViewById(R.id.progressBar_action_bar);

		ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        preencherListaMarcacoesValidas();


        //Dados de Teste
        /*listaMarcacoes.add(0, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
        listaMarcacoes.add(1, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
        listaMarcacoes.add(2, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
        listaMarcacoes.add(3, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
        listaMarcacoes.add(4, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
*/

	}

    private void preencherListaMarcacoesValidas()
    {
        PBloadingValidMarcacoesLista.setVisibility(View.VISIBLE);
        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        int idResp = respBDD.getIdResponsavel();
        marcacaoHttp marcaHttp = new marcacaoHttp();
        marcaHttp.retornarMarcacoesEstado(idResp,2 , marcacaoGetAllAguarda);
    }

    private void preencherListaConsultar()
    {

        itemResponsavelValidar adapter = new itemResponsavelValidar(getApplicationContext(), R.layout.responsavel_itemtipo_listaconsultas, listaMarcacoes);
        listviewMarcacoes.setAdapter(adapter);

        listviewMarcacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                pacienteBDD paciBdd = new pacienteBDD(getApplicationContext());
                int idPaciente = listaMarcacoes.get(position).getIdPacienteMarc();
                String nomePaciente = paciBdd.getNomeApelidoPacienteById(idPaciente);

                int idMarcacao = listaMarcacoes.get(position).getIdMarcacaoMarc();

                Intent intent = new Intent(responsavelValidar.this, responsavelDetalhesMarcacaoValidar.class);
                intent.putExtra("paciente", nomePaciente);
                intent.putExtra("idMacarcao",idMarcacao);
                startActivity(intent);

            }
        });
        PBloadingValidMarcacoesLista.setVisibility(View.INVISIBLE);
    }

    interfaceResultadoAsyncPost marcacaoGetAllAguarda = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        marcacaoJson marcJ = new marcacaoJson(conteudo);
                        listaMarcacoes.addAll(marcJ.transformJsonMarcacao());
                        marcacaoBDD marcBDD = new marcacaoBDD(getApplicationContext());
                        marcBDD.deleteMarcacoesByEstado(2);
                        for (int i = 0; i < listaMarcacoes.size(); i++) {
                            marcBDD.inserirMarcacaoComId(listaMarcacoes.get(i));
                        }

                       // preencherListaMarcacoesAguradar();
                        preencherListaConsultar();
                    }
                    else if(codigo == 1)
                    {
                        //preencherListaMarcacoesAguradar();
                        preencherListaConsultar();
                    }
                    else
                    {
                        PBloadingValidMarcacoesLista.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }
    };





}
