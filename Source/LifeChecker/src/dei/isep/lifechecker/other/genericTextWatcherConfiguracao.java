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
			Log.i("instancia","Mail: " + text);
			break;
		case R.id.tb_password_resp:
			lifeCheckerManager.getInstance().setPassResposnavel(text);
			Log.i("instancia","Pass: " + text);
			break;
		}

		// TODO Auto-generated method stub

	}

}
