package surveyapp.thesmader.com.surveyapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private List<String> mData;
    private Context mContext;

    public RecyclerViewAdapter(Context context,List<String> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));

                Toast.makeText(mContext, mData.get(position), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(mContext, entryActivity.class);
                String item=mData.get(position);
                int a=item.indexOf(" ");
                String scode=item.substring(0,a);
                String year=item.substring(a+1,a+4);
                String sem=item.substring(a+4,item.length());
                i.putExtra("subject",scode);
                i.putExtra("year",year);
                i.putExtra("semester",sem);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView imageName;
        CardView parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
