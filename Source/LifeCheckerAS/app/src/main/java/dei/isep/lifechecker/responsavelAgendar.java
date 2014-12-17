package dei.isep.lifechecker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.maps.*;

import java.util.ArrayList;

import dei.isep.lifechecker.adapter.spinnerPacienteAdapter;
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

    GoogleMap googleMap;




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
        preencherMapa();
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


        spinnerPacientes.setAdapter(new spinnerPacienteAdapter(this, listaPac));
        //ArrayAdapter<paciente> arrayAdapter = new ArrayAdapter<paciente>(responsavelAgendar.this,R.layout.spinner_paciente, listaPac);
       // spinnerPacientes.setAdapter(new spinnerPacienteAdapter(this, R.layout.spinner_paciente, listaPac));
        //spinnerPacienteAdapter adapter = new spinnerPacienteAdapter(this,R.layout.spinner_paciente, listaPac);
        ///spinnerPacientes.setAdapter(adapter);


    }

    public void preencherMapa()
    {
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initilizeMap() {
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map_fragment)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }


    }


}
