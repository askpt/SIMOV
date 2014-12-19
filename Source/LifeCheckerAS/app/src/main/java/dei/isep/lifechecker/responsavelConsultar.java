package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelHoje;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_consultarmarcacoes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.consultarMarcacao);
		Context context = getApplicationContext();
		
		listviewMarcacoes = (ListView)findViewById(R.id.list_responsavel_marcacoes);
		
		ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        //Dados de Teste
        listaMarcacoes.add(0, new marcacao(1, 1, "Oftalmologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(1, new marcacao(1, 1, "Psicologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(2, new marcacao(1, 1, "Fisioterapia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(3, new marcacao(1, 1, "Oftalmologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));
        listaMarcacoes.add(4, new marcacao(1, 1, "Psicologia", "12:13:14", "2014-04-01", 0, 0, "Hospital S.Joao", "HoraUp", "DataUp"));

        itemResponsavelConsultar adapter = new itemResponsavelConsultar(context, R.layout.responsavel_itemtipo_listaconsultas, listaMarcacoes);
		listviewMarcacoes.setAdapter(adapter);

        listviewMarcacoes.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                startActivity(new Intent(responsavelConsultar.this, responsavelDetalhesMarcacao.class));

            }
        });
	}

}
