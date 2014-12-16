package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelConsultar;
import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.model.marcacao;
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
		Context context = getApplicationContext();
		
		listviewMarcacoes = (ListView)findViewById(R.id.list_responsavel_marcacoes);
		
		ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        //listaMarcacoes.add(0, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
		
		itemResponsavelConsultar adapter = new itemResponsavelConsultar(context, R.layout.responsavel_itemtipo_listaconsultas, listaMarcacoes);
		listviewMarcacoes.setAdapter(adapter);

        listviewMarcacoes.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(responsavelConsultar.this, responsavelDetalhesMarcacao.class);
                startActivity(intent);

            }
        });
	}

}
