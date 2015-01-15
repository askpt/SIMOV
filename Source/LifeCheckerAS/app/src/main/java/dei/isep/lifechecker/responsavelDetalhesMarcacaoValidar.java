package dei.isep.lifechecker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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

import java.util.List;

import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.json.locationJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;

public class responsavelDetalhesMarcacaoValidar extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    ImageButton IBTvalidarYes = null;
    ImageButton IBTvalidarNo = null;
    EditText ETpaciente = null;
    EditText ETmarcacao = null;
    EditText EThora = null;
    EditText ETdata = null;
    EditText ETlocal = null;
    TextView TVcomentarios = null;

    ProgressBar PBloadingUpdate = null;

    Button BTvalidarLocal = null;

    GoogleMap googleMap;

    double longitude =0;
    double latitude =0;

    private dei.isep.lifechecker.model.marcacao mar;
    private validarDados vd = new validarDados();
    LatLng ltlg;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_detalhesmarcacaovalidar);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.validarMarcacao);


        TVcomentarios = (TextView)findViewById(R.id.ET_responsavel_detalhesmarcacaovalidar_comentarios);
        IBTvalidarYes = (ImageButton)findViewById(R.id.bt_responsavel_detalhesmarcacaovalidar_yes);
        IBTvalidarNo = (ImageButton)findViewById(R.id.bt_responsavel_detalhesmarcacaovalidar_not);
        ETpaciente = (EditText) findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_paciente);
        ETmarcacao = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_marcacao);
        EThora = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_hora);
        ETdata = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_data);
        ETlocal = (EditText)findViewById(R.id.tb_responsavel_detalhesmarcacaovalidar_local);

        BTvalidarLocal = (Button)findViewById(R.id.bt_responsavel_detalhesmarcacaovalidar_validar_local);
        PBloadingUpdate = (ProgressBar)findViewById(R.id.loading_detalhesmarcacaovalidar_marcacao_responsavel);
        IBTvalidarYes.setOnClickListener(btnCarregado);
        IBTvalidarNo.setOnClickListener(btnCarregado);
        BTvalidarLocal.setOnClickListener(btnCarregado);

        PBloadingUpdate.setVisibility(View.INVISIBLE);


        preencherMapa();
        preencherInformacao();
    }

    private void preencherInformacao()
    {
        Intent intent = getIntent();

        /*
                        intent.putExtra("paciente", nomePaciente);
                intent.putExtra("idMacarcao",idMarcacao);
         */
        String nomePaciente = intent.getStringExtra("paciente");
        int idMarcacao = intent.getIntExtra("idMacarcao",-1);

        marcacaoBDD marcaBdd = new marcacaoBDD(getApplicationContext());
        mar = marcaBdd.getMarcacaoByID(idMarcacao);
        ETpaciente.setText(nomePaciente);

        ETmarcacao.setText(mar.getTipoMarc());
        EThora.setText(mar.getHoraMarc().substring(0, mar.getHoraMarc().length() - 3));
        ETdata.setText(mar.getDataMarc());
        ETlocal.setText(mar.getLocalMarc());
        longitude = mar.getLongitudeMarc();
        latitude = mar.getLatitudeMarc();

        Log.i("coord", String.valueOf(longitude));
        Log.i("coord", String.valueOf(latitude));
        addMarcador();

    }


    final View.OnClickListener btnCarregado = new View.OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_responsavel_detalhesmarcacaovalidar_validar_local:
                    verLocal();
                    break;
                case R.id.bt_responsavel_detalhesmarcacaovalidar_yes: //Yes
                    alterarMarcacao(1);
                    break;
                case R.id.bt_responsavel_detalhesmarcacaovalidar_not: //No
                    alterarMarcacao(3);
                    break;
            }
        }
    };

    public void preencherMapa()
    {
        try {
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initilizeMap() {
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map_fragment_validar)).getMap();
                googleMap.setMyLocationEnabled(true);
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    public void alterarMarcacao(int estadoMarcacao)
    {
        String tipoMarcacao = ETmarcacao.getText().toString();
        String hora = EThora.getText().toString();
        String data = ETdata.getText().toString();
        String local = ETlocal.getText().toString();
        validarDados validar = new validarDados();

        if(validar.validarTempo24H(hora) &&
                validar.validarDataAMD(data) &&
                validar.validarLocalidade(local) &&
                validar.validarLatitude(latitude) &&
                validar.validarLongitude(longitude))
        {
            hora +=":00";
            PBloadingUpdate.setVisibility(View.VISIBLE);
            //BTvalidarMarcacao.setEnabled(false);

            mar.setTipoMarc(tipoMarcacao);
            mar.setDataMarc(data);
            mar.setHoraMarc(hora);
            mar.setLatitudeMarc(latitude);
            mar.setLongitudeMarc(longitude);
            mar.setLocalMarc(local);
            mar.setIdEstadoMarc(estadoMarcacao);

            marcacaoHttp marHttp = new marcacaoHttp();
            marHttp.updateMarcacao(mar, resultadoUpdateMarcacao);
        }
        else
        {
            TVcomentarios.setText(getResources().getString(R.string.err_dados_formularios));
        }

    }

    private void addMarcador() {

        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        googleMap.clear();
                        LatLng ltlg = new LatLng(latitude, longitude);
                        MarkerOptions marker = new MarkerOptions().position(ltlg).title(getResources().getString(R.string.marcacao));
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        googleMap.addMarker(marker);


                        CameraUpdate center = CameraUpdateFactory.newCameraPosition(new CameraPosition(ltlg, 15, 0, 0));
                        googleMap.moveCamera(center);
                        PBloadingUpdate.setVisibility(View.INVISIBLE);
                        BTvalidarLocal.setEnabled(true);
                        IBTvalidarYes.setEnabled(true);
                        IBTvalidarNo.setEnabled(true);
                    }
                });
            }
        }.start();
    }

    public void verLocal()
    {
        BTvalidarLocal.setEnabled(false);
        IBTvalidarYes.setEnabled(false);
        IBTvalidarNo.setEnabled(false);

        PBloadingUpdate.setVisibility(View.VISIBLE);

        marcacao marca  = new marcacao();
        String endereco = ETlocal.getText().toString();
        Log.i("locali", endereco);

        locationHTTP localti = new locationHTTP();
        localti.obterCoordenadasPorString(endereco,listenerLocal);

        //marca.getLatLong(endereco,interfaceListenerLocal,getApplicationContext());
    };


    interfaceResultadoAsyncPost listenerLocal = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1 && conteudo.length() > 10) {
                        locationJson locaJson = new locationJson(conteudo);
                        try {
                            ltlg = locaJson.getLatLong();
                            longitude = ltlg.longitude;
                            latitude = ltlg.latitude;
                            addMarcador();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.local_invalido), Toast.LENGTH_LONG).show();
                        PBloadingUpdate.setVisibility(View.INVISIBLE);
                        BTvalidarLocal.setEnabled(true);
                        IBTvalidarYes.setEnabled(false);
                        IBTvalidarNo.setEnabled(false);
                    }
                }
            });
        }
    };

    interfaceResultadoAsyncPost resultadoUpdateMarcacao = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1) {
                        PBloadingUpdate.setVisibility(View.INVISIBLE);
                        //String idValor = conteudo.replaceAll("[\\r\\n]+", "");
                        //int idMarcacao  = Integer.valueOf(idValor);
                        //mar.setIdMarcacaoMarc(idMarcacao);

                        marcacaoBDD marcBDD = new marcacaoBDD(getApplicationContext());
                        marcBDD.atualizarMarcacao(mar);


                        Intent intent = new Intent(getApplication(), responsavelMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }
                    else
                    {
                        //BTvalidarMarcacao.setEnabled(true);
                    }

                }
            });

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
        ETdata.setText(vd.formatDate(year, monthOfYear, dayOfMonth));
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hh, int mm) {
        EThora.setText(vd.formatTime(hh, mm));
    }

}
