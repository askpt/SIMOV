package dei.isep.lifechecker.databaseonline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.model.paciente;

public class pacienteHttp {

    public String dataAtualCompleta()
    {
        Date dNow = new Date();
        SimpleDateFormat dataFromatada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataAtual = dataFromatada.format(dNow);
        return dataAtual;
    }

    public String dataAtual()
    {
        Date dNow = new Date();
        SimpleDateFormat dataFromatada = new SimpleDateFormat("yyyy-MM-dd");
        String dataAtual = dataFromatada.format(dNow);
        return dataAtual;
    }

    public String horaAtual()
    {
        Date dNow = new Date();
        SimpleDateFormat dataFromatada = new SimpleDateFormat("HH:mm:ss");
        String dataAtual = dataFromatada.format(dNow);
        return dataAtual;
    }
	
	public void verificarMail(String mail, interfaceResultadoAsyncPost interfaceListener)
	{
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email="
				+ mail;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		executarTaskPOST(url, postParameters, interfaceListener);
	}


	
	public void addPaciente(paciente paciente, 
			interfaceResultadoAsyncPost interfaceListener)
	{
        String url = "http://simovws.azurewebsites.net/api/Pacientes/";

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Nome", paciente.getNomePaciente()));
		postParameters.add(new BasicNameValuePair("Apelido", paciente.getApelidoPaciente()));
		postParameters.add(new BasicNameValuePair("Email", paciente.getMailPaciente()));
		postParameters.add(new BasicNameValuePair("ContactoTlf", paciente.getContactoPaciente()));

		postParameters.add(new BasicNameValuePair("Responsavel_ID", String.valueOf(paciente.getIdResponsavelPaciente())));
        postParameters.add(new BasicNameValuePair("NomeLocal", String.valueOf(paciente.getNomeLocalPaciente())));
        postParameters.add(new BasicNameValuePair("Data", String.valueOf(paciente.getDataLocalPaciente() + "T" + paciente.getHoraLocalPaciente())));
        postParameters.add(new BasicNameValuePair("Ativo", Boolean.toString(paciente.getAtivoPaciente())));
		postParameters.add(new BasicNameValuePair("HoraSincronizacao", dataAtualCompleta()));
		

		executarTaskPOST(url, postParameters, interfaceListener);
	}

    public void updatePaciente(paciente paciente, interfaceResultadoAsyncPost listenerUpdate)
    {

        String url = "http://simovws.azurewebsites.net/api/Pacientes/" + paciente.getIdPaciente();

        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("ID", String.valueOf(paciente.getIdPaciente())));
        postParameters.add(new BasicNameValuePair("Nome", paciente.getNomePaciente()));
        postParameters.add(new BasicNameValuePair("Apelido", paciente.getApelidoPaciente()));
        postParameters.add(new BasicNameValuePair("Email", paciente.getMailPaciente()));
        postParameters.add(new BasicNameValuePair("ContactoTlf", paciente.getContactoPaciente()));

        postParameters.add(new BasicNameValuePair("Longitude", Double.toString(paciente.getLongitudePaciente())));
        postParameters.add(new BasicNameValuePair("Latitude", Double.toString(paciente.getLatitudePaciente())));


        postParameters.add(new BasicNameValuePair("NomeLocal", String.valueOf(paciente.getNomeLocalPaciente())));
        postParameters.add(new BasicNameValuePair("Data", String.valueOf(dataAtual() + "T" + horaAtual())));
        postParameters.add(new BasicNameValuePair("Ativo", Boolean.toString(paciente.getAtivoPaciente())));
        postParameters.add(new BasicNameValuePair("HoraSincronizacao", dataAtualCompleta()));
        postParameters.add(new BasicNameValuePair("Responsavel_ID", String.valueOf(paciente.getIdResponsavelPaciente())));
        executarTaskPut(url, postParameters, listenerUpdate);
    }
	
	public void retornarPacientesIdResposnavel(int id, interfaceResultadoAsyncPost interfaceListener)
	{
		String url = "http://simovws.azurewebsites.net/api/Pacientes/GetPacientes/" + id;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		executarTaskGet(url, postParameters, interfaceListener);
	}

    public  void retornarPacienteById(int idPaciente, interfaceResultadoAsyncPost interfaceListener)
    {
        String url = "http://simovws.azurewebsites.net/api/Pacientes/" + idPaciente;
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
