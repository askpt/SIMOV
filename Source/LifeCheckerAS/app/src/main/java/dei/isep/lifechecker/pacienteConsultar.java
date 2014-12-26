package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemPacienteConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class pacienteConsultar extends Activity {

    ListView listviewMarcacoes = null;
    ProgressBar PBloadingActionBar = null;

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
        pacienteHttp paciHTTP = new pacienteHttp();
        paciHTTP.retornarPacienteById(idPaciente, pacienteGetPaciente);
    }

    interfaceResultadoAsyncPost pacienteGetPaciente = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        ArrayList<marcacao> listMarca = new ArrayList<marcacao>();
                       pacienteJson paciJson = new pacienteJson(conteudo);
                        marcacaoJson marcaJson = new marcacaoJson(conteudo);
                        listMarca = marcaJson.transformarPacientToMarcacoesDele();
                        
                       String ccc = conteudo + " ";

                        itemPacienteConsultar adapter = new itemPacienteConsultar(getApplicationContext(), R.layout.paciente_itemtipo_consultarmarcacoes, listMarca);
                        listviewMarcacoes.setAdapter(adapter);

                        listviewMarcacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                                startActivity(new Intent(pacienteConsultar.this, pacienteAlterarMarcacao.class));

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
