package dei.isep.lifechecker.model;

public class estadoMarcacao {
	
	int idEstadoMarcacao;
	String explicacaoEstMarc;
	
	String horaSincroEstMarc;
	String dataSincroEstMarc;
	
	public estadoMarcacao(){}

	public estadoMarcacao(int idEstadoMarcacao, String explicacaoEstMarc,
			String horaSincroEstMarc, String dataSincroEstMarc) {
		super();
		this.idEstadoMarcacao = idEstadoMarcacao;
		this.explicacaoEstMarc = explicacaoEstMarc;
		this.horaSincroEstMarc = horaSincroEstMarc;
		this.dataSincroEstMarc = dataSincroEstMarc;
	}

	public estadoMarcacao(String explicacaoEstMarc,
			String horaSincroEstMarc, String dataSincroEstMarc) {
		super();
		this.explicacaoEstMarc = explicacaoEstMarc;
		this.horaSincroEstMarc = horaSincroEstMarc;
		this.dataSincroEstMarc = dataSincroEstMarc;
	}

	public int getIdEstadoMarcacao() {
		return idEstadoMarcacao;
	}

	public void setIdEstadoMarcacao(int idEstadoMarcacao) {
		this.idEstadoMarcacao = idEstadoMarcacao;
	}

	public String getExplicacaoEstMarc() {
		return explicacaoEstMarc;
	}

	public void setExplicacaoEstMarc(String explicacaoEstMarc) {
		this.explicacaoEstMarc = explicacaoEstMarc;
	}

	public String getHoraSincroEstMarc() {
		return horaSincroEstMarc;
	}

	public void setHoraSincroEstMarc(String horaSincroEstMarc) {
		this.horaSincroEstMarc = horaSincroEstMarc;
	}

	public String getDataSincroEstMarc() {
		return dataSincroEstMarc;
	}

	public void setDataSincroEstMarc(String dataSincroEstMarc) {
		this.dataSincroEstMarc = dataSincroEstMarc;
	}
	
	
	
	

}
