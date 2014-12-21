package dei.isep.lifechecker;

import dei.isep.lifechecker.database.marcacaoBDD;
import dei.isep.lifechecker.database.pacienteBDD;
import dei.isep.lifechecker.database.responsavelBDD;
import dei.isep.lifechecker.databaseonline.marcacaoHttp;
import dei.isep.lifechecker.databaseonline.pacienteHttp;
import dei.isep.lifechecker.databaseonline.responsavelHttp;
import dei.isep.lifechecker.json.marcacaoJson;
import dei.isep.lifechecker.json.pacienteJson;
import dei.isep.lifechecker.json.responsavelJson;
import dei.isep.lifechecker.model.marcacao;
import dei.isep.lifechecker.model.paciente;
import dei.isep.lifechecker.model.responsavel;
import dei.isep.lifechecker.other.validarDados;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class configuracaoRecConta extends Fragment {

	EditText edtMail;
    EditText ETpass;
	Button btnEnviarMail;
    Button BTNRecuperar;
	TextView txtComentarios;
	ProgressBar pbLoadEnvio;
    int idResponsavel = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myView = inflater.inflate(R.layout.configuracao_recuperacao_conta,
				container, false);

		edtMail = (EditText) myView
				.findViewById(R.id.tb_configuracao_recuperacao_email);

        ETpass = (EditText) myView
                .findViewById(R.id.tb_configuracao_recuperacao_pass);


		txtComentarios = (TextView) myView
				.findViewById(R.id.text_configuracao_recuperacao_comentarios);
		btnEnviarMail = (Button) myView
				.findViewById(R.id.bt_configuracao_recuperacao_validar);

        BTNRecuperar = (Button) myView
                .findViewById(R.id.bt_configuracao_recuperacao_entrar);

        myView.findViewById(R.id.bt_configuracao_recuperacao_validar).setOnClickListener(btnCarregado);
        myView.findViewById(R.id.bt_configuracao_recuperacao_entrar).setOnClickListener(btnCarregado);

		//btnEnviarMail.setOnClickListener(this);
        //BTNRecuperar.setOnClickListener();

		pbLoadEnvio = (ProgressBar) myView
				.findViewById(R.id.loading_configuracao_recuperacao);
		pbLoadEnvio.setVisibility(View.INVISIBLE);

		txtComentarios.setVisibility(View.INVISIBLE);

		return myView;
	}

    final OnClickListener btnCarregado = new OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.bt_configuracao_recuperacao_validar:
                    recuperarMail();
                    break;
                case R.id.bt_configuracao_recuperacao_entrar:
                    recuperarConta();
                    break;
            }
        }
    };

    public void recuperarMail()
    {
        txtComentarios.setVisibility(View.VISIBLE);
        txtComentarios.setTextColor(Color.BLACK);
        String mail = edtMail.getText().toString();
        validarDados validar = new validarDados();
        if (validar.mailValidacaoFormato(mail) == true) {
            btnEnviarMail.setText(R.string.esperar);
            btnEnviarMail.setEnabled(false);
            BTNRecuperar.setEnabled(false);

            txtComentarios.setText(R.string.est_verificar_mail_resp);
            responsavelHttp respHttp = new responsavelHttp();
            respHttp.enviarMailRecuperacao(mail, enviarMailRecuperacaoListenner);
            pbLoadEnvio.setVisibility(View.VISIBLE);
        } else {
            txtComentarios.setTextColor(getResources().getColor(
                    R.color.vermelho));
            txtComentarios.setText(R.string.verificar_mail_formato_errado);
        }
    }

    public void recuperarConta()
    {
        txtComentarios.setVisibility(View.VISIBLE);
        txtComentarios.setTextColor(Color.BLACK);
        txtComentarios.setText(R.string.est_verificar_dados_login);
        String mail = edtMail.getText().toString();
        String pass = ETpass.getText().toString();
        validarDados validar = new validarDados();
        if(validar.mailValidacaoFormato(mail) &&
                validar.passValidarFormato(pass))
        {
            btnEnviarMail.setEnabled(false);
            BTNRecuperar.setEnabled(false);
            responsavelHttp respHttp = new responsavelHttp();
            respHttp.getIdResposnavel(mail,pass,verificarContaMailPass);
        }
        else
        {
            txtComentarios.setText(R.string.err_dados_formularios);
        }
    }

    interfaceResultadoAsyncPost verificarContaMailPass = new interfaceResultadoAsyncPost() {

        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(conteudo.length() > 10)
                    {
                        Log.i("login", " passou");
                        txtComentarios.setText(" pasou");

                        responsavelJson resJson = new responsavelJson(conteudo);
                        responsavel resp = resJson.transformJsonResponsavel();
                        responsavelBDD respBdd = new responsavelBDD(getActivity().getApplicationContext());
                        respBdd.inserirResponsavelComId(resp);

                        idResponsavel = resp.getIdResponsavel();

                        pacienteHttp paciHttp = new pacienteHttp();
                        paciHttp.retornarPacientesIdResposnavel(resp.getIdResponsavel(),recuperarPacientesListener);
                    }
                    else
                    {
                        txtComentarios.setText(" Errou");

                        btnEnviarMail.setEnabled(true);
                        BTNRecuperar.setEnabled(true);
                        txtComentarios.setText(R.string.err_login_errado);
                        Log.i("login", " NAO passou");
                    }

                }
            });
        }
    };

	interfaceResultadoAsyncPost enviarMailRecuperacaoListenner = new interfaceResultadoAsyncPost() {

		@Override
		public void obterResultado(final int codigo, final String conteudo) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					pbLoadEnvio.setVisibility(View.INVISIBLE);
					btnEnviarMail.setText(R.string.password);
					btnEnviarMail.setEnabled(true);
                    BTNRecuperar.setEnabled(true);
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

    interfaceResultadoAsyncPost recuperarPacientesListener = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pacienteJson paciJson = new pacienteJson(conteudo);
                    ArrayList<paciente> arrayPaci = paciJson.transformJsonPaciente();
                    pacienteBDD paciBDD = new pacienteBDD(getActivity().getApplicationContext());
                    for (int i = 0; i < arrayPaci.size(); i++) {
                        paciBDD.inserirPacienteComId(arrayPaci.get(i));
                    }

                    marcacaoHttp marcHttp = new marcacaoHttp();
                    marcHttp.retornarMarcacoesEstado(idResponsavel,1,recuperarMarcacoesEst1);

                }
            });
        }
    };

    interfaceResultadoAsyncPost recuperarMarcacoesEst1 = new interfaceResultadoAsyncPost() {
        @Override
        public void obterResultado(final int codigo, final String conteudo) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(conteudo.length() > 10)
                    {
                        ArrayList<marcacao> listMarc = new ArrayList<marcacao>();
                        marcacaoJson marcJson = new marcacaoJson(conteudo);
                        listMarc = marcJson.transformJsonMarcacao();

                        marcacaoBDD marcBDD = new marcacaoBDD(getActivity().getApplicationContext());
                        marcBDD.deleteMarcacoesByEstado(1);
                        for (int i = 0; i < listMarc.size(); i++) {
                            marcBDD.inserirMarcacaoComId(listMarc.get(i));

                        }

                        Intent intent = new Intent(getActivity(), responsavelMenu.class);
                        getActivity().startActivity(intent);

                        //marcacaoHttp marcHttp = new marcacaoHttp();
                        //marcHttp.retornarMarcacoesEstado(idResponsavel,2,recuperarMarcacoesEst2);
                    }
                    else
                    {

                        Intent intent = new Intent(getActivity(), responsavelMenu.class);
                        getActivity().startActivity(intent);

                        //marcacaoHttp marcHttp = new marcacaoHttp();
                        //marcHttp.retornarMarcacoesEstado(idResponsavel,2,recuperarMarcacoesEst2);
                    }

                }
            });
        }
    };
}
