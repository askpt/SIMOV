package dei.isep.lifechecker.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dei.isep.lifechecker.model.paciente;

public class pacienteJson {
	
	private String HISTORIC_ALERTA_JSON_PACI = "HistoricoAlertas";
	private String MARCACOES_JSON_PACI = "Marcacoes";
	private String ID_JSON_PACI = "ID";
	private String NOME_JSON_PACI = "Nome";
	private String APELIDO_JSON_PACI = "Apelido";
	private String MAIL_JSON_PACI = "Email";
	private String CONTACTOTLF_JSON_PACI = "ContactoTlf";
	private String LONG_JSON_PACI = "Longitude";
	private String LAT_JSON_PACI = "Latitude";
	private String NLOCAL_JSON_PACI = "NomeLocal";
	private String DATA_JSON_PACI = "NomeLocal";
	private String ATIVO_JSON_PACI = "Ativo";
	private String DATAHORASINCRO_JSON_PACI = "HoraSincronizacao";
	private String ID_RESP_JSON_PACI = "Responsavel_ID";
	
	
	String conteudo;
	
	public pacienteJson()
	{
		this.conteudo = "";
	};
	
	public pacienteJson(String conteudo)
	{
		this.conteudo = conteudo;
	}
	
	private JSONObject jsonObj;
	
	public List<paciente> transformJsonPaciente()
	{
		List<paciente> listaPacientes = new ArrayList<paciente>();
		try {
			JSONArray jsonArray = new JSONArray(conteudo);
			for(int i = 0; i < jsonArray.length(); i++)
			{
				paciente paci = new paciente();
				jsonObj = (JSONObject) jsonArray.getJSONObject(i);
				paci = transformJsonOneResponsavel();
				listaPacientes.add(paci);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaPacientes;
	}
	
	private paciente transformJsonOneResponsavel()
	{
		paciente paci = new paciente();
		try {
			paci.setIdPaciente(jsonObj.getInt(ID_JSON_PACI));
			paci.setNomePaciente(jsonObj.getString(NOME_JSON_PACI));
			paci.setApelidoPaciente(jsonObj.getString(APELIDO_JSON_PACI));
			paci.setMailPaciente(jsonObj.getString(MAIL_JSON_PACI));
			paci.setContactoPaciente(jsonObj.getString(CONTACTOTLF_JSON_PACI));
			paci.setIdResponsavelPaciente(jsonObj.getInt(ID_RESP_JSON_PACI));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paci;
		
	}

}
