package surveyapp.thesmader.com.surveyapp;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView subjectCode,yearCode,year,stream,whichSem,asSem;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.parent_layout);
            subjectCode = (TextView)itemView.findViewById(R.id.subject_code);
            yearCode=(TextView)itemView.findViewById(R.id.year_display_card);
            year=(TextView)itemView.findViewById(R.id.year);
            whichSem=(TextView)itemView.findViewById(R.id.which_sem);
            stream=(TextView)itemView.findViewById(R.id.stream_card_view);
            asSem=(TextView)itemView.findViewById(R.id.semester_card_view);
        }
    }

    List<String> feature;
    List<String> streams;
    List<String> midendsems;
    List<String> year;
    List<String> semester;
    List<String> examYear;

    RVAdapter(interimActivity interimActivity, List<String> feature, List<String> streams,List<String>midendsems, List<String>year,List<String >semester,List<String>examYear){
        this.feature = feature;
        this.streams=streams;
        this.midendsems=midendsems;
        this.year=year;
        this.semester=semester;
        this.examYear=examYear;
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
                in.putExtra("stream",correspond(streams.get(index)));
                in.putExtra("MidEnd",midendsems.get(index));
                in.putExtra("examYear",examYear.get(index));
                viewGroup.getContext().startActivity(in);

            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.subjectCode.setText(feature.get(i));
        personViewHolder.yearCode.setText(examYear.get(i));
        personViewHolder.year.setText(yrCorrespond(year.get(i))+" year");
        personViewHolder.whichSem.setText(meCorrespond(semester.get(i)));
        personViewHolder.stream.setText(correspond(streams.get(i)));
        personViewHolder.asSem.setText(semCorrespond(semester.get(i)));
    }


    @Override
    public int getItemCount() {
        return feature.size();
    }

    public String semCorrespond(String s)
    {
        if(s.equals("1"))
            return "Autumn Sem";
        else if(s.equals("0"))
            return "Spring Sem";
        else return "Summer supp";
    }

    public String meCorrespond(String s)
    {
        if(s.equals("1"))
            return "End Sem";
        else return "Mid Sem";
    }
    public String yrCorrespond(String s)
    {
        if(s.equals("1"))
            return "1st";
        else if(s.equals("2"))
            return "2nd";
        else if(s.equals("3"))
            return "3rd";
        else if(s.equals("4"))
            return "4th";
        else return "5";


    }
    public String correspond(String a)
    {
        if (a.equals("0"))
            return "B.Tech";
        else if (a.equals("1"))return "M.A";
        else if (a.equals("2"))return "M.B.A";
        else if (a.equals("3"))return "B.Arch";
        else if (a.equals("4"))return "M.Sc";
        else if (a.equals("5"))return "Int M.Sc";
        else if (a.equals("6"))return "M.Tech(Res)";
        else if (a.equals("7"))return "Dual Degree";
        else if (a.equals("8"))return "Ph.D";
        else return "-1";
    }
}