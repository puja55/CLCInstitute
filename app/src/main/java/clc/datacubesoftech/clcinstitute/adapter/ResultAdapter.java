package clc.datacubesoftech.clcinstitute.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.fragment.Fragment_Row_Result;
import clc.datacubesoftech.clcinstitute.model.Result;

/**
 * Created by Pri on 9/17/2016.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.View_Holder> {
    Context mContext;
    ArrayList<Result> resultList;

    public ResultAdapter(Context mContext, ArrayList<Result> resultList) {
        this.mContext=mContext;
        this.resultList=resultList;

    }

    public class View_Holder extends RecyclerView.ViewHolder {

        public TextView tname;
        public TextView tdate;
        public TextView texam;

        public View_Holder(View itemView) {
            super(itemView);
            tname = (TextView) itemView.findViewById(R.id.tname);
            tdate=(TextView)itemView.findViewById(R.id.tdate);
            texam=(TextView)itemView.findViewById(R.id.texam);
        }
    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_result_recycler_row, parent, false);
        return new View_Holder(v);

    }
    @Override
    public void onBindViewHolder(final ResultAdapter.View_Holder holder, final int position) {

        holder.tname.setText(resultList.get(position).getName().toString());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                Bundle bundle=new Bundle();
                bundle.putString("pos",""+pos);

                Fragment_Row_Result fragment_result = new Fragment_Row_Result();
                fragment_result.setArguments(bundle);
                FragmentManager mFragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame, fragment_result, "result").addToBackStack("Profile").commit();

            }
        });

        holder.tdate.setText(resultList.get(position).getDate().toString());
        holder.texam.setText(resultList.get(position).getExam().toString());
    }
    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return resultList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}