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

import java.util.List;

import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;

public class pacienteAlterarMarcacao extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Button BTvalidarMarcacao = null;
    Button BTvalidarLocal = null;
    ProgressBar PBloadingUpdate = null;
    EditText paciente = null;
    EditText ETmarcacao = null;
    EditText EThora = null;
    EditText ETdata = null;
    EditText ETlocal = null;
    TextView TVComentarios = null;

    GoogleMap googleMap;

    double longitude =0;
    double latitude =0;

    private marcacao mar;
    private validarDados vd = new validarDados();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_alterarmarcacao);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.detalhesMarcacao);

        BTvalidarMarcacao = (Button)findViewById(R.id.bt_paciente_editmarcacao_agendar);
        BTvalidarLocal = (Button)findViewById(R.id.bt_paciente_editmarcacao_validar_local);
        PBloadingUpdate = (ProgressBar)findViewById(R.id.progressBar_action_bar);
        paciente = (EditText) findViewById(R.id.et_paciente_editmarcacao_pacientes);
        ETmarcacao = (EditText)findViewById(R.id.tb_paciente_editmarcacao_marcacao);
        EThora = (EditText)findViewById(R.id.tb_paciente_editmarcacao_hora);
        ETdata = (EditText)findViewById(R.id.tb_paciente_editmarcacao_data);
        ETlocal = (EditText)findViewById(R.id.tb_paciente_editmarcacao_local);

        TVComentarios = (TextView)findViewById(R.id.tv_comentario_edit_marcacao_paci);

        paciente.setEnabled(false);

        findViewById(R.id.bt_paciente_editmarcacao_agendar).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_paciente_editmarcacao_validar_local).setOnClickListener(btnCarregado);

        BTvalidarMarcacao.setEnabled(false);

        PBloadingUpdate.setVisibility(View.VISIBLE);
        preencherMapa();
        preencherInformacao();
    }

    private void preencherInformacao()
    {
        Intent intent = getIntent();

        int idMarcacao = intent.getIntExtra("idMarcacaoPaci", -1);
        marcacaoBDD marcaBdd = new marcacaoBDD(getApplicationContext());
        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        mar = marcaBdd.getMarcacaoByID(idMarcacao);
        String nomePaciente  = paciBDD.getNomeApelidoPacienteById(mar.getIdPacienteMarc());

        ETmarcacao.setText(mar.getTipoMarc());
        EThora.setText(mar.getHoraMarc().substring(0, mar.getHoraMarc().length() - 3));
        ETdata.setText(mar.getDataMarc());
        ETlocal.setText(mar.getLocalMarc());
        longitude = mar.getLongitudeMarc();
        latitude = mar.getLatitudeMarc();

        paciente.setText(nomePaciente);
        addMarcador();
    }



    final View.OnClickListener btnCarregado = new View.OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_paciente_editmarcacao_validar_local:
                    verLocal();
                    break;
                case R.id.bt_paciente_editmarcacao_agendar:
                    alterarMarcacao();
                    break;
            }
        }
    };

    public void alterarMarcacao()
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
            BTvalidarMarcacao.setEnabled(false);

            mar.setTipoMarc(tipoMarcacao);
            mar.setDataMarc(data);
            mar.setHoraMarc(hora);
            mar.setLatitudeMarc(latitude);
            mar.setLongitudeMarc(longitude);
            mar.setLocalMarc(local);
            mar.setIdEstadoMarc(2);

            marcacaoHttp marHttp = new marcacaoHttp();
            marHttp.updateMarcacao(mar, resultadoUpdateMarcacao);
        }
        else
        {
            TVComentarios.setText(getResources().getString(R.string.err_dados_formularios));
        }

    }

    interfaceResultadoAsyncPost resultadoUpdateMarcacao = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(codigo == 1) {
                        PBloadingUpdate.setVisibility(View.INVISIBLE);

                        marcacaoBDD marcBDD = new marcacaoBDD(getApplicationContext());
                        marcBDD.atualizarMarcacao(mar);


                        Intent intent = new Intent(getApplication(), pacienteMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }
                    else
                    {
                        BTvalidarMarcacao.setEnabled(true);
                    }

                }
            });

        }
    };

    public void verLocal()
    {
        BTvalidarMarcacao.setEnabled(false);
        BTvalidarLocal.setEnabled(false);
        PBloadingUpdate.setVisibility(View.VISIBLE);
        marcacao marca  = new marcacao();
        String endereco = ETlocal.getText().toString();
        Log.i("locali", endereco);

        marca.getLatLong(endereco,interfaceListenerLocal,getApplicationContext());
    };

    interfaceAdressList interfaceListenerLocal = new interfaceAdressList() {
        @Override
        public void listaCoordenadas(final int codigo, final List<Address> enderecos) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1) {
                        latitude = enderecos.get(0).getLatitude();
                        longitude = enderecos.get(0).getLongitude();
                        addMarcador();
                    } else {
                        PBloadingUpdate.setVisibility(View.INVISIBLE);
                        BTvalidarLocal.setEnabled(true);
                        BTvalidarMarcacao.setEnabled(true);
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.local_invalido), Toast.LENGTH_LONG);
                    }
                }
            });
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
                        BTvalidarMarcacao.setEnabled(true);
                        BTvalidarLocal.setEnabled(true);
                        PBloadingUpdate.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }.start();
    }

    private void initilizeMap() {
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map_fragment_paci_marca_detal)).getMap();
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

