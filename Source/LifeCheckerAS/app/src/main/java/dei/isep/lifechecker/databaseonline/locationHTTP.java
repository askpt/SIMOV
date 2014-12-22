package dei.isep.lifechecker.databaseonline;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import dei.isep.lifechecker.interfaceResultadoAsyncPost;

/**
 * Created by Diogo on 21-12-2014.
 */
public class locationHTTP {

    public void obterCoordenadasPorString(String localidade, interfaceResultadoAsyncPost listener)
    {
        String address = localidade.replaceAll(" ", "%20");
        String url = "http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false";
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        executarTaskPOST(url,postParameters,listener);
    }

    public LatLng getLatLong(JSONObject jsonObject)
    {
        Double lon = new Double(0);
        Double lat = new Double(0);

        try {

            lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (Exception e) {
            e.printStackTrace();

        }

        LatLng gp = new LatLng(lat,lon);

        return gp;
    }


    private void executarTaskPOST(String url, List<NameValuePair> postParameters, interfaceResultadoAsyncPost interfaceListener)
    {
        httpPost httpP;
        httpP = new httpPost(url, postParameters);
        httpP.setOnResultListener(interfaceListener);
        httpP.execute();
    }
}
