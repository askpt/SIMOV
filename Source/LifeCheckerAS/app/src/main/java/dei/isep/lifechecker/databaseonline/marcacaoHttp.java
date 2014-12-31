package dei.isep.lifechecker.databaseonline;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.model.marcacao;

/**
 * Created by Diogo on 17-12-2014.
 */
public class marcacaoHttp {

    public String dataAtual()
    {
        Date dNow = new Date();
        SimpleDateFormat dataFromatada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataAtual = dataFromatada.format(dNow);
        return dataAtual;
    }

    public void addmarcacao(marcacao marcacao, interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/Marcacoes";
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("PacienteID",String.valueOf(marcacao.getIdPacienteMarc())));
        postParameters.add(new BasicNameValuePair("TipoMarcacao",marcacao.getTipoMarc()));
        postParameters.add(new BasicNameValuePair("Data",marcacao.getDataMarc() + "T" + marcacao.getHoraMarc()));
        postParameters.add(new BasicNameValuePair("Longitude",String.valueOf(marcacao.getLongitudeMarc())));
        postParameters.add(new BasicNameValuePair("Latitude",String.valueOf(marcacao.getLatitudeMarc())));
        postParameters.add(new BasicNameValuePair("Local",marcacao.getLocalMarc()));
        postParameters.add(new BasicNameValuePair("EstadoMarcacaoId",String.valueOf(marcacao.getIdEstadoMarc())));
        postParameters.add(new BasicNameValuePair("HoraSincronizacao",dataAtual()));

        executarTaskPOST(url, postParameters, interfaceListener);
    }

    public void updateMarcacao(marcacao marcacao, interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/Marcacoes/" + marcacao.getIdMarcacaoMarc();
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("Id",String.valueOf(marcacao.getIdMarcacaoMarc())));
        postParameters.add(new BasicNameValuePair("PacienteID",String.valueOf(marcacao.getIdPacienteMarc())));
        postParameters.add(new BasicNameValuePair("TipoMarcacao",marcacao.getTipoMarc()));
        postParameters.add(new BasicNameValuePair("Data",marcacao.getDataMarc() + "T" + marcacao.getHoraMarc()));
        postParameters.add(new BasicNameValuePair("Longitude",String.valueOf(marcacao.getLongitudeMarc())));
        postParameters.add(new BasicNameValuePair("Latitude",String.valueOf(marcacao.getLatitudeMarc())));
        postParameters.add(new BasicNameValuePair("Local",marcacao.getLocalMarc()));
        postParameters.add(new BasicNameValuePair("EstadoMarcacaoId",String.valueOf(marcacao.getIdEstadoMarc())));
        postParameters.add(new BasicNameValuePair("HoraSincronizacao",dataAtual()));

        executarTaskPut(url, postParameters, interfaceListener);
    }

    public void retornarMarcacoesEstado(int idResponsavel, int estado, interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/Marcacoes/GetMarcacaoPacientes/" + idResponsavel + "/" + estado;
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        executarTaskGet(url, postParameters, interfaceListener);
    }

    public void retornarMarcacoesByPaciente(int idPaciente, interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/Marcacoes/ObterMarcacoesPacientes/" + idPaciente;
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
