package dei.isep.lifechecker;

import java.util.List;
import java.util.Vector;

import dei.isep.lifechecker.adapter.fragmentAdapter;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;


public class configuracaoFragmentos extends FragmentActivity {

	private fragmentAdapter fAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.configuracao_fragmento);
		inserirActionBar();

		Log.i("qqqqqqqqq", "ppppppppppppppppp");

		Intent intent = getIntent();
		int opcao = intent.getIntExtra("opcao", 0);

		List<Fragment> fragments = new Vector<Fragment>();
		Log.i("98888888888","9888888888888888");
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

		
		
		//********

		//configuracaoRespConta confRest = new configuracaoRespConta();

		
//		emailNewResp.setText("aaaaaaaaaaaa");
		
		// ***********************


		// int ressourceID = getResources().getIdentifier("tb_email_resp", "id",
		// this.getPackageName());

	
		// btnVerificarMailResp =
		// getResources().getIdentifier("bt_validar_mail", "Button",
		// getPackageName());
		Log.i("iiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiii");
		// verificarEmailExist();

	}

	public void inserirActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		TextView textView = (TextView) actionBar.getCustomView().findViewById(
				R.id.actionBar_Titulo);
		textView.setText(getResources().getString(R.string.configuracao));
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}

	

}
