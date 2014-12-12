package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.itemResponsavelHoje;
import dei.isep.lifechecker.model.marcacao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class responsavelMenu extends Activity {
	
	Intent intent = null;
	ListView listaHoje = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_menu);
		Context context = getApplicationContext();
		
		listaHoje = (ListView)findViewById(R.id.listview_responsavel_menu_listahoje);
				
		findViewById(R.id.bt_responsavel_menu_agendar).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_responsavel_menu_consultar).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_responsavel_menu_validar).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_responsavel_menu_localizar).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_responsavel_menu_alertas).setOnClickListener(btnCarregado);
		findViewById(R.id.bt_responsavel_menu_pacientes).setOnClickListener(btnCarregado);
		
		ArrayList<marcacao> listaMarcacoes = new ArrayList<marcacao>();
		
		itemResponsavelHoje adapter = new itemResponsavelHoje(context, R.layout.responsavel_itemtipo_hoje, listaMarcacoes);
		listaHoje.setAdapter(adapter);
	}
	
	final OnClickListener btnCarregado = new OnClickListener()
	{
		public void onClick(final View v)
		{
			switch(v.getId())
			{
				case R.id.bt_responsavel_menu_agendar:
					intent = new Intent(responsavelMenu.this, responsavelAgendar.class);
					break;
				case R.id.bt_responsavel_menu_consultar:
					intent = new Intent(responsavelMenu.this, responsavelConsultar.class);
					break;
				case R.id.bt_responsavel_menu_validar:
					intent = new Intent(responsavelMenu.this, responsavelValidar.class);
					break;
				case R.id.bt_responsavel_menu_localizar:
					intent = new Intent(responsavelMenu.this, responsavelLocalizar.class);
					break;
				case R.id.bt_responsavel_menu_alertas:
					intent = new Intent(responsavelMenu.this, responsavelAlertas.class);
					break;
				case R.id.bt_responsavel_menu_pacientes:
					intent = new Intent(responsavelMenu.this, responsavelPacientes.class);
					break;
			}
			
			startActivity(intent);
			
		}
	};

}
