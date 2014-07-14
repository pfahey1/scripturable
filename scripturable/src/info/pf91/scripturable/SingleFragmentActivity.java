package info.pf91.scripturable;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public abstract class SingleFragmentActivity extends FragmentActivity  {
	
	 protected abstract Fragment createFragment();
	 
	 public int getLayoutResId(){
		 return R.layout.activity_fragment;
	 }

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(getLayoutResId());
	        FragmentManager manager = getSupportFragmentManager();
	        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer); 
	        
	    	
	        if (fragment == null) {
	            fragment = createFragment();
	            manager.beginTransaction()
	                .add(R.id.fragmentContainer, fragment)
	                .commit();
	        }
	               
	    }
	    
	    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.options_menu, menu);

	        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	            searchView.setIconifiedByDefault(false);
	        }
	        
	        return true;
	    }
	    
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.search:
	                onSearchRequested();
	                break;
	             
	            case R.id.notes:
	           
	            	//((NoteAdapter) getListAdapter()).notifyDataSetChanged(); 
	            	//mCallbacks.onNoteSelected(note);  	
	            	break;   
	            	
	           case android.R.id.home: 
	            	if (NavUtils.getParentActivityName(this) != null) { 
	            		NavUtils.navigateUpFromSameTask(this); 
	            		}
	            	
	            	break;
	            	
	           case R.id.new_note:
	        	   Note note = new Note(); 
	            	NoteLab.get(this).addNote(note); 
	            	Intent i = new Intent(this, NotePagerActivity.class); 
	            	i.putExtra(NoteFragment.EXTRA_NOTE_ID, note.getId()); 
	            	startActivityForResult(i, 0); 
	            	break;
	            	
	            case R.id.saved_notes:
	            	Intent s = new Intent(this, NoteListActivity.class); 
		               startActivity(s); 
		               break;
	            	
	            case R.id.browser:
	            	Intent w = new Intent(this, GooglePageActivity.class); 
	               startActivity(w);     
	               break; 
	            	
	            default:
	                return false;
	        }
			return false;
	    }
	    
	

}
