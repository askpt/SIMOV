package dei.isep.lifechecker.alarme;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import dei.isep.lifechecker.R;
import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.historicoAlertasHttp;
import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.interfaceAdressList;
import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.json.locationJson;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.GPSTracker;
import dei.isep.lifechecker.other.lifeCheckerManager;

/**
 * Created by Diogo on 27-12-2014.
 */
public class marcacaoAlarme extends IntentService {

    int idMarcaca = 0;
    marcacao marca;
    double distancia;
    Location localAtual;
    long proximaAtualizacaoMili;
    PendingIntent pendingIntent;
    Time proximaAtualizacao;
    private NetworkInfo networkInfo;
    paciente paci;
    responsavel resp;

    public marcacaoAlarme(){
        super(marcacaoAlarme.class.getSimpleName());
    };

    @Override
    protected void onHandleIntent(Intent intent) {
        lifeCheckerManager.getInstance().setaVerificar(true);

        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        paci = paciBDD.getPaciente();
        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        resp = respBDD.getResponsavel();

        Log.i("Alarme", "passousssss B");
        idMarcaca = intent.getIntExtra("idMarcacao", -1);
        if(idMarcaca == -1)
        {
            idMarcaca = lifeCheckerManager.getInstance().getIdMarcacao();
        }
        else
        {
            lifeCheckerManager.getInstance().setIdMarcacao(idMarcaca);
        }
        proximaAtualizacao();

    }

    private void proximaAtualizacao()
    {
        marcacaoBDD marcaBDD = new marcacaoBDD(getApplicationContext());
        marca = marcaBDD.getMarcacaoByID(idMarcaca);
        Log.i("Alarme", "passousssss C");
        Intent intent = new Intent(this, this.getClass());
        pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.i("Alarme", "passousssss D");
        long tempoAtualMiliSec = System.currentTimeMillis();
        Time tempoMacacao = new Time();
        Log.i("Alarme", "passousssss E");
        //************** Ver se data é hoje
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = s.format(new Date());
        String datemarcacao = marca.getDataMarc() + " " + marca.getHoraMarc();
        Date agoraData = new Date();
        Date marcacaoData = new Date();
        try {
            agoraData = s.parse(dateNow);
            marcacaoData = s.parse(datemarcacao);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long minutosDiferentes = marcacaoData.getTime() - agoraData.getTime();
        minutosDiferentes = TimeUnit.MINUTES.convert(minutosDiferentes, TimeUnit.MILLISECONDS);

        long minutosAlarm = 0;

        boolean enviarNotificacao;
        if(minutosDiferentes <= 65 && minutosDiferentes > 0)
        {
            minutosAlarm = 10;
            enviarNotificacao = true;
        }
        else
        {
            minutosAlarm = minutosDiferentes - 60;
            enviarNotificacao = false;
        }

        proximaAtualizacaoMili = tempoAtualMiliSec + minutosAlarm * DateUtils.MINUTE_IN_MILLIS;
        proximaAtualizacao = new Time();
        proximaAtualizacao.set(proximaAtualizacaoMili);
        //**************

        if(tempoAtualMiliSec < proximaAtualizacao.toMillis(true))
        {
            enviarAlerta(enviarNotificacao);
        }
        else
        {
            lifeCheckerManager.getInstance().setaVerificar(false);
        }
    }

    private void enviarAlerta(boolean enviar)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(enviar == true && networkInfo != null)
        {
            Log.i("alarme", "passou alarme COM notificar");
            GPSTracker gps = new GPSTracker(this);
            if(gps.canGetLocation())
            {
                localAtual = gps.getLocation();
                gps.stopUsingGPS();
                Location coordenadasMarcacao = new Location("");
                coordenadasMarcacao.setLatitude(marca.getLatitudeMarc());
                coordenadasMarcacao.setLongitude(marca.getLongitudeMarc());
                gps.stopUsingGPS();
                distancia = localAtual.distanceTo(coordenadasMarcacao);
                if(distancia > 200)
                {
                    if(resp.getNotificacaoSMS() || resp.getNotificacaoMail())
                    {
                        String conteudoSMS = getString(R.string.sms_no_marcacao, paci.getNomePaciente());
                        conteudoSMS += "\n" + getString(R.string.sms_no_marcacao_dist, distancia);
                        if(resp.getNotificacaoSMS()) {
                            Log.i("SMS", "marcacao: sem local");
                            enviarSMS(conteudoSMS);
                        }
                        if(resp.getNotificacaoMail())
                        {
                            Log.i("MAIL", "marcacao: sem local");
                            responsavelHttp respHttp = new responsavelHttp();
                            respHttp.enviarMail(resp.getIdResponsavel(), conteudoSMS,enviarMailListener);
                        }

                    }
                    enviarHistoAlertaResp();
                }

            }
            else
            {
                gps.showSettingsAlert();
            }
        }
        else
        {
            if(resp.getNotificacaoSMS())
            {
                if(resp.getNotificacaoSMS()) {
                    Log.i("SMS", "marcacao: sem NET");
                    String conteudoSMS = getString(R.string.sms_no_internet, paci.getNomePaciente());
                    enviarSMS(conteudoSMS);
                }
            }
            Log.i("alarme", "passou alarme Sem notificar");
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, proximaAtualizacaoMili, pendingIntent);

        }
    }

    private void enviarHistoAlertaResp()
    {
        locationHTTP locationHttp = new locationHTTP();
        locationHttp.obterLocalPorCoordenadas(localAtual,listenerLocal);
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
                        histoAlt.setIdAlertaHistAlt(1);
                        histoAlt.setIdPacienteHistAlt(marca.getIdPacienteMarc());
                        historicoAlertasHttp histAltHttp = new historicoAlertasHttp();
                        histAltHttp.addHistoricoAlerta(histoAlt,listenerHistoricoAlerta);
                    }
                };

        };

    interfaceResultadoAsyncPost listenerHistoricoAlerta = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {

            if (codigo == 1) {
                Log.i("alarmealarme", "passou alarme");
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, proximaAtualizacaoMili, pendingIntent);
            }
        };

    };


    private void enviarSMS(String conteudo)
    {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(resp.getContactoResponsavel(), null, conteudo, null, null);
    }

    interfaceResultadoAsyncPost enviarMailListener = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            if (codigo == 1) {
            }
        };

    };

}
