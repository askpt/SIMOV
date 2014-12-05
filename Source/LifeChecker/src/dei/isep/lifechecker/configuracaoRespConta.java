package dei.isep.lifechecker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;

import dei.isep.lifechecker.databaseonline.httpPost;
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
		View myView = inflater.inflate(R.layout.configuracao_responsavel_conta,container, false);
		btnValidarMail = (Button) myView
				.findViewById(R.id.bt_validar_mail_resp);
		btnValidarMail.setOnClickListener(this);
		txtMail = (EditText) myView.findViewById(R.id.tb_email_resp);
		// txtMail.addTextChangedListener(tWMail);
		txtPass = (EditText) myView.findViewById(R.id.tb_password_resp);
		txtPassConfirm = (EditText) myView
				.findViewById(R.id.tb_password_confirm_resp);

		pbLoadingMail = (ProgressBar) myView
				.findViewById(R.id.CircleLoadingMailVerify);

		tvConfirmarMail = (TextView) myView
				.findViewById(R.id.tv_estado_vrificacao_mail);

		// btnValidarMail.setOnClickListener(this);

		tvConfirmarMail.setVisibility(View.INVISIBLE);
		pbLoadingMail.setVisibility(View.INVISIBLE);
		
		txtMail.addTextChangedListener(new genericTextWatcherConfiguracao(txtMail));
		txtPass.addTextChangedListener(new genericTextWatcherConfiguracao(txtPass));
		
		return myView;
	}

	@Override
	public void onResume() {
		super.onResume();
		// txtMail.setText("Now I can access my EditText!");
	}

	@Override
	public void onClick(View v) {
		tvConfirmarMail.setVisibility(View.VISIBLE);

		// Validar Mail

		if (mailValidacaoFormato(txtMail.getText().toString()) == true) {

			if(txtPass.getText().toString().compareTo(txtPassConfirm.getText().toString()) == 0)
			{
				if (passValidarFormato(txtPass.getText().toString()) == true) {
					pbLoadingMail.setVisibility(View.VISIBLE);
					tvConfirmarMail.setTextColor(Color.BLACK);
					tvConfirmarMail.setText(R.string.verificar_mail_verificando);
					String mail = txtMail.getText().toString();

					String url = "http://simovws.azurewebsites.net/api/Responsaveis/VerificarSeExisteEmail?email="
							+ mail;
					List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

					htPost = new httpPost(url, postParameters);
					htPost.setOnResultListener(htPostResult);
					htPost.execute();
				} else {
					tvConfirmarMail.setTextColor(getResources().getColor(
							R.color.vermelho));
					tvConfirmarMail
							.setText(R.string.verificar_password_formato_errado);
				}
			}
			else
			{
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

				}
			});

		}
	};

	public boolean mailValidacaoFormato(String mail) {
		boolean resultado = false;
		Pattern patternMail = Pattern.compile(
				"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matchPatternMail = patternMail.matcher(mail);
		resultado = matchPatternMail.find();
		return resultado;
	}

	public boolean passValidarFormato(String password) {
		boolean resultado = false;
		Pattern patternPassword = Pattern
				.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,30})");
		Matcher matchPatternPassword = patternPassword.matcher(password);
		resultado = matchPatternPassword.find();
		return resultado;
	}

}
