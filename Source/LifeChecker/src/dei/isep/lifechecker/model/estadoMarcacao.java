package dei.isep.lifechecker.model;

public class estadoMarcacao {
	
	int idEstadoMarcacao;
	String explicacaoEstMarc;
	
	public estadoMarcacao(){}

	public estadoMarcacao(int idEstadoMarcacao, String explicacaoEstMarc) {
		super();
		this.idEstadoMarcacao = idEstadoMarcacao;
		this.explicacaoEstMarc = explicacaoEstMarc;
	}

	public estadoMarcacao(String explicacaoEstMarc) {
		super();
		this.explicacaoEstMarc = explicacaoEstMarc;
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
	};
	
	

}
