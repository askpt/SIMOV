package dei.isep.lifechecker.model;

public class alerta {
	
	int idAlerta;
	String explicacaoAlerta;
	
	public alerta (){}

	public alerta(int idAlerta, String explicacaoAlerta) {
		super();
		this.idAlerta = idAlerta;
		this.explicacaoAlerta = explicacaoAlerta;
	}

	public alerta(String explicacaoAlerta) {
		super();
		this.explicacaoAlerta = explicacaoAlerta;
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
	};
	
	

}
