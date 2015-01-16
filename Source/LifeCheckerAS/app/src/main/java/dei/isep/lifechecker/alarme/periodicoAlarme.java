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
import android.os.Handler;
import android.os.PowerManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.preferenciasAplicacao;
import dei.isep.lifechecker.pacienteAlarme;

/**
 * Created by Diogo on 02-01-2015.
 */
public class periodicoAlarme extends IntentService {

    private  SensorManager sensorManager;
    private  Sensor sensorAceleromtro;
    private paciente paci;
    private responsavel resp;

    private boolean diurno = false;
    private boolean barulhoNoturno = false;

    //**********
    private static final int INTERVALO_MICRO_ALARME = 300;
    private alarmeMicrofone microAlarme;
    private double calibragemAlarme;

    private int mThresholdAlarme;
    private PowerManager.WakeLock mWakeLockAlarme;
    private Handler mHandlerAlarme = new Handler();
    //**********

    private preferenciasAplicacao prefApp;

    double DBLimite = 10.0;

    SensorEventListener sensorEvtListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            switch(event.sensor.getType())
            {
                case Sensor.TYPE_ACCELEROMETER:
                    lifeCheckerManager.getInstance().setAcelaromtroXACT(event.values[0]);
                    lifeCheckerManager.getInstance().setAcelaromtroYACT(event.values[1]);
                    lifeCheckerManager.getInstance().setAcelaromtroZACT(event.values[2]);
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


    @Override
    public void onCreate() {
        super.onCreate();
        //Micro

        //Micro FIM
    }

    protected void onHandleIntent(Intent intent) {
        lifeCheckerManager.getInstance().setAlarmesDiurna(true);
        preferenciasAplicacao pref = new preferenciasAplicacao(getApplicationContext());

        //microAtivo
        boolean ativo = pref.getAlarmeMicroNocturno();
        Log.i("Ativo", Boolean.toString(ativo));
        if(!ativo) {
            prefApp = new preferenciasAplicacao(getApplicationContext());
            mThresholdAlarme = 8;
            calibragemAlarme = Math.abs(prefApp.getCalibracao());

            microAlarme = new alarmeMicrofone();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLockAlarme = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");
            pref.setAlarmeMicroNocturno(true);
            startAlarme();
        }


        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        resp = respBDD.getResponsavel();

        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        paci = paciBDD.getPaciente();

        Log.i("variavel"," valor diurno: " + Boolean.toString(diurno));

        if(diurno == true) {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                sensorAceleromtro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                if (sensorAceleromtro != null && lifeCheckerManager.getInstance().getRegistouAcelaromtro() == false) {
                    sensorManager.registerListener(sensorEvtListener, sensorAceleromtro, SensorManager.SENSOR_DELAY_NORMAL);
                    lifeCheckerManager.getInstance().setRegistouAcelaromtro(true);
                }

            }

            float variacaoX = Math.abs(lifeCheckerManager.getInstance().getAcelaromtroXACT() - lifeCheckerManager.getInstance().getAcelaromtroX());
            float variacaoY = Math.abs(lifeCheckerManager.getInstance().getAcelaromtroYACT() - lifeCheckerManager.getInstance().getAcelaromtroY());
            float variacaoZ = Math.abs(lifeCheckerManager.getInstance().getAcelaromtroZACT() - lifeCheckerManager.getInstance().getAcelaromtroZ());
            if (variacaoX < 0.3 && variacaoY < 0.3 && variacaoZ < 0.3 && (variacaoX != 0 || variacaoY != 0 || variacaoZ != 0)) {

                Intent intentAlarme = new Intent(this.getApplicationContext(), pacienteAlarme.class);
                intentAlarme.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intentAlarme);
            /*
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null)
            {
                enviarHistoAlertaResp();
            }
            if(resp.getNotificacaoSMS())
            {
                Log.i("SMS", "enviar sms vida");
                String Conteudo = getResources().getString(R.string.sms_no_internet, paci.getNomePaciente() + " " + paci.getApelidoPaciente());
                enviarSMS(Conteudo);
            }*/


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
        }
        else
        {
            prefApp = new preferenciasAplicacao(getApplicationContext());
            double valor = prefApp.getDBMaxNoturno();
            Log.i("Noturno", "verificação noturna: " + Double.toString(valor));
            if(valor < 10 && valor != 0.0)
            {
                Log.i("Noturno", "yeeessss");
                Intent intentAlarme = new Intent(this.getApplicationContext(), pacienteAlarme.class);
                intentAlarme.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intentAlarme);
            }
            Log.i("Noturno", "nooooo");
        }
        prefApp.setDBMaxNoturno(1);
        proximaAtualizacao();
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

    private void proximaAtualizacao()
    {
        barulhoNoturno = false;
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

    //MICROFONE

    private Runnable mSleepTaskAlarme = new Runnable() {
        public void run() {
            //Log.i("Noise", "runnable mSleepTask");
            startAlarme();
        }
    };

    private Runnable mPollTaskAlarme = new Runnable() {
        public void run() {

            double amp = microAlarme.getAmplitude();
            //Log.i("calibra Alarme", Double.toString(amp));
            //Log.i("calibracao Alarme", "calib A");
            //Log.i("calibracao Valor Alarme", Double.toString(calibragemAlarme));
            //Log.i("amp Alarme", Double.toString(amp));
            Log.i("calibracao total Alarme", Double.toString(amp + calibragemAlarme));
            if((amp + calibragemAlarme) > DBLimite)
            {
                prefApp = new preferenciasAplicacao(getApplicationContext());
                prefApp.setDBMaxNoturno(amp + calibragemAlarme);
            }


            //Log.i("Noise", "runnable mPollTask");

            if ((amp > mThresholdAlarme)) {
                callForHelpAlarme(amp);
                //Log.i("Noise", "==== onCreate ===");
            }
            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandlerAlarme.postDelayed(mPollTaskAlarme, INTERVALO_MICRO_ALARME);
        }
    };

    private void callForHelpAlarme(double signalEMA) {

        //stop();

        // Show alert when noise thersold crossed
        Toast.makeText(getApplicationContext(), "Noise Thersold Crossed, do here your stuff.",
                Toast.LENGTH_LONG).show();
        Log.d("SONUND", String.valueOf(signalEMA));
        //tv_noice.setText(signalEMA+"dB");
    }

    //MICROFONE METODOS
    private void startAlarme() {
        //Log.i("Noise", "==== start ===");
        microAlarme.start();
        if (!mWakeLockAlarme.isHeld()) {
            mWakeLockAlarme.acquire();
        }
        //Noise monitoring start
        // Runnable(mPollTask) will execute after POLL_INTERVAL
        mHandlerAlarme.postDelayed(mPollTaskAlarme, INTERVALO_MICRO_ALARME);
    }

    private void stopAlarme() {
        Log.i("Noise", "==== Stop Noise Monitoring===");
        if (mWakeLockAlarme.isHeld()) {
            mWakeLockAlarme.release();
        }
        mHandlerAlarme.removeCallbacks(mSleepTaskAlarme);
        mHandlerAlarme.removeCallbacks(mPollTaskAlarme);
        microAlarme.stop();
        //microfoneRunning = false;

    }

}
