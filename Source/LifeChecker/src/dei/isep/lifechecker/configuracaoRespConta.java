package dei.isep.lifechecker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class configuracaoRespConta extends Fragment implements OnClickListener {

	Button btnValidarMail;
	EditText txtMail;
	EditText txtPass;
	EditText txtPassConfirm;
	boolean isVisible = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_responsavel_conta,
				container, false);
		btnValidarMail = (Button) myView.findViewById(R.id.bt_validar_mail);
		txtMail = (EditText) myView.findViewById(R.id.tb_email);
		txtPass = (EditText) myView.findViewById(R.id.tb_password);
		txtPassConfirm = (EditText) myView
				.findViewById(R.id.tb_password_confirm);
		btnValidarMail.setOnClickListener(this);

		isVisible = true;

		// txtMail.setText("wqeqweqweqwe");
		Log.i("8888888888888888888", "1020202010101010101010201212");
		return myView;
	}

	@Override
	public void onClick(View v) {
		Log.i("8888888888888888888", "888888888888888");
		Log.i("999998888899999", txtMail.getText().toString());
		Bundle args = new Bundle();
		configuracaoRespPaciente newFragment = new configuracaoRespPaciente();
		args.putString("A", "tetete");
		args.putInt("B", 2);
		newFragment.setArguments(args);
		// txtMail.setText("qqqqqqqqqq");
		Toast.makeText(getActivity().getApplicationContext(), "qweqweqwe",
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void setMenuVisibility(final boolean visible) {
		super.setMenuVisibility(visible);
		if (isVisible == true) {
			if (visible) {
				Log.i("Visibilidade", "1VISIVEL");
			} else {
				Log.i("Visibilidade", "1INNNNNNNVISIVEL");
			}
		}
	}

}
