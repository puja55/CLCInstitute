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
import android.widget.TableRow;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.model.BaseEntity;
import clc.datacubesoftech.clcinstitute.model.Result;
import clc.datacubesoftech.clcinstitute.url.ServerUrl;
import clc.datacubesoftech.clcinstitute.utils.ConnectionDetector;
import clc.datacubesoftech.clcinstitute.volleyLibrary.VolleyRequest;
/**
 * Created by User on 13/11/2016.
 */
public class Fragment_Row_Result extends Fragment {
    private RequestQueue requestQueue;
    ConnectionDetector cd;
    String sub_result,pos;
    Context context;
    ArrayList<Result> subjectList = new ArrayList<>();
    TableRow math_omrow,math_mmrow,bot_omrow,bot_mmrow,zoo_omrow,zoo_mmrow;
    TextView tv_negmarks,tv_phy_mm,tv_phy_om,tv_che_mm,tv_che_om,tv_math_mm,tv_math_om,
            tv_zoo_mm,tv_zoo_om,tv_bot_mm,tv_bot_om,tv_per
            ,totalom,totalmm,tv_rank;
    LinearLayout ll_gif,ll_top;
    CircularProgressView progressBar;
    public Fragment_Row_Result(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cd = new ConnectionDetector(getActivity());
        requestQueue = new VolleyRequest(getActivity()).uploadData();
        context = getActivity();
        View view=inflater.inflate(R.layout.subject_result,container,false);
        tv_negmarks=(TextView)view.findViewById(R.id.tv_negmarks);
        tv_phy_mm=(TextView)view.findViewById(R.id.tv_tv_phymm);
        tv_phy_om=(TextView)view.findViewById(R.id.tv_phyom);
        tv_che_mm=(TextView)view.findViewById(R.id.tv_chemm);
        tv_che_om=(TextView)view.findViewById(R.id.tv_cheom);
        tv_math_mm=(TextView)view.findViewById(R.id.tv_mathmm);
        tv_math_om=(TextView)view.findViewById(R.id.tv_mathom);
        tv_bot_mm=(TextView)view.findViewById(R.id.tv_botmm);
        tv_bot_om=(TextView)view.findViewById(R.id.tv_botom);
        tv_zoo_mm=(TextView)view.findViewById(R.id.tv_zoomm);
        tv_zoo_om=(TextView)view.findViewById(R.id.tv_zooom);
        tv_per=(TextView)view.findViewById(R.id.tv_per);
        totalmm=(TextView)view.findViewById(R.id.tv_totmm);
        totalom=(TextView)view.findViewById(R.id.tv_totom);
        tv_rank=(TextView)view.findViewById(R.id.tv_rank);
        math_omrow=(TableRow)view.findViewById(R.id.tabl_math_OM);
        math_mmrow=(TableRow)view.findViewById(R.id.tabl_math_MM);
        bot_omrow=(TableRow)view.findViewById(R.id.tabl_bot_OM);
        bot_mmrow=(TableRow)view.findViewById(R.id.tabl_bot_MM);
        zoo_omrow=(TableRow)view.findViewById(R.id.tabl_zoo_OM);
        zoo_mmrow=(TableRow)view.findViewById(R.id.tabl_ZOO_MM);
        ll_gif = (LinearLayout) view.findViewById(R.id.ll_gif);
        ll_top = (LinearLayout) view.findViewById(R.id.ll_top);

        progressBar = (CircularProgressView) view.findViewById(R.id.progressBar);
        progressBar.startAnimation();

        Bundle bundle=this.getArguments();
        pos=bundle.getString("pos");
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
            String serverURL = ServerUrl.profile;

            gotoSession(serverURL);
        }
    }


    public void gotoSession(final String url) {
        requestQueue.getCache().clear();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url,

                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Responce", "" + response);
                        sub_result = response.toString();

                        Log.e("Responce.........", "" + sub_result);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();
                        ll_gif.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        ll_top.setVisibility(View.VISIBLE);

                        BaseEntity model = gson.fromJson(sub_result, BaseEntity.class);

                        if (model.getStatus().equalsIgnoreCase("success")) {
                            subjectList = model.results;
                            JSONObject textResult ;
                            try {
                                JSONArray arrProducts = response.getJSONArray("results");
                                for(int i=0; i<arrProducts.length(); i++) {
                                    if (Integer.parseInt(pos)==i) {
                                        JSONObject details = (JSONObject) arrProducts.get(i);

                                        textResult = details.getJSONObject("details");
                                        String Neg_Mark = textResult.getString("Neg_Mark");
                                        String phy_MM = textResult.getString("phy_MM");
                                        String phy_OM = textResult.getString("phy_OM");
                                        String che_mm = textResult.getString("che_mm");
                                        String che_om = textResult.getString("che_om");
                                        String bot_mm = textResult.getString("bot_mm");
                                        String bot_om = textResult.getString("bot_om");
                                        String totalMarks = textResult.getString("totalMarks");
                                        String totalOM = textResult.getString("totalOM");
                                        String per = textResult.getString("per");
                                        String rank = textResult.getString("rank");

                                        tv_negmarks.setText(Neg_Mark);
                                        tv_phy_mm.setText(phy_MM);
                                        tv_phy_om.setText(phy_OM);
                                        tv_che_mm.setText(che_mm);
                                        tv_che_om.setText(che_om);
                                        tv_rank.setText(rank);
                                        tv_per.setText(per);
                                        totalom.setText(totalOM);
                                        totalmm.setText(totalMarks);

                                        if (bot_mm.equalsIgnoreCase("Not Include")){
                                            bot_mmrow.setVisibility(View.GONE);
                                            bot_omrow.setVisibility(View.GONE);
                                            zoo_mmrow.setVisibility(View.GONE);
                                            zoo_omrow.setVisibility(View.GONE);
                                            math_mmrow.setVisibility(View.VISIBLE);
                                            math_omrow.setVisibility(View.VISIBLE);
                                            String math_mm=textResult.getString("math_mm");
                                            String math_om=textResult.getString("math_om");
                                            tv_math_mm.setText(math_mm);
                                            tv_math_om.setText(math_om);
                                        }
                                        else{
                                            math_mmrow.setVisibility(View.GONE);
                                            math_omrow.setVisibility(View.GONE);
                                            bot_mmrow.setVisibility(View.VISIBLE);
                                            bot_omrow.setVisibility(View.VISIBLE);
                                            zoo_mmrow.setVisibility(View.VISIBLE);
                                            zoo_omrow.setVisibility(View.VISIBLE);
                                            String zoo_mm = textResult.getString("zoo_mm");
                                            String zoo_om = textResult.getString("zoo_om");
                                            tv_zoo_mm.setText(zoo_mm);
                                            tv_zoo_om.setText(zoo_om);
                                            tv_bot_mm.setText(bot_mm);
                                            tv_bot_om.setText(bot_om);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
