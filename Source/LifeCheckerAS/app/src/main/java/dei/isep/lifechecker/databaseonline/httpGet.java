package dei.isep.lifechecker.databaseonline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;

import android.os.AsyncTask;
import android.util.Log;

public class httpGet extends AsyncTask<String, Void, String> {

	interfaceResultadoAsyncPost interfaceResultadoAsyncPost;
	List<NameValuePair> postParameters;
	String url;

	public httpGet(String url, List<NameValuePair> postParameters) {
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

		StringBuilder builder = new StringBuilder();
		BufferedReader inBuffer = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				Log.v("Getter", "Your data: " + builder.toString());

				interfaceResultadoAsyncPost.obterResultado(1,
						builder.toString());
			} else {
				Log.e("Getter", "Failed to download file");
			}

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