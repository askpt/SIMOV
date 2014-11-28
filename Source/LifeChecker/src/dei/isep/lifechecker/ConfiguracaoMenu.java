package dei.isep.lifechecker;

import dei.isep.lifechecker.database.alertaBDD;
import dei.isep.lifechecker.database.estadoMarcacaoBDD;
import dei.isep.lifechecker.database.historicoAlertasBDD;
import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.model.alerta;
import dei.isep.lifechecker.model.estadoMarcacao;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class configuracaoMenu extends Activity{
	
	Button btnResponsavel = null;
	Button btnPaciente = null;
	Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracao_menu);
		
		responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
		responsavel resp = new responsavel("Diogo", "Leite", "912955395", true, true, 10, 10, "diogo@hotmail.com", "1234", "10:10:10", "12-12-12");
		
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		respBDD.inserirResponsavel(resp);
		
		pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
		paciente paciente = new paciente(1, "Maria", "tera", "andr@hotmail.com", "912542525", true, "12:12:12", "15-10-14");

		paciBDD.inserirPaciente(paciente);
		paciBDD.inserirPaciente(paciente);
		paciente pacienteB = new paciente(2654, "Maria", "Leitão", "andr@hotmail.com", "912542525", true, "12:12:12", "15-10-14");
		paciBDD.inserirPaciente(pacienteB);
		
		alertaBDD alerBDD = new alertaBDD(getApplicationContext());
		alerta alerta = new alerta("tetetete", "12:12:12", "12-12-12");
		
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		alerBDD.inserirAlerta(alerta);
		
		historicoAlertasBDD historicBDD = new historicoAlertasBDD(getApplicationContext());
		historicoAlertas histo = new historicoAlertas(1, 1, "12:12:12", "24-12-2014", 45875, 47854, "Porto", "13:13:13", "12-12-12");
		historicBDD.inserirHistoricoAlerta(histo);
		historicBDD.inserirHistoricoAlerta(histo);
		historicBDD.inserirHistoricoAlerta(histo);
		historicoAlertas histoB = new historicoAlertas(155, 1, "12:12:12", "24-12-2014", 45875, 47854, "rrrrrrrr", "13:13:13", "12-12-12");
		historicBDD.inserirHistoricoAlerta(histoB);
		historicoAlertas histoC = new historicoAlertas(1, 1333, "12:12:12", "24-12-2014", 45875, 47854, "ttttttttttttttt", "13:13:13", "12-12-12");
		historicBDD.inserirHistoricoAlerta(histoC);
		historicoAlertas histoD = new historicoAlertas(1, 3, "12:12:12", "24-12-2014", 45875, 47854, "uuuuuuuuuuu", "13:13:13", "12-12-12");
		historicBDD.inserirHistoricoAlerta(histoD);
		
		
		estadoMarcacaoBDD estMarcaBDD = new estadoMarcacaoBDD(getApplicationContext());
		estadoMarcacao estmarca = new estadoMarcacao("testetetetete", "13:13:13", "12-12-12");
		
		estMarcaBDD.inserirEstadoMarcacao(estmarca);
		estMarcaBDD.inserirEstadoMarcacao(estmarca);
		estMarcaBDD.inserirEstadoMarcacao(estmarca);
		estMarcaBDD.inserirEstadoMarcacao(estmarca);
		estMarcaBDD.inserirEstadoMarcacao(estmarca);
		
		findViewById(R.id.bt_responsavel).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_paciente).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_recuperacao).setOnClickListener(btnCarregado);
		
		marcacaoBDD marcaBDD = new marcacaoBDD(getApplicationContext());
		marcacao marca = new marcacao(); 
		
		inserirActionBar();
		
		//**************************************************************
		

		
		//************************

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
			
			intent = new Intent(configuracaoMenu.this, configuracaoFragmentos.class);
			intent.putExtra("opcao", opcao);
			
			startActivity(intent);
			//finish();
		}
	};
	
	public void inserirActionBar()
	{
		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.action_bar, null);
		TextView titulo = (TextView) mCustomView.findViewById(R.id.actionBar_Titulo);
		titulo.setText(R.string.configuracao);

		ImageButton flechaEsquerda = (ImageButton) mCustomView
				.findViewById(R.id.actionBar_btnFelchaEsquerda);
		flechaEsquerda.setVisibility(View.INVISIBLE);
		/*imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "Refresh Clicked!",
						Toast.LENGTH_LONG).show();
			}
		});*/
		
		ImageButton flechaDireita = (ImageButton) mCustomView
				.findViewById(R.id.btnFelchaEsquerda_btnFelchaDireita);
		flechaDireita.setVisibility(View.INVISIBLE);
		/*imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "Refresh Clicked!",
						Toast.LENGTH_LONG).show();
			}
		});*/

		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
	}
	

}
