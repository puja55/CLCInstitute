package clc.datacubesoftech.clcinstitute.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.adapter.CalendarAdapter;
import clc.datacubesoftech.clcinstitute.model.BaseEntity;
import clc.datacubesoftech.clcinstitute.model.CalendarModl;
import clc.datacubesoftech.clcinstitute.utils.ConnectionDetector;
import clc.datacubesoftech.clcinstitute.volleyLibrary.VolleyRequest;
import static clc.datacubesoftech.clcinstitute.url.ServerUrl.attendance;
/**
 * Created by User on 14/11/2016.
 */

public class Fragment_Calendar extends Fragment implements AdapterView.OnItemClickListener {
    public Calendar month, itemmonth;/* calendar instances.*/
    public CalendarAdapter dateadapter;// adapter instance
    public Handler handler;// for grabbing some event values for showing the dot marker.
    public ArrayList<String> items; // container to store calendar items which
    static int thisMonth;
    int titleId;
    private RequestQueue requestQueue;
    String title = "MainActivity";
    ConnectionDetector cd;
    Context context;
    private String attendance_result;
    String sessionId = null;
    int flag = 0;
    ArrayList<CalendarModl> attendanceList = new ArrayList<>();
    GridView gridview;
    CircularProgressView progressBar;
    LinearLayout ll_gif,ll_top;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cd = new ConnectionDetector(getActivity());
        requestQueue = new VolleyRequest (getActivity()).uploadData();
        context = getActivity();
        View view=inflater.inflate(R.layout.calendar_layout,container,false);
        gridview=(GridView)view.findViewById(R.id.gridview);
        gridview.setOnItemClickListener(this);
        dateadapter=new CalendarAdapter();
        month = Calendar.getInstance();
        itemmonth = (Calendar) month.clone();
        items = new ArrayList<String>();
        handler = new Handler();
        handler.post(calendarUpdater);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        RelativeLayout previous = (RelativeLayout)view.findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();
            }
        });
        progressBar = (CircularProgressView) view.findViewById(R.id.progressBar);
        progressBar.startAnimation();
        ll_gif = (LinearLayout) view.findViewById(R.id.ll_gif);
        ll_top = (LinearLayout) view.findViewById(R.id.ll_root);
        gotoSession();
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    private void gotoSession() {
        ll_gif.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ll_top.setVisibility(View.GONE);

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getContext(), "Plz check your internet connection", Toast.LENGTH_LONG).show();
        } else {
            String serverURL = attendance;

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
                        attendance_result = response.toString();

                        Log.e("Responce.........", "" + attendance_result);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();

                        ll_gif.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        ll_top.setVisibility(View.VISIBLE);

                        BaseEntity baseEntity = gson.fromJson(attendance_result, BaseEntity.class);
                        if (baseEntity.getStatus().equalsIgnoreCase("success")) {
                            ArrayList<BaseEntity.Attendance> responceList = null;

                            ArrayList<CalendarModl> newsModels = new ArrayList<>();

                            for (int i = 0; i < baseEntity.getAttendance().size(); i++) {
                                CalendarModl newsModelCategory = new CalendarModl();
                                newsModelCategory.setAttendance(baseEntity.getAttendance().get(i));
                                newsModelCategory.setCategory(true);
                                newsModels.add(newsModelCategory);
                                for (int j = 0; j < baseEntity.getAttendance().get(i).getAttendance1().size(); j++) {
                                    CalendarModl newsModelData = new CalendarModl();
                                    newsModelData.setAttendance1(baseEntity.getAttendance().get(i).getAttendance1().get(j));
                                    newsModelData.setCategory(false);
                                    newsModels.add(newsModelData);
                                }
                            }


                            dateadapter = new CalendarAdapter(context, (GregorianCalendar) month, newsModels);
                            gridview.setAdapter(dateadapter);

                            if (baseEntity != null) {
                                responceList = baseEntity.getAttendance();
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
    protected void setNextMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR) + 1),
                    month.getActualMinimum(Calendar.MONTH), 1);
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR) - 1),
                    month.getActualMaximum(Calendar.MONTH), 1);
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
        }

    }
    private void refreshCalendar() {
        TextView title = (TextView)getView().findViewById(R.id.title);
        dateadapter.refreshDays();
        dateadapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items
        thisMonth=month.get(Calendar.MONTH);
        thisMonth=thisMonth+1;
        Log.e("ThisMonth",""+thisMonth);
        CalendarAdapter dataAdapter=new CalendarAdapter(thisMonth);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }
    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String itemvalue;
            for (int i = 0; i < 7; i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(Calendar.DATE, 1);
                items.add("utukykt");
                items.add("2012-10-07");
                items.add("2012-10-15");
                items.add("2012-10-20");
                items.add("2016-11-30");
                items.add("2012-11-28");
            }

            dateadapter.setItems(items);
            dateadapter.notifyDataSetChanged();
        }
    };


}
