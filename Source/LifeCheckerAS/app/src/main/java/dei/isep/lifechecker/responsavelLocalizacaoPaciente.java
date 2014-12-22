package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.lifeCheckerManager;

public class responsavelLocalizacaoPaciente extends Activity {

    TextView TVpaciente = null;
    TextView TVhora = null;
    ProgressBar PBloadingUpdate = null;

    GoogleMap googleMap;

    double longitude =0;
    double latitude =0;

    LatLng ltlg;

    private paciente pac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_localizarpaciente);
        lifeCheckerManager.getInstance().inserirActionBar(this, R.string.localizacaoDoPaciente);

        TVpaciente = (TextView) findViewById(R.id.text_responsavel_localizar_paciente);
        TVhora = (TextView) findViewById(R.id.text_responsavel_localizar_hora);
        PBloadingUpdate = (ProgressBar)findViewById(R.id.loading_responsavel_localizarpaciente);

        PBloadingUpdate.setVisibility(View.INVISIBLE);

        preencherMapa();
        preencherInformacao();
    }

    private void preencherInformacao()
    {
        Intent intent = getIntent();

        //pac = lifeCheckerManager.getInstance().getPac();
        String nome = intent.getStringExtra("nome");
        String apelido = intent.getStringExtra("apelido");
        double longitudeRes = intent.getDoubleExtra("longitude",-1);
        double latitudeRes = intent.getDoubleExtra("latitude", -1);
        String dataLocal = intent.getStringExtra("data");
        String horaLocal = intent.getStringExtra("hora");

        /*

                intent.putExtra("hora", listaPacientes.get(position).getHoraLocalPaciente());
                intent.putExtra("data", listaPacientes.get(position).getDataLocalPaciente());
         */

        TVpaciente.setText(nome + " " + apelido);
        if (longitudeRes != -1 && latitudeRes != -1 && dataLocal != null && horaLocal != null)
        {
            TVhora.setText(horaLocal + " " + dataLocal);
            longitude = longitudeRes;
            latitude = latitudeRes;
            LatLng latlng = new LatLng(latitude,longitude);
            ltlg = latlng;
            addMarcador();
        }
        else
        {
            TVhora.setText("sem dados");
        }

    }

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
                        R.id.map_responsavel_localizarpaciente)).getMap();
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
                        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude,longitude)).title(getResources().getString(R.string.marcacao));
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        googleMap.addMarker(marker);

                        CameraUpdate center = CameraUpdateFactory.newCameraPosition(new CameraPosition(ltlg, 15, 0, 0));
                        googleMap.moveCamera(center);
                    }
                });
            }
        }.start();
    }

}
