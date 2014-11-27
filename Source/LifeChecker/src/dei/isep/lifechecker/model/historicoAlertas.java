package dei.isep.lifechecker.model;

public class historicoAlertas {
	
	int idHistAlt;
	int idPacienteHistAlt;
	int idAlertaHistAlt;
	String horaHistAlt;
	String dataHistAlt;
	double latitudeHistAlt;
	double longitudeHistAlt;
	String localHistAlt;
	boolean estaOnlineHistAlt;
	
	public historicoAlertas(){}

	/*
	 * Construtor com todos atributos
	 */
	public historicoAlertas(int idHistAlt, int idPacienteHistAlt,
			int idAlertaHistAlt, String horaHistAlt, String dataHistAlt,
			double latitudeHistAlt, double longitudeHistAlt,
			String localHistAlt, boolean estaOnlineHistAlt) {
		super();
		this.idHistAlt = idHistAlt;
		this.idPacienteHistAlt = idPacienteHistAlt;
		this.idAlertaHistAlt = idAlertaHistAlt;
		this.horaHistAlt = horaHistAlt;
		this.dataHistAlt = dataHistAlt;
		this.latitudeHistAlt = latitudeHistAlt;
		this.longitudeHistAlt = longitudeHistAlt;
		this.localHistAlt = localHistAlt;
		this.estaOnlineHistAlt = estaOnlineHistAlt;
	}
	
	/*
	 * Contrutor sem ID
	 */

	public historicoAlertas(int idPacienteHistAlt, int idAlertaHistAlt,
			String horaHistAlt, String dataHistAlt, double latitudeHistAlt,
			double longitudeHistAlt, String localHistAlt,
			boolean estaOnlineHistAlt) {
		super();
		this.idPacienteHistAlt = idPacienteHistAlt;
		this.idAlertaHistAlt = idAlertaHistAlt;
		this.horaHistAlt = horaHistAlt;
		this.dataHistAlt = dataHistAlt;
		this.latitudeHistAlt = latitudeHistAlt;
		this.longitudeHistAlt = longitudeHistAlt;
		this.localHistAlt = localHistAlt;
		this.estaOnlineHistAlt = estaOnlineHistAlt;
	}

	public int getIdHistAlt() {
		return idHistAlt;
	}

	public void setIdHistAlt(int idHistAlt) {
		this.idHistAlt = idHistAlt;
	}

	public int getIdPacienteHistAlt() {
		return idPacienteHistAlt;
	}

	public void setIdPacienteHistAlt(int idPacienteHistAlt) {
		this.idPacienteHistAlt = idPacienteHistAlt;
	}

	public int getIdAlertaHistAlt() {
		return idAlertaHistAlt;
	}

	public void setIdAlertaHistAlt(int idAlertaHistAlt) {
		this.idAlertaHistAlt = idAlertaHistAlt;
	}

	public String getHoraHistAlt() {
		return horaHistAlt;
	}

	public void setHoraHistAlt(String horaHistAlt) {
		this.horaHistAlt = horaHistAlt;
	}

	public String getDataHistAlt() {
		return dataHistAlt;
	}

	public void setDataHistAlt(String dataHistAlt) {
		this.dataHistAlt = dataHistAlt;
	}

	public double getLatitudeHistAlt() {
		return latitudeHistAlt;
	}

	public void setLatitudeHistAlt(double latitudeHistAlt) {
		this.latitudeHistAlt = latitudeHistAlt;
	}

	public double getLongitudeHistAlt() {
		return longitudeHistAlt;
	}

	public void setLongitudeHistAlt(double longitudeHistAlt) {
		this.longitudeHistAlt = longitudeHistAlt;
	}

	public String getLocalHistAlt() {
		return localHistAlt;
	}

	public void setLocalHistAlt(String localHistAlt) {
		this.localHistAlt = localHistAlt;
	}

	public boolean getEstaOnlineHistAlt() {
		return estaOnlineHistAlt;
	}

	public void setEstaOnlineHistAlt(boolean estaOnlineHistAlt) {
		this.estaOnlineHistAlt = estaOnlineHistAlt;
	};
	
	
	
	
	
	

}
