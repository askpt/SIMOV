package dei.isep.lifechecker.databaseonline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.model.responsavel;

public class responsavelHttp {

	public responsavelHttp() {
	};

	public static final String JSON_RESP_ID = "ID";

	public void inserirResponsavel(responsavel responsavel,
			interfaceResultadoAsyncPost interfaceListener) {

		Date dNow = new Date();
		SimpleDateFormat dataFromatada = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String dataAtual = dataFromatada.format(dNow);
		String url = "http://simovws.azurewebsites.net/api/Responsaveis";

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("Nome", responsavel
				.getNomeResposnavel()));
		postParameters.add(new BasicNameValuePair("Apelido", responsavel
				.getApelidoResposnavel()));
		postParameters.add(new BasicNameValuePair("ContactoTlf", responsavel
				.getContactoResponsavel()));
		postParameters.add(new BasicNameValuePair("NotifMail", String
				.valueOf(responsavel.getNotificacaoMail())));
		postParameters.add(new BasicNameValuePair("NotifSms", String
				.valueOf(responsavel.getNotificacaoSMS())));
		postParameters.add(new BasicNameValuePair("PeriodDiurna", String
				.valueOf(responsavel.getPeriodicidadeDiurna())));
		postParameters.add(new BasicNameValuePair("PeriodNoturna", String
				.valueOf(responsavel.getPeriodicidadeNoturna())));
		postParameters.add(new BasicNameValuePair("Email", responsavel
				.getMailResponsavel()));
		postParameters.add(new BasicNameValuePair("Password", responsavel
				.getPassResponsavel()));
		postParameters.add(new BasicNameValuePair("HoraSincronizacao",
				dataAtual));
		executarTaskPOST(url, postParameters, interfaceListener);

	}

	public void getIdResposnavel(String mail, String pass, interfaceResultadoAsyncPost interfaceListener) {

		String url = "http://simovws.azurewebsites.net/api/Responsaveis/Login?email="
				+ mail + "&pass=" + pass;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		executarTaskPOST(url, postParameters, interfaceListener);
	}

	public void verificarMail(String mail,
			interfaceResultadoAsyncPost interfaceListener) {
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email="
				+ mail;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		executarTaskPOST(url, postParameters, interfaceListener);
	}
	
	public void enviarMailRecuperacao(String mail, interfaceResultadoAsyncPost interfaceListener)
	{
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/ResetPassword?email=" + mail;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		executarTaskPOST(url, postParameters, interfaceListener);
	}

	
	public void loginVerificar(String mail, String pass, interfaceResultadoAsyncPost interfaceListener)
	{
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/Login?email=" + mail +"&pass=" + pass;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		executarTaskPOST(url, postParameters, interfaceListener);
	}
	
	private void executarTaskPOST(String url, List<NameValuePair> postParameters,
			interfaceResultadoAsyncPost interfaceListener) {
		httpPost httpP;
		httpP = new httpPost(url, postParameters);
		httpP.setOnResultListener(interfaceListener);
		httpP.execute();
	}

}
