package dei.isep.lifechecker.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dei.isep.lifechecker.model.historicoAlertas;

/**
 * Created by Diogo on 22-12-2014.
 */
public class historicoAlertaJson {
    private String ID_JSON_HISTALERT = "Id";
    private String DATA_JSON_HISTALERT = "Data";
    private String LONG_JSON_HISTALERT = "Longitude";
    private String LAT_JSON_HISTALERT = "Latitude";
    private String LOCAL_JSON_HISTALERT = "Local";
    private String HORADATASINCRO_JSON_HISTALERT = "HoraSincronizacao";
    private String IDALERTA_JSON_HISTALERT = "AlertaId";
    private String IDPACIENTE_JSON_HISTALERT = "PacienteID";

    String conteudo;

    public historicoAlertaJson()
    {
        this.conteudo = "";
    }

    public historicoAlertaJson(String conteudo)
    {
        this.conteudo = conteudo;
    }

    private JSONObject jsonObj;

    public ArrayList<historicoAlertas> transformJsonHistoricoAlerta()
    {
        ArrayList<historicoAlertas> listaHistorioAlertas = new ArrayList<historicoAlertas>();
        try
        {
            JSONArray jsonArray = new JSONArray(conteudo);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = (JSONObject) jsonArray.getJSONObject(i);
                historicoAlertas histoAlert = new historicoAlertas();
                histoAlert = transformJsonOneHistoricoAlerta();
                listaHistorioAlertas.add(histoAlert);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return  listaHistorioAlertas;
    }

    public historicoAlertas transformJsonOneHistoricoAlerta()
    {
        historicoAlertas histoAler = new historicoAlertas();
        try
        {
            JsonGeral jsonGeral = new JsonGeral();
            histoAler.setIdHistAlt(jsonObj.getInt(ID_JSON_HISTALERT));
            histoAler.setIdPacienteHistAlt(jsonObj.getInt(IDPACIENTE_JSON_HISTALERT));
            histoAler.setIdAlertaHistAlt(jsonObj.getInt(IDALERTA_JSON_HISTALERT));
            histoAler.setHoraHistAlt(jsonGeral.getHoraSincroBDDOnline(jsonObj.getString(DATA_JSON_HISTALERT)));
            histoAler.setDataHistAlt(jsonGeral.getDataSincroBDDOnline(jsonObj.getString(DATA_JSON_HISTALERT)));
            histoAler.setLatitudeHistAlt(jsonObj.getDouble(LAT_JSON_HISTALERT));
            histoAler.setLongitudeHistAlt(jsonObj.getDouble(LONG_JSON_HISTALERT));
            histoAler.setLocalHistAlt(jsonObj.getString(LOCAL_JSON_HISTALERT));
            histoAler.setHoraSincroHistAlt(jsonGeral.getHoraSincroBDDOnline(jsonObj.getString(HORADATASINCRO_JSON_HISTALERT)));
            histoAler.setDataSincroHistAlt(jsonGeral.getDataSincroBDDOnline(jsonObj.getString(HORADATASINCRO_JSON_HISTALERT)));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return histoAler;
    }

}
