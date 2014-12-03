package dei.isep.lifechecker;

import java.util.List;
import java.util.Vector;
import java.util.zip.Inflater;

import dei.isep.lifechecker.adapter.fragmentAdapter;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class configuracaoFragmentos extends FragmentActivity {

	private fragmentAdapter fAdapter;

	private Button btnVerificarMailResp;
	private EditText emailNewResp;
	private EditText passNewResp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.configuracao_fragmento);
		inserirActionBar();
		//verificarEmailExist();
		Log.i("qqqqqqqqq","ppppppppppppppppp");

		Intent intent = getIntent();
		int opcao = intent.getIntExtra("opcao", 0);
		// Création de la liste de Fragments que fera défiler le PagerAdapter
		List<Fragment> fragments = new Vector<Fragment>();

		switch (opcao) {
		case 1:
			fragments.add(Fragment.instantiate(this,
					configuracaoRespConta.class.getName()));
			fragments.add(Fragment.instantiate(this,
					configuracaoRespDados.class.getName()));
			fragments.add(Fragment.instantiate(this,
					configuracaoRespAlerta.class.getName()));
			fragments.add(Fragment.instantiate(this,
					configuracaoRespPeriodicidade.class.getName()));
			fragments.add(Fragment.instantiate(this,
					configuracaoRespPaciente.class.getName()));
			break;
		case 2:
			fragments.add(Fragment.instantiate(this,
					configuracaoPacConta.class.getName()));
			break;

		case 3:
			fragments.add(Fragment.instantiate(this,
					configuracaoRecConta.class.getName()));
			break;
		}

		this.fAdapter = new fragmentAdapter(super.getSupportFragmentManager(),
				fragments);

		ViewPager pager = (ViewPager) super
				.findViewById(R.id.configuracao_fragmento);
		// Affectation de l'adapter au ViewPager
		pager.setAdapter(this.fAdapter);
		

	}

	public void inserirActionBar() {
		Log.i("yyyyyyyyyyyyyyyyy","zzzzzzzzzzzzzzzzz");
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		TextView textView = (TextView) actionBar.getCustomView().findViewById(
				R.id.actionBar_Titulo);
		textView.setText(getResources().getString(R.string.configuracao));
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_HOME_AS_UP);
	}

	public void verificarEmailExist() {
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup v = (ViewGroup)inflater.inflate(R.layout.configuracao_responsavel_conta, null, false);
		btnVerificarMailResp = (Button)v.findViewById(R.id.bt_validar_mail);
		
		
		Log.i("wwwwwwwwwwwww","eeeeeeeeeeeeee");
		View inflatedView = getLayoutInflater().inflate(
				R.layout.configuracao_responsavel_conta, null);

		//btnVerificarMailResp = (Button)findViewById(R.id.bt_validar_mail);
		
		//btnVerificarMailResp = (Button) inflatedView.findViewById(R.id.bt_validar);
		emailNewResp = (EditText) inflatedView.findViewById(R.id.tb_email);
		passNewResp = (EditText) inflatedView.findViewById(R.id.tb_password);

		emailNewResp.setText("aaaaaaaaaaaa");

		//btnVerificarMailResp = getResources().getIdentifier("bt_validar_mail", "Button", getPackageName());
		Log.i("iiiiiiiiiiiiiiiii","iiiiiiiiiiiiiiiiii");
		Log.i("ggggggggggggggggggg",passNewResp.getText().toString());

		btnVerificarMailResp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("TESTE","ccccccccccccccccc");
				if (emailNewResp.getText().length() != 0
						|| passNewResp.getText().length() != 0) {
					Toast.makeText(getApplication().getApplicationContext(), "wewewe",
							Toast.LENGTH_LONG).show();
					emailNewResp.setText("aaaaaaaaaaaa");
				} else {
					emailNewResp.setText("bbbbbbbbbbbbb");
				}
				// TODO Auto-generated method stub
			}
		});
	}
	
	public void onValidRespMail()
	{
		Log.i("wwwwwwwwwwwww","passssssss");
	}
	
	
}
