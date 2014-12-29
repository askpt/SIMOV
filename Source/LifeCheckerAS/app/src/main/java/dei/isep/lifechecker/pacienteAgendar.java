package dei.isep.lifechecker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.json.locationJson;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;

import static android.view.View.OnClickListener;

public class pacienteAgendar extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Button agendarMarcacao = null;
    EditText paciente = null;
    EditText marcacao = null;
    EditText hora = null;
    EditText data = null;
    EditText local = null;

    //private validarDados vasd = new validarDados();

    TextView TVcomentariosAddMarca;

    ProgressBar PBloadingMarcacao;

    GoogleMap googleMap;

    double longitude =0;
    double latitude =0;

    private dei.isep.lifechecker.model.marcacao mar;
    private validarDados vd = new validarDados();

    ArrayList<paciente> listaPac = new ArrayList<paciente>();

    LatLng ltlg;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_agendarmarcacao);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.agendarMarcacao);

        paciente = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_paciente);
        marcacao = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_marcacao);
        hora = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_hora);
        data = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_data);
        local = (EditText)findViewById(R.id.tb_paciente_agendarmarcacao_local);

//        agendarMarcacao.setOnClickListener(this);
        PBloadingMarcacao = (ProgressBar)findViewById(R.id.progressBar_action_bar);

        TVcomentariosAddMarca = (TextView)findViewById(R.id.tv_comentario_edit_marcacao_paciente);

        agendarMarcacao = (Button)findViewById(R.id.bt_paciente_agendarmarcacao_agendar);


        findViewById(R.id.bt_paciente_agendarmarcacao_agendar).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_paciente_validar_local).setOnClickListener(btnCarregado);

        PBloadingMarcacao.setVisibility(View.INVISIBLE);
        agendarMarcacao.setEnabled(false);
        TVcomentariosAddMarca.setVisibility(View.INVISIBLE);
        preencherMapa();
        preencherNomePaciente();
    }

    final OnClickListener btnCarregado = new OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_paciente_validar_local:
                    verLocal();
                    break;
                case R.id.bt_paciente_agendarmarcacao_agendar:
                    adicionarMarcacao();
                    break;
            }
        }
    };

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
        data.setText(vd.formatDate(year, monthOfYear, dayOfMonth));
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hh, int mm) {
        hora.setText(vd.formatTime(hh, mm));
    }
    public void adicionarMarcacao()
    {
        String tipoMarcacao = marcacao.getText().toString();
        String horaConteudo = hora.getText().toString();
        String dataConteudo = data.getText().toString();
        String localConteudo = local.getText().toString();
        validarDados validar = new validarDados();

        if(validar.validarTipoMarcacao(tipoMarcacao) &&
                validar.validarTempo24H(horaConteudo) &&
                validar.validarDataAMD(dataConteudo) &&
                validar.validarLocalidade(localConteudo) &&
                validar.validarLatitude(latitude) &&
                validar.validarLongitude(longitude))
        {
            horaConteudo +=":00";
            PBloadingMarcacao.setVisibility(View.VISIBLE);
            agendarMarcacao.setEnabled(false);
            responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
            int idResponsavel = respBDD.getIdResponsavel();
            pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
            int idPaciente = paciBDD.getIdPaicente();

            mar = new marcacao(idPaciente,2,tipoMarcacao,horaConteudo,dataConteudo,latitude,longitude,localConteudo);
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

                        Intent intent = new Intent(getApplication(), pacienteMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }
                    else
                    {
                        agendarMarcacao.setEnabled(true);
                    }
                }
            });
        }
    };

    public void preencherNomePaciente()
    {
        PBloadingMarcacao.setVisibility(View.VISIBLE);
        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        int idPaciente = paciBDD.getIdPaicente();
        pacienteHttp paciHttp = new pacienteHttp();
        paciHttp.retornarPacienteById(idPaciente, listenerPacienteById);

    }




    public void verLocal()
    {

        validarDados validar = new validarDados();
        if(validar.validarLocalidade(local.getText().toString()))
        {
            PBloadingMarcacao.setVisibility(View.VISIBLE);
            agendarMarcacao.setEnabled(false);
            marcacao marca  = new marcacao();
            String endereco = local.getText().toString();

            locationHTTP localti = new locationHTTP();
            localti.obterCoordenadasPorString(endereco,listenerLocal);

            //marca.getLatLong(endereco, interfaceListenerViewLocal,getApplicationContext());
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
                        locationJson locJson = new locationJson(conteudo);
                        try {
                            ltlg = locJson.getLatLong();
                            agendarMarcacao.setEnabled(true);
                            longitude = ltlg.longitude;
                            latitude = ltlg.latitude;
                            addMarcador();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                            PBloadingMarcacao.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });
        }
    };

    interfaceResultadoAsyncPost listenerPacienteById = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1 && conteudo.length() > 10) {
                        pacienteJson paciJson = new pacienteJson(conteudo);
                        ArrayList<paciente> paciList = new ArrayList<paciente>();
                        paciente paci = new paciente();
                        paci = paciJson.transformOnePaciente();
                        paciente.setText(paci.getNomePaciente() + " " + paci.getApelidoPaciente());
                        PBloadingMarcacao.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    };

    public void preencherMapa()
    {
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
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
                        PBloadingMarcacao.setVisibility(View.INVISIBLE);

                    }
                });
            }
        }.start();
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
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }


}
