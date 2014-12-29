package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import dei.isep.lifechecker.adapter.itemPacienteConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class pacienteConsultar extends Activity {

    ListView listviewMarcacoes = null;
    ProgressBar PBloadingActionBar = null;
    ArrayList<marcacao> listMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_consultarmarcacoes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.consultarMarcacao);
        Context context = getApplicationContext();

        listviewMarcacoes = (ListView)findViewById(R.id.list_paciente_consultarmarcacoes);

        PBloadingActionBar = (ProgressBar)findViewById(R.id.progressBar_action_bar);


        preencherLista();
    }

    private void preencherLista()
    {

        PBloadingActionBar.setVisibility(View.VISIBLE);
        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        int idPaciente = paciBDD.getIdPaicente();
        marcacaoHttp marcaHttp = new marcacaoHttp();
        marcaHttp.retornarMarcacoesByPaciente(idPaciente,marcacoesByPaciente);
    }

    interfaceResultadoAsyncPost marcacoesByPaciente = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        listMarca = new ArrayList<marcacao>();
                        marcacaoJson marcaJson = new marcacaoJson(conteudo);
                        listMarca = marcaJson.transformJsonMarcacao();
                        Collections.reverse(listMarca);
                        marcacaoBDD marcaBDD = new marcacaoBDD(getApplicationContext());
                        marcaBDD.deleteConteudoMarcacoes();
                        for (int i = 0; i < listMarca.size(); i++) {
                            marcaBDD.inserirMarcacaoSemVerificacao(listMarca.get(i));
                        }
                        listMarca = marcaBDD.listaMarcacoesOrdenada();
                       String ccc = conteudo + " ";
                        itemPacienteConsultar adapter = new itemPacienteConsultar(getApplicationContext(), R.layout.paciente_itemtipo_consultarmarcacoes, listMarca);
                        listviewMarcacoes.setAdapter(adapter);

                        listviewMarcacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                int estadoMarca = listMarca.get(position).getIdEstadoMarc();
                                if(estadoMarca == 3) {
                                    int idMarcacao = listMarca.get(position).getIdMarcacaoMarc();
                                    Intent intent = new Intent(pacienteConsultar.this, pacienteAlterarMarcacao.class);
                                    intent.putExtra("idMarcacaoPaci", idMarcacao);
                                    startActivity(intent);
                                }
                                else if (estadoMarca == 2)
                                {
                                    Toast.makeText(getApplication(), getResources().getString(R.string.erro_alterar_por_validar_marcacao_paciente), Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.erro_alterar_marcacao_paciente),Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                    else
                    {

                    }

                    PBloadingActionBar.setVisibility(View.INVISIBLE);

                }
            });
        }
    };

}
