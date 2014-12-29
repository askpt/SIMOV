package dei.isep.lifechecker.databaseonline;

import android.location.Location;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public void obterLocalPorCoordenadas(Location local, interfaceResultadoAsyncPost listener)
    {
        String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + local.getLatitude() + "," + local.getLongitude();
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        executarTaskPOST(url,postParameters,listener);
    }

    private void executarTaskPOST(String url, List<NameValuePair> postParameters, interfaceResultadoAsyncPost interfaceListener)
    {
        httpPost httpP;
        httpP = new httpPost(url, postParameters);
        httpP.setOnResultListener(interfaceListener);
        httpP.execute();
    }
}
