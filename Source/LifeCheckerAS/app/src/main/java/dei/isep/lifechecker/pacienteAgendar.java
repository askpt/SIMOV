package dei.isep.lifechecker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;

import static android.view.View.OnClickListener;

public class pacienteAgendar extends Activity{

    Button agendarMarcacao = null;
    EditText paciente = null;
    EditText marcacao = null;
    EditText hora = null;
    EditText data = null;
    EditText local = null;

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

        PBloadingMarcacao = (ProgressBar)findViewById(R.id.progressBar_action_bar);

        TVcomentariosAddMarca = (TextView)findViewById(R.id.tv_comentario_edit_marcacao_paciente);

        agendarMarcacao = (Button)findViewById(R.id.bt_paciente_agendarmarcacao_agendar);


        findViewById(R.id.bt_paciente_agendarmarcacao_agendar).setOnClickListener(btnCarregado);
        findViewById(R.id.bt_paciente_validar_local).setOnClickListener(btnCarregado);

        PBloadingMarcacao.setVisibility(View.INVISIBLE);
        agendarMarcacao.setEnabled(false);
        TVcomentariosAddMarca.setVisibility(View.INVISIBLE);
        preencherMapa();
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
                case R.id.bt_responsavel_addmarcacao_agendar:
                    //adicionarMarcacao();
                    break;
            }
        }
    };


    public void verLocal()
    {

        validarDados validar = new validarDados();
        if(validar.validarLocalidade(local.getText().toString()))
        {
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
                        locationHTTP localH = new locationHTTP();
                        try {
                            JSONObject jsonConteudo = new JSONObject(conteudo);
                            ltlg = localH.getLatLong(jsonConteudo);
                            agendarMarcacao.setEnabled(true);
                            longitude = ltlg.longitude;
                            latitude = ltlg.latitude;
                            addMarcador();
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


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
