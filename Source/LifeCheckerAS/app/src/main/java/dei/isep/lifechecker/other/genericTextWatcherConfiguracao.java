package dei.isep.lifechecker.other;

import dei.isep.lifechecker.R;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

public class genericTextWatcherConfiguracao implements TextWatcher {

	private View view;

	public genericTextWatcherConfiguracao(View view) {
		this.view = view;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable editable) {
		String text = editable.toString();
		switch (view.getId()) {
		case R.id.tb_configuracao_respconta_email:
			lifeCheckerManager.getInstance().setMailResponsavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		case R.id.tb_configuracao_respconta_password:
			lifeCheckerManager.getInstance().setPassResposnavel(text);
			Log.i("instancia", "Pass: " + text);
			break;
			
		case R.id.tb_configuracao_respconta_confirmarpass:
			lifeCheckerManager.getInstance().setPassConfirmResponsavel(text);
			Log.i("instancia", "Pass confirm : " + text);
			break;

		case R.id.tb_configuracao_respdados_nome:
			lifeCheckerManager.getInstance().setNomeResponsavel(text);
			Log.i("instancia", "Nome Responsavel: " + text);
			break;
		case R.id.tb_configuracao_respdados_apelido:
			lifeCheckerManager.getInstance().setApelidoResponsavel(text);
			Log.i("instancia", "Apelido Responsavel: " + text);
			break;
		case R.id.tb_configuracao_respdados_telefone:
			lifeCheckerManager.getInstance().setTelefoneResponsavel(text);
			Log.i("instancia", "Telefone Responsavel: " + text);
			break;


		case R.id.tb_configuracao_respperiodicidade_diurno:
			//**
			try{
				lifeCheckerManager.getInstance().setMinutosDiurno(
						Integer.valueOf(text));
				Log.i("instancia", "Pass: " + text);
			}catch (NumberFormatException e){
				Log.i("instancia", "Pass: " + text);
				lifeCheckerManager.getInstance().setMinutosDiurno(-1);
			}
			break;
		case R.id.tb_configuracao_respperiodicidade_noturno:
			try{
				lifeCheckerManager.getInstance().setMinutosNoturo(
						Integer.valueOf(text));
				Log.i("instancia", "Pass: " + text);
			}catch (NumberFormatException e){
				Log.i("instancia", "Pass: " + text);
				lifeCheckerManager.getInstance().setMinutosNoturo(-1);
			}
			break;
			
		case R.id.tb_configuracao_resppaciente_nome:
			lifeCheckerManager.getInstance().setNomePacienteResposnavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		case R.id.tb_configuracao_resppaciente_apelido:
			lifeCheckerManager.getInstance().setApelidoPacienteResposnavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		case R.id.tb_configuracao_resppaciente_email:
			lifeCheckerManager.getInstance().setMailPacienteResposnavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		case R.id.tb_configuracao_resppaciente_telefone:
			lifeCheckerManager.getInstance().setContactoPacienteResposnavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		}
	}

	// TODO Auto-generated method stub

	public boolean converterStringToBoolean(String valor) {
		String resultado = valor.replaceAll("[\r\n]+", "");
		boolean resultadobool = Boolean.valueOf(resultado);
		return resultadobool;
	}

}
