package dei.isep.lifechecker.other;

import android.util.Log;

public class lifeCheckerManager {
	private static lifeCheckerManager _instance;
	
	private lifeCheckerManager(){};
	
	private int idResponsavel;

	private String mailResponsavel;
	private String passResposnavel;
	private String nomeResponsavel;
	private String apelidoResponsavel;
	private String telefoneResponsavel;
	
	private boolean notificacaoMailResposnavel;
	private boolean notificacaoSMSResposnavel;
	
	private int minutosNoturo;
	private int minutosDiurno;
	
	private String nomePacienteResposnavel;
	private String ApelidoPacienteResposnavel;
	private String mailPacienteResposnavel;
	private String contactoPacienteResposnavel;
	
	public synchronized static lifeCheckerManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new lifeCheckerManager();
		}
		return _instance;
	}
	
	public int getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(int idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public String getMailResponsavel() {
		return mailResponsavel;
	}

	public void setMailResponsavel(String mailResponsavel) {
		Log.i("Singleton","SINGGGG " + mailResponsavel);
		this.mailResponsavel = mailResponsavel;
	}

	public String getPassResposnavel() {
		return passResposnavel;
	}

	public void setPassResposnavel(String passResposnavel) {
		this.passResposnavel = passResposnavel;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getApelidoResponsavel() {
		return apelidoResponsavel;
	}

	public void setApelidoResponsavel(String apelidoResponsavel) {
		this.apelidoResponsavel = apelidoResponsavel;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	public boolean getNotificacaoMailResposnavel() {
		return notificacaoMailResposnavel;
	}

	public void setNotificacaoMailResposnavel(boolean notificacaoMailResposnavel) {
		this.notificacaoMailResposnavel = notificacaoMailResposnavel;
	}

	public boolean getNotificacaoSMSResposnavel() {
		return notificacaoSMSResposnavel;
	}

	public void setNotificacaoSMSResposnavel(boolean notificacaoSMSResposnavel) {
		this.notificacaoSMSResposnavel = notificacaoSMSResposnavel;
	}

	public int getMinutosNoturo() {
		return minutosNoturo;
	}

	public void setMinutosNoturo(int minutosNoturo) {
		this.minutosNoturo = minutosNoturo;
	}

	public int getMinutosDiurno() {
		return minutosDiurno;
	}

	public void setMinutosDiurno(int minutosDiurno) {
		this.minutosDiurno = minutosDiurno;
	}

	public String getNomePacienteResposnavel() {
		return nomePacienteResposnavel;
	}

	public void setNomePacienteResposnavel(String nomePacienteResposnavel) {
		this.nomePacienteResposnavel = nomePacienteResposnavel;
	}

	public String getApelidoPacienteResposnavel() {
		return ApelidoPacienteResposnavel;
	}

	public void setApelidoPacienteResposnavel(String apelidoPacienteResposnavel) {
		ApelidoPacienteResposnavel = apelidoPacienteResposnavel;
	}

	public String getMailPacienteResposnavel() {
		return mailPacienteResposnavel;
	}

	public void setMailPacienteResposnavel(String mailPacienteResposnavel) {
		this.mailPacienteResposnavel = mailPacienteResposnavel;
	}

	public String getContactoPacienteResposnavel() {
		return contactoPacienteResposnavel;
	}

	public void setContactoPacienteResposnavel(String contactoPacienteResposnavel) {
		this.contactoPacienteResposnavel = contactoPacienteResposnavel;
	}
	
	
	

}
