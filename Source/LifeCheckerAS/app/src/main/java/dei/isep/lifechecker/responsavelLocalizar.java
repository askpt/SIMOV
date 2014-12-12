package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.adapter.itemResponsavelLocalizar;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class responsavelLocalizar extends Activity {
	
	ListView listviewPacientes = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_localizacaopacientes);
		Context context = getApplicationContext();
		
		listviewPacientes = (ListView)findViewById(R.id.list_responsavel_localizacaopacientes_pacientes);
		
		ArrayList<paciente> listaPacientes = new ArrayList<paciente>();
		
		itemResponsavelLocalizar adapter = new itemResponsavelLocalizar(context, R.layout.responsavel_itemtipo_localizacaopacientes, listaPacientes);
		listviewPacientes.setAdapter(adapter);
		
	}

}
