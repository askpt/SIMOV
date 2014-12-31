package dei.isep.lifechecker.json;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Diogo on 29-12-2014.
 */
public class locationJson {

    JSONObject jsonObj;
    String conteudo;

    public locationJson(){};

    public locationJson(String conteudo)
    {
        this.conteudo = conteudo;
    }

    public LatLng getLatLong()
    {
        try {
            jsonObj = new JSONObject(conteudo);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        Double lon = new Double(0);
        Double lat = new Double(0);

        try {

            lon = ((JSONArray)jsonObj.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObj.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (Exception e) {
            e.printStackTrace();

        }

        LatLng gp = new LatLng(lat,lon);

        return gp;
    }

    public boolean getStatus()
    {
        boolean resultado;
        try
        {
            jsonObj = new JSONObject(conteudo);
            String status = jsonObj.getString("status");
            if(status.compareTo("OK") == 0)
            {
                resultado = true;
            }
            else
            {
                resultado = false;
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
            resultado = false;
        }
        return resultado;

    }

    public ArrayList<String> getAdress()
    {

        String addre = null;
        //Address addr1 = null;

        ArrayList<String> enderecos = new ArrayList<String>();

        try
        {
            jsonObj = new JSONObject(conteudo);
            JSONArray jsonArray  = ((JSONArray) jsonObj.get("results"));
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = (JSONObject) jsonArray.getJSONObject(i);
                String localCidade = transformJsonOneLocation();
                enderecos.add(localCidade);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return enderecos;
    }

    private String transformJsonOneLocation()
    {
        String lugar = "";
        try{
            lugar = jsonObj.getString("formatted_address");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return lugar;
    }
}
