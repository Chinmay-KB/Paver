package surveyapp.thesmader.com.surveyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class interimActivity extends BaseActivity {

    FirebaseFirestore db;
    TextView namev,mailv;
    ListView listView;
    String s[];
    FirebaseAuth mAuth;
    List<String> namesList = new ArrayList<>();
    List<String> streams=new ArrayList<>();
    List<String> midendsems=new ArrayList<>();
    List<String> year=new ArrayList<>();
    List<String> semester=new ArrayList<>();
    RVAdapter adapter;
    FloatingActionButton fab;
    private AppBarLayout mAppBarLayout;
    android.widget.Toolbar toolbar;
    CollapsingToolbarLayout ctl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*toolbar = findViewById(R.id.toolbar_1);
        toolbar.setNavigationIcon(R.drawable.ham);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.START);
            }
        });*/
        mAuth = FirebaseAuth.getInstance();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.landing_page_new, null, false);
        //setContentView(R.layout.landing_page_new);
        mDrawer.addView(contentView, 0);
        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
       //final TextView namev=(TextView)findViewById(R.id.display_name);
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(users.getDisplayName());
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        Toolbar toolbar = findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ham);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navOpen(v);
            }
        });
        db=FirebaseFirestore.getInstance();
        db.collection(users.getEmail())
                .whereGreaterThanOrEqualTo("Subject Code"," ")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(DocumentSnapshot doc:task.getResult())
                            {
                               String s=doc.getString("Subject Code");
                                namesList.add(s);
                                midendsems.add(doc.getString("Mid or End sem"));
                                streams.add(doc.getString("Stream"));
                                year.add(doc.getString("Year"));
                                semester.add(doc.getString("Semester"));

                            }
                        }

                         adapter.notifyDataSetChanged();

                    }
                });
        RecyclerView recyclerView=findViewById(R.id.lv);
        adapter=new RVAdapter(this,namesList,streams,midendsems,year,semester);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.floatingActionButton4);
        fab.setTransitionName("reveal");
    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void redirect(View view)
    {
        MainActivity ob=new MainActivity();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        finish();
    }

    public void signOut(MenuItem item){
        mAuth.signOut();
        Intent i = new Intent(interimActivity.this, SignIn.class);
        startActivity(i);
        finish();
    }

    public void bugReport(MenuItem menuItem){
        String uriText =
                "mailto:chinmay.kabi@gmail.com" +
                        "?subject=" + Uri.encode("Bug!!!") +
                        "&body=" + Uri.encode("Mention the bug in detail, attach screenshots if possible.");

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, "Send email"));
        }
    }

    public void navOpen(View view) {
        mDrawer.openDrawer(Gravity.START);
    }

    public void aboutOpen(MenuItem item){
        Intent i = new Intent(interimActivity.this, AboutNew.class);
        startActivity(i);
    }

    /*public Animator createCircularReveal(View view){
        Animator anim;
        return anim;
    }*/
}
