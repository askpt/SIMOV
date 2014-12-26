package dei.isep.lifechecker.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dei.isep.lifechecker.model.alerta;

/**
 * Created by Diogo on 22-12-2014.
 */
public class alertaJson {

    /*
            "Id": 2,
        "Designacao": "Não está a dar sinais de vida",
        "HoraSincronizacao": "2014-12-22T14:32:23.847"
     */
    private String ID_JSON_ALERTA = "Id";
    private String DESIGNACAO_JSON_ALERTA = "Designacao";
    private String DATAHORASINCRO_JSON_ALERTA = "HoraSincronizacao";

    String conteudo;

    public alertaJson()
    {
        this.conteudo = "";
    }

    public alertaJson(String conteudo)
    {
        this.conteudo = conteudo;
    }

    private JSONObject jsonObj;

    public ArrayList<alerta> transformJsonAlertas()
    {
        ArrayList<alerta> listaAlerta = new ArrayList<alerta>();
        try
        {
            JSONArray jsonArray= new JSONArray(conteudo);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = (JSONObject) jsonArray.getJSONObject(i);
                alerta estM = new alerta();
                estM = transformJsonOneAlerta();
                listaAlerta.add(estM);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return listaAlerta;
    }

    public alerta transformJsonOneAlerta()
    {
        alerta alrt = new alerta();
        try{
            JsonGeral jsonGerl = new JsonGeral();
            alrt.setIdAlerta(jsonObj.getInt(ID_JSON_ALERTA));
            alrt.setExplicacaoAlerta(jsonObj.getString(DESIGNACAO_JSON_ALERTA));
            alrt.setDataSincroAlerta(jsonGerl.getDataSincroBDDOnline(jsonObj.getString(DATAHORASINCRO_JSON_ALERTA)));
            alrt.setHoraSincroAlerta(jsonGerl.getHoraSincroBDDOnline(jsonObj.getString(DATAHORASINCRO_JSON_ALERTA)));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return alrt;
    }
}
