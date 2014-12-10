package dei.isep.lifechecker.databaseonline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;
import dei.isep.lifechecker.model.responsavel;

public class responsavelHttp {

	public responsavelHttp() {
	};

	public static final String JSON_RESP_ID = "ID";

	public boolean verificarMail(String mail) {
		String conteudo = "";
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email="
				+ mail;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		httpPost httpPostStart = new httpPost(url, postParameters);
		httpPostStart.execute();
		try {
			conteudo = httpPostStart.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean resultado;
		conteudo = conteudo.replaceAll("[\r\n]+", "");
		if (conteudo.compareTo("true") == 0) {
			resultado = true;
		} else {
			resultado = false;
		}
		return resultado;
	}

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
		executarTask(url, postParameters, interfaceListener);

	}

	public int getIdResposnavel(String mail, String pass, interfaceResultadoAsyncPost interfaceListener) {
		int id = -1;
		String conteudo = "";
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/Login?email="
				+ mail + "&pass=" + pass;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		httpPost httpPostStart = new httpPost(url, postParameters);
		httpPostStart.execute();
		try {
			conteudo = httpPostStart.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(conteudo);
		} catch (JSONException e) {
			return -1;
		}

		int quantidade = jsonObj.length();
		if (quantidade == 1) {
			try {
				id = jsonObj.getInt(JSON_RESP_ID);
			} catch (JSONException e) {
				return -1;
			}
		} else {
			id = -1;
		}

		return id;
	}

	public void verificarMail(String mail,
			interfaceResultadoAsyncPost interfaceListener) {
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email="
				+ mail;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		executarTask(url, postParameters, interfaceListener);
	}

	private void executarTask(String url, List<NameValuePair> postParameters,
			interfaceResultadoAsyncPost interfaceListener) {
		httpPost httpP;
		httpP = new httpPost(url, postParameters);
		httpP.setOnResultListener(interfaceListener);
		httpP.execute();
	}
}
