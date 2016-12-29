package clc.datacubesoftech.clcinstitute.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class LoginID {
	private static LoginID instance;
	
	static String packageName="clc.datacubesoftech.clcinstitute";
	String login_id,session_id,stdImage,stdName;
	private LoginID(){
		
	}
	public static LoginID getInstance()
	{
		if(instance==null)
		{
			instance=new LoginID();
		}
		
		return instance;
	}

	public String getLogin_id(Context context) {
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		return prfs.getString("login_id", "");
	}

	public void setLogin_id(String loginId, Context context) {
		this.login_id = loginId;
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		Editor editor=prfs.edit();
		editor.putString("login_id", login_id);
		editor.commit();
	}
	public static void removeLoginId(Context context) {
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		SharedPreferences.Editor editor=prfs.edit();
		editor.remove("login_id");
		//editor.clear();
		editor.commit();
	}
	public String getSession_id(Context context) {
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		return prfs.getString("session_id", "");
	}

	public void setSession_id(String sessionId, Context context) {
		this.session_id = sessionId;
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		Editor editor=prfs.edit();
		editor.putString("sessiion_id", session_id);
		editor.commit();
	}
	public String getStdImage(Context context) {
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		return prfs.getString("stdImage", "");
	}

	public void setStdImage(String stdImage, Context context) {
		this.stdImage = stdImage;
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		Editor editor=prfs.edit();
		editor.putString("stdImage", stdImage);
		editor.commit();
	}

	public String getStdName(Context context) {
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		return prfs.getString("stdName", "");
	}

	public void setStdName(String stdName, Context context) {
		this.stdName = stdName;
		SharedPreferences prfs = context.getSharedPreferences(packageName, context.MODE_PRIVATE);
		Editor editor=prfs.edit();
		editor.putString("stdName", stdName);
		editor.commit();
	}
}
