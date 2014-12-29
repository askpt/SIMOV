package dei.isep.lifechecker.databaseonline;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.model.historicoAlertas;

/**
 * Created by Diogo on 22-12-2014.
 */
public class historicoAlertasHttp {

    public String dataAtual()
    {
        Date dNow = new Date();
        SimpleDateFormat dataFromatada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataAtual = dataFromatada.format(dNow);
        return dataAtual;
    }


    public void retornarHistoricoAlertasIdResposnavel(int idResponsavel, interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/HistoricoAlertas/GetAlertas/" + idResponsavel;
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        executarTaskGet(url, postParameters, interfaceListener);
    }

    public void addHistoricoAlerta(historicoAlertas histoAlert, interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/HistoricoAlertas";
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("Data",dataAtual()));
        postParameters.add(new BasicNameValuePair("Longitude",String.valueOf(histoAlert.getLongitudeHistAlt())));
        postParameters.add(new BasicNameValuePair("Latitude",String.valueOf(histoAlert.getLatitudeHistAlt())));
        postParameters.add(new BasicNameValuePair("Local",histoAlert.getLocalHistAlt()));
        postParameters.add(new BasicNameValuePair("HoraSincronizacao",dataAtual()));
        postParameters.add(new BasicNameValuePair("AlertaId", String.valueOf(histoAlert.getIdAlertaHistAlt())));
        postParameters.add(new BasicNameValuePair("PacienteId", String.valueOf(histoAlert.getIdPacienteHistAlt())));
        executarTaskPOST(url,postParameters,interfaceListener);

    }


    private void executarTaskPOST(String url, List<NameValuePair> postParameters, interfaceResultadoAsyncPost interfaceListener)
    {
        httpPost httpP;
        httpP = new httpPost(url, postParameters);
        httpP.setOnResultListener(interfaceListener);
        httpP.execute();
    }

    private void executarTaskGet(String url, List<NameValuePair> postParameters, interfaceResultadoAsyncPost interfaceListener)
    {
        httpGet httpG;
        httpG = new httpGet(url,postParameters);
        httpG.setOnResultListener(interfaceListener);
        httpG.execute();
    }

    private void executarTaskPut(String url, List<NameValuePair> postParameters, interfaceResultadoAsyncPost interfaceListener)
    {
        httpPut httpP;
        httpP = new httpPut(url, postParameters);
        httpP.setOnResultListener(interfaceListener);
        httpP.execute();
    }
}
