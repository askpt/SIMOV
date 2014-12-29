package dei.isep.lifechecker.alarme;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
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


import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.databaseonline.historicoAlertasHttp;
import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.interfaceAdressList;
import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.json.locationJson;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.model.marcacao;
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

    public marcacaoAlarme(){
        super(marcacaoAlarme.class.getSimpleName());
    };

    @Override
    protected void onHandleIntent(Intent intent) {
        lifeCheckerManager.getInstance().setaVerificar(true);
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
            minutosAlarm = 5;
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
        if(enviar == true)
        {
            Log.i("alarme", "passou alarme COM notificar");
            GPSTracker gps = new GPSTracker(this);
            if(gps.canGetLocation())
            {


                localAtual = gps.getLocation();
                Location coordenadasMarcacao = new Location("");
                coordenadasMarcacao.setLatitude(marca.getLatitudeMarc());
                coordenadasMarcacao.setLongitude(marca.getLongitudeMarc());
                gps.stopUsingGPS();
                distancia = localAtual.distanceTo(coordenadasMarcacao);
                if(distancia > 200)
                {
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
            Log.i("alarme", "passou alarme Sem notificar");
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, proximaAtualizacaoMili, pendingIntent);
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

}