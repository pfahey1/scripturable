package info.pf91.scripturable;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Note {
	
	private UUID mId; 
	private String mTitle; 
	private String mDetail;

	private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DETAIL = "detail";

  
	public Note() { 
		// Generate unique identifier 
		mId = UUID.randomUUID(); 
		
		}
	
	 public Note(JSONObject json) throws JSONException {
	        mId = UUID.fromString(json.getString(JSON_ID));
	        mTitle = json.getString(JSON_TITLE);
	        mDetail = json.getString(JSON_DETAIL);	        
	    }
	
	 public JSONObject toJSON() throws JSONException {
	        JSONObject json = new JSONObject();
	        json.put(JSON_ID, mId.toString());
	        json.put(JSON_TITLE, mTitle);
	        json.put(JSON_DETAIL, mDetail); 
	        return json;
	    }

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public UUID getId() {
		return mId;
	}

	public String getDetail() {
		return mDetail;
	}

	public void setDetail(String mDetail) {
		this.mDetail = mDetail;
	}
	
	@Override public String toString() { 
		return mTitle; 
		}
	
}

	 


