package dei.isep.lifechecker;

import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.other.validarDados;
import android.support.v4.app.Fragment;
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

public class configuracaoRecConta extends Fragment implements OnClickListener {

	EditText edtMail;
	Button btnEnviarMail;
	TextView txtComentarios;
	ProgressBar pbLoadEnvio;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myView = inflater.inflate(R.layout.configuracao_recuperacao_conta,
				container, false);

		edtMail = (EditText) myView
				.findViewById(R.id.tb_configuracao_recuperacao_email);
		txtComentarios = (TextView) myView
				.findViewById(R.id.text_configuracao_recuperacao_comentarios);
		btnEnviarMail = (Button) myView
				.findViewById(R.id.bt_configuracao_recuperacao_validar);
		btnEnviarMail.setOnClickListener(this);
		pbLoadEnvio = (ProgressBar) myView
				.findViewById(R.id.loading_configuracao_recuperacao);
		pbLoadEnvio.setVisibility(View.INVISIBLE);

		txtComentarios.setVisibility(View.INVISIBLE);

		return myView;
	}

	@Override
	public void onClick(View v) {
		txtComentarios.setVisibility(View.VISIBLE);
		txtComentarios.setTextColor(Color.BLACK);
		String mail = edtMail.getText().toString();
		validarDados validar = new validarDados();
		if (validar.mailValidacaoFormato(mail) == true) {
			btnEnviarMail.setText(R.string.esperar);
			btnEnviarMail.setEnabled(false);
			
			txtComentarios.setText(R.string.est_verificar_mail_resp);
			responsavelHttp respHttp = new responsavelHttp();
			respHttp.enviarMailRecuperacao(mail, enviarMailRecuperacaoListenner);
			pbLoadEnvio.setVisibility(View.VISIBLE);
		} else {
			txtComentarios.setTextColor(getResources().getColor(
					R.color.vermelho));
			txtComentarios.setText(R.string.verificar_mail_formato_errado);
		}

		// TODO Auto-generated method stub

	}

	interfaceResultadoAsyncPost enviarMailRecuperacaoListenner = new interfaceResultadoAsyncPost() {

		@Override
		public void obterResultado(final int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					pbLoadEnvio.setVisibility(View.INVISIBLE);
					btnEnviarMail.setText(R.string.validar);
					btnEnviarMail.setEnabled(true);
					String resultado = conteudo.replaceAll("[\r\n]+", "");
					if (codigo == 1 && resultado.compareTo("true") == 0) {
						String conteudo = String.format(getResources()
								.getString(R.string.est_mail_enviado), edtMail
								.getText().toString());
						txtComentarios.setTextColor(getResources().getColor(
								R.color.verde));
						txtComentarios.setText(conteudo);
					} else {
						txtComentarios.setTextColor(getResources().getColor(
								R.color.vermelho));
						txtComentarios.setText(getResources().getString(
								R.string.est_mail_inexistente));
					}
					

				}
			});

		}
	};
}
