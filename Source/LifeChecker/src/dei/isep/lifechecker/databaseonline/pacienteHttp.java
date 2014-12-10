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
	
	public void verificarMail(String mail, interfaceResultadoAsyncPost interfaceListener)
	{
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email="
				+ mail;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		executarTask(url, postParameters, interfaceListener);
	}
	
	public void addPaciente(paciente paciente, 
			interfaceResultadoAsyncPost interfaceListener)
	{
		Date dNow = new Date();
		SimpleDateFormat dataFromatada = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String dataAtual = dataFromatada.format(dNow);
		String url = "http://simovws.azurewebsites.net/api/Pacientes";

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Nome", paciente.getNomePaciente()));
		postParameters.add(new BasicNameValuePair("Apelido", paciente.getApelidoPaciente()));
		postParameters.add(new BasicNameValuePair("Email", paciente.getMailPaciente()));
		postParameters.add(new BasicNameValuePair("ContactoTlf", paciente.getContactoPaciente()));
		postParameters.add(new BasicNameValuePair("Responsavel_ID", String.valueOf(paciente.getIdResponsavelPaciente())));
		postParameters.add(new BasicNameValuePair("HoraSincronizacao", dataAtual));
		

		executarTask(url, postParameters, interfaceListener);
	}
	
	private void executarTask(String url, List<NameValuePair> postParameters, interfaceResultadoAsyncPost interfaceListener)
	{
		httpPost httpP;
		httpP = new httpPost(url, postParameters);
		httpP.setOnResultListener(interfaceListener);
		httpP.execute();
	}

}
