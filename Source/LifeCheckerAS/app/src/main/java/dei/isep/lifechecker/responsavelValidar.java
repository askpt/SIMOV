package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.adapter.itemResponsavelValidar;
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

public class responsavelValidar extends Activity {
	
	ListView listviewMarcacoes = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_validarmarcacoes);
		Context context = getApplicationContext();
		
		listviewMarcacoes = (ListView)findViewById(R.id.list_responsavel_validar_listamarcacoes);
		
		ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();

        //Dados de Teste
        listaMarcacoes.add(0, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
        listaMarcacoes.add(1, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
        listaMarcacoes.add(2, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
        listaMarcacoes.add(3, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));
        listaMarcacoes.add(4, new marcacao(1, 1, "Teste", "Hora", "Data", 0, 0, "Local", "HoraUp", "DataUp"));

		itemResponsavelValidar adapter = new itemResponsavelValidar(context, R.layout.responsavel_itemtipo_validarmarcacoes, listaMarcacoes);
		listviewMarcacoes.setAdapter(adapter);

        listviewMarcacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                startActivity(new Intent(responsavelValidar.this, responsavelDetalhesMarcacao.class));

            }
        });
	}

}
