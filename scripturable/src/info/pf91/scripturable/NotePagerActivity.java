package info.pf91.scripturable;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


public class NotePagerActivity extends FragmentActivity implements NoteFragment.Callbacks {
	
	private ViewPager mViewPager; 
	private ArrayList <Note> mNotes;
	
	@Override
	public void onNoteUpdated(Note note){}

	@Override public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		
		mViewPager = new ViewPager(this); 
		mViewPager.setId(R.id.viewPager); 
		setContentView(mViewPager);
		
		 mNotes = NoteLab.get( this).getNotes(); 
	        
	        FragmentManager fm = getSupportFragmentManager(); 
	        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) { 
	        	
	        	
				@Override 
	        	public int getCount() { 
	        		return mNotes.size(); 
	        		}
	
	        	
				@Override
	        	public Fragment getItem(int pos) { 
	        		Note note = mNotes.get(pos); 
	        		return NoteFragment.newInstance(note.getId()); 
	        		} 
	        	}); 
	        
	        // Changes action bar titles for the current viewed note
	        
	        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { 
	        	@Override
				public void onPageScrollStateChanged(int state) { } 
	        	@Override
				public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) { } 
	        	@Override
				public void onPageSelected( int pos) { 
	        		Note crime = mNotes.get( pos); 
	        		if (crime.getTitle() != null) { 
	        			setTitle(crime.getTitle()); 
	        			} 
	        		} 
	        	});

	 
	        
	        UUID noteId = (UUID)getIntent().getSerializableExtra(NoteFragment.EXTRA_NOTE_ID);
	        for (int i = 0; i < mNotes.size(); i++) {
	            if (mNotes.get(i).getId().equals(noteId)) {
	                mViewPager.setCurrentItem(i);
	                break;
	            } 
	        }
	        }
	 
		}




