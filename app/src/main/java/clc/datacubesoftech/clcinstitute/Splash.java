package clc.datacubesoftech.clcinstitute;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import clc.datacubesoftech.clcinstitute.sharedPreferences.LoginID;


/**
 * Created by vamsikrishna on 12-Feb-15.
 */
public class Splash extends Activity {

    Activity mActivity = Splash.this;

    String login_id;
    LoginID loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        loginID = LoginID.getInstance();
        login_id = loginID.getLogin_id(this);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    if (loginID.getLogin_id(mActivity).equalsIgnoreCase(""))
                    {
                        Intent intent = new Intent(Splash.this, Login.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}