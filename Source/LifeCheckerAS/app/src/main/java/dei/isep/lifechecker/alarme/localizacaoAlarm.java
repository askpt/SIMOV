package dei.isep.lifechecker.alarme;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;

import java.util.ArrayList;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.database.baseDeDadosInterna;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.locationHTTP;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.json.locationJson;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.GPSTracker;
import dei.isep.lifechecker.other.lifeCheckerManager;

/**
 * Created by Diogo on 29-12-2014.
 */
public class localizacaoAlarm extends IntentService {

    Location localAtual;
    paciente paci;
    responsavel resp;
    boolean repetir = false;


    public localizacaoAlarm()
    {
        super(localizacaoAlarm.class.getSimpleName());
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        paci = paciBDD.getPaciente();
        responsavelBDD respBDD = new responsavelBDD(getApplicationContext());
        resp = respBDD.getResponsavel();

        Log.i("alarme", "recomocou");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null)
        {
            obterPosicao();
        }
        else if(resp.getNotificacaoSMS() == true)
        {
            if(resp.getNotificacaoSMS()) {
                String conteudo = getResources().getString(R.string.sms_no_internet, paci.getNomePaciente() + " " + paci.getApelidoPaciente());
                enviarSMS(conteudo);
            }
        }
        Log.i("Enviar localizacao"," loction");
        proximaAtualizacao(15);
    }


    private void obterPosicao()
    {
        Log.i("alarme", "passou alarme COM notificar");
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation())
        {
            localAtual = gps.getLocation();
            if(localAtual != null) {
                locationHTTP locHttp = new locationHTTP();
                locHttp.obterLocalPorCoordenadas(localAtual, listenerLocal);
            }
            else if(resp.getNotificacaoSMS() || resp.getNotificacaoMail())
            {
                String conteudoSMS = getString(R.string.sms_no_location, paci.getNomePaciente());
                if(resp.getNotificacaoSMS()) {
                    Log.i("SMS", "Loaclização: sem local");
                    enviarSMS(conteudoSMS);
                }
                if(resp.getNotificacaoMail())
                {
                    Log.i("MAIL", "Loaclização: sem local");
                    responsavelHttp respHttp = new responsavelHttp();
                    respHttp.enviarMail(resp.getIdResponsavel(), conteudoSMS,enviarMailListener);
                }

            }
        }
        else if(resp.getNotificacaoSMS() || resp.getNotificacaoMail())
        {
            String conteudoSMS = getString(R.string.sms_no_location, paci.getNomePaciente());
            if(resp.getNotificacaoSMS()) {
                Log.i("SMS", "Loaclização: sem local");
                enviarSMS(conteudoSMS);
            }
            if(resp.getNotificacaoMail())
            {
                responsavelHttp respHttp = new responsavelHttp();
                respHttp.enviarMail(resp.getIdResponsavel(), conteudoSMS,enviarMailListener);
                Log.i("MAIL", "Loaclização: sem local");
            }
        }

    }

    interfaceResultadoAsyncPost listenerLocal = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {

            if (codigo == 1 && conteudo.length() > 10) {
                String conteudoAlterado = conteudo.replaceAll("(\\r|\\n)", "");

                locationJson locaJson = new locationJson(conteudoAlterado);
                boolean status = locaJson.getStatus();

                ArrayList<String> cidades = new ArrayList<String>();
                cidades = locaJson.getAdress();

                //*****RecuperarPaciente
                //recuperarPaciente();
                if(cidades.size() != 0) {
                    paci.setLatitudePaciente(localAtual.getLatitude());
                    paci.setLongitudePaciente(localAtual.getLongitude());
                    paci.setNomeLocalPaciente(cidades.get(0));

                    baseDeDadosInterna bddInt = new baseDeDadosInterna(getApplicationContext());
                    paci.setHoraLocalPaciente(bddInt.horaAtual());
                    paci.setDataLocalPaciente(bddInt.dataAtual());

                    pacienteHttp paciHttp = new pacienteHttp();
                    paciHttp.updatePaciente(paci, listenerUpdateLocalPaci);
                }
                else if(resp.getNotificacaoSMS() || resp.getNotificacaoMail())
                {
                    String conteudoSMS = getString(R.string.sms_no_location, paci.getNomePaciente());
                    if(resp.getNotificacaoSMS()) {
                        Log.i("SMS", "Loaclização: sem local");
                        enviarSMS(conteudoSMS);
                    }
                    if(resp.getNotificacaoMail())
                    {
                        responsavelHttp respHttp = new responsavelHttp();
                        respHttp.enviarMail(resp.getIdResponsavel(), conteudoSMS,enviarMailListener);
                        Log.i("MAIL", "Loaclização: sem local");
                    }
                    //proximaAtualizacao(5);
                }
            }
        };

    };

    interfaceResultadoAsyncPost listenerUpdateLocalPaci = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            if (codigo == 1 && repetir == false) {
                //proximaAtualizacao(20);
                repetir = true;
               }
        };

    };

    private void enviarSMS(String conteudo)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(resp.getContactoResponsavel(), null, conteudo, null, null);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void proximaAtualizacao(int minutos)
    {
        long currentTimeMillis = System.currentTimeMillis();
        long proximaAtualizacaoMili = currentTimeMillis + minutos * DateUtils.MINUTE_IN_MILLIS;
        Log.i("alarmealarme", "passou alarme");
        Intent intent = new Intent(this, this.getClass());
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, proximaAtualizacaoMili, pendingIntent);
        Log.i("alarm", " proximo alarm daqui a " + (proximaAtualizacaoMili - currentTimeMillis)/1000 + " sec");

    }

    interfaceResultadoAsyncPost enviarMailListener = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            if (codigo == 1 && repetir == false) {
            }
        };

    };
}
