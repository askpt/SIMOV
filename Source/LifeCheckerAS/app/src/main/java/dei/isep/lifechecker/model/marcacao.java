package dei.isep.lifechecker.model;

public class marcacao {
	
	int idMarcacaoMarc;
	int idPacienteMarc;
	int idEstadoMarc;
	String tipoMarc;
	String horaMarc;
	String dataMarc;
	double latitudeMarc;
	double longitudeMarc;
	String localMarc;
	
	String horaSincroMarc;
	String dataSincroMarc;
	
	public marcacao(){}

	/*
	 * Marcacao com todos meno IDAutomatico
	 */
	public marcacao(int idPacienteMarc, int idEstadoMarc, String tipoMarc,
			String horaMarc, String dataMarc, double latitudeMarc,
			double longitudeMarc, String localMarc,
			String horaUpdateMarc, String dataUpdateMarc) {
		super();
		this.idPacienteMarc = idPacienteMarc;
		this.idEstadoMarc = idEstadoMarc;
		this.tipoMarc = tipoMarc;
		this.horaMarc = horaMarc;
		this.dataMarc = dataMarc;
		this.latitudeMarc = latitudeMarc;
		this.longitudeMarc = longitudeMarc;
		this.localMarc = localMarc;
		this.horaSincroMarc = horaUpdateMarc;
		this.dataSincroMarc = dataUpdateMarc;
	}

	public int getIdMarcacaoMarc() {
		return idMarcacaoMarc;
	}

	public void setIdMarcacaoMarc(int idMarcacaoMarc) {
		this.idMarcacaoMarc = idMarcacaoMarc;
	}

	public int getIdPacienteMarc() {
		return idPacienteMarc;
	}

	public void setIdPacienteMarc(int idPacienteMarc) {
		this.idPacienteMarc = idPacienteMarc;
	}

	public int getIdEstadoMarc() {
		return idEstadoMarc;
	}

	public void setIdEstadoMarc(int idEstadoMarc) {
		this.idEstadoMarc = idEstadoMarc;
	}

	public String getTipoMarc() {
		return tipoMarc;
	}

	public void setTipoMarc(String tipoMarc) {
		this.tipoMarc = tipoMarc;
	}

	public String getHoraMarc() {
		return horaMarc;
	}

	public void setHoraMarc(String horaMarc) {
		this.horaMarc = horaMarc;
	}

	public String getDataMarc() {
		return dataMarc;
	}

	public void setDataMarc(String dataMarc) {
		this.dataMarc = dataMarc;
	}

	public double getLatitudeMarc() {
		return latitudeMarc;
	}

	public void setLatitudeMarc(double latitudeMarc) {
		this.latitudeMarc = latitudeMarc;
	}

	public double getLongitudeMarc() {
		return longitudeMarc;
	}

	public void setLongitudeMarc(double longitudeMarc) {
		this.longitudeMarc = longitudeMarc;
	}

	public String getLocalMarc() {
		return localMarc;
	}

	public void setLocalMarc(String localMarc) {
		this.localMarc = localMarc;
	}

	public String getHoraSincroMarc() {
		return horaSincroMarc;
	}

	public void setHoraSincroMarc(String horaSincroMarc) {
		this.horaSincroMarc = horaSincroMarc;
	}

	public String getDataSincroMarc() {
		return dataSincroMarc;
	}

	public void setDataSincroMarc(String dataSincroMarc) {
		this.dataSincroMarc = dataSincroMarc;
	}

	
	
	
	
	

}
