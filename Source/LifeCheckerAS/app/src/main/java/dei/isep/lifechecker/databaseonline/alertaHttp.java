package dei.isep.lifechecker.databaseonline;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;

/**
 * Created by Diogo on 22-12-2014.
 */
public class alertaHttp {

    public void retornarTiposAlertas(interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/Alertas";
        List<NameValuePair> postParameter = new ArrayList<NameValuePair>();
        executarTaskGet(url, postParameter, interfaceListener);
    }


    private void executarTaskGet(String url, List<NameValuePair> postParameters, interfaceResultadoAsyncPost interfaceListener)
    {
        httpGet httpG;
        httpG = new httpGet(url,postParameters);
        httpG.setOnResultListener(interfaceListener);
        httpG.execute();
    }
}
