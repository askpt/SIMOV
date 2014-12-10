package dei.isep.lifechecker.json;

import org.json.JSONException;
import org.json.JSONObject;

import dei.isep.lifechecker.model.responsavel;

public class responsavelJson {
	
	private String ID_JSON_RESP = "ID";
	private String NOME_JSON_RESP = "Nome";
	private String APELIDO_JSON_RESP = "Apelido";
	private String CONTACTOTLF_JSON_RESP = "ContactoTlf";
	private String NOTIFMAIL_JSON_RESP = "NotifMail";
	private String NOTIFSMS_JSON_RESP = "NotifSms";
	private String PERIODDIURNA_JSON_RESP = "PeriodDiurna";
	private String PERIODNOTURNA_JSON_RESP = "PeriodNoturna";
	private String MAIL_JSON_RESP = "Email";
	private String PASSWORD_JSON_RESP = "Password";
	private String PACIENTES_JSON_RESP = "HoraSincronizacao";
	
	String conteudo;
	
	public responsavelJson(){};
	
	public responsavelJson(String conteudo)
	{
		this.conteudo = conteudo;
	}
	
	public responsavel transformJsonResponsavel()
	{
		responsavel resp = new responsavel();
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(conteudo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			resp.setIdResponsavel(jsonObj.getInt(ID_JSON_RESP));
			resp.setNomeResposnavel(jsonObj.getString(NOME_JSON_RESP));
			resp.setApelidoResposnavel(jsonObj.getString(APELIDO_JSON_RESP));
			resp.setContactoResponsavel(jsonObj.getString(CONTACTOTLF_JSON_RESP));
			resp.setNotificacaoMail(jsonObj.getBoolean(NOTIFMAIL_JSON_RESP));
			resp.setNotificacaoSMS(jsonObj.getBoolean(NOTIFSMS_JSON_RESP));
			resp.setPeriodicidadeDiurna(jsonObj.getInt(PERIODDIURNA_JSON_RESP));
			resp.setPeriodicidadeNoturna(jsonObj.getInt(PERIODNOTURNA_JSON_RESP));
			
			resp.setMailResponsavel(jsonObj.getString(MAIL_JSON_RESP));
			resp.setPassResponsavel(jsonObj.getString(PASSWORD_JSON_RESP));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
	
	

}
