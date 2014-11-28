package dei.isep.lifechecker;

import java.util.List;
import java.util.Vector;

import dei.isep.lifechecker.adapter.fragmentAdapter;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class configuracaoFragmentos extends FragmentActivity{
	
	private fragmentAdapter fAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.configuracao_fragmento);
		inserirActionBar();

		Intent intent = getIntent();
		int opcao = intent.getIntExtra("opcao", 0);
		// Création de la liste de Fragments que fera défiler le PagerAdapter
		List<Fragment> fragments = new Vector<Fragment>();
		
		switch (opcao) {
		case 1:
			fragments.add(Fragment.instantiate(this, configuracaoRespConta.class.getName()));
			fragments.add(Fragment.instantiate(this, configuracaoRespDados.class.getName()));
			fragments.add(Fragment.instantiate(this, configuracaoRespAlerta.class.getName()));
			fragments.add(Fragment.instantiate(this, configuracaoRespPeriodicidade.class.getName()));
			fragments.add(Fragment.instantiate(this, configuracaoRespPaciente.class.getName()));
			break;
		case 2:
			fragments.add(Fragment.instantiate(this, configuracaoPacConta.class.getName()));
			break;
			
		case 3:
			fragments.add(Fragment.instantiate(this, configuracaoRecConta.class.getName()));
			break;
		}

		this.fAdapter = new fragmentAdapter(super.getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) super.findViewById(R.id.configuracao_fragmento);
		// Affectation de l'adapter au ViewPager
		pager.setAdapter(this.fAdapter);
		
	}
	
	public void inserirActionBar()
	{
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		TextView textView = (TextView) actionBar.getCustomView().findViewById(R.id.actionBar_Titulo);
		textView.setText(getResources().getString(R.string.configuracao));
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
	}
	

}
