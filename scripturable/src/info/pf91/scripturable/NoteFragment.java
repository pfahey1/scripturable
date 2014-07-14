package info.pf91.scripturable;

import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class NoteFragment extends Fragment {
	
	public static final String EXTRA_NOTE_ID = "scripturable.NOTE_ID";
	
	private Note mNote;
	private EditText mTitleField;
	private EditText mDetailField;
	private Button mSaveButton;
	
	Callbacks mCallbacks;

    public interface Callbacks {
        void onNoteUpdated(Note note);
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
        UUID noteId = (UUID)getArguments().getSerializable(EXTRA_NOTE_ID);
        mNote = NoteLab.get(getActivity()).getNote(noteId);
        setHasOptionsMenu(true);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) { 
		View v = inflater.inflate(R.layout.fragment_note, parent, false); 
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) { 
			if (NavUtils.getParentActivityName(getActivity()) != null) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true); 
			}
		}

 
		
		mTitleField = (EditText)v.findViewById(R.id.note_title);
	//	mNote.setTitle(toString());
		mTitleField.setText(mNote.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() { 
			@Override
			public void onTextChanged(CharSequence c, int start, int before, int count) { 	
				
				try{
					
				mNote.setTitle(c.toString());
				//mCallbacks.onNoteUpdated(mNote);
				}
				
				catch(Exception e){
					
					mNote.setTitle("Note Title");
					
				}
							
				} 
			@Override
			public void beforeTextChanged(CharSequence c, int start, int count, int after) { 
				// This space intentionally left blank 
				} 
			@Override
			public void afterTextChanged(Editable c) { 
				// This one too 
				} 
				});
		
		mDetailField = (EditText)v.findViewById(R.id.note_detail);
		//mNote.setDetail(toString());
		mDetailField.setText(mNote.getDetail());
		mDetailField.addTextChangedListener(new TextWatcher() { 
			
			@Override
			public void onTextChanged(CharSequence c, int start, int before, int count) { 
				
				try{
					
					mNote.setDetail(c.toString());
					//mCallbacks.onNoteUpdated(mNote);
					}
					
					catch(Exception e){
						
						mNote.setDetail("Note Details");
						
					}
				
				} 
			@Override
			public void beforeTextChanged(CharSequence c, int start, int count, int after) { 
				// This space intentionally left blank 
				} 
			@Override
			public void afterTextChanged(Editable c) { 
				// This one too 
				} 
				});
		
		mSaveButton = (Button)v.findViewById(R.id.note_save);
		mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	if (NavUtils.getParentActivityName(getActivity()) != null) { 
            		NavUtils.navigateUpFromSameTask(getActivity()); 
            		}
            	
            	else{
            		mCallbacks.onNoteUpdated(mNote);
            	}
            	
               
            }
        });
		
		Button reportButton = (Button)v.findViewById(R.id.note_report);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, mNote.getTitle());
                i.putExtra(Intent.EXTRA_TEXT, mNote.getDetail());
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            } 
        });
		
	
		return v; 
		}
	
	 public static NoteFragment newInstance(UUID noteId) {
	        Bundle args = new Bundle();
	        args.putSerializable(EXTRA_NOTE_ID, noteId);

	        NoteFragment fragment = new NoteFragment();
	        fragment.setArguments(args);

	        return fragment;
	    }
	
	
	@Override
	    public void onPause() {
	        super.onPause();
	        NoteLab.get(getActivity()).saveNotes();
	    }
	
	
	 
}
