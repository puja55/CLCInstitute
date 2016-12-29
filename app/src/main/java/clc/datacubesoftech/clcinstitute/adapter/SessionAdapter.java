package clc.datacubesoftech.clcinstitute.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.model.Session;

/**
 * Created by datacube on 8/29/2016.
 */
public class SessionAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    Holder holder;
    ArrayList<Session> sessionArrayList;

    public SessionAdapter(Context context,ArrayList<Session> sessionArrayList ){
        this.context = context;
        this.sessionArrayList = sessionArrayList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return sessionArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return sessionArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.row_session,parent,false);
            holder.tv_seesion = (TextView)convertView.findViewById(R.id.tv_seesion);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.tv_seesion.setText(sessionArrayList.get(position).getSession_name().toString());

        return convertView;
    }

    public class Holder {

        TextView tv_seesion;
    }
}
