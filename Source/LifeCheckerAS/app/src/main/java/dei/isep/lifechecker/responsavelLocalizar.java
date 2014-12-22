package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.adapter.itemResponsavelLocalizar;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class responsavelLocalizar extends Activity {
	
	ListView listviewPacientes = null;
    ArrayList<paciente> listaPacientes;

    ProgressBar PBloadingMarcacao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_localizacaopacientes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.localizarPacientes);

        PBloadingMarcacao = (ProgressBar)findViewById(R.id.progressBar_action_bar);
		
		listviewPacientes = (ListView)findViewById(R.id.list_responsavel_localizacaopacientes_pacientes);
		
        listaPacientes = new ArrayList<paciente>();

        preencherListaPacientes();
	}

    private void preencherListaPacientes() {
        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        int idResp = respBDD.getIdResponsavel();
        PBloadingMarcacao.setVisibility(View.VISIBLE);
        pacienteHttp pacHttp = new pacienteHttp();
        pacHttp.retornarPacientesIdResposnavel(idResp, pacienteGetAll);
    }

    interfaceResultadoAsyncPost pacienteGetAll = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        pacienteJson pacJ = new pacienteJson(conteudo);
                        listaPacientes = pacJ.transformJsonPaciente();
                        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
                        paciBDD.deleteConteudoPaciente();
                        for (int i = 0; i < listaPacientes.size(); i++) {
                            paciBDD.inserirPacienteComId(listaPacientes.get(i));
                        }
                        preencherListaLocalizar();
                    }
                }
            });
        }
    };

    private void preencherListaLocalizar()
    {
        PBloadingMarcacao.setVisibility(View.INVISIBLE);
        itemResponsavelLocalizar adapter = new itemResponsavelLocalizar(getApplicationContext(), R.layout.responsavel_itemtipo_localizacaopacientes, listaPacientes);
        listviewPacientes.setAdapter(adapter);

        listviewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(listaPacientes.get(position).getDataLocalPaciente().compareTo("2000-01-01") != 0)
                {
                    lifeCheckerManager.getInstance().setPac(listaPacientes.get(position));
                    Intent intent = new Intent(responsavelLocalizar.this, responsavelLocalizacaoPaciente.class);
                    intent.putExtra("latitude", listaPacientes.get(position).getLatitudePaciente());
                    intent.putExtra("longitude", listaPacientes.get(position).getLongitudePaciente());
                    intent.putExtra("nome", listaPacientes.get(position).getNomePaciente());
                    intent.putExtra("apelido", listaPacientes.get(position).getApelidoPaciente());
                    intent.putExtra("hora", listaPacientes.get(position).getHoraLocalPaciente());
                    intent.putExtra("data", listaPacientes.get(position).getDataLocalPaciente());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplication(),getResources().getString(R.string.sem_local,(listaPacientes.get(position).getNomePaciente() + " " + listaPacientes.get(position).getApelidoPaciente())),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
