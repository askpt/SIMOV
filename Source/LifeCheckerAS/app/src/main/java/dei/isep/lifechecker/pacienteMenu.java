package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import dei.isep.lifechecker.adapter.itemPacienteProximas;
import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class pacienteMenu extends Activity{

    Intent intent = null;
    ListView listviewProximas = null;

    ArrayList<marcacao> listaMarcacoes;
    ProgressBar PBLoadMarcacoesHoje;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_menu);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.lifechecker);
        Context context = getApplicationContext();

        listviewProximas = (ListView)findViewById(R.id.list_paciente_menu_proximasconsultas);

        findViewById(R.id.bt_paciente_menu_agendarmarcacao).setOnClickListener(btnClick);
        findViewById(R.id.bt_paciente_menu_consultar).setOnClickListener(btnClick);

        ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        PBLoadMarcacoesHoje = (ProgressBar)findViewById(R.id.progressBar_action_bar);

        PBLoadMarcacoesHoje.setVisibility(View.VISIBLE);
        preencherListaMarcacoes();
    }

    private void preencherListaMarcacoes() {
        marcacaoHttp marcaHttp = new marcacaoHttp();
        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        int idPaciente = paciBDD.getIdPaicente();
        marcaHttp.retornarMarcacoesByPaciente(idPaciente, marcacaoGetAllValidasHoje);
    }

    interfaceResultadoAsyncPost marcacaoGetAllValidasHoje = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        marcacaoJson marcJ = new marcacaoJson(conteudo);
                        listaMarcacoes = marcJ.transformJsonMarcacao();
                        ArrayList<marcacao> listaMarcacoesHoje = new ArrayList<marcacao>();
                        Calendar c = Calendar.getInstance();
                        String dataHoje = c.get(Calendar.YEAR) + "-" + String.valueOf(c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
                        for(Iterator<marcacao> i = listaMarcacoes.iterator(); i.hasNext(); ) {
                            marcacao tmp = i.next();
                            if (tmp.getDataMarc().compareTo(dataHoje) > 0 && tmp.getIdEstadoMarc() == 1) {
                                listaMarcacoesHoje.add(tmp);
                            }
                        }
                        listaMarcacoes = listaMarcacoesHoje;
                        preencherListaHoje();
                    }
                }
            });
        }
    };

    private void preencherListaHoje()
    {

        itemPacienteProximas adapter = new itemPacienteProximas(getApplicationContext(), R.layout.paciente_itemtipo_proximasconsultas, listaMarcacoes);
        listviewProximas.setAdapter(adapter);
        PBLoadMarcacoesHoje.setVisibility(View.INVISIBLE);
    }

    final View.OnClickListener btnClick = new View.OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_paciente_menu_agendarmarcacao:
                    intent = new Intent(pacienteMenu.this, pacienteAgendar.class);
                    break;
                case R.id.bt_paciente_menu_consultar:
                    intent = new Intent(pacienteMenu.this, pacienteConsultar.class);
                    break;
            }
            startActivity(intent);

        }
    };
}
