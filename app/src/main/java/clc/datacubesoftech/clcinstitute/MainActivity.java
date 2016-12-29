package clc.datacubesoftech.clcinstitute;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

import clc.datacubesoftech.clcinstitute.dialog.ErrorDialog;
import clc.datacubesoftech.clcinstitute.fragment.CallingStatus;
import clc.datacubesoftech.clcinstitute.fragment.Fragent_Result;
import clc.datacubesoftech.clcinstitute.fragment.Fragment_Calendar;
import clc.datacubesoftech.clcinstitute.fragment.Student_Profile_Home;


public class MainActivity extends AppCompatActivity {
    FrameLayout fm;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    FloatingActionButton fab;
    ImageView img_home,img_result,img_calender,img_call;
    ErrorDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = (FrameLayout) findViewById(R.id.frame);
        dialog = new ErrorDialog(this);
        Student_Profile_Home student_new = new Student_Profile_Home();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame, student_new, "home").commit();
        img_home = (ImageView) findViewById(R.id.img_home);
        img_result = (ImageView) findViewById(R.id.img_result);
        img_calender = (ImageView) findViewById(R.id.img_calender);
        img_call = (ImageView) findViewById(R.id.img_call);

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student_Profile_Home student_new = new Student_Profile_Home();
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame, student_new, "home").addToBackStack("home").commit();

            }
        });

        img_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragent_Result fragmentResult = new Fragent_Result();
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame, fragmentResult, "result").addToBackStack("result").commit();

            }
        });

        img_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment_Calendar fragment_calendar = new Fragment_Calendar();
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame, fragment_calendar, "calendar").addToBackStack("calendar").commit();

            }
        });

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallingStatus callingStatus = new CallingStatus();
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame, callingStatus, "home").addToBackStack("home").commit();

            }
        });

    }
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count <= 0) {
            dialog.showDialogExit(getResources().getString(R.string.exit));
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}

