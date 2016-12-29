package clc.datacubesoftech.clcinstitute.fragment;

/**
 * Created by User on 13/11/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import clc.datacubesoftech.clcinstitute.model.BaseEntity;
import clc.datacubesoftech.clcinstitute.model.Profile;
import clc.datacubesoftech.clcinstitute.sharedPreferences.LoginID;
import clc.datacubesoftech.clcinstitute.utils.ConnectionDetector;
import clc.datacubesoftech.clcinstitute.volleyLibrary.VolleyRequest;
import de.hdodenhof.circleimageview.CircleImageView;
import static clc.datacubesoftech.clcinstitute.url.ServerUrl.profile;
/**
 * Created by Pri on 11/10/2016.
 */

public class Fragment_StudentProfile extends Fragment {
    private RequestQueue requestQueue;
    ConnectionDetector cd;
    String profile_result;
    Context context;
    ArrayList<Profile> profileList = new ArrayList<>();
    TextView tv_fathersname,tv_location,tv_course_name,tv_mob_no,tv_dob,tv_gender,tv_stdname,tv_formno;
    CircularProgressView progressBar;
    LinearLayout ll_gif,ll_top;
    CircleImageView imgprofile;
    LoginID loginID;
    String std_img = null;
    public Fragment_StudentProfile(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cd = new ConnectionDetector(getActivity());
        requestQueue = new VolleyRequest(getActivity()).uploadData();
        context = getActivity();
        View view=inflater.inflate(R.layout.student_profile_view, container, false);
        loginID = LoginID.getInstance();
        tv_fathersname=(TextView)view.findViewById(R.id.tv_fathersname);
        tv_course_name=(TextView)view.findViewById(R.id.tv_course_name);
        tv_dob=(TextView)view.findViewById(R.id.tv_dob);
        tv_gender=(TextView)view.findViewById(R.id.tv_gender);
        tv_location=(TextView)view.findViewById(R.id.tv_location);
        tv_mob_no=(TextView)view.findViewById(R.id.tv_mob_no);
        tv_stdname=(TextView)view.findViewById(R.id.student_name) ;
        tv_formno=(TextView)view.findViewById(R.id.tv_form_no) ;
        imgprofile = (CircleImageView)view.findViewById(R.id.user_profileimg);
        progressBar = (CircularProgressView) view.findViewById(R.id.progressBar);
        progressBar.startAnimation();
        ll_gif = (LinearLayout) view.findViewById(R.id.ll_gif);
        ll_top = (LinearLayout) view.findViewById(R.id.ll_top);

        gotoProfile();
        return view;
    }
    private void gotoProfile() {

        ll_gif.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ll_top.setVisibility(View.GONE);

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getActivity(), "Plz check your internet connection", Toast.LENGTH_LONG).show();
        } else {
            String serverURL = profile;

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
                        profile_result = response.toString();

                        Log.e("Responce.........", "" + profile_result);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();

                        ll_gif.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        ll_top.setVisibility(View.VISIBLE);



                        BaseEntity model = gson.fromJson(profile_result, BaseEntity.class);

                        if (model.getStatus().equalsIgnoreCase("success")) {
                            profileList = model.student;

                            tv_fathersname.setText(profileList.get(0).getStd_FatherName().toString());
                            tv_gender.setText(profileList.get(0).getStd_Gender().toString());
                            tv_mob_no.setText(profileList.get(0).getStd_mobile1().toString());
                            tv_location.setText("Village:"+profileList.get(0).getStd_village().toString()+" "+
                                    "Post:"+profileList.get(0).getStd_post().toString()+" "+"Tehsil:"+
                                    profileList.get(0).getStd_Tehsil().toString()+" "+"District:"+
                                    profileList.get(0).getStd_City().toString()
                                    +" "+"State:"+profileList.get(0).getStd_State().toString()+" "+"Pin Code:"+
                                    profileList.get(0).getStd_Zip().toString());
                            tv_dob.setText(profileList.get(0).getStd_DateofBirth().toString());
                            tv_course_name.setText(profileList.get(0).getStd_courseId().toString());
                            String stdName=(profileList.get(0).getStd_FirstName().toString());
                            tv_stdname.setText(stdName);
                            loginID.setStdName(stdName,getActivity());
                            tv_formno.setText(profileList.get(0).getForm_no().toString());
                            String imgURL =profileList.get(0).getStd_image().toString().trim();

                            loginID.setStdImage(imgURL,context);

                            Log.e("Studentimg",""+loginID);
                            Picasso.with(context)
                                    .load(imgURL).fit()
                                    .placeholder(R.drawable.logo_clc).fit() // optional
                                    .error(R.drawable.logo_clc).fit()
                                    .into(imgprofile);

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
