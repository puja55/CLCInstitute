package clc.datacubesoftech.clcinstitute;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Downloader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clc.datacubesoftech.clcinstitute.adapter.SessionAdapter;
import clc.datacubesoftech.clcinstitute.dialog.ErrorDialog;
import clc.datacubesoftech.clcinstitute.model.BaseEntity;
import clc.datacubesoftech.clcinstitute.model.Session;
import clc.datacubesoftech.clcinstitute.sharedPreferences.LoginID;
import clc.datacubesoftech.clcinstitute.url.ServerUrl;
import clc.datacubesoftech.clcinstitute.utils.ConnectionDetector;
import clc.datacubesoftech.clcinstitute.volleyLibrary.VolleyRequest;

import static clc.datacubesoftech.clcinstitute.R.id.ll_root;

/**
 * Created by Pri on 11/9/2016.
 */
public class Login extends Activity implements Spinner.OnItemSelectedListener {
    Activity mActivity = Login.this;
    private RequestQueue requestQueue;
    String title = "Login";
    ConnectionDetector cd;
    LinearLayout ll_top;
    LoginID loginID;
    Context context;
    EditText ed_user, ed_pass;
    Spinner spr_session;
    Button bt_login;
    String loginId, passwordId, result;
    ErrorDialog dialog;
    private String spinner_result;
    String sessionId = null;
    int flag = 0;
    CharSequence text = "Incorrect login";
    ArrayList<Session> sessionList = new ArrayList<>();
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(this);
        requestQueue = new VolleyRequest(this).uploadData();
        context = this;
        setContentView(R.layout.login);
        loginID = LoginID.getInstance();
        sessionId = loginID.getSession_id(this);
        ll_top = (LinearLayout) findViewById(ll_root);
        ed_user = (EditText) findViewById(R.id.edt_user);
        ed_pass = (EditText) findViewById(R.id.edt_password);
        spr_session = (Spinner) findViewById(R.id.spr_session);
        bt_login = (Button) findViewById(R.id.btn_login);
        spr_session.setOnItemSelectedListener(this);

        gotoSession();

        Log.e("first time value", "" + sessionId);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            loginId = ed_user.getText().toString().trim();
            ed_user.setText(loginId);
            loginID.setLogin_id(loginId,mActivity);
            passwordId = ed_pass.getText().toString().trim();
                loginID.setSession_id(sessionId,mActivity);
                if (loginId.equalsIgnoreCase("") && passwordId.equalsIgnoreCase("") && sessionId.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_LONG).show();
                } else if (loginId.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_LONG).show();
                } else if (passwordId.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_LONG).show();
                }

                else {


                    gotoBookDtails();
                }
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void gotoBookDtails() {
        if (!cd.isConnectingToInternet()) {
            dialog.showNetworkDialog(getResources().getString(R.string.check_internet));
        } else {

            String serverURL = ServerUrl.serverBookDetails;
            if (!cd.isConnectingToInternet()) {
                dialog.showNetworkDialog(getResources().getString(R.string.check_internet));
            } else {

               serverURL = serverURL + "?login=" + loginId + "&password=" + passwordId + "&sessionId=" + sessionId;
                gotoBookDtails(serverURL);
            }
        }
    }


    public void gotoBookDtails(String url) {

        Log.e("login url", "" + url );

        requestQueue.getCache().clear();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url,
                new JSONObject(),
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {


                        Log.e("Responce", "" + response);
                          result = response.toString();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();

                        BaseEntity model = gson.fromJson(result, BaseEntity.class);


                        if (model.getStatus().equalsIgnoreCase("Success")) {
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            startActivity(intent);

                        } else {

                            Toast.makeText(mActivity,model.getMsg(), Toast.LENGTH_LONG).show();

                        }


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Responce", "" + error);
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
            }
        }) ;

        requestQueue.add(postRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        sessionId = sessionList.get(position).getSession_id().toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void gotoSession() {


        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getApplicationContext(), "Plz check your internet connection", Toast.LENGTH_LONG).show();
        } else {
            String serverURL = ServerUrl.spinner;

            gotoSession(serverURL);
        }
    }


    public void gotoSession(String url) {
        requestQueue.getCache().clear();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,

                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Responce", "" + response);
                        spinner_result = response.toString();

                        Log.e("Responce.........", "" + spinner_result);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();

                        BaseEntity model = gson.fromJson(spinner_result, BaseEntity.class);

                        if (model.getStatus().equalsIgnoreCase("Success")) {
                            sessionList = model.session;

                            SessionAdapter sessionAdapter = new SessionAdapter(context, sessionList);
                            spr_session.setAdapter(sessionAdapter);


                        } else {

                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Responce", "" + error);
                        /*dashboard.showCustomAlert();   */
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

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();


        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
