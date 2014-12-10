package dei.isep.lifechecker;

import dei.isep.lifechecker.other.lifeCheckerManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class configuracaoRespAlerta extends Fragment {

	public CheckBox chboxMail;
	public CheckBox chboxSMS;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(
				R.layout.configuracao_responsavel_alertas, container, false);
		chboxMail = (CheckBox) myView.findViewById(R.id.cb_configuracao_respalertas_mail);
		chboxSMS = (CheckBox) myView.findViewById(R.id.cb_configuracao_respalertas_sms);
		/*
		 * ImageButton flechaEsquerda = (ImageButton) myFragment
		 * .findViewById(R.id.actionBar_btnFelchaEsquerda);
		 * flechaEsquerda.setVisibility(View.VISIBLE);
		 */
		chboxMail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (chboxMail.isChecked()) {
					lifeCheckerManager.getInstance()
							.setNotificacaoMailResposnavel(true);
					Log.i("instancia", "CheckBox Mail: " + true);
				} else {
					lifeCheckerManager.getInstance()
							.setNotificacaoMailResposnavel(false);
					Log.i("instancia", "CheckBox Mail: " + false);
				}
			}
		});
		
		chboxSMS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(chboxSMS.isChecked())
				{
					lifeCheckerManager.getInstance().setNotificacaoSMSResposnavel(true);
					Log.i("instancia", "CheckBox SMS: " + true);
				}
				else
				{
					lifeCheckerManager.getInstance().setNotificacaoSMSResposnavel(false);
					Log.i("instancia", "CheckBox SMS: " + false);
				}
				// TODO Auto-generated method stub
				
			}
		});
		return myView;
	}

}
