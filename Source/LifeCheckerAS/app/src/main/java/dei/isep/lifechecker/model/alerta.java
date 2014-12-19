package dei.isep.lifechecker.model;

public class alerta {
	
	int idAlerta;
	String explicacaoAlerta;

	
	String horaSincroAlerta;
	String dataSincroAlerta;
	
	
	public alerta (){}

	public alerta(int idAlerta, String explicacaoAlerta,
			String horaSincroAlerta, String dataSincroAlerta) {
		super();
		this.idAlerta = idAlerta;
		this.explicacaoAlerta = explicacaoAlerta;

		this.horaSincroAlerta = horaSincroAlerta;
		this.dataSincroAlerta = dataSincroAlerta;
	}

	public alerta(String explicacaoAlerta,
			String horaSincroAlerta, String dataSincroAlerta) {
		super();
		this.explicacaoAlerta = explicacaoAlerta;
		this.horaSincroAlerta = horaSincroAlerta;
		this.dataSincroAlerta = dataSincroAlerta;
	}

	public int getIdAlerta() {
		return idAlerta;
	}

	public void setIdAlerta(int idAlerta) {
		this.idAlerta = idAlerta;
	}

	public String getExplicacaoAlerta() {
		return explicacaoAlerta;
	}

	public void setExplicacaoAlerta(String explicacaoAlerta) {
		this.explicacaoAlerta = explicacaoAlerta;
	}

	public String getHoraSincroAlerta() {
		return horaSincroAlerta;
	}

	public void setHoraSincroAlerta(String horaSincroAlerta) {
		this.horaSincroAlerta = horaSincroAlerta;
	}

	public String getDataSincroAlerta() {
		return dataSincroAlerta;
	}

	public void setDataSincroAlerta(String dataSincroAlerta) {
		this.dataSincroAlerta = dataSincroAlerta;
	}
	
	
	
	

}
