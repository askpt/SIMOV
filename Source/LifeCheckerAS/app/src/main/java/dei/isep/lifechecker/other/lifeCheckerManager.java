package dei.isep.lifechecker.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import dei.isep.lifechecker.R;
import dei.isep.lifechecker.model.*;

public class lifeCheckerManager {
	private static lifeCheckerManager _instance;
	
	private lifeCheckerManager(){};

    private paciente pac;

	private int idResponsavel;

	private String mailResponsavel;
	private String passResposnavel;
	private String passConfirmResponsavel;
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

    //Marcações
    private int idMarcacao;
    private boolean aVerificar;

    public boolean getaVerificar() {
        return aVerificar;
    }

    public void setaVerificar(boolean aVerificar) {
        this.aVerificar = aVerificar;
    }



    public int getIdMarcacao() {
        return idMarcacao;
    }

    public void setIdMarcacao(int idMarcacao) {
        this.idMarcacao = idMarcacao;
    }

    //****Local da aplicacao
    private Locale locale = new Locale("pt","PT");

	
	//Pacientes
	private ArrayList<paciente> listaPaciente;

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

	public String getPassConfirmResponsavel() {
		return passConfirmResponsavel;
	}

	public void setPassConfirmResponsavel(String passConfirmResponsavel) {
		this.passConfirmResponsavel = passConfirmResponsavel;
	}
	
	public ArrayList<paciente> getListaPaciente() {
		return listaPaciente;
	}

	public void setListaPaciente(ArrayList<paciente> listaPaciente) {
		this.listaPaciente = listaPaciente;
	}

    public paciente getPac() {
        return pac;
    }

    public void setPac(paciente pac) {
        this.pac = pac;
    }

    public void inserirActionBar(Activity a, int idTitle) {
        ActionBar actionBar = a.getActionBar();
        actionBar.setCustomView(R.layout.action_bar);
        TextView textView = (TextView) actionBar.getCustomView().findViewById(
                R.id.actionBar_Titulo);
        textView.setText(a.getResources().getString(idTitle));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
	

}
