package dei.isep.lifechecker.databaseonline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class httpGet extends AsyncTask<String, Void, Boolean> {
	

	@Override
	protected Boolean doInBackground(String... params) {
		String result = "fail";
		Boolean resultadoGet = false;
		String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email=";
		url += params[0];
		BufferedReader inStream = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpRequest = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpRequest);
			inStream = new BufferedReader(
				new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer buffer = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = inStream.readLine()) != null) {
				buffer.append(line + NL);
			}
			inStream.close();

			result = buffer.toString();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultadoGet;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
	}

}
