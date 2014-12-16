package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;

public class responsavelAgendar extends Activity implements OnClickListener {
	
	Spinner spinnerPacientes = null;
	Button agendarMarcacao = null;
	EditText marcacao = null;
	EditText hora = null;
	EditText data = null;
	EditText local = null;

    ArrayList<paciente> listaPac = new ArrayList<paciente>();
    ArrayList<String> listaNomePacientes = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_agendarmarcacao);
		
		spinnerPacientes = (Spinner)findViewById(R.id.spinner_responsavel_addmarcacao_pacientes);
		agendarMarcacao = (Button)findViewById(R.id.bt_responsavel_addmarcacao_agendar);
		marcacao = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_marcacao);
		hora = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_hora);
		data = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_data);
		local = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_local);
		
		//agendarMarcacao.setOnClickListener(this);


        preencherCmbox();
	}
	

	public void onClick(final View v)
	{
		
	};

    public void preencherCmbox()
    {

        responsavel resp = new responsavel();
        responsavelBDD respBdd = new responsavelBDD(getApplicationContext());
        int idResponsavel = respBdd.getIdResponsavel();
        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        listaPac = paciBDD.listaPacientes(idResponsavel);


        for(paciente pac : listaPac)
        {
            String content = pac.getNomePaciente() + " " + pac.getApelidoPaciente();
            listaNomePacientes.add(content);
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listaNomePacientes);

        //resp = respBdd.getResponsavel();

    }

}
