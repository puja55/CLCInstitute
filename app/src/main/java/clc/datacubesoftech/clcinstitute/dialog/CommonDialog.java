package clc.datacubesoftech.clcinstitute.dialog;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import clc.datacubesoftech.clcinstitute.utils.ConnectionDetector;

public class CommonDialog {
	 Context context;
	 ErrorDialog dialogs;
	 ConnectionDetector cd;
	 ProgressBar progressBar;
	 RelativeLayout rl_timeslot;

	public CommonDialog(Context context){
		this.context=context;
		cd = new ConnectionDetector(context);
	}

}