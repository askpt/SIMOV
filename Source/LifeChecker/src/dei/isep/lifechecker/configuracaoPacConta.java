package dei.isep.lifechecker;

import java.util.ArrayList;

import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.json.responsavelJson;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class configuracaoPacConta extends Fragment implements OnClickListener {

	EditText edtMail;
	EditText edtPass;
	Button btValidar;
	ProgressBar pbLoading;
	TextView txtInformacao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myView = inflater.inflate(R.layout.configuracao_paciente_conta,
				container, false);
		edtMail = (EditText) myView
				.findViewById(R.id.tb_configuracao_contapaciente_email);
		edtPass = (EditText) myView
				.findViewById(R.id.tb_configuracao_contapaciente_password);
		btValidar = (Button) myView
				.findViewById(R.id.bt_configuracao_contapaciente_validar);
		pbLoading = (ProgressBar) myView
				.findViewById(R.id.loading_configuracao_paciente_conta);
		txtInformacao = (TextView) myView
				.findViewById(R.id.text_configuracao_paciente_informacao);

		btValidar.setOnClickListener(this);
		pbLoading.setVisibility(View.INVISIBLE);
		txtInformacao.setVisibility(View.INVISIBLE);

		return myView;
	}

	@Override
	public void onClick(View v) {
		txtInformacao.setVisibility(View.VISIBLE);
		txtInformacao.setTextColor(Color.BLACK);

		String mailContent = edtMail.getText().toString();
		String passContent = edtPass.getText().toString();

		validarDados validar = new validarDados();
		if (validar.mailValidacaoFormato(mailContent)) {
			pbLoading.setVisibility(View.VISIBLE);
			txtInformacao.setText(R.string.est_verificar_dados_login);
			btValidar.setText(R.string.esperar);
			btValidar.setEnabled(false);
			responsavelHttp respHttp = new responsavelHttp();
			respHttp.loginVerificar(mailContent, passContent, resultadoLogin);
		} else {
			txtInformacao.setTextColor(getResources()
					.getColor(R.color.vermelho));
			txtInformacao.setText(R.string.verificar_mail_formato_errado);
		}
		// TODO Auto-generated method stub
	}

	interfaceResultadoAsyncPost resultadoLogin = new interfaceResultadoAsyncPost() {
		@Override
		public void obterResultado(final int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {

					if (codigo == 1 && conteudo.length() > 10) {
						txtInformacao.setText(R.string.est_recuperacao_pacientes);
						// conteudo = responsavel e vai converter para objeto
						responsavelJson respJson = new responsavelJson(conteudo);
						responsavel resp = respJson.transformJsonResponsavel();

						// obtem o ID e vai buscar todos os seus pacientes
						pacienteHttp paciHttp = new pacienteHttp();
						paciHttp.retornarPacientesIdResposnavel(
								resp.getIdResponsavel(), pacientesResponsavel);
					} else {
						txtInformacao.setTextColor(getResources().getColor(
								R.color.vermelho));
						txtInformacao.setText(R.string.err_login_errado);
						pbLoading.setVisibility(View.INVISIBLE);
						btValidar.setText(R.string.validar);
						btValidar.setEnabled(true);
					}

				}
			});

		}
	};

	// **********
	interfaceResultadoAsyncPost pacientesResponsavel = new interfaceResultadoAsyncPost() {
		@Override
		public void obterResultado(final int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					ArrayList<paciente> listaPacientes = new ArrayList<paciente>();
					if(codigo == 1 && conteudo.length() > 10)
					{
						pacienteJson paciJson = new pacienteJson(conteudo);
						listaPacientes = paciJson.transformJsonPaciente();
						int idResp = listaPacientes.get(0).getIdResponsavelPaciente();
						lifeCheckerManager.getInstance().setIdResponsavel(idResp);
						//Chama nova activity para selecionar paciente
						trocarFragment(listaPacientes);
						
					}
					else
					{
						pbLoading.setVisibility(View.INVISIBLE);
						btValidar.setText(R.string.validar);
						btValidar.setEnabled(true);
					}

				}
			});
		}
	};
	
	public void trocarFragment(ArrayList<paciente> listPacientes)
	{
		lifeCheckerManager.getInstance().setListaPaciente(listPacientes);
		configuracaoPacSelecao fragment2 = new configuracaoPacSelecao();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.addToBackStack("abc");
		fragmentTransaction.hide(configuracaoPacConta.this);
		fragmentTransaction.add(android.R.id.content, fragment2);
		fragmentTransaction.commit();
		
	}

}
