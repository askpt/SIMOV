package dei.isep.lifechecker;


import dei.isep.lifechecker.database.alertaBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.model.alerta;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.preferenciasAplicacao;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class lifeCheckerMain extends Activity {
	
	Intent intent = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		/*
		responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
		responsavel resp = new responsavel("Diogo", "Leite", "912955395", true, true, 10, 10, "diogo@hotmail.com", "1234", "13:13:13", "12-12-12");
		
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		
		pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
		paciente paciente = new paciente(1, "Maria", "tera", "andr@hotmail.com", "912542525", true, "13:13:13", "12-12-12");

		paciBDD.inserirPaciente(paciente);
		paciBDD.inserirPaciente(paciente);
		paciente pacienteB = new paciente(2654, "Maria", "Leit�o", "andr@hotmail.com", "912542525", true, "13:13:13", "12-12-12");
		paciBDD.inserirPaciente(pacienteB);
		
		alertaBDD alerBDD = new alertaBDD(getApplicationContext());
		alerta alerta = new alerta("tetetete","13:13:13", "12-12-12");
		
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);*/
		
		
		Intent novaActivity = null;
		/*pacienteBDD pacienteBDD = new pacienteBDD(getApplicationContext());
		responsavelBDD responsavelBDD = new responsavelBDD(getApplicationContext());*/

        preferenciasAplicacao prefApp = new preferenciasAplicacao(getApplicationContext());
        int configuracao = prefApp.getTipoUser();
        configuracao = 1;

        //0 = configuração
        //1 = vista resposnavel;
        //2 = vista paciente
        switch (configuracao)
        {
            case 0:
                novaActivity = new Intent(lifeCheckerMain.this, configuracaoMenu.class);
                break;
            case 1:
                novaActivity = new Intent(lifeCheckerMain.this, responsavelMenu.class);
                break;
            case 2:
                novaActivity = new Intent(lifeCheckerMain.this, configuracaoMenu.class);
                break;
        }
		
		startActivity(novaActivity);

	}

}
