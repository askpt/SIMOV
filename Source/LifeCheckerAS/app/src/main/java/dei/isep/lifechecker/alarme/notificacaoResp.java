package dei.isep.lifechecker.alarme;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.database.alertaBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.databaseonline.historicoAlertasHttp;
import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.json.historicoAlertaJson;
import dei.isep.lifechecker.model.historicoAlertas;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.responsavelAlertas;

/**
 * Created by Diogo on 12-01-2015.
 */
public class notificacaoResp extends IntentService{

    ArrayList<historicoAlertas> listaHistoricoAlertas;
    public notificacaoResp()
    {
        super(notificacaoResp.class.getSimpleName());
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        criarNotificacao();
    }

    protected void criarNotificacao()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null) {
            proximaAtualizacao(10);
            int idResponsavel = lifeCheckerManager.getInstance().getIdResponsavel();
            historicoAlertasHttp histAlHttp = new historicoAlertasHttp();
            histAlHttp.retornarHistoricoAlertasIdResposnavel(idResponsavel, historicoAlertasGetAllValidas);
        }
        else
        {
            proximaAtualizacao(1);
        }


    }

    interfaceResultadoAsyncPost historicoAlertasGetAllValidas = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
                    if(codigo == 1 && conteudo.length() > 10)
                    {
                        historicoAlertaJson histoAlerJson = new historicoAlertaJson(conteudo);
                        listaHistoricoAlertas = histoAlerJson.transformJsonHistoricoAlerta();
                        Collections.reverse(listaHistoricoAlertas);
                        criarNotificacoesEnviar();
                    }
            }
    };

    private void criarNotificacoesEnviar()
    {
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, responsavelAlertas.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);
        historicoAlertas hitAlt;

        pacienteBDD paciBDD = new pacienteBDD(getApplicationContext());
        alertaBDD alrtBDD = new alertaBDD((getApplicationContext()));

        int idUltimaAlerta = lifeCheckerManager.getInstance().getUltimaNotificacao();
        for (int i = idUltimaAlerta; i < listaHistoricoAlertas.size(); i++) {
            hitAlt = listaHistoricoAlertas.get(i);
            String nomePaciente = paciBDD.getNomeApelidoPacienteById(hitAlt.getIdPacienteHistAlt());
            String tituloA = getString(R.string.paciente_alarme_notificacao, (nomePaciente));
            String tituloB = nomePaciente;
            String tituloC = alrtBDD.getDesignacaoById(hitAlt.getIdAlertaHistAlt());
            Log.i("idPaciente",String.valueOf(hitAlt.getIdPacienteHistAlt() ));
            Log.i("nomePaciente", nomePaciente);
            Notification.Builder builder = new Notification.Builder(this)
                    .setWhen(System.currentTimeMillis())
                    .setTicker(tituloA)
                    .setSmallIcon(R.drawable.alerta_ativo)
                    .setContentTitle(tituloB)
                    .setContentText(tituloC)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentIntent(pendingIntent);

            mNotification.notify(i, builder.build());
        }
        lifeCheckerManager.getInstance().setUltimaNotificacao(listaHistoricoAlertas.size());
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
}
