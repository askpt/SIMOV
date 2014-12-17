package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelAlertas;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelAlertas extends Activity {
	
	ListView listviewAlertas = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_alertas);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.historicoDeAlertas);
        Context context = getApplicationContext();

		listviewAlertas = (ListView)findViewById(R.id.list_responsavel_alertas);

        ArrayList<historicoAlertas> listaAlertas = new ArrayList<historicoAlertas>();

        //Dados de Teste
        listaAlertas.add(0, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));
        listaAlertas.add(1, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));
        listaAlertas.add(2, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));
        listaAlertas.add(3, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));
        listaAlertas.add(4, new historicoAlertas(1, 1, 1, "12:13:14", "2014-02-01", 0.0, 0.0, "Rua das Couves", "HoraUp", "DataUp"));

        itemResponsavelAlertas adapter = new itemResponsavelAlertas(context, R.layout.responsavel_itemtipo_alertas, listaAlertas);
        listviewAlertas.setAdapter(adapter);
	}

}
