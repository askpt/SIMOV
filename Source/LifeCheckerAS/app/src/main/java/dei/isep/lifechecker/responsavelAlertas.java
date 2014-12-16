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

public class responsavelAlertas extends Activity {
	
	ListView listviewAlertas = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_alertas);
        Context context = getApplicationContext();

		listviewAlertas = (ListView)findViewById(R.id.list_responsavel_alertas);

        ArrayList<historicoAlertas> listaAlertas = new ArrayList<historicoAlertas>();

        itemResponsavelAlertas adapter = new itemResponsavelAlertas(context, R.layout.responsavel_itemtipo_alertas, listaAlertas);
        listviewAlertas.setAdapter(adapter);
	}

}
