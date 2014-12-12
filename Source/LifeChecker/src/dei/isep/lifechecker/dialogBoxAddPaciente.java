package dei.isep.lifechecker;

import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.other.lifeCheckerManager;
import dei.isep.lifechecker.other.validarDados;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class dialogBoxAddPaciente extends DialogFragment {
	
	interfaceResultadoDialogBox interfaceDialog;
	


	EditText edtNomePaciente;
	EditText edtApelidoPaciente;
	EditText edtMailPaciente;
	EditText edtcontactoPaciente;

	TextView txtEstado;
	
	Button btnInserir;
	Button btnCancelar;

	ProgressBar pbLoadigNovoPaciente;

	String listaErros = "";
	
	paciente pacient;
	
	public void setOnResultListener(
			interfaceResultadoDialogBox interfaceDialog) {
		if (interfaceDialog != null) {
			this.interfaceDialog = interfaceDialog;
		}
	}

	public static dialogBoxAddPaciente newinstance(int title) {
		dialogBoxAddPaciente dialog = new dialogBoxAddPaciente();
		Bundle args = new Bundle();
		args.putInt("title", title);
		dialog.setArguments(args);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.dialogbox_add_paciente,
				container, false);

		edtNomePaciente = (EditText) myView
				.findViewById(R.id.tb_configuracao_dialog_resppaciente_nome);
		edtApelidoPaciente = (EditText) myView
				.findViewById(R.id.tb_configuracao_dialog_resppaciente_apelido);
		edtMailPaciente = (EditText) myView
				.findViewById(R.id.tb_configuracao_dialog_resppaciente_email);
		edtcontactoPaciente = (EditText) myView
				.findViewById(R.id.tb_configuracao_dialog_resppaciente_telefone);

		btnInserir = (Button) myView.findViewById(R.id.bt_configuracao_dialog_resppaciente_validar);
		btnCancelar = (Button) myView.findViewById(R.id.bt_configuracao_dialog_resppaciente_Cancelar);
		
		
		myView.findViewById(R.id.bt_configuracao_dialog_resppaciente_validar)
				.setOnClickListener(btnCarregado);
		myView.findViewById(R.id.bt_configuracao_dialog_resppaciente_Cancelar)
				.setOnClickListener(btnCarregado);

		txtEstado = (TextView) myView
				.findViewById(R.id.text_configuracao_dialog_resppaciente_informacao);

		pbLoadigNovoPaciente = (ProgressBar) myView
				.findViewById(R.id.loading_configuracao_dialog_paciente_novo);

		pbLoadigNovoPaciente.setVisibility(View.INVISIBLE);
		txtEstado.setVisibility(View.INVISIBLE);
		/*
		 * Button button = (Button) v.findViewById(R.id.buttonShow);
		 * button.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { ((DialogActivity) getActivity())
		 * .showDialogType(DialogActivity.TYPE_ALERT_DIALOG); } });
		 */

		getDialog().setTitle(getArguments().getInt("title"));

		return myView;
	}

	final OnClickListener btnCarregado = new OnClickListener() {
		@Override
		public void onClick(View v) {
			listaErros = "";
			if (v.getId() == R.id.bt_configuracao_dialog_resppaciente_validar) {
				txtEstado.setVisibility(View.VISIBLE);
				txtEstado.setTextColor(Color.BLACK);

				if (validarNome() == 0 && validarApelido() == 0 && validarMail() == 0 && validarContacto() == 0) {

					pbLoadigNovoPaciente.setVisibility(View.VISIBLE);
					btnInserir.setEnabled(false);
					btnCancelar.setEnabled(false);
					validarmailOnlinePaciente(edtMailPaciente.getText().toString());
				} else {
					txtEstado.setTextColor(getResources().getColor(R.color.vermelho));
					txtEstado.setText(listaErros);
				}

				/*
				 * Se validar
				 */
			} else {
				interfaceDialog.obterResultado(1, "");
				getDialog().dismiss();
				
			}
			// TODO Auto-generated method stub

		}
	};
	
	public void validarmailOnlinePaciente(String mail) {
		txtEstado.setText(getResources().getString(
				R.string.est_inserir_paciente));
		pacienteHttp paciHttp = new pacienteHttp();
		paciHttp.verificarMail(mail, htPostResultMailPaciente);
	}
	
	public void inserirPaciente() {
		txtEstado.setText(getResources().getString(
				R.string.est_inserir_paciente));
		int idResp = lifeCheckerManager.getInstance().getIdResponsavel();
		pacient = new paciente(idResp,
				edtNomePaciente.getText().toString(), 
				edtApelidoPaciente.getText().toString(), 
				edtMailPaciente.getText().toString(),
				edtcontactoPaciente.getText().toString(), true);
		pacienteHttp addPaciente = new pacienteHttp();
		addPaciente.addPaciente(pacient, addPacienteListener);
	}

	interfaceResultadoAsyncPost htPostResultMailPaciente = new interfaceResultadoAsyncPost() {

		@Override
		public void obterResultado(int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					String resultado = conteudo.replaceAll("[\r\n]+", "");
					boolean resultadobool = Boolean.valueOf(resultado);
					if (resultadobool == true) {
						listaErros += getResources().getString(R.string.verificar_mail_indisponivel)+ "\n";
						txtEstado.setText(listaErros);
						/*btnValidarResponsavel.setText(R.string.validar);
						btnValidarResponsavel.setEnabled(true);*/
						pbLoadigNovoPaciente.setVisibility(View.INVISIBLE);
						btnInserir.setEnabled(true);
						btnCancelar.setEnabled(true);
					} else {
						inserirPaciente();
					}
					
				}
			});

		}
	};
	
	interfaceResultadoAsyncPost addPacienteListener = new interfaceResultadoAsyncPost() {

		@Override
		public void obterResultado(final int codigo, String conteudo) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (codigo == 1) {
						/*btnValidarResponsavel.setText(R.string.validar);
						btnValidarResponsavel.setEnabled(true);
						 */
						pbLoadigNovoPaciente.setVisibility(View.INVISIBLE);
						interfaceDialog.obterResultado(2, "");
						getDialog().dismiss();
					}
					else
					{
						btnInserir.setEnabled(true);
						btnCancelar.setEnabled(true);
					}

				}
			});

		}
	};
	
	public int validarNome()
	{
		validarDados validar = new validarDados();
		if(edtNomePaciente.getText().toString() != null
				&& validar.nomeApelidoVerificar(edtNomePaciente.getText().toString()) == true )
		{
			
			return 0;
		}
		else
		{
			listaErros = getResources().getString(R.string.erro_nome) + "\n";
			return 1;
		}
	}
	
	public int validarApelido()
	{
		validarDados validar = new validarDados();
		if(edtApelidoPaciente.getText().toString() != null
				&& validar.nomeApelidoVerificar(edtApelidoPaciente.getText().toString()) == true )
		{
			
			return 0;
		}
		else
		{
			listaErros = getResources().getString(R.string.erro_apelido) + "\n";
			return 1;
		}
	}
	
	public int validarMail()
	{
		validarDados validar = new validarDados();
		if(edtMailPaciente.getText().toString() != null
				&& validar.mailValidacaoFormato(edtMailPaciente.getText().toString()) == true )
		{
			
			return 0;
		}
		else
		{
			listaErros = getResources().getString(R.string.verificar_mail_formato_errado) + "\n";
			return 1;
		}
	}
	
	public int validarContacto()
	{
		validarDados validar = new validarDados();
		if(edtcontactoPaciente.getText().toString() != null
				&& validar.contactoVerificar(edtcontactoPaciente.getText().toString()) == true )
		{
			
			return 0;
		}
		else
		{
			listaErros = getResources().getString(R.string.erro_contacto) + "\n";
			return 1;
		}
	}
}
