package dei.isep.lifechecker.databaseonline;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;

public class pacienteHttp {
	
	public void verificarMail(String mail, interfaceResultadoAsyncPost interfaceListener)
	{
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email="
				+ mail;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
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
