package clc.datacubesoftech.clcinstitute.fragment;

/**
 * Created by User on 13/11/2016.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import clc.datacubesoftech.clcinstitute.Login;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.sharedPreferences.LoginID;
import de.hdodenhof.circleimageview.CircleImageView;

import static clc.datacubesoftech.clcinstitute.R.layout.login;

public class Student_Profile_Home extends Fragment {

Context context;
    ImageView profile;
    LineChart lineChart;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    TextView tv_viewall,tv_feedback;
    ImageView feed;
    //Weekly Calendar
    private static final String tag = "MyCalendarActivity";
    LoginID loginID;
    String imageUrl,stdName;
    private TextView currentMonth,text_std_name;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    @SuppressLint("NewApi")
    private int actualYear, actualMonth, actualDay,month,year;
    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        loginID=LoginID.getInstance();
        imageUrl = "http://clcresult.in//application//assets//upload//171031.jpg";
        //stdName=loginID.getStdName(context);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.student_profile_home, container,false);
        lineChart = (LineChart) v.findViewById(R.id.chart1);
        CircleImageView imageView = (CircleImageView)v.findViewById(R.id.img_profile);
        feed=(ImageView)v.findViewById(R.id.img_feedback);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(feed);
            }
        });
        text_std_name=(TextView)v.findViewById(R.id.tv_studentName);

        text_std_name.setText("SAWAN KUMAR MEENA");
        Picasso.with(getActivity())
                .load(imageUrl).fit()
                .placeholder(R.drawable.logo_clc).fit() // optional
                .error(R.drawable.logo_clc).fit()
                .into(imageView);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(2f, 0));
        entries.add(new Entry(1f, 1));
        entries.add(new Entry(1f, 2));
        entries.add(new Entry(3f, 3));
        LineDataSet dataset = new LineDataSet(entries,"Test");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Min M%");
        labels.add("Min R%");
        labels.add("DTS%");
        labels.add("Camp%");
        LineData data = new LineData(labels, dataset);
        lineChart.setData(data); // set the data and list of lables into chart
        lineChart.setDescription("Description");
        dataset.setDrawFilled(true); // to fill the below area of line in graph
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); // to change the color scheme
        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        actualYear = year;
        actualMonth = month;
        actualDay = _calendar.get(Calendar.DAY_OF_MONTH);
        Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
                + year);
        currentMonth = (TextView) v.findViewById(R.id.tv_date);
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        calendarView = (GridView)v.findViewById(R.id.gv_calendar);
        // Initialised
        adapter = new GridCellAdapter(getActivity().getApplicationContext(),
                R.id.calendar_day_gridcell, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
        tv_viewall=(TextView)v.findViewById(R.id.tv_viewAll);
        profile=(ImageView) v.findViewById(R.id.img_profile) ;
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_StudentProfile profile = new Fragment_StudentProfile();
                mFragmentManager = getActivity().getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame, profile, "profile").addToBackStack("profile").commit();
            }
        });
        tv_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_Calendar fragment_calendar = new Fragment_Calendar();
                mFragmentManager = getActivity().getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame, fragment_calendar, "calendar").addToBackStack("calendar").commit();


            }
        });
        return v;

    }

    private void showPopupMenu(ImageView feed) {
        PopupMenu popup = new PopupMenu(feed.getContext(), feed);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_feedback, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.action_feedback:
                    Fragment_Feedback fragment_tag=new Fragment_Feedback();
                    mFragmentManager = getActivity().getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.frame,fragment_tag, "tag").addToBackStack("Tag").commit();
                    return true;

                case R.id.action_logout:
                    loginID.removeLoginId(getActivity());
                    Log.e("LOGINID",""+loginID);

                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    return true;

                default:
            }
            return false;
        }

    }
    @Override
    public void onDestroy() {
        Log.d(tag, "Destroying View ...");
        super.onDestroy();
    }

    // Inner Class
    public class GridCellAdapter extends BaseAdapter {
        private static final String tag = "GridCellAdapter";
        private final Context _context;
        private final List<String> list;
        private static final int DAY_OFFSET = 1;
        private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
                "Wed", "Thu", "Fri", "Sat" };
        private final String[] months = { "January", "February", "March",
                "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
                31, 30, 31 };
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private TextView gridcell;
        private ImageView currentimg;
        private final HashMap<String, Integer> eventsPerMonthMap;
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "dd-MMM-yyyy");
        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId,
                               int month, int year) {
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
                    + "Year: " + year);
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
            Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
            Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
            Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

            // Print Month
            printMonth(month, year);

            // Find Number of Events
            eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
        }

        private String getMonthAsString(int i) {
            return months[i];
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * Prints Month
         *
         * @param mm
         * @param yy
         */
        private void printMonth(int mm, int yy)
        {
            Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int currentMonth = mm - 1;
            String currentMonthName = getMonthAsString(currentMonth);
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);

            Log.d(tag, "Current Month: " + " " + currentMonthName + " having " + daysInMonth + " days.");

            GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, getCurrentDayOfMonth());
            Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;

            Log.d(tag, "Week Day:" + currentWeekDay + " is " + getWeekDayAsString(currentWeekDay));
            Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
            Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                if (mm == 2) ++daysInMonth;
                else if (mm == 3)
                    ++daysInPrevMonth;

            // move at the begging of the week
            int i = getCurrentDayOfMonth();
            int j = currentWeekDay;
            while (i > 1 && j > 0)
            {
                i--;
                j--;
            }
            // print the week, starting at Sunday
            for (j = 0; i <= daysInMonth && j < 7; i++, j++)
            {
                Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);
                // If it is the current date, printing in BLUE (Orange)
                if (i == actualDay && mm == actualMonth && yy == actualYear)
                {
                    list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                }
                else
                {
                    list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                }
            }
        }

        /**
         * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
         * ALL entries from a SQLite database for that month. Iterate over the
         * List of All entries, and get the dateCreated, which is converted into
         * day.
         *
         * @param year
         * @param month
         * @return
         */
        private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                    int month) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();

            return map;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.row_calender, parent, false);
            }

            // Get a reference to the Day gridcell
            gridcell = (TextView) row.findViewById(R.id.tv_calendarDate);
            // ACCOUNT FOR SPACING
            Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
            String[] day_color = list.get(position).split("-");
            String theday = day_color[0];
            String themonth = day_color[2];
            String theyear = day_color[3];

            // Set the Day GridCell
            gridcell.setText(theday);
            gridcell.setTag(theday + "-" + themonth + "-" + theyear);
            Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
                    + theyear);

            if (day_color[1].equals("GREY")) {
                gridcell.setTextColor(getResources()
                        .getColor(R.color.yellow));
            }
            if (day_color[1].equals("WHITE")) {
                gridcell.setTextColor(getResources().getColor(
                        R.color.yellow));
            }
            if (day_color[1].equals("BLUE")) {
                gridcell.setBackground(getResources().getDrawable(R.drawable.calender_bg));
                gridcell.setTextColor(getResources().getColor(R.color.black));
            }
            return row;
        }

        public int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }

        public void setCurrentWeekDay(int currentWeekDay) {
            this.currentWeekDay = currentWeekDay;
        }

        public int getCurrentWeekDay() {
            return currentWeekDay;
        }
    }
}


