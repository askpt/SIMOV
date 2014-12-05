package dei.isep.lifechecker.databaseonline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import android.os.AsyncTask;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;

public class httpPost extends AsyncTask<String, Void, String> {

	interfaceResultadoAsyncPost interfaceResultadoAsyncPost;
	List<NameValuePair> postParameters;
	String url;

	public httpPost(String url, List<NameValuePair> postParameters) {
		this.url = url;
		this.postParameters = postParameters;
	}

	public void setOnResultListener(
			interfaceResultadoAsyncPost interfaceResultadoAsyncPost) {
		if (interfaceResultadoAsyncPost != null) {
			this.interfaceResultadoAsyncPost = interfaceResultadoAsyncPost;
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String resultPost = "";
		BufferedReader inBuffer = null;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);

			HttpResponse httpResponse = httpClient.execute(request);
			inBuffer = new BufferedReader(new InputStreamReader(httpResponse
					.getEntity().getContent()));

			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String newLine = System.getProperty("line.separator");
			while ((line = inBuffer.readLine()) != null) {
				stringBuffer.append(line + newLine);
			}
			inBuffer.close();

			resultPost = stringBuffer.toString();
		} catch (Exception e) {
			// Do something about exceptions
			resultPost = e.getMessage();
		} finally {
			if (inBuffer != null) {
				try {
					inBuffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// TODO Auto-generated method stub
		interfaceResultadoAsyncPost.obterResultado(1, resultPost);
		return resultPost;
	}

	protected void onPostExecute(String resultPost) {
		super.onPostExecute(resultPost);
	}

}