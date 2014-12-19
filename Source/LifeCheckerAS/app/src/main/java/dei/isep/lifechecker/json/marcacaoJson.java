package dei.isep.lifechecker.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dei.isep.lifechecker.model.marcacao;

/**
 * Created by Diogo on 19-12-2014.
 */
public class marcacaoJson {

    private String ID_JSON_MARCACAO = "Id";
    private String ID_PACIENTE_MARCACAO = "PacienteID";
    private String ID_ESTADO_MARCACAO = "EstadoMarcacaoId";
    private String TIPO_JSON_MARCACAO = "TipoMarcacao";
    private String DATA_JSON_MARCACAO = "Data";
    private String LONGITUDE_MARCACAO = "Longitude";
    private String LATITUDE_MARCACAO = "Latitude";
    private String LOCAL_MARCACAO = "Local";
    private String HORA_DATA_SINCRO_MARCACAO = "HoraSincronizacao";



    String conteudo;

    public marcacaoJson()
    {
        this.conteudo = "";
    }

    public marcacaoJson(String conteudo)
    {
        this.conteudo = conteudo;
    }

    private JSONObject jsonObj;

    public ArrayList<marcacao> transformJsonMarcacao()
    {
        ArrayList<marcacao> listaEstadoMarcacao = new ArrayList<marcacao>();
        try
        {
            JSONArray jsonArray= new JSONArray(conteudo);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = (JSONObject) jsonArray.getJSONObject(i);
                marcacao estM = new marcacao();
                estM = transformJsonOneMarcacao();
                listaEstadoMarcacao.add(estM);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return listaEstadoMarcacao;
    }

    private marcacao transformJsonOneMarcacao()
    {
        marcacao marc = new marcacao();
        try{
            JsonGeral jsonGerl = new JsonGeral();
            marc.setIdMarcacaoMarc(jsonObj.getInt(ID_JSON_MARCACAO));
            marc.setIdPacienteMarc(jsonObj.getInt(ID_PACIENTE_MARCACAO));
            marc.setIdEstadoMarc(jsonObj.getInt(ID_ESTADO_MARCACAO));
            marc.setTipoMarc(jsonObj.getString(TIPO_JSON_MARCACAO));
            marc.setDataMarc(jsonGerl.getDataSincroBDDOnline(jsonObj.getString(DATA_JSON_MARCACAO)));
            marc.setHoraMarc(jsonGerl.getHoraSincroBDDOnline(jsonObj.getString(DATA_JSON_MARCACAO)));
            marc.setLatitudeMarc(jsonObj.getDouble(LATITUDE_MARCACAO));
            marc.setLongitudeMarc(jsonObj.getDouble(LONGITUDE_MARCACAO));
            marc.setLocalMarc(jsonObj.getString(LOCAL_MARCACAO));

            marc.setDataSincroMarc(jsonGerl.getDataSincroBDDOnline(jsonObj.getString(HORA_DATA_SINCRO_MARCACAO)));
            marc.setHoraSincroMarc(jsonGerl.getHoraSincroBDDOnline(jsonObj.getString(HORA_DATA_SINCRO_MARCACAO)));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return marc;
    }
}
