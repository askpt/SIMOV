package dei.isep.lifechecker.adapter;

import java.util.List;

import dei.isep.lifechecker.configuracaoPacSelecao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class fragmentAdapter  extends FragmentPagerAdapter{
	
	private final List<Fragment> fragments;
	
	public fragmentAdapter(FragmentManager fm, List<Fragment> fragments)
	{
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragmento = null;
		switch(position)
		{
		case 1:
			Log.i("FRAGMENT", "11111111111111111111");
			break;
		case 2:
			Log.i("FRAGMENT", "1112222222222");
			break;
		case 3:
			Log.i("FRAGMENT", "33333333333333333333333333333333");
			break;
		case 5:
			fragmento = new configuracaoPacSelecao();
		}
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
	

}
