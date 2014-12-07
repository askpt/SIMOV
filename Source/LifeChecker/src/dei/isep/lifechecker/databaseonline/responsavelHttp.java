package dei.isep.lifechecker.databaseonline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;

public class responsavelHttp {
	
	public responsavelHttp(){};
	
	public boolean verificarMail(String mail)
	{
		String conteudo = "";
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email=" + mail;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		httpPost httpPostStart = new httpPost(url, postParameters);
		httpPostStart.execute(mail);
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
		if(conteudo.compareTo("true") == 0)
		{
			resultado = true;
		}
		else
		{
			resultado = false;
		}
		return resultado;
	}

}
