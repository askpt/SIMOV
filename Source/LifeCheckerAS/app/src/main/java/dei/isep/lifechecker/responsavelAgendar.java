package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class responsavelAgendar extends Activity implements OnClickListener {
	
	Spinner spinnerPacientes = null;
	Button agendarMarcacao = null;
	EditText marcacao = null;
	EditText hora = null;
	EditText data = null;
	EditText local = null;
	
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
		
		agendarMarcacao.setOnClickListener(this);
	}
	

	public void onClick(final View v)
	{
		
	};

}
