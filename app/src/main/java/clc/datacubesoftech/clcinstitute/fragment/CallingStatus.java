package clc.datacubesoftech.clcinstitute.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.adapter.CallingStatus_Adapter;
import clc.datacubesoftech.clcinstitute.model.BaseEntity;
import clc.datacubesoftech.clcinstitute.model.Calling;
import clc.datacubesoftech.clcinstitute.sharedPreferences.LoginID;
import clc.datacubesoftech.clcinstitute.url.ServerUrl;
import clc.datacubesoftech.clcinstitute.utils.ConnectionDetector;
import clc.datacubesoftech.clcinstitute.volleyLibrary.VolleyRequest;
import de.hdodenhof.circleimageview.CircleImageView;

public class CallingStatus extends Fragment {

    private ListView listview;
    private RequestQueue requestQueue;
    LoginID loginID;
    String stdName,std_img,calling_result;
    CircleImageView img;
    TextView std_Name;
    ConnectionDetector cd;
    Context context;
    ArrayList<Calling> callingList = new ArrayList<>();
    CircularProgressView progressBar;
    LinearLayout ll_gif,ll_top;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        requestQueue = new VolleyRequest(getActivity()).uploadData();
        context=getActivity();
        loginID= LoginID.getInstance();
        std_img = "http://clcresult.in//application//assets//upload//171031.jpg";
//        stdName=loginID.getStdName(context);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.calling_status, container, false);
        std_Name=(TextView)view.findViewById(R.id.student_name);
        img=(CircleImageView) view.findViewById(R.id.user_profileimg);

        std_Name.setText("SAWAN KUMAR MEENA");
        Picasso.with(getActivity())
                .load(std_img).fit()
                .placeholder(R.drawable.logo_clc).fit() // optional
                .error(R.drawable.logo_clc).fit()
                .into(img);
        listview=(ListView) view.findViewById(R.id.list_callingstatus);
        progressBar = (CircularProgressView) view.findViewById(R.id.progressBar);
        progressBar.startAnimation();
        ll_gif = (LinearLayout) view.findViewById(R.id.ll_gif);
        ll_top = (LinearLayout) view.findViewById(R.id.ll_top);
        gotoCallingStatus();
        return view;

    }
    private void gotoCallingStatus() {
        ll_gif.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ll_top.setVisibility(View.GONE);

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getActivity(), "Plz check your internet connection", Toast.LENGTH_LONG).show();
        } else {
            String serverURL = ServerUrl.callingStatus;

            gotoSession(serverURL);
        }
    }
    public void gotoSession(String url) {
        requestQueue.getCache().clear();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url,

                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Responce", "" + response);
                        calling_result = response.toString();

                        Log.e("Responce.........", "" + calling_result);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();
                        ll_gif.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        ll_top.setVisibility(View.VISIBLE);

                        BaseEntity model = gson.fromJson(calling_result, BaseEntity.class);

                        if (model.getStatus().equalsIgnoreCase("success")) {
                            callingList = model.callingFeedback;

                            CallingStatus_Adapter callingStatus_adapter = new CallingStatus_Adapter(context, callingList);
                            listview.setAdapter(callingStatus_adapter);
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Responce", "" + error);
                    }
                }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        int socketTimeout = 500000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);

        requestQueue.add(postRequest);
    }
}
