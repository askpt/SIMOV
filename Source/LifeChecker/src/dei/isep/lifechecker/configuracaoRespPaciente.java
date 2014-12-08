package dei.isep.lifechecker;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import dei.isep.lifechecker.databaseonline.httpPost;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.genericTextWatcherConfiguracao;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class configuracaoRespPaciente extends Fragment implements
		OnClickListener {

	Button btnValidarResponsavel;
	ProgressBar pbConfiguracao;
	httpPost htPostMailResp;
	httpPost htPostMailPaciente;
	TextView tvComentarios;
	
	EditText nomePaciente;
	EditText apelidoPaciente;
	EditText mailPaciente;
	EditText contactoPaciente;
	
	
	boolean mailExiste = false;

	String listaErros = "";
	int validMailPaciente = -1; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(
				R.layout.configuracao_responsavel_paciente, container, false);
		btnValidarResponsavel = (Button) myView
				.findViewById(R.id.bt_validar_paciente_responsavel);
		btnValidarResponsavel.setOnClickListener(this);
		pbConfiguracao = (ProgressBar) myView
				.findViewById(R.id.progressBarLoadingAddConfig);

		tvComentarios = (TextView) myView
				.findViewById(R.id.tv_comentario_configuracao_resposnavel);

		
		nomePaciente = (EditText) myView.findViewById(R.id.tb_nome_paciente_resp);
		apelidoPaciente = (EditText) myView.findViewById(R.id.tb_apelido_paciente_resp);
		mailPaciente = (EditText) myView.findViewById(R.id.tb_mail_paciente_resp);
		contactoPaciente = (EditText) myView.findViewById(R.id.tb_telefone_paciente_resp);
		
		nomePaciente.addTextChangedListener(new genericTextWatcherConfiguracao(nomePaciente));
		apelidoPaciente.addTextChangedListener(new genericTextWatcherConfiguracao(apelidoPaciente));
		mailPaciente.addTextChangedListener(new genericTextWatcherConfiguracao(mailPaciente));
		contactoPaciente.addTextChangedListener(new genericTextWatcherConfiguracao(contactoPaciente));
		
		
		pbConfiguracao.setVisibility(View.INVISIBLE);
		return myView;
	}

	@Override
	public void onClick(View v) {
		// True = contem erros; False = não contem erros
		boolean erroConta = false;
		boolean erroDadosResp = false;
		boolean erroPeriodicidade = false;
		boolean erroDadosPaciente = false;
		
		mailExiste = false;

		// Variaveis com informação
		String mailResponsavel = "";
		String passeResposnavel = "";
		String passeConfigrmacaoResposnavel = "";
		
		listaErros = "";
		// ******
		validarDados validar = new validarDados();
		validMailPaciente = 0;
		int valiConta = validarConta();
		int valiDados = validarDadosNovoResposnavel();
		int valiPeriodicidade = valirdarPeriodicidades();
		validMailPaciente = validarPacienteResposnavel();
			
		

		// Verificar mail
		if (valiConta == 0 && validMailPaciente == 0 &&  valiDados == 0 && valiPeriodicidade == 0) {
			validarmailOnlineResponsavel(lifeCheckerManager.getInstance().getMailResponsavel());
		}
		else
		{
			tvComentarios.setText(listaErros);
		}
			
	}

	public int validarConta() {
		int erros = 3;
		validarDados validar = new validarDados();
		if (lifeCheckerManager.getInstance().getMailResponsavel() != null
				&& validar.mailValidacaoFormato(lifeCheckerManager
						.getInstance().getMailResponsavel()) == true) {
			erros--;
			
		} 
		else 
		{
			listaErros += getResources().getString(R.string.responsavel) + " :" + getResources().getString(R.string.verificar_mail_formato_errado) + "\n";
			erros++;
		}
		
		if(lifeCheckerManager.getInstance().getPassResposnavel() != null &&
				validar.passValidarFormato(lifeCheckerManager.getInstance().getPassResposnavel()) == true)
		{
			erros--;
		}
		else
		{
			listaErros += getResources().getString(R.string.responsavel) + " :" +  getResources().getString(R.string.verificar_password_formato_errado) + "\n";
			erros++;
		}
		
		if(lifeCheckerManager.getInstance().getPassResposnavel() != null
				&& lifeCheckerManager.getInstance()
				.getPassConfirmResponsavel() != null
		&& lifeCheckerManager
				.getInstance()
				.getPassResposnavel()
				.compareTo(
						lifeCheckerManager.getInstance()
								.getPassConfirmResponsavel()) == 0)
		{
			
			erros--;
			
		}
		else
		{
			listaErros += getResources().getString(R.string.responsavel) + " :" +  getResources().getString(R.string.verificar_password_diferentes) + "\n";
			erros++;
		}

		return erros;
	}
	
	public int validarPacienteResposnavel()
	{
		int erros = 4;
		
		validarDados validar = new validarDados();
		if (lifeCheckerManager.getInstance().getNomePacienteResposnavel() != null
				&& validar.nomeApelidoVerificar(lifeCheckerManager
						.getInstance().getMailPacienteResposnavel()) == true) {
			erros--;
			
		} 
		else 
		{
			listaErros += getResources().getString(R.string.paciente) + " :" +  getResources().getString(R.string.nome) + "\n";
			erros++;
		}
		
		
		if (lifeCheckerManager.getInstance().getApelidoPacienteResposnavel() != null
				&& validar.nomeApelidoVerificar(lifeCheckerManager
						.getInstance().getApelidoPacienteResposnavel()) == true) {
			erros--;
			
		} 
		else 
		{
			listaErros += getResources().getString(R.string.paciente) + " :" +  getResources().getString(R.string.erro_apelido) + "\n";
			erros++;
		}
		
		if (lifeCheckerManager.getInstance().getMailPacienteResposnavel() != null
				&& validar.mailValidacaoFormato(lifeCheckerManager
						.getInstance().getMailPacienteResposnavel()) == true) {
			erros--;
			
		} 
		else 
		{
			listaErros += getResources().getString(R.string.paciente) + " :" +  getResources().getString(R.string.verificar_mail_formato_errado) + "\n";
			erros++;
		}
		String etetertetr = lifeCheckerManager.getInstance().getContactoPacienteResposnavel();
		if (lifeCheckerManager.getInstance().getContactoPacienteResposnavel() != null
				&& validar.contactoVerificar(lifeCheckerManager
						.getInstance().getContactoPacienteResposnavel()) == true) {
			erros--;
			
		} 
		else 
		{
			listaErros += getResources().getString(R.string.paciente) + " :" +  getResources().getString(R.string.erro_contacto) + "\n";
			erros++;
		}
		return erros;
	}
	
	public int validarDadosNovoResposnavel()
	{
		int erros = 3;
		
		validarDados validar = new validarDados();
		
		if (lifeCheckerManager.getInstance().getNomeResponsavel() != null
				&& validar.nomeApelidoVerificar(lifeCheckerManager
						.getInstance().getNomeResponsavel()) == true) {
			erros--;
			
		} 
		else 
		{
			listaErros += getResources().getString(R.string.responsavel) + " :" + getResources().getString(R.string.erro_nome) + "\n";
			erros++;
		}
		
		
		if (lifeCheckerManager.getInstance().getApelidoResponsavel() != null
				&& validar.nomeApelidoVerificar(lifeCheckerManager
						.getInstance().getApelidoResponsavel()) == true) {
			erros--;
			
		} 
		else 
		{
			listaErros += getResources().getString(R.string.responsavel) + " :" + getResources().getString(R.string.erro_apelido) + "\n";
			erros++;
		}
		
		if (lifeCheckerManager.getInstance().getTelefoneResponsavel() != null
				&& validar.contactoVerificar(lifeCheckerManager
						.getInstance().getTelefoneResponsavel()) == true) {
			erros--;
			
		} 
		else 
		{
			listaErros += getResources().getString(R.string.responsavel) + " :" + getResources().getString(R.string.erro_contacto) + "\n";
			erros++;
		}
		
		
		return erros;
	}
	
	public int valirdarPeriodicidades()
	{
		int erros = 2;
		if (lifeCheckerManager.getInstance().getMinutosNoturo() >  0 && lifeCheckerManager.getInstance().getMinutosNoturo() < 60 ) 
		{
			erros--;
		} 
		else 
		{
			listaErros += getResources().getString(R.string.responsavel) + " :" + getResources().getString(R.string.erro_period_noturn) + "\n";
			erros++;
		}
		
		if (lifeCheckerManager.getInstance().getMinutosDiurno() > 0 && lifeCheckerManager.getInstance().getMinutosDiurno() < 60) 
		{
			erros--;
		} 
		else 
		{
			listaErros += getResources().getString(R.string.responsavel) + " :" + getResources().getString(R.string.erro_period_dirurn) + "\n";
			erros++;
		}
		
		
		return erros;
	}
	
	public void validarmailOnlineResponsavel(String mail)
	{
		responsavelHttp respHttp = new responsavelHttp();
		respHttp.verificarMail(mail, htPostResultMailResp);
	}

	
	public void validarmailOnlinePaciente()
	{
		String mail = lifeCheckerManager.getInstance().getMailPacienteResposnavel();
		pacienteHttp paciHttp = new pacienteHttp();
		paciHttp.verificarMail(mail, htPostResultMailPaciente);
	}
	
	public void inserirResposnavel()
	{
		responsavel rsponsavel = new responsavel(
				lifeCheckerManager.getInstance().getNomeResponsavel(),
				lifeCheckerManager.getInstance().getApelidoResponsavel(),
				lifeCheckerManager.getInstance().getTelefoneResponsavel(),
				lifeCheckerManager.getInstance().getNotificacaoMailResposnavel(),
				lifeCheckerManager.getInstance().getNotificacaoSMSResposnavel(),
				lifeCheckerManager.getInstance().getMinutosDiurno(),
				lifeCheckerManager.getInstance().getMinutosNoturo(),
				lifeCheckerManager.getInstance().getMailResponsavel(),
				lifeCheckerManager.getInstance().getPassResposnavel());
		
		responsavelHttp addResp = new responsavelHttp();
		addResp.inserirResponsavel(rsponsavel, addResponsavelListener);
	}
	
	interfaceResultadoAsyncPost addResponsavelListener = new interfaceResultadoAsyncPost() {
		
		@Override
		public void obterResultado(int codigo, String conteudo) {
			if(codigo == 1)
			{
				tvComentarios.setText("inserrriiiiiuuuuu um reponsavel");
			}
		}
	};
	
	interfaceResultadoAsyncPost htPostResultMailPaciente = new interfaceResultadoAsyncPost() {

		@Override
		public void obterResultado(int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					String resultado = conteudo.replaceAll("[\r\n]+", "");
					boolean resultadobool = Boolean.valueOf(resultado);
					if (resultadobool == true) {
						listaErros += getResources().getString(R.string.paciente) + ": " + getResources().getString(R.string.verificar_mail_indisponivel) + "\n";
						mailExiste = true;
					} else {
						listaErros += getResources().getString(R.string.paciente) + ": " + getResources().getString(R.string.verificar_mail_disponivel) + "\n";
						inserirResposnavel();
					}
					tvComentarios.setText(listaErros);
				}
			});

		}
	};
	
	interfaceResultadoAsyncPost htPostResultMailResp = new interfaceResultadoAsyncPost() {

		@Override
		public void obterResultado(int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					String resultado = conteudo.replaceAll("[\r\n]+", "");
					boolean resultadobool = Boolean.valueOf(resultado);
					if (resultadobool == true) {
						listaErros += getResources().getString(R.string.responsavel) + ": " + getResources().getString(R.string.verificar_mail_indisponivel) + "\n";
						mailExiste = true;
					} else {
						listaErros += getResources().getString(R.string.responsavel) + ": " + getResources().getString(R.string.verificar_mail_disponivel) + "\n";
					}
					if(validMailPaciente == 0)
					{
						tvComentarios.setText(listaErros);
						validarmailOnlinePaciente();
					}

					pbConfiguracao.setVisibility(View.INVISIBLE);
				}
			});

		}
	};

}