package dei.isep.lifechecker;

import android.app.Activity;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import dei.isep.lifechecker.adapter.spinnerPacienteAdapter;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;

public class responsavelAgendar extends Activity implements OnClickListener {
	
	Spinner spinnerPacientes = null;
	Button BTagendarMarcacao = null;
	EditText ETmarcacao = null;
	EditText EThora = null;
	EditText ETdata = null;
	EditText ETlocal = null;

    GoogleMap googleMap;

    double longitude =0;
    double latitude =0;




    ArrayList<paciente> listaPac = new ArrayList<paciente>();
    ArrayList<String> listaNomePacientes = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_agendarmarcacao);
		
		spinnerPacientes = (Spinner)findViewById(R.id.spinner_responsavel_addmarcacao_pacientes);
		BTagendarMarcacao = (Button)findViewById(R.id.bt_responsavel_addmarcacao_validar_local);
		ETmarcacao = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_marcacao);
		EThora = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_hora);
		ETdata = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_data);
		ETlocal = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_local);
		
		BTagendarMarcacao.setOnClickListener(this);


        preencherCmbox();
        preencherMapa();
	}
	

	public void onClick(final View v)
	{
        marcacao marca  = new marcacao();
        String endereco = ETlocal.getText().toString();

        marca.getLatLong(endereco,interfaceListener,getApplicationContext());
	};


    interfaceAgendarMarcacao interfaceListener = new interfaceAgendarMarcacao() {
        @Override
        public void listaCoordenadas(int codigo, List<Address> enderecos) {
            if(codigo == 1)
            {
                int qtd = 0;
                qtd = enderecos.size();
                latitude = enderecos.get(0).getLatitude();
                longitude = enderecos.get(0).getLongitude();
                addMarcador();
            }
        }
    };

    public void preencherCmbox()
    {

        responsavel resp = new responsavel();
        responsavelBDD respBdd = new responsavelBDD(getApplicationContext());
        int idResponsavel = respBdd.getIdResponsavel();
        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        listaPac = paciBDD.listaPacientes(idResponsavel);


        spinnerPacientes.setAdapter(new spinnerPacienteAdapter(this, listaPac));
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
                googleMap.setMyLocationEnabled(true);

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

    private void addMarcador() {

        new Thread() {
            public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                googleMap.clear();
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude,longitude)).title(getResources().getString(R.string.marcacao));
                                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                googleMap.addMarker(marker);
                                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
                                CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
                                googleMap.moveCamera(center);
                                googleMap.animateCamera(zoom);
                            }
                        });
            }
        }.start();
    }


}
