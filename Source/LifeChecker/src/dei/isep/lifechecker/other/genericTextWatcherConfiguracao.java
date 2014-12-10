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
		case R.id.tb_email_resp:
			lifeCheckerManager.getInstance().setMailResponsavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		case R.id.tb_password_resp:
			lifeCheckerManager.getInstance().setPassResposnavel(text);
			Log.i("instancia", "Pass: " + text);
			break;
			
		case R.id.tb_password_confirm_resp:
			lifeCheckerManager.getInstance().setPassConfirmResponsavel(text);
			Log.i("instancia", "Pass confirm : " + text);
			break;

		case R.id.tb_nome_responsavel:
			lifeCheckerManager.getInstance().setNomeResponsavel(text);
			Log.i("instancia", "Nome Responsavel: " + text);
			break;
		case R.id.tb_apelido_responsavel:
			lifeCheckerManager.getInstance().setApelidoResponsavel(text);
			Log.i("instancia", "Apelido Responsavel: " + text);
			break;
		case R.id.tb_telefone_responsavel:
			lifeCheckerManager.getInstance().setTelefoneResponsavel(text);
			Log.i("instancia", "Telefone Responsavel: " + text);
			break;


		case R.id.tb_diurno_responsavel:
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
		case R.id.tb_noturno_responsavel:
			try{
				lifeCheckerManager.getInstance().setMinutosNoturo(
						Integer.valueOf(text));
				Log.i("instancia", "Pass: " + text);
			}catch (NumberFormatException e){
				Log.i("instancia", "Pass: " + text);
				lifeCheckerManager.getInstance().setMinutosNoturo(-1);
			}
			break;
			
		case R.id.tb_nome_paciente_resp:
			lifeCheckerManager.getInstance().setNomePacienteResposnavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		case R.id.tb_apelido_paciente_resp:
			lifeCheckerManager.getInstance().setApelidoPacienteResposnavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		case R.id.tb_mail_paciente_resp:
			lifeCheckerManager.getInstance().setMailPacienteResposnavel(text);
			Log.i("instancia", "Mail: " + text);
			break;
		case R.id.tb_telefone_paciente_resp:
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
