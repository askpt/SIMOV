package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.adapter.itemResponsavelLocalizar;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_localizacaopacientes);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.localizarPacientes);
		Context context = getApplicationContext();
		
		listviewPacientes = (ListView)findViewById(R.id.list_responsavel_localizacaopacientes_pacientes);
		
		final ArrayList<paciente> listaPacientes = new ArrayList<paciente>();

        //Dados de Teste
        listaPacientes.add(0, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));
        listaPacientes.add(1, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));
        listaPacientes.add(2, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));
        listaPacientes.add(3, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));
        listaPacientes.add(4, new paciente(1, 1, "Nome", "Paciente", "Mail", "220000000", 0.0, 0.0, "Rua das Couves", "13:14:15", "2014-02-01", true, "HoraUp", "DataUp"));

		itemResponsavelLocalizar adapter = new itemResponsavelLocalizar(context, R.layout.responsavel_itemtipo_localizacaopacientes, listaPacientes);
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
