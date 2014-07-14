package info.pf91.scripturable;

import android.support.v4.app.Fragment;

public class GooglePageActivity extends SingleFragmentActivity {

	@Override
	public Fragment createFragment() {
		
		return new GooglePageFragment();
	}

}
