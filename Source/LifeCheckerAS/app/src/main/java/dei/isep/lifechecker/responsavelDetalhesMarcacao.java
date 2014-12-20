package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;

public class responsavelDetalhesMarcacao extends Activity{

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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_detalhesmarcacao);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.detalhesMarcacao);

        BTvalidarMarcacao = (Button)findViewById(R.id.bt_responsavel_editmarcacao_agendar);
        BTvalidarLocal = (Button)findViewById(R.id.bt_responsavel_editmarcacao_validar_local);
        PBloadingUpdate = (ProgressBar)findViewById(R.id.progressBar_action_bar);
        paciente = (EditText) findViewById(R.id.et_responsavel_editmarcacao_pacientes);
        ETmarcacao = (EditText)findViewById(R.id.tb_responsavel_editmarcacao_marcacao);
        EThora = (EditText)findViewById(R.id.tb_responsavel_editmarcacao_hora);
        ETdata = (EditText)findViewById(R.id.tb_responsavel_editmarcacao_data);
        ETlocal = (EditText)findViewById(R.id.tb_responsavel_editmarcacao_local);

        TVComentarios = (TextView)findViewById(R.id.tv_comentario_edit_marcacao);

        paciente.setEnabled(false);

        findViewById(R.id.bt_responsavel_editmarcacao_agendar).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_responsavel_editmarcacao_validar_local).setOnClickListener(btnCarregado);

        preencherMapa();
        preencherInformacao();
    }

    private void preencherInformacao()
    {
        Intent intent = getIntent();

        String nomePaciente = intent.getStringExtra("paciente");
        int idMarcacao = intent.getIntExtra("idMacarcao", -1);
        marcacaoBDD marcaBdd = new marcacaoBDD(getApplicationContext());
        mar = marcaBdd.getMarcacaoByID(idMarcacao);
        ETmarcacao.setText(mar.getTipoMarc());
        EThora.setText(mar.getHoraMarc().substring(0, mar.getHoraMarc().length() - 3));
        ETdata.setText(mar.getDataMarc());
        ETlocal.setText(mar.getLocalMarc());
        longitude = mar.getLongitudeMarc();
        latitude = mar.getLatitudeMarc();



        paciente.setText(nomePaciente);
        addMarcador();
    }


    final OnClickListener btnCarregado = new OnClickListener()
    {
        public void onClick(final View v)
        {
            switch(v.getId())
            {
                case R.id.bt_responsavel_editmarcacao_validar_local:
                    verLocal();
                    break;
                case R.id.bt_responsavel_editmarcacao_agendar:
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
                        BTvalidarMarcacao.setEnabled(true);
                    }

                }
            });

        }
    };

    public void verLocal()
    {
        marcacao marca  = new marcacao();
        String endereco = ETlocal.getText().toString();
        Log.i("locali", endereco);

        marca.getLatLong(endereco,interfaceListenerLocal,getApplicationContext());
    };

    interfaceAgendarMarcacao interfaceListenerLocal = new interfaceAgendarMarcacao() {
        @Override
        public void listaCoordenadas(final int codigo, final List<Address> enderecos) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codigo == 1) {
                        latitude = enderecos.get(0).getLatitude();
                        longitude = enderecos.get(0).getLongitude();
                        BTvalidarMarcacao.setEnabled(true);
                        addMarcador();
                    } else {
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

    private void initilizeMap() {
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map_fragment)).getMap();
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
                    }
                });
            }
        }.start();
    }



}
