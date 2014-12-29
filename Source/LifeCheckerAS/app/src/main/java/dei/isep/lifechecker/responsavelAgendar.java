package dei.isep.lifechecker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dei.isep.lifechecker.adapter.spinnerPacienteAdapter;
import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.json.locationJson;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.validarDados;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelAgendar extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
	
	Spinner spinnerPacientes;
	Button BTvalidarLocal;
    Button BTaddMarcacao;
	EditText ETmarcacao;
	EditText EThora;
	EditText ETdata;
	EditText ETlocal;

    TextView TVcomentariosAddMarca;

    ProgressBar PBloadingMarcacao;

    GoogleMap googleMap;

    double longitude =0;
    double latitude =0;

    private marcacao mar;
    private validarDados vd = new validarDados();

    ArrayList<paciente> listaPac = new ArrayList<paciente>();

    LatLng ltlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responsavel_agendarmarcacao);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.agendarMarcacao);
		
		spinnerPacientes = (Spinner)findViewById(R.id.spinner_responsavel_addmarcacao_pacientes);
		BTvalidarLocal = (Button)findViewById(R.id.bt_responsavel_addmarcacao_validar_local);
        BTaddMarcacao = (Button)findViewById(R.id.bt_responsavel_addmarcacao_agendar);
		ETmarcacao = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_marcacao);
		EThora = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_hora);
		ETdata = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_data);
		ETlocal = (EditText)findViewById(R.id.tb_responsavel_addmarcacao_local);

        PBloadingMarcacao = (ProgressBar)findViewById(R.id.progressBar_action_bar);

        TVcomentariosAddMarca = (TextView)findViewById(R.id.tv_comentario_add_marcacao);

        findViewById(R.id.bt_responsavel_addmarcacao_validar_local).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_responsavel_addmarcacao_agendar).setOnClickListener(btnCarregado);

        PBloadingMarcacao.setVisibility(View.INVISIBLE);
        BTaddMarcacao.setEnabled(false);
        TVcomentariosAddMarca.setVisibility(View.INVISIBLE);
        preencherCmbox();
        preencherMapa();
	}


    final OnClickListener btnCarregado = new OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_responsavel_addmarcacao_validar_local:
                    verLocal();
                    break;
                case R.id.bt_responsavel_addmarcacao_agendar:
                    adicionarMarcacao();
                    break;
            }
        }
    };

    public void adicionarMarcacao()
    {
        int idPaciente = (int)spinnerPacientes.getSelectedItemId();
        String tipoMarcacao = ETmarcacao.getText().toString();
        String hora = EThora.getText().toString();
        String data = ETdata.getText().toString();
        String local = ETlocal.getText().toString();
        validarDados validar = new validarDados();

        if(validar.validarTipoMarcacao(tipoMarcacao) &&
                validar.validarTempo24H(hora) &&
                validar.validarDataAMD(data) &&
                validar.validarLocalidade(local) &&
                validar.validarLatitude(latitude) &&
                validar.validarLongitude(longitude))
        {
            hora +=":00";
            PBloadingMarcacao.setVisibility(View.VISIBLE);
            BTaddMarcacao.setEnabled(false);
            mar = new marcacao(idPaciente,1,tipoMarcacao,hora,data,latitude,longitude,local);
            marcacaoHttp marHttp = new marcacaoHttp();
            marHttp.addmarcacao(mar, resultadoAddMarcacao);
        }
        else
        {
            TVcomentariosAddMarca.setText(getResources().getString(R.string.err_dados_formularios));
        }

    }

    interfaceResultadoAsyncPost resultadoAddMarcacao = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1) {
                        PBloadingMarcacao.setVisibility(View.INVISIBLE);
                        String idValor = conteudo.replaceAll("[\\r\\n]+", "");
                        int idMarcacao  = Integer.valueOf(idValor);
                        mar.setIdMarcacaoMarc(idMarcacao);

                        marcacaoBDD marcBDD = new marcacaoBDD(getApplicationContext());
                        marcBDD.inserirMarcacaoComId(mar);

                        Intent intent = new Intent(getApplication(), responsavelMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }
                    else
                    {
                        BTaddMarcacao.setEnabled(true);
                    }
                }
            });
        }
    };


	

	public void verLocal()
	{

        validarDados validar = new validarDados();
        if(validar.validarLocalidade(ETlocal.getText().toString()))
        {
            BTaddMarcacao.setEnabled(false);
            marcacao marca  = new marcacao();
            String endereco = ETlocal.getText().toString();

            locationHTTP localti = new locationHTTP();
            localti.obterCoordenadasPorString(endereco,listenerLocal);
        }
        else
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.local_invalido),Toast.LENGTH_LONG);
        }

	};

    interfaceResultadoAsyncPost listenerLocal = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1 && conteudo.length() > 10) {
                        locationJson localJson = new locationJson(conteudo);
                        try {
                            ltlg = localJson.getLatLong();
                            BTaddMarcacao.setEnabled(true);
                            longitude = ltlg.longitude;
                            latitude = ltlg.latitude;
                            addMarcador();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }
                }
            });
        }
    };



    public void preencherCmbox()
    {

        PBloadingMarcacao.setVisibility(View.VISIBLE);
        responsavelBDD respBdd = new responsavelBDD(getApplicationContext());
        int idResponsavel = respBdd.getIdResponsavel();

        pacienteHttp paciHttp = new pacienteHttp();
        paciHttp.retornarPacientesIdResposnavel(idResponsavel,pacientesGetAllMeus);


    }

    interfaceResultadoAsyncPost pacientesGetAllMeus = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1 && conteudo.length() > 10) {
                        ArrayList<paciente> listaPaciente = new ArrayList<paciente>();
                        pacienteJson paciJson = new pacienteJson(conteudo);
                        listaPac = paciJson.transformJsonPaciente();

                        preencherSpinner();
                        PBloadingMarcacao.setVisibility(View.INVISIBLE);

                    }
                }
            });
        }
    };

    public void preencherSpinner()
    {
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
                                MarkerOptions marker = new MarkerOptions().position(ltlg).title(getResources().getString(R.string.marcacao));
                                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                googleMap.addMarker(marker);
                                CameraUpdate center = CameraUpdateFactory.newCameraPosition(new CameraPosition(ltlg, 15, 0, 0));
                                googleMap.moveCamera(center);

                            }
                        });
            }
        }.start();
    }

    public void clickDate(final View v)
    {
        int[] hoje = vd.getDataHoje();
        DatePickerDialog dialog = new DatePickerDialog(this, this, hoje[0], hoje[1], hoje[2]);
        dialog.show();
    };

    public void clickTime (final View v)
    {
        TimePickerDialog tp = new TimePickerDialog(this, this, 0, 0, true );
        tp.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ETdata.setText(vd.formatDate(year, monthOfYear, dayOfMonth));
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hh, int mm) {
        EThora.setText(vd.formatTime(hh, mm));
    }
}
