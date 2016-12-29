package clc.datacubesoftech.clcinstitute.volleyLibrary;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequest {
	 Context context;
	private RequestQueue requestQueue;

	public VolleyRequest(Context context){
		this.context=context;
	}

	public  RequestQueue uploadData() {
		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(context);
            return requestQueue;
		}
        return null;
    }

}
