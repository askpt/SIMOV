package dei.isep.lifechecker;


import dei.isep.lifechecker.database.alertaBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.model.alerta;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class lifeCheckerMain extends Activity {
	
	Button btnResponsavel = null;
	Button btnPaciente = null;
	Intent intent = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracao_menu);
		
		responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
		responsavel resp = new responsavel("Diogo", "Leite", "912955395", true, true, 10, 10, "diogo@hotmail.com", "1234", false);
		
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		
		pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
		paciente paciente = new paciente(1, "Maria", "tera", "andr@hotmail.com", "912542525", true, false);

		paciBDD.inserirPaciente(paciente);
		paciBDD.inserirPaciente(paciente);
		paciente pacienteB = new paciente(2654, "Maria", "Leitão", "andr@hotmail.com", "912542525", true, false);
		paciBDD.inserirPaciente(pacienteB);
		
		alertaBDD alerBDD = new alertaBDD(getApplicationContext());
		alerta alerta = new alerta("tetetete");
		
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
			
			intent = new Intent(lifeCheckerMain.this, configuracaoMenu.class);
			intent.putExtra("opcao", opcao);
			startActivity(intent);
			finish();
		}
	};
	

}
