package dei.isep.lifechecker.alarme;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.historicoAlertasHttp;
import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.json.locationJson;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.GPSTracker;
import dei.isep.lifechecker.other.lifeCheckerManager;

/**
 * Created by Diogo on 02-01-2015.
 */
public class periodicoAlarme extends IntentService {

    private  SensorManager sensorManager;
    private  Sensor sensorAceleromtro;
    Location localAtual;
    private paciente paci;
    private responsavel resp;
    private NetworkInfo networkInfo;

    SensorEventListener sensorEvtListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            switch(event.sensor.getType())
            {
                case Sensor.TYPE_ACCELEROMETER:
                    lifeCheckerManager.getInstance().setAcelaromtroXACT(event.values[0]);
                    lifeCheckerManager.getInstance().setAcelaromtroYACT(event.values[1]);
                    lifeCheckerManager.getInstance().setAcelaromtroZACT(event.values[2]);
                    /*lifeCheckerManager.getInstance().setAcelaromtroX(event.values[0]);
                    lifeCheckerManager.getInstance().setAcelaromtroY(event.values[1]);
                    lifeCheckerManager.getInstance().setAcelaromtroZ(event.values[2]);*/
                    lifeCheckerManager.getInstance().setTempAlteracao(System.currentTimeMillis());
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public periodicoAlarme() {
        super(periodicoAlarme.class.getSimpleName());
    }

    protected void onHandleIntent(Intent intent) {
        lifeCheckerManager.getInstance().setAlarmesDiurna(true);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager != null){
            sensorAceleromtro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if(sensorAceleromtro!=null && lifeCheckerManager.getInstance().getRegistouAcelaromtro() == false){
                sensorManager.registerListener(sensorEvtListener,sensorAceleromtro,SensorManager.SENSOR_DELAY_NORMAL);
                lifeCheckerManager.getInstance().setRegistouAcelaromtro(true);
            }

        }

        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        resp = respBDD.getResponsavel();
        float variacaoX = Math.abs(lifeCheckerManager.getInstance().getAcelaromtroXACT() - lifeCheckerManager.getInstance().getAcelaromtroX());
        float variacaoY = Math.abs(lifeCheckerManager.getInstance().getAcelaromtroYACT() - lifeCheckerManager.getInstance().getAcelaromtroY());
        float variacaoZ = Math.abs(lifeCheckerManager.getInstance().getAcelaromtroZACT() - lifeCheckerManager.getInstance().getAcelaromtroZ());
        if(variacaoX < 0.3 && variacaoY < 0.3 && variacaoZ < 0.3)
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            networkInfo = connectivityManager.getActiveNetworkInfo();

            pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
            int idPaci = paciBDD.getIdPaicente();
            paci = paciBDD.getPacienteById(idPaci);

            if(resp.getNotificacaoSMS())
            {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(resp.getContactoResponsavel(), null, getResources().getString(R.string.sms_no_internet, paci.getNomePaciente() + " " + paci.getApelidoPaciente()), null, null);
                Log.i("SMS", "EnviouSMS");
            }
            if(resp.getNotificacaoMail() && networkInfo != null)
            {

                Log.i("Mail", " A enviar mail");
                /*Intent mailIntent = new Intent((Intent.ACTION_SENDTO));
                mailIntent.setType("text/plain");
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mailIntent.putExtra(Intent.EXTRA_EMAIL, resp.getMailResponsavel());
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "LifeChecker");
                mailIntent.putExtra(Intent.EXTRA_TEXT, "Problem with " + paci.getNomePaciente() + " " + paci.getApelidoPaciente());

                try {
                    startActivity(Intent.createChooser(mailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {

                }*/
            }

            if(networkInfo != null)
            {
                enviarHistoAlertaResp();
            }

           /* Intent intentAlarm = new Intent(this, pacienteAlarme.class);
            intentAlarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intentAlarm);*/
        }

        Log.i("Gyro X", Float.toString(lifeCheckerManager.getInstance().getAcelaromtroX()) + " " + lifeCheckerManager.getInstance().getAcelaromtroXACT());
        Log.i("Gyro Y", Float.toString(lifeCheckerManager.getInstance().getAcelaromtroY()) + " " + lifeCheckerManager.getInstance().getAcelaromtroYACT());
        Log.i("Gyro Z", Float.toString(lifeCheckerManager.getInstance().getAcelaromtroZ()) + " " + lifeCheckerManager.getInstance().getAcelaromtroZACT());

        lifeCheckerManager.getInstance().setAcelaromtroX(lifeCheckerManager.getInstance().getAcelaromtroXACT());
        lifeCheckerManager.getInstance().setAcelaromtroY(lifeCheckerManager.getInstance().getAcelaromtroYACT());
        lifeCheckerManager.getInstance().setAcelaromtroZ(lifeCheckerManager.getInstance().getAcelaromtroZACT());

        /*lifeCheckerManager.getInstance().setAcelaromtroX(lifeCheckerManager.getInstance().getAcelaromtroXACT());
        lifeCheckerManager.getInstance().setAcelaromtroY(lifeCheckerManager.getInstance().getAcelaromtroYACT());
        lifeCheckerManager.getInstance().setAcelaromtroZ(lifeCheckerManager.getInstance().getAcelaromtroZACT());*/

        long tempo = System.currentTimeMillis();
        lifeCheckerManager.getInstance().setTempAlteracao(tempo);
        //long tempoDiff = tempo - lifeCheckerManager.getInstance().getTempAlteracao();
        //.i("Tempo Differente",Long.toString(tempoDiff));
        proximaAtualizacao();
    }

    private void proximaAtualizacao()
    {
        int minutos = tempoProximaActualizacao();
        //sensorManager.unregisterListener(this, gyroscopio);
        long currentTimeMillis = System.currentTimeMillis();
        long proximaAtualizacaoMili = currentTimeMillis + minutos * DateUtils.MINUTE_IN_MILLIS;
        Log.i("AlarmeVivo", "passou alarme");
        Intent intent = new Intent(this, this.getClass());
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, proximaAtualizacaoMili, pendingIntent);
        Log.i("AlarmeVivo", " proximo alarm daqui a " + (currentTimeMillis-proximaAtualizacaoMili)/1000 + " sec");
    }

    private int tempoProximaActualizacao()
    {
        int tempoProxima = 0;
        try {
            String tempoA = "07:00:00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(tempoA);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            String tempoB = "19:00:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(tempoB);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);


            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            Date d = new SimpleDateFormat("HH:mm:ss").parse(formattedDate);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();

            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                tempoProxima = resp.getPeriodicidadeNoturna();
            }
            else
            {
                tempoProxima = resp.getPeriodicidadeDiurna();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(tempoProxima == 0)
        {
            tempoProxima = 60*12+1;
        }
        return tempoProxima;
    }


    private void enviarHistoAlertaResp()
    {
        GPSTracker gps = new GPSTracker(this);
        localAtual = gps.getLocation();
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
                histoAlt.setIdAlertaHistAlt(2);
                histoAlt.setIdPacienteHistAlt(paci.getIdPaciente());
                historicoAlertasHttp histAltHttp = new historicoAlertasHttp();
                histAltHttp.addHistoricoAlerta(histoAlt,listenerHistoricoAlerta);
            }
        };

    };

    interfaceResultadoAsyncPost listenerHistoricoAlerta = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            proximaAtualizacao();
        };

    };

}
