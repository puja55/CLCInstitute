package clc.datacubesoftech.clcinstitute.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import clc.datacubesoftech.clcinstitute.adapter.ResultAdapter;
import clc.datacubesoftech.clcinstitute.model.BaseEntity;
import clc.datacubesoftech.clcinstitute.model.Result;
import clc.datacubesoftech.clcinstitute.sharedPreferences.LoginID;
import clc.datacubesoftech.clcinstitute.url.ServerUrl;
import clc.datacubesoftech.clcinstitute.utils.ConnectionDetector;
import clc.datacubesoftech.clcinstitute.volleyLibrary.VolleyRequest;
import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by User on 09/11/2016.
 */

public class Fragent_Result extends Fragment {
    RecyclerView recyclerView;
    private RequestQueue requestQueue;
    ConnectionDetector cd;
    Context context;
    ArrayList<Result> resultList = new ArrayList<>();
    CircularProgressView progressBar;
    LinearLayout ll_gif,ll_top;
    CircleImageView result_profile;
    LoginID loginID;
    TextView result_username;
    String  result_profile1,result_stuname,result_result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        requestQueue = new VolleyRequest(getActivity()).uploadData();
        context = getActivity();
        loginID=LoginID.getInstance();
       result_profile1="http://clcresult.in//application//assets//upload//171031.jpg";
      /* result_stuname=loginID.getStdName(getActivity());*/
    }
    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.frag_result_recycler, container, false);
            recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
            progressBar = (CircularProgressView) v.findViewById(R.id.progressBar);
            progressBar.startAnimation();
            ll_gif = (LinearLayout) v.findViewById(R.id.ll_gif);
            ll_top = (LinearLayout) v.findViewById(R.id.ll_top);
            result_profile=(CircleImageView)v.findViewById(R.id.result_profileimg);
            result_username=(TextView)v.findViewById(R.id.result_username);
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(this, R.drawable.divider));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            result_username.setText("SAWAN KUMAR MEENA");
            Picasso.with(context)
                    .load(result_profile1).fit()
                    .placeholder(R.drawable.logo_clc).fit() // optional
                    .error(R.drawable.logo_clc).fit()
                    .into(result_profile);
            gotoResult();
            return  v;

        }
    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

            private  final int[] ATTRS = new int[]{android.R.attr.listDivider};
            private Drawable mDivider;
            public DividerItemDecoration(Fragent_Result context, int resId) {
                mDivider = ContextCompat.getDrawable(getActivity(), resId);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mDivider.getIntrinsicHeight();

                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }
    private void gotoResult() {
        ll_gif.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ll_top.setVisibility(View.GONE);

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getActivity(), "Plz check your internet connection", Toast.LENGTH_LONG).show();
        } else {
            String serverURL = ServerUrl.profile;

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
                        result_result = response.toString();

                        Log.e("Responce.........", "" + result_result);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();
                        ll_gif.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        ll_top.setVisibility(View.VISIBLE);

                        BaseEntity model = gson.fromJson(result_result, BaseEntity.class);

                        if (model.getStatus().equalsIgnoreCase("success")) {
                            resultList = model.results;
                            Log.e("RESULTLIST",""+resultList.toString());


                            ResultAdapter adapter_category_listresult_new = new ResultAdapter(context,resultList);
                            recyclerView.setAdapter(adapter_category_listresult_new);

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


