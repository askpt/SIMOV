package dei.isep.lifechecker.model;

public class Responsavel {
	
	int idResponsavel;
	String nomeResposnavel;
	String apelidoResposnavel;
	String contactoResponsavel;
	boolean notificacaoMail;
	boolean notificacaoSMS;
	int periodicidadeDiurna;
	int periodicidadeNoturna;
	
	String mailResponsavel;
	String passResponsavel;
	boolean estaOnline;
	
	public Responsavel(){}

	
	/*
	 * Criação de um responsável com todos atributos
	 */
	public Responsavel(int idResponsavel, String nomeResposnavel,
			String apelidoResposnavel, String contactoResponsavel,
			boolean notificacaoMail, boolean notificacaoSMS,
			int periodicidadeDiurna, int periodicidadeNoturna,
			String mailResponsavel, String passResponsavel,
			boolean estaOnline) {
		this.idResponsavel = idResponsavel;
		this.nomeResposnavel = nomeResposnavel;
		this.apelidoResposnavel = apelidoResposnavel;
		this.contactoResponsavel = contactoResponsavel;
		this.notificacaoMail = notificacaoMail;
		this.notificacaoSMS = notificacaoSMS;
		this.periodicidadeDiurna = periodicidadeDiurna;
		this.periodicidadeNoturna = periodicidadeNoturna;
		this.mailResponsavel = mailResponsavel;
		this.passResponsavel = passResponsavel;
		this.estaOnline = estaOnline;
	}

	/*
	 * Criação de um responsável sem ID
	 */
	public Responsavel(String nomeResposnavel, String apelidoResposnavel,
			String contactoResponsavel, boolean notificacaoMail,
			boolean notificacaoSMS, int periodicidadeDiurna,
			int periodicidadeNoturna, String mailResponsavel,
			String passResponsavel, boolean estaOnline) {
		super();
		this.nomeResposnavel = nomeResposnavel;
		this.apelidoResposnavel = apelidoResposnavel;
		this.contactoResponsavel = contactoResponsavel;
		this.notificacaoMail = notificacaoMail;
		this.notificacaoSMS = notificacaoSMS;
		this.periodicidadeDiurna = periodicidadeDiurna;
		this.periodicidadeNoturna = periodicidadeNoturna;
		this.mailResponsavel = mailResponsavel;
		this.passResponsavel = passResponsavel;
		this.estaOnline = estaOnline;
	}


	public int getIdResponsavel() {
		return idResponsavel;
	}


	public void setIdResponsavel(int idResponsavel) {
		this.idResponsavel = idResponsavel;
	}


	public String getNomeResposnavel() {
		return nomeResposnavel;
	}


	public void setNomeResposnavel(String nomeResposnavel) {
		this.nomeResposnavel = nomeResposnavel;
	}


	public String getApelidoResposnavel() {
		return apelidoResposnavel;
	}


	public void setApelidoResposnavel(String apelidoResposnavel) {
		this.apelidoResposnavel = apelidoResposnavel;
	}


	public String getContactoResponsavel() {
		return contactoResponsavel;
	}
	
	
	public boolean getEstaOnline() {
		return estaOnline;
	}


	public void setContactoResponsavel(String contactoResponsavel) {
		this.contactoResponsavel = contactoResponsavel;
	}


	public boolean getNotificacaoMail() {
		return notificacaoMail;
	}


	public void setNotificacaoMail(boolean notificacaoMail) {
		this.notificacaoMail = notificacaoMail;
	}


	public boolean getNotificacaoSMS() {
		return notificacaoSMS;
	}


	public void setNotificacaoSMS(boolean notificacaoSMS) {
		this.notificacaoSMS = notificacaoSMS;
	}


	public int getPeriodicidadeDiurna() {
		return periodicidadeDiurna;
	}


	public void setPeriodicidadeDiurna(int periodicidadeDiurna) {
		this.periodicidadeDiurna = periodicidadeDiurna;
	}


	public int getPeriodicidadeNoturna() {
		return periodicidadeNoturna;
	}


	public void setPeriodicidadeNoturna(int periodicidadeNoturna) {
		this.periodicidadeNoturna = periodicidadeNoturna;
	}


	public String getMailResponsavel() {
		return mailResponsavel;
	}


	public void setMailResponsavel(String mailResponsavel) {
		this.mailResponsavel = mailResponsavel;
	}


	public String getPassResponsavel() {
		return passResponsavel;
	}


	public void setPassResponsavel(String passResponsavel) {
		this.passResponsavel = passResponsavel;
	}
	

	public void setEstaOnline(boolean estaOnline) {
		this.estaOnline = estaOnline;
	}
	
	
	
	
	
	
	

}
