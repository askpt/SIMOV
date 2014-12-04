package dei.isep.lifechecker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class configuracaoRespConta extends Fragment {//implements OnClickListener {

	public Button btnValidarMail;
	public EditText txtMail;
	public EditText txtPass;
	public EditText txtPassConfirm;
	
	public TextWatcher watcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Log.i("texto","TEXTAAAAAA");
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			Log.i("texto","TEXTBBBBBB");
			
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			Log.i("texto","TEXTCCCCC");
			lifeCheckerManager.getInstance().setMailResponsavel(s.toString());
			
			Log.i("texto","TEXTDDDDDD " + lifeCheckerManager.getInstance().getMailResponsavel());
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.configuracao_responsavel_conta,
				container, false);
		btnValidarMail = (Button) myView.findViewById(R.id.bt_validar_mail_resp);
		txtMail = (EditText) myView.findViewById(R.id.tb_email_resp);
		//txtMail.addTextChangedListener(tWMail);
		txtPass = (EditText) myView.findViewById(R.id.tb_password_resp);
		txtPassConfirm = (EditText) myView
				.findViewById(R.id.tb_password_confirm_resp);
		
        txtMail.setText("Now I can access my EditText!");
		//btnValidarMail.setOnClickListener(this);
		txtMail.addTextChangedListener(watcher);
		return myView;
	}
	
	
	  @Override
	    public void onResume() {
	        super.onResume();
	        //txtMail.setText("Now I can access my EditText!");
	    }


	
/*
	@Override
	public void onClick(View v) {
		
	

		String test = emailNewResp.getText().toString();
		test += " qweqwe";
		
		Log.i("8888888888888888888", "888888888888888");
		//Log.i("999998888899999", txtMail.getText().toString());
		Bundle args = new Bundle();
		configuracaoRespPaciente newFragment = new configuracaoRespPaciente();
		args.putString("A", "tetete");
		args.putInt("B", 2);
		newFragment.setArguments(args);
		// txtMail.setText("qqqqqqqqqq");
		Toast.makeText(getActivity().getApplicationContext(), "qweqweqwe",
				Toast.LENGTH_LONG).show();

	}
*/
	

}
