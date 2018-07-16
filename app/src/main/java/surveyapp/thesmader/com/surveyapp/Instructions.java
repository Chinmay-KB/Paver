package surveyapp.thesmader.com.surveyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Instructions extends AppCompatActivity {

    //private InstructionAdapter adapter;
    //private List<InstructionModel> inst_list = new ArrayList<>();
    //private String[] inst_array = {"Landing Screen","Subject Details Screen","Data Entry Screen","FAQs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    public void LSClick(View view) {
        startActivity(new Intent(this, LandingInstructions.class));
    }

    public void SubClick(View view) {
        startActivity(new Intent(this, SubjectDetails.class));
    }

    public void DataClick(View view){
        startActivity(new Intent(this, DataEntry.class));
    }
}
