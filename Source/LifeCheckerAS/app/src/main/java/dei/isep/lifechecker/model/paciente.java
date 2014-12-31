package dei.isep.lifechecker.model;

import android.os.Parcelable;

public class paciente {
	
	int idPaciente;
	int idResponsavelPaciente;
	String nomePaciente;
	String apelidoPaciente;
	String mailPaciente;
	String contactoPaciente;
	double latitudePaciente;
	double longitudePaciente;
	String nomeLocalPaciente;
	String horaLocalPaciente;
	String dataLocalPaciente;
	boolean ativoPaciente;
	boolean estaOnlinePaciente;
	
	String horaSincroPaciente;
	String dataSincroPaciente;
	
	public paciente (){};
	
	/*
	 * Paciente com todos os atributos
	 */
	
	public paciente(int idPaciente, int idResponsavelPaciente,
			String nomePaciente, String apelidoPaciente, String mailPaciente,
			String contactoPaciente, double latitudePaciente,
			double longitudePaciente, String nomeLocalPaciente,
			String horaLocalPaciente, String dataLocalPaciente, boolean ativoPaciente,
			String horaSincroPaciente, String dataSincroPaciente) {
		this.idPaciente = idPaciente;
		this.idResponsavelPaciente = idResponsavelPaciente;
		this.nomePaciente = nomePaciente;
		this.apelidoPaciente = apelidoPaciente;
		this.mailPaciente = mailPaciente;
		this.contactoPaciente = contactoPaciente;
		this.latitudePaciente = latitudePaciente;
		this.longitudePaciente = longitudePaciente;
		this.nomeLocalPaciente = nomeLocalPaciente;
		this.horaLocalPaciente = horaLocalPaciente;
		this.dataLocalPaciente = dataLocalPaciente;
		this.ativoPaciente = true;
		this.horaSincroPaciente = horaSincroPaciente;
		this.dataSincroPaciente = dataSincroPaciente;
	}
	
	/*
	 * Cira��o de um paciente sem ID dele
	 */

	public paciente(int idResponsavelPaciente, String nomePaciente,
			String apelidoPaciente, String mailPaciente,
			String contactoPaciente, double latitudePaciente,
			double longitudePaciente, String nomeLocalPaciente,
			String horaLocalPaciente, String dataLocalPaciente,
			boolean ativoPaciente,
			String horaSincroPaciente, String dataSincroPaciente) {
		this.idResponsavelPaciente = idResponsavelPaciente;
		this.nomePaciente = nomePaciente;
		this.apelidoPaciente = apelidoPaciente;
		this.mailPaciente = mailPaciente;
		this.contactoPaciente = contactoPaciente;
		this.latitudePaciente = latitudePaciente;
		this.longitudePaciente = longitudePaciente;
		this.nomeLocalPaciente = nomeLocalPaciente;
		this.horaLocalPaciente = horaLocalPaciente;
		this.dataLocalPaciente = dataLocalPaciente;
		this.ativoPaciente = true;
		this.horaSincroPaciente = horaSincroPaciente;
		this.dataSincroPaciente = dataSincroPaciente;
	}
	
	/*
	 * Cria��o de um paciente
	 */

    /*
	public paciente(int idResponsavelPaciente,
			String nomePaciente, String apelidoPaciente, String mailPaciente,
			String contactoPaciente, boolean ativoPaciente,
			String horaSincroPaciente, String dataSincroPaciente) {
		super();
		this.idResponsavelPaciente = idResponsavelPaciente;
		this.nomePaciente = nomePaciente;
		this.apelidoPaciente = apelidoPaciente;
		this.mailPaciente = mailPaciente;
		this.contactoPaciente = contactoPaciente;
		this.ativoPaciente = true;
		this.horaSincroPaciente = horaSincroPaciente;
		this.dataSincroPaciente = dataSincroPaciente;
	}*/
	
	public paciente(int idResponsavelPaciente,
			String nomePaciente, String apelidoPaciente, String mailPaciente,
			String contactoPaciente, boolean ativoPaciente) {
		super();
        this.idResponsavelPaciente = idResponsavelPaciente;
        this.nomePaciente = nomePaciente;
        this.apelidoPaciente = apelidoPaciente;
        this.mailPaciente = mailPaciente;
        this.contactoPaciente = contactoPaciente;
        this.latitudePaciente = 0;
        this.longitudePaciente = 0;
        this.nomeLocalPaciente = "Sem Local";
        this.horaLocalPaciente = "11:11:11";
        this.dataLocalPaciente = "2000-01-01";
        this.ativoPaciente = true;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public int getIdResponsavelPaciente() {
		return idResponsavelPaciente;
	}

	public void setIdResponsavelPaciente(int idResponsavelPaciente) {
		this.idResponsavelPaciente = idResponsavelPaciente;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getApelidoPaciente() {
		return apelidoPaciente;
	}

	public void setApelidoPaciente(String apelidoPaciente) {
		this.apelidoPaciente = apelidoPaciente;
	}

	public String getMailPaciente() {
		return mailPaciente;
	}

	public void setMailPaciente(String mailPaciente) {
		this.mailPaciente = mailPaciente;
	}

	public String getContactoPaciente() {
		return contactoPaciente;
	}

	public void setContactoPaciente(String contactoPaciente) {
		this.contactoPaciente = contactoPaciente;
	}

	public double getLatitudePaciente() {
		return latitudePaciente;
	}

	public void setLatitudePaciente(double latitudePaciente) {
		this.latitudePaciente = latitudePaciente;
	}

	public double getLongitudePaciente() {
		return longitudePaciente;
	}

	public void setLongitudePaciente(double longitudePaciente) {
		this.longitudePaciente = longitudePaciente;
	}

	public String getNomeLocalPaciente() {
		return nomeLocalPaciente;
	}

	public void setNomeLocalPaciente(String nomeLocalPaciente) {
		this.nomeLocalPaciente = nomeLocalPaciente;
	}

	public String getHoraLocalPaciente() {
		return horaLocalPaciente;
	}

	public void setHoraLocalPaciente(String horaLocalPaciente) {
		this.horaLocalPaciente = horaLocalPaciente;
	}

	public boolean getAtivoPaciente() {
		return ativoPaciente;
	}

	public void setAtivoPaciente(boolean ativoPaciente) {
		this.ativoPaciente = ativoPaciente;
	}

	public boolean getEstaOnlinePaciente() {
		return estaOnlinePaciente;
	}

	public void setEstaOnlinePaciente(boolean estaOnlinePaciente) {
		this.estaOnlinePaciente = estaOnlinePaciente;
	}

	public String getHoraSincroPaciente() {
		return horaSincroPaciente;
	}

	public void setHoraSincroPaciente(String horaSincroPaciente) {
		this.horaSincroPaciente = horaSincroPaciente;
	}

	public String getDataSincroPaciente() {
		return dataSincroPaciente;
	}

	public void setDataSincroPaciente(String dataSincroPaciente) {
		this.dataSincroPaciente = dataSincroPaciente;
	}

    public String getDataLocalPaciente() {
        return dataLocalPaciente;
    }

    public void setDataLocalPaciente(String dataLocalPaciente) {
        this.dataLocalPaciente = dataLocalPaciente;
    }

    public boolean isAtivoPaciente() {
        return ativoPaciente;
    }

    public boolean isEstaOnlinePaciente() {
        return estaOnlinePaciente;
    }
}
