package dei.isep.lifechecker;

import android.app.Activity;
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
		
		listaHoje = (ListView)findViewById(R.id.listview_responsavel_menu_listahoje);
				
		
		findViewById(R.id.bt_responsavel_menu_agendar).setOnClickListener(btnCarregado);
		
	}
	
	final OnClickListener btnCarregado = new OnClickListener()
	{
		public void onClick(final View v)
		{
			switch(v.getId())
			{
				case R.id.bt_responsavel_menu_agendar:
					intent = new Intent(responsavelMenu.this, paraOndeVais.class);
					break;
				case R.id.bt_configuracao_menu_paciente:
					break;
				case R.id.bt_configuracao_menu_recuperacao:
					break;
			}
			
			startActivity(intent);
			
		}
	};

}
