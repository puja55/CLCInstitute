package clc.datacubesoftech.clcinstitute.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.model.Calling;

/**
 * Created by Pri on 8/12/2016.
 */
public class CallingStatus_Adapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Calling> callingArrayList;

    public CallingStatus_Adapter(Context context, ArrayList<Calling> callingArrayList ) {
        this.context = context;
        this.callingArrayList=callingArrayList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return callingArrayList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return callingArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = inflater.inflate(R.layout.callingstatus_row, null, true);
        TextView textViewcall_date1 = (TextView) listViewItem.findViewById(R.id.tv_calling_date1);
        TextView textViewpurpose1 = (TextView) listViewItem.findViewById(R.id.tv_purpose1);
        TextView textViewfeedback1 = (TextView) listViewItem.findViewById(R.id.tv_feedback1);


       textViewcall_date1.setText(callingArrayList.get(position).getCalling_date().toString());
       textViewfeedback1.setText(callingArrayList.get(position).getFeedback().toString());
       textViewpurpose1.setText(callingArrayList.get(position).getPurpose().toString());

        return  listViewItem;
    }
}

