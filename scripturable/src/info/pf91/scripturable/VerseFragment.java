package info.pf91.scripturable;

import java.util.UUID;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class VerseFragment extends Fragment {
	
	private TextView mDefinition;
	private TextView mWord;
	
	
	public static final String EXTRA_VERSE_ID = "scripturable.VERSE_ID";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       //UUID verseId = (UUID)getArguments().getSerializable(EXTRA_VERSE_ID);
      //mVerse = VerseLab.get(getActivity()).getVerse(verseId);
        setHasOptionsMenu(true);
       
            
    }
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_verse, parent, false);
        
		 Uri uri = getActivity().getIntent().getData();
	        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

	        if (cursor == null) {
	        	getActivity().finish();
	        } 
	        
	        else {
	            cursor.moveToFirst();

	            mWord = (TextView) layout.findViewById(R.id.word);
	            mDefinition = (TextView) layout.findViewById(R.id.definition);

	            int wIndex = cursor.getColumnIndexOrThrow(BibleDatabase.KEY_WORD);
	            int dIndex = cursor.getColumnIndexOrThrow(BibleDatabase.KEY_DEFINITION);
	            
	            String verse = cursor.getString(wIndex);
	            String definition = cursor.getString(dIndex);
	            
	            mWord.setText(verse);
	            mDefinition.setText(definition);   
	 
	        }
	        
	        
	        /*
	        Button verseSaveButton = (Button)layout.findViewById(R.id.verse_save);
	        verseSaveButton.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
		            	
		            	
		            	
		            	
		                Intent i = new Intent(getActivity(), VerseListActivity.class);
		                i.setType("text/plain");
		               // i.putExtra("key", mWord.getText().toString());
		               // i.putExtra("key2", mDefinition.getText().toString());
		                i.putExtra(Intent.EXTRA_SUBJECT, mVerse.getVerse().toString());
		                i.putExtra(Intent.EXTRA_TEXT, mVerse.getDefinition().toString());
		               // i = Intent.createChooser(i, getString(R.string.send_verse));
		                startActivity(i);
		            } 
		            	
		            }
		        });
	        
	*/
	        
	        Button verseButton = (Button)layout.findViewById(R.id.verse_report);
	       verseButton.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	                Intent i = new Intent(Intent.ACTION_SEND);
	                i.setType("text/plain");
	                i.putExtra(Intent.EXTRA_SUBJECT, mWord.getText().toString());
	                i.putExtra(Intent.EXTRA_TEXT, mDefinition.getText().toString());
	                i = Intent.createChooser(i, getString(R.string.send_verse));
	                startActivity(i);
	            } 
	        });
			
		
   
        return layout; 
        
	}
	
	 public static VerseFragment newInstance(UUID verseId) {
	        Bundle args = new Bundle();
	        args.putSerializable(EXTRA_VERSE_ID, verseId);

	        VerseFragment fragment = new VerseFragment();
	        fragment.setArguments(args);

	        return fragment;
	    }
	 
	 @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) 
	    @Override
	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		 inflater.inflate(R.menu.options_menu_verse_activity, menu);
		    super.onCreateOptionsMenu(menu,inflater);

	        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
	            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
	            searchView.setIconifiedByDefault(false);
	        }
	        
	       
	    }
	 
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        
	        case R.id.new_note:
	        	   Note note = new Note(); 
	            	NoteLab.get(getActivity().getApplicationContext()).addNote(note); 
	            	Intent i = new Intent(getActivity(), NotePagerActivity.class); 
	            	i.putExtra(NoteFragment.EXTRA_NOTE_ID, note.getId()); 
	            	startActivityForResult(i, 0); 
	            	break;
	            	
	            case R.id.saved_notes:
	            	Intent s = new Intent(getActivity(), NoteListActivity.class); 
		               startActivity(s); 
		               break;
	    
	            case R.id.font1:
	            	mWord.setTextSize(15);
	            	mDefinition.setTextSize(8);
	            	return true;
	            	
	            case R.id.font2:
	            	mWord.setTextSize(35);
	            	mDefinition.setTextSize(28);
	            	return true;
	            	
	            case R.id.font3:
	            	mWord.setTextSize(25);
	            	mDefinition.setTextSize(18);
	            	return true;
	            	
	            case R.id.browser:
	            	Intent w = new Intent(getActivity(), GooglePageActivity.class); 
	               startActivity(w);     
	               break; 
	            	     
	            default:
	                return false;
	        }
			return false;
	 }
	
}
