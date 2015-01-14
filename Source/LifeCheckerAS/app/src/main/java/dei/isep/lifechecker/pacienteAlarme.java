package dei.isep.lifechecker;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.historicoAlertasHttp;
import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.json.locationJson;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.GPSTracker;


public class pacienteAlarme extends Activity {

    ImageView imgAlarm;
    private NetworkInfo networkInfo;
    paciente paci;
    responsavel resp;
    Location localAtual;
    int enviarAlerta = 0;
    int tempo = 0;
    Ringtone rt;
    boolean aEnviarMail = false;

    CountDownTimer minResposta = new CountDownTimer(10000, 500) {

        public void onTick(long millisUntilFinished) {
            //Atualização de algo
            //tvCountDown.setText(Integer.toString(60-tempo));
            Log.i("Tempo passou",Integer.toString(tempo++));
            if(enviarAlerta == 1)
            {
                enviarAlerta = 3;
                Intent intent = new Intent(pacienteAlarme.this, pacienteMenu.class);
                startActivity(intent);
                rt.stop();
                minResposta.cancel();
            }
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
        }

        public void onFinish() {
            // quando Acaba
            if(enviarAlerta == 0) {
                enviarAlerta =-1;
                aEnviarMail = true;
                enviarAlerta();
                Intent intent = new Intent(pacienteAlarme.this, pacienteMenu.class);
                startActivity(intent);
                rt.stop();

            }

            //mTextField.setText("done!");
        }
    }.start();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_alarme);
        imgAlarm = (ImageView) findViewById(R.id.img_alerta);
        rt = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        rt.play();

        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        resp = respBDD.getResponsavel();
        paci = paciBDD.getPaciente();
        minResposta.start();

        imgAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                enviarAlerta = 1;
            }

        });
    }



   private void enviarAlerta()
   {
       ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
       networkInfo = connectivityManager.getActiveNetworkInfo();
       if(networkInfo != null)
       {
           enviarHistoAlertaResp();
       }
       String Conteudo = getResources().getString(R.string.sms_no_life, paci.getNomePaciente() + " " + paci.getApelidoPaciente());
       if(resp.getNotificacaoSMS())
       {
           Log.i("SMS", "enviar sms vida");

           enviarSMS(Conteudo);
       }
       if(resp.getNotificacaoMail() && networkInfo != null)
       {
           aEnviarMail = true;
           responsavelHttp respHttp = new responsavelHttp();
           respHttp.enviarMail(resp.getIdResponsavel(),Conteudo, enviarMailListener);
       }
   }

    private void enviarHistoAlertaResp()
    {
        GPSTracker gps = new GPSTracker(this);
        localAtual = gps.getLocation();
        if(localAtual != null) {
            locationHTTP locationHttp = new locationHTTP();
            locationHttp.obterLocalPorCoordenadas(localAtual, listenerLocal);
        }
        else {
            String conteudoSMS = getString(R.string.sms_no_location, paci.getNomePaciente());

            if (resp.getNotificacaoSMS() == true) {

                enviarSMS(conteudoSMS);
            }
            if (resp.getNotificacaoMail()) {
                Log.i("MAIL", "marcacao: sem local");
                responsavelHttp respHttp = new responsavelHttp();
                respHttp.enviarMail(resp.getIdResponsavel(), conteudoSMS, enviarMailListener);
            }
        }
    }

    private void enviarSMS(String conteudo)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(resp.getContactoResponsavel(), null, conteudo, null, null);
        Log.i("SMS", "EnviouSMS");
    }

    interfaceResultadoAsyncPost listenerLocal = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {

            if (codigo == 1 && conteudo.length() > 10) {
                String conteudoAlterado = conteudo.replaceAll("(\\r|\\n)", "");

                locationJson locaJson = new locationJson(conteudoAlterado);
                ArrayList<String> cidades = new ArrayList<String>();
                cidades = locaJson.getAdress();

                historicoAlertas histoAlt = new historicoAlertas();
                histoAlt.setLongitudeHistAlt(localAtual.getLongitude());
                histoAlt.setLatitudeHistAlt(localAtual.getLatitude());
                histoAlt.setLocalHistAlt(cidades.get(0));
                histoAlt.setIdAlertaHistAlt(2);
                histoAlt.setIdPacienteHistAlt(paci.getIdPaciente());
                historicoAlertasHttp histAltHttp = new historicoAlertasHttp();
                histAltHttp.addHistoricoAlerta(histoAlt,listenerHistoricoAlerta);
            }
        };

    };

    interfaceResultadoAsyncPost enviarMailListener = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            if (codigo == 1) {
                Log.i("mail","passsssssssssssssiiiiiiiiiiiii");
                aEnviarMail = false;
            }
        };

    };

    interfaceResultadoAsyncPost listenerHistoricoAlerta = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            //proximaAtualizacao();
        };

    };






}
