package com.onbond.task;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class Task extends CordovaPlugin {
    public static final String ACTION_ADD_CALENDAR_ENTRY = "addTaskEntry";
	private String selected_email;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (ACTION_ADD_CALENDAR_ENTRY.equals(action)) { 
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,Email.CONTENT_URI);
		    	    contactPickerIntent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE );
		    	    this.cordova.getActivity().startActivityForResult(contactPickerIntent, 1009);
		    	    callbackContext.success();
		    	    return true;
			}

			callbackContext.error("Invalid action");
			return false;
			} 
		catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
			}
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        if (resultCode == 200) {
            switch (requestCode) 
            {
            case 1009:
                Cursor cursor = null;
                try {
                	Uri result = data.getData();
                    Log.v("DEBUG_TAG", "Got a contact result: " + result.toString());

                    String id = result.getLastPathSegment();
                    cursor = getContentResolver().query(Email.CONTENT_URI,  null, BaseColumns._ID + "=" + id, null, null);
                    int emailIdx = cursor.getColumnIndex(Email.DATA);
                    if (cursor.moveToFirst()) {
                    	selected_email = cursor.getString(emailIdx);
                        Log.v("DEBUG_TAG", "Got email: " + selected_email);
                    } 
                    
                    else {
                        Log.w("DEBUG_TAG", "No results");
                    }
                } catch (Exception e) {
                    Log.e("DEBUG_TAG", "Failed to get email data", e);
                } finally {
                    if (cursor != null) {
                        cursor.close();
                }
                }
                break;
            }

        } else {
            Log.w("DEBUG_TAG", "Warning: activity result not ok");
        }   
    }
    
    private ContentResolver getContentResolver() {
		// TODO Auto-generated method stub
		return null;
	}


    public String getEmailSelected()
    {
    	return selected_email;
    }

}
