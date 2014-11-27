package dei.isep.lifechecker;

import java.util.List;
import java.util.Vector;

import dei.isep.lifechecker.adapter.FragmentAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class ConfiguracaoFragmentos extends FragmentActivity{
	
	private FragmentAdapter fAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.configuracao_fragmento);

		Intent intent = getIntent();
		int opcao = intent.getIntExtra("opcao", 0);
		// Création de la liste de Fragments que fera défiler le PagerAdapter
		List<Fragment> fragments = new Vector<Fragment>();
		
		switch (opcao) {
		case 1:
			fragments.add(Fragment.instantiate(this, ConfiguracaoRespConta.class.getName()));
			fragments.add(Fragment.instantiate(this, ConfiguracaoRespDados.class.getName()));
			fragments.add(Fragment.instantiate(this, ConfiguracaoRespAlerta.class.getName()));
			fragments.add(Fragment.instantiate(this, ConfiguracaoRespPeriodicidade.class.getName()));
			fragments.add(Fragment.instantiate(this, ConfiguracaoRespPaciente.class.getName()));
			break;
		case 2:
			fragments.add(Fragment.instantiate(this, ConfiguracaoPacConta.class.getName()));
			break;
			
		case 3:
			fragments.add(Fragment.instantiate(this, ConfiguracaoRecConta.class.getName()));
			break;
		}

		this.fAdapter = new FragmentAdapter(super.getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) super.findViewById(R.id.configuracao_fragmento);
		// Affectation de l'adapter au ViewPager
		pager.setAdapter(this.fAdapter);
	}

}
