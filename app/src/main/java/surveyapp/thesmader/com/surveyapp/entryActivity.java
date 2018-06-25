package surveyapp.thesmader.com.surveyapp;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.xml.datatype.Duration;

/**
 * Created by Chinmay on 02-05-2018.
 */

public class entryActivity extends AppCompatActivity implements View.OnClickListener{
   public static String scode;
    public static String semesterValue;
    public static String yearValue;
    public static int marksValue;
    public static int mainValue;
    public static int sup1;
    public static int sup2;
    public static int sup3;

    EditText marks;
    EditText paper1;
    EditText paper2;
    EditText paper3;
    EditText paper4;

    int editIndex;

    FloatingActionButton tableEditButton;
    public int index;
    List<String> a;
    int xIndex[],xMarks[],xMain[],xs1[],xs2[],xs3[];
    public String[] data;
    public String[] keyOfData;
    private TableLayout table;
    public RadioGroup stream1,stream2;

    String stream,midend;
    FloatingActionButton bs3,bs2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user = new HashMap<>();
    private CollectionReference notebookRef;
    EditText s2,s3;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        table=(TableLayout)findViewById(R.id.table_layout);
        xMarks=new int[5];
        xMain=new int[5];
        xIndex=new int[5];
        xs1=new int[5];
        xs2=new int[5];
        xs3=new int[5];

        for(int i=0;i<4;i++)
        {
            xs1[i]=0;
            xs2[i]=0;
            xs3[1]=0;
        }
        setContentView(R.layout.page_entry);
        //tableEditButton=(FloatingActionButton)findViewById(R.id.tableEditButton);
        //tableEditButton.setVisibility(View.GONE);
         stream1=(RadioGroup)findViewById(R.id.stream1);
         stream2=(RadioGroup)findViewById(R.id.stream2);
         bs3=findViewById(R.id.floatingActionButton6);
         bs2=findViewById(R.id.floatingActionButton5);
        Intent i=getIntent();
        scode=i.getStringExtra("subject");

        yearValue=i.getStringExtra("year");
        semesterValue=i.getStringExtra("semester");
        stream=i.getStringExtra("stream");
        midend=i.getStringExtra("MidEnd");
        TextView subjectDisplay=(TextView) findViewById(R.id.textView10);
        subjectDisplay.setText(scode);

        s2=(EditText)findViewById(R.id.extra_paper_wastage3);
        s3=(EditText)findViewById(R.id.extra_paper_wastage4);
        s2.setVisibility(View.GONE);
        s3.setVisibility(View.GONE);

        data=new String[5];
        keyOfData=new String[5];
        notebookRef=db.collection(scode);
        index=0;

         RadioButton r1=(RadioButton)findViewById(R.id.btech);
         RadioButton r2=(RadioButton)findViewById(R.id.ma);
         RadioButton r3=(RadioButton)findViewById(R.id.mba);
         RadioButton r4=(RadioButton)findViewById(R.id.barch);
         RadioButton r5=(RadioButton)findViewById(R.id.msc);
         RadioButton r6=(RadioButton)findViewById(R.id.dd);
         RadioButton r7=(RadioButton)findViewById(R.id.imsc);
         RadioButton r8=(RadioButton)findViewById(R.id.mres);
         RadioButton r9=(RadioButton)findViewById(R.id.phd);

    if (stream.equals("B.Tech"))
        r1.setChecked(true);
    if (stream.equals("M.A"))
        r2.setChecked(true);
    if (stream.equals("M.B.A"))
        r3.setChecked(true);
    if (stream.equals("B.Arch"))
        r4.setChecked(true);
    if (stream.equals("M.Sc"))
        r5.setChecked(true);
    if (stream.equals("Integrated M.Sc"))
        r7.setChecked(true);
    if (stream.equals("M.Tech(Res)"))
        r8.setChecked(true);
    if (stream.equals("Dual Degree"))
        r6.setChecked(true);
    if (stream.equals("Ph.D"))
        r9.setChecked(true);

         uiRef();

    }

    public void editTableSave(View view) {
        if (!marks.getText().toString().isEmpty() && !paper1.getText().toString().isEmpty() && !paper2.getText().toString().isEmpty())// &&  && !paper4.getText().toString().isEmpty()) {
        {
            marksValue = Integer.parseInt(marks.getText().toString());
            mainValue = Integer.parseInt(paper1.getText().toString());
            sup1 = Integer.parseInt(paper2.getText().toString());
            if (!paper3.getText().toString().isEmpty())
                sup2 = Integer.parseInt(paper3.getText().toString());
            if (!paper4.getText().toString().isEmpty())
                sup3 = Integer.parseInt(paper4.getText().toString());
            xMarks[editIndex]=marksValue;
            xMain[editIndex]=mainValue;
            xs1[editIndex]=sup1;
            xs2[editIndex]=sup2;
            xs3[editIndex]=sup3;
        }
        DocumentReference tEdit=db.collection(scode).document(keyOfData[editIndex]);
        tEdit.update("marks",marksValue);
        tEdit.update("Main sheet wasted",mainValue);
        tEdit.update("Supplementary 1",sup1);
        tEdit.update("Supplementary 2",sup2);
        tEdit.update("Supplementary 3",sup3)
                .addOnSuccessListener(new OnSuccessListener<Void>(){

                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(entryActivity.this,"Updation successful",Toast.LENGTH_SHORT).show();
                    }
                });
        tableEditButton.setVisibility(View.GONE);
        uiRef();
    }




public void onClick(View view) {

    marks = (EditText) findViewById(R.id.marks_entry);

    paper1 = (EditText) findViewById(R.id.main_paper_wastage);

    paper2 = (EditText) findViewById(R.id.extra_paper_wastage);

    paper3 = (EditText) findViewById(R.id.extra_paper_wastage3);

    paper4 = (EditText) findViewById(R.id.extra_paper_wastage4);
    String key;
    if (!marks.getText().toString().isEmpty() && !paper1.getText().toString().isEmpty() && !paper2.getText().toString().isEmpty())// &&  && !paper4.getText().toString().isEmpty()) {
    {
        marksValue = Integer.parseInt(marks.getText().toString());
        mainValue = Integer.parseInt(paper1.getText().toString());
        sup1 = Integer.parseInt(paper2.getText().toString());
        if (!paper3.getText().toString().isEmpty())
            sup2 = Integer.parseInt(paper3.getText().toString());
        if (!paper4.getText().toString().isEmpty())
            sup3 = Integer.parseInt(paper4.getText().toString());

        user.put("Year", yearValue);
        user.put("Semester", semesterValue);
        user.put("marks", marksValue);
        user.put("Main sheet wasted", mainValue);
        user.put("Supplementary 1", sup1);
        user.put("Stream", stream);
        user.put("Mid or end sem", midend);
        if (paper3.getText().toString().isEmpty())
         user.put("Supplementary 2", 0);
        else
            user.put("Supplementary 2", sup2);
        if (paper4.getText().toString().isEmpty())
            user.put("Supplementary 3", 0);
        else
            user.put("Supplementary 3", sup3);
        user.put("Index", index);
        db.collection(scode)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FirestoreDemo", "DocumentSnapshot added with ID " + documentReference.getId());
                        updateUI(documentReference.getId().toString()); // Pass on values over here
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FirestoreDemo", "Error adding document", e);
                    }
                });

        Toast.makeText(getApplicationContext(), Integer.toString(mainValue), Toast.LENGTH_SHORT).show();
        marks.setText("");
        paper1.setText((""));
        paper2.setText("");
        paper3.setText("");
        paper4.setText("");
    } else
        Toast.makeText(getApplicationContext(), "Don't leave the fields blank", Toast.LENGTH_SHORT).show();
    // setContentView(R.layout.page_entry);
    s2.setVisibility(View.GONE);
    s3.setVisibility(View.GONE);
    bs2.setVisibility(View.VISIBLE);
    bs3.setVisibility(View.VISIBLE);

}
    public void streamChoice1(View view)
    {

       int selectedId=stream1.getCheckedRadioButtonId();
       Button rb1=(Button)findViewById(selectedId);
       stream=rb1.getText().toString();
        if(stream2.getCheckedRadioButtonId()!=-1)
       stream2.clearCheck();
    }
    public void streamChoice2(View view)
    {
        int selectedId=stream2.getCheckedRadioButtonId();
        Button rb2=(Button)findViewById(selectedId);
        stream=rb2.getText().toString();
        if(stream1.getCheckedRadioButtonId()!=-1)
       stream1.clearCheck();
    }

public void savingData(View view)
{
    goBack(view);
}
public void updateUI(String key)
{
    if(index<5)
    {
       keyOfData[index]=key;
        xIndex[index]=index+1;
        xMarks[index]=marksValue;
        xMain[index]=mainValue;
        xs1[index]=sup1;
        xs2[index]=sup2;
        xs3[index]=sup3;
    }
    else{
        data[0]=data[1];
        data[1]=data[2];
        data[2]=data[3];
        data[3]=data[4];
        keyOfData[0]=keyOfData[1];
        keyOfData[1]=keyOfData[2];
        keyOfData[2]=keyOfData[3];
        keyOfData[3]=keyOfData[4];
        for(int i=0;i<4;i++)
        {
            xIndex[i]=xIndex[i+1];
            xMarks[i]=xMarks[i+1];
            xMain[i]=xMain[i+1];
            xs1[i]=xs1[i+1];
            xs2[i]=xs2[i+1];
            xs3[i]=xs3[i+1];
        }

        keyOfData[4]=key;
        xIndex[4]=index+1;
        xMarks[4]=marksValue;
        xMain[4]=mainValue;
        xs1[4]=sup1;
        xs2[4]=sup2;
        xs3[4]=sup3;



    }
    index++;
    uiRef();
}

public void uiRef()
{
    TableLayout stk = (TableLayout) findViewById(R.id.table_layout);
    stk.removeAllViews();
    Typeface typeface = ResourcesCompat.getFont(this, R.font.q);
    stk.setColumnStretchable(6,true);
    TableRow tbrow0 = new TableRow(this);
    TextView tv0 = new TextView(this);
    tv0.setTypeface(typeface);
    tv0.setText("S.No   ");
    tv0.setTextSize(25);
    tv0.setTextColor(Color.BLACK);
    tbrow0.addView(tv0);
    TextView tv1 = new TextView(this);
    tv1.setText("Marks   ");
    tv1.setTypeface(typeface);
    tv1.setTextSize(25);
    tv1.setTextColor(Color.BLACK);
    tbrow0.addView(tv1);
    TextView tv2 = new TextView(this);
    tv2.setText("Main  ");
    tv2.setTextSize(25);
    tv2.setTypeface(typeface);
    tv2.setTextColor(Color.BLACK);
    tbrow0.addView(tv2);
    TextView tv3 = new TextView(this);
    tv3.setText("S1  ");
    tv3.setTextColor(Color.BLACK);
    tv3.setTextSize(25);
    tv3.setTypeface(typeface);
    tbrow0.addView(tv3);
    TextView tv4 = new TextView(this);
    tv4.setTextSize(25);
    tv4.setText("S2  ");
    tv4.setTypeface(typeface);
    tv4.setTextColor(Color.BLACK);
    tbrow0.addView(tv4);
    TextView tv5 = new TextView(this);
    tv5.setText("S3");
    tv5.setTextSize(25);
    tv5.setTypeface(typeface);
    tv5.setTextColor(Color.BLACK);
    tbrow0.addView(tv5);
    stk.addView(tbrow0);
    int limit;
    if(index>4)
        limit=5;
    else
        limit=index;
    for (int i = 0; i <limit; i++) {
        TableRow tbrow = new TableRow(this);
        TextView t1v = new TextView(this);
        tbrow.setId(i);
        t1v.setText(Integer.toString(xIndex[i]));
        t1v.setTextColor(Color.BLACK);
        t1v.setTextSize(24);
        t1v.setTypeface(typeface);
        t1v.setGravity(Gravity.CENTER);
        tbrow.addView(t1v);
        TextView t2v = new TextView(this);
        t2v.setText(Integer.toString(xMarks[i]));
        t2v.setTextColor(Color.BLACK);
        t2v.setTypeface(typeface);
        t2v.setTextSize(24);
        t2v.setGravity(Gravity.CENTER);
        tbrow.addView(t2v);
        TextView t3v = new TextView(this);
        t3v.setText(Integer.toString(xMain[i]));
        t3v.setTextColor(Color.BLACK);
        t3v.setTypeface(typeface);
        t3v.setTextSize(24);
        t3v.setGravity(Gravity.CENTER);
        tbrow.addView(t3v);
        TextView t4v = new TextView(this);
        t4v.setText(Integer.toString(xs1[i]));
        t4v.setTextColor(Color.BLACK);
        t4v.setTypeface(typeface);
        t4v.setTextSize(24);
        t4v.setGravity(Gravity.CENTER);
        tbrow.addView(t4v);
        TextView t5v= new TextView(this);
        t5v.setText(Integer.toString(xs2[i]));
        t5v.setTextColor(Color.BLACK);
        t5v.setTypeface(typeface);
        t5v.setTextSize(24);
        t5v.setGravity(Gravity.CENTER);
        tbrow.addView(t5v);
        TextView t6v = new TextView(this);
        t6v.setText(Integer.toString(xs3[i]));
        t6v.setTextColor(Color.BLACK);
        t6v.setTypeface(typeface);
        t6v.setGravity(Gravity.CENTER);
        t6v.setTextSize(24);
        tbrow.addView(t6v);
        stk.addView(tbrow);
        tbrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.selection));
                int k=v.getId();
                Toast.makeText(entryActivity.this,Integer.toString(k),Toast.LENGTH_SHORT).show();
                editTable(k);
            }
        });
    }
}
public void editTable(int keyValue)
{
    tableEditButton.setVisibility(View.VISIBLE);

    if(keyValue>4)
    {
        keyValue=4-xIndex[4]-keyValue;
        editIndex=keyValue;
    }
    else editIndex=keyValue;

    marks.setText(Integer.toString(xMarks[keyValue]),TextView.BufferType.EDITABLE);
    paper1.setText(Integer.toString(xMain[keyValue]),TextView.BufferType.EDITABLE);
    paper2.setText(Integer.toString(xs1[keyValue]),TextView.BufferType.EDITABLE);
    paper3.setText(Integer.toString(xs2[keyValue]),TextView.BufferType.EDITABLE);
    paper4.setText(Integer.toString(xs3[keyValue]),TextView.BufferType.EDITABLE);

}
public void pageAdd2(View view)
    {

        bs3.setVisibility(View.GONE);
        s3.setVisibility(View.VISIBLE);
    }
public void pageAdd1(View view)
{

    bs2.setVisibility(View.GONE);
    s2.setVisibility(View.VISIBLE);
}
public void onBackPressed(View view)
{
    savingData(view);
}
public void goBack(View view)
{
    startActivity(new Intent(this, interimActivity.class));
    finish();
}

}
