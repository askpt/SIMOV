package dei.isep.lifechecker;


import dei.isep.lifechecker.database.AlertaBDD;
import dei.isep.lifechecker.database.PacienteBDD;
import dei.isep.lifechecker.database.ResponsavelBDD;
import dei.isep.lifechecker.model.Alerta;
import dei.isep.lifechecker.model.Paciente;
import dei.isep.lifechecker.model.Responsavel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LifeCheckerMain extends Activity {
	
	Button btnResponsavel = null;
	Button btnPaciente = null;
	Intent intent = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracao_menu);
		
		ResponsavelBDD respBDD = new ResponsavelBDD(getApplicationContext());
		Responsavel resp = new Responsavel("Diogo", "Leite", "912955395", true, true, 10, 10, "diogo@hotmail.com", "1234", false);
		
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		
		PacienteBDD paciBDD = new PacienteBDD(getApplicationContext());
		Paciente paciente = new Paciente(1, "Maria", "tera", "andr@hotmail.com", "912542525", true, false);

		paciBDD.inserirPaciente(paciente);
		paciBDD.inserirPaciente(paciente);
		Paciente pacienteB = new Paciente(2654, "Maria", "Leitão", "andr@hotmail.com", "912542525", true, false);
		paciBDD.inserirPaciente(pacienteB);
		
		AlertaBDD alerBDD = new AlertaBDD(getApplicationContext());
		Alerta alerta = new Alerta("tetetete");
		
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		
		findViewById(R.id.bt_responsavel).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_paciente).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_recuperacao).setOnClickListener(btnCarregado);
		

	}
	
	final OnClickListener btnCarregado = new OnClickListener()
	{
		public void onClick(final View v)
		{
			
			int opcao = 0;
			switch(v.getId())
			{
				case R.id.bt_responsavel:
					opcao = 1;
					break;
				case R.id.bt_paciente:
					opcao = 2;
					break;
				case R.id.bt_recuperacao:
					opcao = 3;
					break;
			}
			
			intent = new Intent(LifeCheckerMain.this, ConfiguracaoMenu.class);
			intent.putExtra("opcao", opcao);
			startActivity(intent);
			finish();
		}
	};
	

}
