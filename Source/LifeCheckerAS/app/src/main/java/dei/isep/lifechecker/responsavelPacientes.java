package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelPacientes extends Activity implements OnClickListener {
	
	ListView listviewPacientes = null;
	Button novoPaciente = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_listapacientes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.pacientes);
        Context context = getApplicationContext();
		
		listviewPacientes = (ListView)findViewById(R.id.list_responsavel_listapacientes_pacientes);
		novoPaciente = (Button)findViewById(R.id.bt_responsavel_listapacientes_novo);
		
		novoPaciente.setOnClickListener(this);

        ArrayList<paciente> listaPacientes = new ArrayList<paciente>();

        //Dados de Teste
        listaPacientes.add(0, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));
        listaPacientes.add(1, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));
        listaPacientes.add(2, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));
        listaPacientes.add(3, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));
        listaPacientes.add(4, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));

        itemResponsavelPacientes adapter = new itemResponsavelPacientes(context, R.layout.responsavel_itemtipo_pacientes, listaPacientes);
        listviewPacientes.setAdapter(adapter);

        listviewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                startActivity(new Intent(responsavelPacientes.this, responsavelEditarPaciente.class));

            }
        });
	}
	

	public void onClick(final View v)
	{
		
	};


}
