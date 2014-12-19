package dei.isep.lifechecker.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dei.isep.lifechecker.model.estadoMarcacao;

/**
 * Created by Diogo on 18-12-2014.
 */
public class estadoMarcacaoJson {

    private String ID_JSON_ESTMARC = "Id";
    private String DESIGNACAO_JSON_ESTMARC = "Designacao";
    private String DATAHORASINCRO_JSON_ESTMARC = "HoraSincronizacao";

    String conteudo;

    public estadoMarcacaoJson()
    {
        this.conteudo = "";
    }

    public estadoMarcacaoJson(String conteudo)
    {
        this.conteudo = conteudo;
    }

    private JSONObject jsonObj;

    public ArrayList<estadoMarcacao> transformJsonEstadoMarcacao()
    {
        ArrayList<estadoMarcacao> listaEstadoMarcacao = new ArrayList<estadoMarcacao>();
        try
        {
            JSONArray jsonArray= new JSONArray(conteudo);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = (JSONObject) jsonArray.getJSONObject(i);
                estadoMarcacao estM = new estadoMarcacao();
                estM = transformJsonOneEstadoMarcacao();
                listaEstadoMarcacao.add(estM);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return listaEstadoMarcacao;
    }

    private estadoMarcacao transformJsonOneEstadoMarcacao()
    {
        estadoMarcacao estMarc = new estadoMarcacao();
        try{
            JsonGeral jsonGerl = new JsonGeral();
            estMarc.setIdEstadoMarcacao(jsonObj.getInt(ID_JSON_ESTMARC));
            estMarc.setExplicacaoEstMarc(jsonObj.getString(DESIGNACAO_JSON_ESTMARC));
            estMarc.setDataSincroEstMarc(jsonGerl.getDataSincroBDDOnline(jsonObj.getString(DATAHORASINCRO_JSON_ESTMARC)));
            estMarc.setHoraSincroEstMarc(jsonGerl.getHoraSincroBDDOnline(jsonObj.getString(DATAHORASINCRO_JSON_ESTMARC)));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return estMarc;
    }



}
