package surveyapp.thesmader.com.surveyapp;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import java.util.Arrays;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView subjectCode;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.parent_layout);
            subjectCode = (TextView)itemView.findViewById(R.id.subject_code);
        }
    }

    List<String> feature;
    List<String> streams;
    List<String> midendsems;
    List<String> year;
    List<String> semester;

    RVAdapter(interimActivity interimActivity, List<String> feature, List<String> streams,List<String>midendsems, List<String>year,List<String >semester){
        this.feature = feature;
        this.streams=streams;
        this.midendsems=midendsems;
        this.year=year;
        this.semester=semester;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public PersonViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list, viewGroup, false);
        final PersonViewHolder pvh = new PersonViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int index=pvh.getAdapterPosition();
                Intent in=new Intent(viewGroup.getContext(),entryActivity.class);
                in.putExtra("subject",feature.get(index));
                in.putExtra("year",year.get(index));
                in.putExtra("semester",semester.get(index));
                in.putExtra("stream",streams.get(index));
                in.putExtra("MidEnd",midendsems.get(index));
                viewGroup.getContext().startActivity(in);

            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.subjectCode.setText(feature.get(i));
    }


    @Override
    public int getItemCount() {
        return feature.size();
    }
}