package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelAlertas;
import dei.isep.lifechecker.adapter.itemResponsavelPacientes;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.model.paciente;

public class responsavelPacientes extends Activity implements OnClickListener {
	
	ListView listviewPacientes = null;
	Button novoPaciente = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_listapacientes);
        Context context = getApplicationContext();
		
		listviewPacientes = (ListView)findViewById(R.id.list_responsavel_listapacientes_pacientes);
		novoPaciente = (Button)findViewById(R.id.bt_responsavel_listapacientes_novo);
		
		novoPaciente.setOnClickListener(this);

        ArrayList<paciente> listaPacientes = new ArrayList<paciente>();

        itemResponsavelPacientes adapter = new itemResponsavelPacientes(context, R.layout.responsavel_itemtipo_pacientes, listaPacientes);
        listviewPacientes.setAdapter(adapter);
	}
	

	public void onClick(final View v)
	{
		
	};


}
