package clc.datacubesoftech.clcinstitute.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.utils.ConnectionDetector;

public class ErrorDialog {
    Context context;
    Dialog dialogFeedback;
    EditText edt_name,edt_email,edt_mobile,edt_comment;
    String result;
    ConnectionDetector cd;
    private RequestQueue requestQueue;


	public ErrorDialog(Context context){
		this.context=context;
	}


    public void showDialog(String msg) {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_error);

        TextView tv_Msg = (TextView)dialog.findViewById(R.id.tv_Msg);
        tv_Msg.setText(""+msg);

        dialog.findViewById(R.id.btn_Ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });}


    public void showNetworkDialog(String msg) {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_error);

        TextView tv_Msg = (TextView)dialog.findViewById(R.id.tv_Msg);
        tv_Msg.setText(""+msg);

        dialog.findViewById(R.id.btn_Ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDialogExit(String msg) {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_exit);
        TextView tv_Msg = (TextView)dialog.findViewById(R.id.tv_Msg);
        tv_Msg.setText("" + msg);

        dialog.findViewById(R.id.btn_Ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				((Activity)context).finish();
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				((Activity)context).finish(); // destroy current activity..*//*
				intent.addCategory(Intent.CATEGORY_HOME);
				((Activity)context).startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());

                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

     /*::::::::::::::font function::::::::::::::::::::::::::::::::::::::::::::::::::::
        ViewGroup root = (ViewGroup)dialog.findViewById(R.id.rl_root);
        fontType=new FontType(context,root);*/

        dialog.show();
    }

}
