package dei.isep.lifechecker.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class FragmentAdapter  extends FragmentPagerAdapter{
	
	private final List<Fragment> fragments;
	
	public FragmentAdapter(FragmentManager fm, List<Fragment> fragments)
	{
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		switch(position)
		{
		case 1:
			Log.i("FRAGMENT", "11111111111111111111");
			break;
		case 2:
			Log.i("FRAGMENT", "1112222222222");
			break;
		}
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
	

}
