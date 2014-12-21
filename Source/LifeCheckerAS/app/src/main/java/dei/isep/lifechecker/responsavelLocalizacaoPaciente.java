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

        pac = lifeCheckerManager.getInstance().getPac();

        TVpaciente.setText(pac.getNomePaciente() + " " + pac.getApelidoPaciente());
        if (pac.getHoraLocalPaciente()!=null)
        {
            TVhora.setText(pac.getHoraLocalPaciente().substring(0, pac.getHoraLocalPaciente().length() - 3));
            longitude = pac.getLongitudePaciente();
            latitude = pac.getLatitudePaciente();
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
