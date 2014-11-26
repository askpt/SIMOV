package dei.isep.lifechecker;

import dei.isep.lifechecker.adapter.FragmentAdapter;
import dei.isep.lifechecker.database.PacienteBDD;
import dei.isep.lifechecker.database.ResponsavelBDD;
import dei.isep.lifechecker.model.Paciente;
import dei.isep.lifechecker.model.Responsavel;
import android.app.Activity;
import android.os.Bundle;

public class LifeCheckerMain extends Activity {
	
	private FragmentAdapter fragmentAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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

	}
}
