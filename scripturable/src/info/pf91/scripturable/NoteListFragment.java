package info.pf91.scripturable;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class NoteListFragment extends ListFragment {
	
	private static final String TAG = "NoteListFragment";
	
    private ArrayList<Note> mNotes;
    
    private Callbacks mCallbacks;
    private ListView mListView;
   

    public interface Callbacks {
        void onNoteSelected(Note note);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.notes_title);
        mNotes = NoteLab.get(getActivity()).getNotes();
        NoteAdapter adapter = new NoteAdapter(mNotes);
        setListAdapter(adapter);  
        updateUI();
    }
    
   
	@TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        
        mListView = (ListView)v.findViewById(android.R.id.list);
        
        
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(mListView);
        } 
        else {
        	mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
            
        	mListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
                
                @Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.note_list_item_context, menu);
                    return true;
                }
            
                @Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
                        long id, boolean checked) {
                	
                }
            
                @Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_note:
                            NoteAdapter adapter = (NoteAdapter)getListAdapter();
                            NoteLab crimeLab = NoteLab.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    crimeLab.deleteNote(adapter.getItem(i));
                                }
                            }
                            mode.finish(); 
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }
          
                @Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                	
                    return false;
                }
                
                @Override
				public void onDestroyActionMode(ActionMode mode) {
                	updateUI();

                }
            });
        }
    
    
        return v;
    }
	

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) { 
        Note c = (Note)(getListAdapter()).getItem(position);
          
        mCallbacks.onNoteSelected(c);
      
    }

    public class NoteAdapter extends ArrayAdapter<Note> {
        public NoteAdapter(ArrayList<Note> notes) {
            super(getActivity(), android.R.layout.simple_list_item_1, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_note, null);
            }

            // configure the view for this Note
            Note c = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.note_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
          TextView detailTextView =
                (TextView)convertView.findViewById(R.id.note_list_item_detailsTextView);
           detailTextView.setText(c.getDetail().toString());
            
            return convertView;
        }
    }
    
    
	@Override 
    public void onResume(){ 
    	super.onResume(); 
    	((NoteAdapter)getListAdapter()).notifyDataSetChanged(); 
    	}
	

    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.note_list_item_context, menu);
         
    }
    
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        NoteAdapter adapter = (NoteAdapter)getListAdapter();
        Note note = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_note:
                NoteLab.get(getActivity()).deleteNote(note);
                adapter.notifyDataSetChanged();
                
                return true;
         
        }
        
        return super.onContextItemSelected(item);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
    	switch (item.getItemId()) {
          
            case R.id.notes:
            	Note note = new Note(); 
            	NoteLab.get(getActivity()).addNote(note); 
            	((NoteAdapter) getListAdapter()).notifyDataSetChanged(); 
            	mCallbacks.onNoteSelected(note);
            	return true;   
            default:
                return false;
        }
    
   }
    
    public void updateUI() {
        ((NoteAdapter)getListAdapter()).notifyDataSetChanged();
     
    }
    
}




