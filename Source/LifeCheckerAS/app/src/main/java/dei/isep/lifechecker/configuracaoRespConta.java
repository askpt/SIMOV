package dei.isep.lifechecker;

import dei.isep.lifechecker.databaseonline.httpPost;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import dei.isep.lifechecker.other.*;

public class configuracaoRespConta extends Fragment implements OnClickListener {

	public Button btnValidarMail;
	public EditText txtMail;
	public EditText txtPass;
	public EditText txtPassConfirm;
	public TextView tvConfirmarMail;
	public ProgressBar pbLoadingMail;
	httpPost htPost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_responsavel_conta,
				container, false);
		btnValidarMail = (Button) myView
				.findViewById(R.id.bt_configuracao_respconta_validar);
		btnValidarMail.setOnClickListener(this);
		btnValidarMail.setText(R.string.validar);
		txtMail = (EditText) myView.findViewById(R.id.tb_configuracao_respconta_email);
		// txtMail.addTextChangedListener(tWMail);
		txtPass = (EditText) myView.findViewById(R.id.tb_configuracao_respconta_password);
		txtPassConfirm = (EditText) myView
				.findViewById(R.id.tb_configuracao_respconta_confirmarpass);

		pbLoadingMail = (ProgressBar) myView
				.findViewById(R.id.loading_configuracao_respconta_loading);

		tvConfirmarMail = (TextView) myView
				.findViewById(R.id.text_configuracao_respconta_estado);

		// btnValidarMail.setOnClickListener(this);

		tvConfirmarMail.setVisibility(View.INVISIBLE);
		pbLoadingMail.setVisibility(View.INVISIBLE);

		txtMail.addTextChangedListener(new genericTextWatcherConfiguracao(
				txtMail));
		txtPass.addTextChangedListener(new genericTextWatcherConfiguracao(
				txtPass));
		txtPassConfirm
				.addTextChangedListener(new genericTextWatcherConfiguracao(
						txtPassConfirm));
		
		

		return myView;
	}

	@Override
	public void onClick(View v) {
		tvConfirmarMail.setVisibility(View.VISIBLE);

		// Validar Mail
		validarDados validar = new validarDados();

		if (validar.mailValidacaoFormato(txtMail.getText().toString()) == true) {

			if (txtPass.getText().toString()
					.compareTo(txtPassConfirm.getText().toString()) == 0) {
				if (validar.passValidarFormato(txtPass.getText().toString()) == true) {
					btnValidarMail.setText(R.string.esperar);
					btnValidarMail.setEnabled(false);
					
					pbLoadingMail.setVisibility(View.VISIBLE);
					tvConfirmarMail.setTextColor(Color.BLACK);
					tvConfirmarMail
							.setText(R.string.verificar_mail_verificando);
					String mail = txtMail.getText().toString();

					responsavelHttp respHTTP = new responsavelHttp();
					respHTTP.verificarMail(mail, htPostResult);
					
					
				} else {
					tvConfirmarMail.setTextColor(getResources().getColor(
							R.color.vermelho));
					tvConfirmarMail
							.setText(R.string.verificar_password_formato_errado);
				}
			} else {
				tvConfirmarMail.setTextColor(getResources().getColor(
						R.color.vermelho));
				tvConfirmarMail.setText(R.string.verificar_password_diferentes);

			}

		} else {
			tvConfirmarMail.setTextColor(getResources().getColor(
					R.color.vermelho));
			tvConfirmarMail.setText(R.string.verificar_mail_formato_errado);
		}
	}

	interfaceResultadoAsyncPost htPostResult = new interfaceResultadoAsyncPost() {

		@Override
		public void obterResultado(int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					
					String resultado = conteudo.replaceAll("[\r\n]+", "");
					boolean resultadobool = Boolean.valueOf(resultado);
					if (resultadobool == true) {
						tvConfirmarMail
								.setText(R.string.verificar_mail_indisponivel);
						tvConfirmarMail.setTextColor(getResources().getColor(
								R.color.vermelho));
					} else {
						tvConfirmarMail
								.setText(R.string.verificar_mail_disponivel);
						tvConfirmarMail.setTextColor(getResources().getColor(
								R.color.verde));
					}
					pbLoadingMail.setVisibility(View.INVISIBLE);
					btnValidarMail.setText(R.string.validar);
					btnValidarMail.setEnabled(true);

				}
			});
		}
	};
}
