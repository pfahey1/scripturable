package info.pf91.scripturable;

import android.support.v4.app.Fragment;

public class SearchableActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new SearchableFragment();
	}

}
