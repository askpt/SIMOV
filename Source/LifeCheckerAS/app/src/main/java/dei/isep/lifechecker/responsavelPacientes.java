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

public class responsavelPacientes extends Activity implements OnClickListener {
	
	ListView listaPacientes = null;
	Button novoPaciente = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_listapacientes);
		
		listaPacientes = (ListView)findViewById(R.id.list_responsavel_listapacientes_pacientes);
		novoPaciente = (Button)findViewById(R.id.bt_responsavel_listapacientes_novo);
		
		novoPaciente.setOnClickListener(this);
	}
	

	public void onClick(final View v)
	{
		
	};

}
