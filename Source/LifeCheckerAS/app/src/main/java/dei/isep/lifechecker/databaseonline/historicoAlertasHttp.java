package dei.isep.lifechecker.databaseonline;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;

/**
 * Created by Diogo on 22-12-2014.
 */
public class historicoAlertasHttp {


    public void retornarHistoricoAlertasIdResposnavel(int idResponsavel, interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/HistoricoAlertas/GetAlertas/" + idResponsavel;
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        executarTaskGet(url, postParameters, interfaceListener);
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
