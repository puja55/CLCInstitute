package clc.datacubesoftech.clcinstitute.adapter;

/**
 * Created by User on 05/12/2016.
 */
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.model.CalendarModl;

public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    private java.util.Calendar month;
    public GregorianCalendar pmonth,pmonthmaxset,selectedDate; // calendar instance for previous month
    String monthStr = "0";
    String year="0";
    int firstDay,maxWeeknumber,maxP,calMaxP,mnthlength,currentDatePosition,thismonth;
    String itemvalue, curentDateString;
    DateFormat df;
    private ArrayList<String> items;
    public static List<String> dayString;
    TextView attendance;
    private View previousView;
    ArrayList<CalendarModl> attendanceArrayList;
    int flag=0,n;
    public CalendarAdapter(){

    }
    public CalendarAdapter(Context c, GregorianCalendar monthCalendar, ArrayList<CalendarModl> attendanceArrayList) {
        CalendarAdapter.dayString = new ArrayList<String>();
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        mContext = c;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        this.attendanceArrayList = attendanceArrayList;

        refreshDays();
    }

    public CalendarAdapter(int thismonth){
        this.thismonth=thismonth;
    }

    public void setItems(ArrayList<String> items) {

        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
    }

    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {
        return attendanceArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_row_layout, null);

        }

        dayView = (TextView) v.findViewById(R.id.tv_date);
        attendance = (TextView) v.findViewById(R.id.tv_attendance);
        // separates daystring into parts.
        String[] separatedTime = dayString.get(position).split("-");
        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        // checking whether the day is in current month or not.
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            // setting offdays to white color.
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
            attendance.setText(" ");
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
            attendance.setText(" ");
        } else {
            // setting curent month's days in blue color.
            dayView.setTextColor(Color.BLUE);

            int year1=attendanceArrayList.get(0).getAttendance().getYear();

            int month=attendanceArrayList.get(0).getAttendance().getMonthid();

            if (Integer.parseInt(year)==year1 && month == Integer.parseInt(monthStr))
                switch (Integer.parseInt(monthStr)) {
                case 12:
                    for (int i = 0; i < Integer.parseInt(gridvalue); i++) {
                        String atten = attendanceArrayList.
                                get(Integer.parseInt(gridvalue)).getAttendance1().getC1().toString();
                        if (atten.equalsIgnoreCase("A")) {
                            attendance.setTextColor(Color.RED);
                        } else if (atten.equalsIgnoreCase("P")) {
                            attendance.setTextColor(Color.GREEN);
                        } else  {
                            attendance.setTextColor(Color.BLACK);
                        }

                        attendance.setText(atten);

                    }
                    notifyDataSetChanged();
                    break;
            }
            else if (month!=Integer.parseInt(monthStr)){
                attendance.setText(" ");
            }
        }
        if (dayString.get(position).equals(curentDateString)) {
            setSelected(v);
            previousView = v;
        } else {
            v.setBackgroundResource(R.drawable.list_item_background);
        }

        dayView.setText(gridvalue);

        // create date string for comparison
        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        year= "" +(month.get(GregorianCalendar.YEAR));
        Log.e("THISYEAR",""+year);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        if (year.length() == 1) {
            year = "0" + year;
        }
        return v;
    }
    public View setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.drawable.list_item_background);
        }
        previousView = view;
        view.setBackgroundResource(R.drawable.calendar_cel_selectl);
        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        dayString.clear();
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());

            if(curentDateString.equalsIgnoreCase(itemvalue))
            {
                currentDatePosition = n;
            }

            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }
}

