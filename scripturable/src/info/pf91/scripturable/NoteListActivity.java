package info.pf91.scripturable;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class NoteListActivity extends SingleFragmentActivity implements NoteListFragment.Callbacks, NoteFragment.Callbacks {

	
	@Override
	protected Fragment createFragment() {
		
		return new NoteListFragment();
	}
	
	
@Override
public int getLayoutResId() { 
		return R.layout.activity_masterdetail; 
		}
	
	@Override
	public void onNoteSelected(Note note) { 
		
		if (findViewById(R.id.detailFragmentContainer) == null) {
            // start an instance of NotePagerActivity
            Intent i = new Intent(this, NotePagerActivity.class);
            i.putExtra(NoteFragment.EXTRA_NOTE_ID, note.getId());
            startActivity(i);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = NoteFragment.newInstance(note.getId());

            if (oldDetail != null) {
                ft.remove(oldDetail);
            } 

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
	}
	
	
	@Override
	public void onNoteUpdated(Note note) {
        FragmentManager fm = getSupportFragmentManager();
        NoteListFragment listFragment = (NoteListFragment)
                fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }

	
}
