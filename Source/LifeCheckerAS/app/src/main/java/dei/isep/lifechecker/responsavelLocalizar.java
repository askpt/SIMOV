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
import android.widget.TextView;

public class responsavelLocalizar extends Activity {
	
	ListView listviewPacientes = null;
    ArrayList<paciente> listaPacientes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_localizacaopacientes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.localizarPacientes);
		
		listviewPacientes = (ListView)findViewById(R.id.list_responsavel_localizacaopacientes_pacientes);
		
        listaPacientes = new ArrayList<paciente>();

        preencherListaPacientes();
	}

    private void preencherListaPacientes() {
        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        int idResp = respBDD.getIdResponsavel();
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
                        preencherListaLocalizar();
                    }
                }
            });
        }
    };

    private void preencherListaLocalizar()
    {
        itemResponsavelLocalizar adapter = new itemResponsavelLocalizar(getApplicationContext(), R.layout.responsavel_itemtipo_localizacaopacientes, listaPacientes);
        listviewPacientes.setAdapter(adapter);

        listviewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                lifeCheckerManager.getInstance().setPac(listaPacientes.get(position));
                Intent intent = new Intent(responsavelLocalizar.this, responsavelLocalizacaoPaciente.class);
                startActivity(intent);
            }
        });
    }
}
