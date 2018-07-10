package surveyapp.thesmader.com.surveyapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static int examYear;
    public TextView inputCount;

    @ServerTimestamp
    Date time;

    EditText marks;
    EditText paper1;
    EditText paper2;
    EditText paper3;
    EditText paper4;

    int editIndex;

    android.support.v7.widget.AppCompatButton tableEditButton, addButton, saveButton;
    public int index;
    List<String> a;
    String xIndex[],xMarks[],xMain[],xs1[],xs2[],xs3[];
    public String[] data;
    public String[] keyOfData;
    private TableLayout table;
    public RadioGroup stream1,stream2;

    String stream,midend;
    FloatingActionButton bs3,bs2,bs1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user = new HashMap<>();
    private CollectionReference notebookRef;
    EditText s2,s3,s1;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        table=(TableLayout)findViewById(R.id.table_layout);
        xMarks=new String[5];
        xMain=new String[5];
        xIndex=new String[5];
        xs1=new String[5];
        xs2=new String[5];
        xs3=new String[5];




        for(int i=0;i<4;i++)
        {
            xIndex[i]="0";
            xs1[i]="0";
            xs2[i]="0";
            xs3[1]="0";
        }
        setContentView(R.layout.page_entry);
        tableEditButton= (AppCompatButton) findViewById(R.id.tableEditButton);
        tableEditButton.setVisibility(View.GONE);
        saveButton = findViewById(R.id.floatingActionButton3);
        addButton = findViewById(R.id.floatingActionButton2);
         stream1=(RadioGroup)findViewById(R.id.stream1);
         stream2=(RadioGroup)findViewById(R.id.stream2);
         bs3=findViewById(R.id.floatingActionButton6);
         bs2=findViewById(R.id.floatingActionButton5);
         bs1 = findViewById(R.id.s1_fab);
        Intent i=getIntent();
        scode=i.getStringExtra("subject");

        yearValue=i.getStringExtra("year");
        semesterValue=i.getStringExtra("semester");
        stream=i.getStringExtra("stream");
        midend=i.getStringExtra("MidEnd");
        examYear=Integer.parseInt(i.getStringExtra("examYear"));
        TextView subjectDisplay=(TextView) findViewById(R.id.textView10);
         inputCount=(TextView)findViewById(R.id.howMany);
        subjectDisplay.setText(scode);
        s1 = findViewById(R.id.extra_paper_wastage);
        s2=(EditText)findViewById(R.id.extra_paper_wastage3);
        s3=(EditText)findViewById(R.id.extra_paper_wastage4);
        s1.setVisibility(View.GONE);
        s2.setVisibility(View.GONE);
        s3.setVisibility(View.GONE);

        data=new String[5];
        keyOfData=new String[5];
        notebookRef=db.collection(scode);
        index=0;
        howManyAdditions=0;

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
        if (!marks.getText().toString().isEmpty() && !paper1.getText().toString().isEmpty())// && !paper2.getText().toString().isEmpty())// &&  && !paper4.getText().toString().isEmpty()) {
        {
            marksValue = Integer.parseInt(marks.getText().toString());
            mainValue = Integer.parseInt(paper1.getText().toString());
            if (!paper2.getText().toString().isEmpty())
                sup1 = Integer.parseInt(paper2.getText().toString());
            //sup1 = Integer.parseInt(paper2.getText().toString());
            if (!paper3.getText().toString().isEmpty())
                sup2 = Integer.parseInt(paper3.getText().toString());
            if (!paper4.getText().toString().isEmpty())
                sup3 = Integer.parseInt(paper4.getText().toString());
            xMarks[editIndex]=Integer.toString(marksValue);
            xMain[editIndex]=Integer.toString(mainValue);
            xs1[editIndex]=Integer.toString(sup1);
            xs2[editIndex]=Integer.toString(sup2);
            xs3[editIndex]=Integer.toString(sup3);
        }
        DocumentReference tEdit=db.collection(scode).document(keyOfData[editIndex]);
        tEdit.update("marks",marksValue);
        tEdit.update("Main",mainValue);
        tEdit.update("S1",sup1);
        tEdit.update("S2",sup2);
        tEdit.update("S3",sup3)
                .addOnSuccessListener(new OnSuccessListener<Void>(){

                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(entryActivity.this,"Updation successful",Toast.LENGTH_SHORT).show();
                    }
                });
        tableEditButton.setVisibility(View.GONE);
        s2.setText("");
        s3.setText("");
        sup2 = 0;
        sup3 = 0;
        s3.setVisibility(View.GONE);
        s2.setVisibility(View.GONE);
        s1.setVisibility(View.GONE);
        bs1.setVisibility(View.VISIBLE);
        bs2.setVisibility(View.VISIBLE);
        bs3.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        addButton.setVisibility(View.VISIBLE);
        Button deleteButton=(Button)findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.GONE);
        uiRef();

    }




public void onClick(View view) {

         howManyAdditions++;
    marks = (EditText) findViewById(R.id.marks_entry);

    paper1 = (EditText) findViewById(R.id.main_paper_wastage);

    paper2 = (EditText) findViewById(R.id.extra_paper_wastage);

    paper3 = (EditText) findViewById(R.id.extra_paper_wastage3);

    paper4 = (EditText) findViewById(R.id.extra_paper_wastage4);
    String key;
    if (!marks.getText().toString().isEmpty() && !paper1.getText().toString().isEmpty() )//&& !paper2.getText().toString().isEmpty())// &&  && !paper4.getText().toString().isEmpty()) {
    {
        marksValue = Integer.parseInt(marks.getText().toString());
        mainValue = Integer.parseInt(paper1.getText().toString());
        if (!paper2.getText().toString().isEmpty())
        sup1 = Integer.parseInt(paper2.getText().toString());
        if (!paper3.getText().toString().isEmpty())
            sup2 = Integer.parseInt(paper3.getText().toString());
        if (!paper4.getText().toString().isEmpty())
            sup3 = Integer.parseInt(paper4.getText().toString());

        user.put("Year", yrCorrespond(yearValue));
        user.put("Exam Year",examYear);
        user.put("Semester", semCorrespond(semesterValue));
        user.put("Stream", correspond(stream));
        Toast.makeText(entryActivity.this,stream,Toast.LENGTH_SHORT);
        user.put("Mid or end sem", meCorrespond(midend));
        user.put("marks", marksValue);
        user.put("Main", mainValue);
        if(paper2.getText().toString().isEmpty())
        user.put("S1", 0);
        else
            user.put("S1",sup1);
        //user.put("timeStamp", FieldValue.serverTimestamp());
        if (paper3.getText().toString().isEmpty())
         user.put("S2", 0);
        else
            user.put("S2", sup2);
        if (paper4.getText().toString().isEmpty())
            user.put("S3", 0);
        else
            user.put("S3", sup3);
        db.collection(scode)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FirestoreDemo", "DocumentSnapshot added with ID " + documentReference.getId());
                        updateUI(documentReference.getId().toString()); // Pass on values over here
                        sup1=0;
                        sup2=0;
                        sup3=0;
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
    s1.setVisibility(View.GONE);
    s2.setVisibility(View.GONE);
    s3.setVisibility(View.GONE);
    bs1.setVisibility(View.VISIBLE);
    bs2.setVisibility(View.VISIBLE);
    bs3.setVisibility(View.VISIBLE);

}
    public String semCorrespond(String s)
    {
        if(s.equals("Autumn Semester"))
            return "1";
        else if(s.equals("Spring Semester"))
            return "0";
        else return "2";
    }

    public String meCorrespond(String s)
    {
        if(s.equals("End Sem"))
            return "1";
        else return "0";
    }
    public String yrCorrespond(String s)
    {
        if(s.equals("1st"))
            return "1";
        else if(s.equals("2nd"))
            return "2";
        else if(s.equals("3rd"))
            return "3";
        else if(s.equals("4th"))
            return "4";
        else return "5";


    }
    public void streamChoice1(View view)
    {

       int selectedId=stream1.getCheckedRadioButtonId();
       Button rb1=(Button)findViewById(selectedId);
       stream=correspond(rb1.getText().toString());
        if(stream2.getCheckedRadioButtonId()!=-1)
       stream2.clearCheck();
    }
    public void streamChoice2(View view)
    {
        int selectedId=stream2.getCheckedRadioButtonId();
        Button rb2=(Button)findViewById(selectedId);
        stream=correspond(rb2.getText().toString());
        if(stream1.getCheckedRadioButtonId()!=-1)
       stream1.clearCheck();
    }
    public String correspond(String a)
    {
        if (a.equals("B.Tech"))
            return "0";
        else if (a.equals("M.A"))return "1";
        else if (a.equals("M.B.A"))return "2";
        else if (a.equals("B.Arch"))return "3";
        else if (a.equals("M.Sc"))return "4";
        else if (a.equals("Integrated M.Sc"))return "5";
        else if (a.equals("M.Tech(Res)"))return "6";
        else if (a.equals("Dual Degree"))return "7";
        else if (a.equals("Ph.D"))return "8";
        else return "-1";
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
        xIndex[index]=Integer.toString(index+1);
        xMarks[index]=Integer.toString(marksValue);
        xMain[index]=Integer.toString(mainValue);
        xs1[index]=Integer.toString(sup1);
        xs2[index]=Integer.toString(sup2);
        xs3[index]=Integer.toString(sup3);
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
        xIndex[4]=Integer.toString(index+1);
        xMarks[4]=Integer.toString(marksValue);
        xMain[4]=Integer.toString(mainValue);
        xs1[4]=Integer.toString(sup1);
        xs2[4]=Integer.toString(sup2);
        xs3[4]=Integer.toString(sup3);



    }
    index++;
    uiRef();
}
    TableLayout stk;
    TableRow tbrow;

    public int howManyAdditions;
    public void uiRef()
{

    inputCount.setText(Integer.toString(howManyAdditions));
    stk = (TableLayout) findViewById(R.id.table_layout);
    stk.removeAllViews();
    Typeface typeface = ResourcesCompat.getFont(this, R.font.q);
    stk.setColumnStretchable(5,true);
    TableRow tbrow0 = new TableRow(this);
   /* TextView tv0 = new TextView(this);
    tv0.setTypeface(typeface);
    tv0.setText(" #   ");
    tv0.setTextSize(22);
    tv0.setTextColor(Color.BLACK);
    tbrow0.addView(tv0);
    */
   tbrow0.setGravity(View.TEXT_ALIGNMENT_CENTER);
   stk.setStretchAllColumns(true);
    TextView tv1 = new TextView(this);
    tv1.setText("Marks");
    tv1.setTypeface(typeface);
    tv1.setTextSize(22);
    tv1.setTextColor(Color.BLACK);
    tbrow0.addView(tv1);
    TextView tv2 = new TextView(this);
    tv2.setText("Main ");
    tv2.setTextSize(22);
    tv2.setTypeface(typeface);
    tv2.setTextColor(Color.BLACK);
    tbrow0.addView(tv2);
    TextView tv3 = new TextView(this);
    tv3.setText(" S1  ");
    tv3.setTextColor(Color.BLACK);
    tv3.setTextSize(22);
    tv3.setTypeface(typeface);
    tbrow0.addView(tv3);
    TextView tv4 = new TextView(this);
    tv4.setTextSize(22);
    tv4.setText("  S2 ");
    tv4.setTypeface(typeface);
    tv4.setTextColor(Color.BLACK);
    tbrow0.addView(tv4);
    TextView tv5 = new TextView(this);
    tv5.setText(" S3  ");
    tv5.setTextSize(22);
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
        tbrow = new TableRow(this);
        tbrow.setGravity(View.TEXT_ALIGNMENT_CENTER);
        TextView t1v = new TextView(this);
        tbrow.setId(i);
        if(xIndex[i].equals("-")) {

            continue;
        }
        /*t1v.setText(Integer.toString(++howManyAdditions));
        t1v.setTextColor(Color.GRAY);
        t1v.setTextSize(24);
        t1v.setTypeface(typeface);
        t1v.setGravity(Gravity.CENTER);
        tbrow.addView(t1v); */
        TextView t2v = new TextView(this);
        t2v.setText(xMarks[i]);
        t2v.setTextColor(Color.GRAY);
        t2v.setTypeface(typeface);
        t2v.setTextSize(24);
        t2v.setGravity(Gravity.CENTER);
        tbrow.addView(t2v);
        TextView t3v = new TextView(this);
        t3v.setText(xMain[i]);
        t3v.setTextColor(Color.GRAY);
        t3v.setTypeface(typeface);
        t3v.setTextSize(24);
        t3v.setGravity(Gravity.CENTER);
        tbrow.addView(t3v);
        TextView t4v = new TextView(this);
        t4v.setText(xs1[i]);
        t4v.setTextColor(Color.GRAY);
        t4v.setTypeface(typeface);
        t4v.setTextSize(24);
        t4v.setGravity(Gravity.CENTER);
        tbrow.addView(t4v);
        TextView t5v= new TextView(this);
        t5v.setText(xs2[i]);
        t5v.setTextColor(Color.GRAY);
        t5v.setTypeface(typeface);
        t5v.setTextSize(24);
        t5v.setGravity(Gravity.CENTER);
        tbrow.addView(t5v);
        TextView t6v = new TextView(this);
        t6v.setText(xs3[i]);
        t6v.setTextColor(Color.GRAY);
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
int valForDelete;
     public void deleteData(View view)
     {
         db.collection(scode).document(keyOfData[valForDelete])
                 .delete()
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Toast.makeText(entryActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(entryActivity.this, "Something went wrong while deleting!", Toast.LENGTH_SHORT).show();
                     }
                 });

         tableEditButton.setVisibility(View.GONE);
         Button deleteButton=(Button)findViewById(R.id.deleteButton);
         deleteButton.setVisibility(View.GONE);
         paper1.setText("");
         paper2.setText("");
         paper3.setText("");
         paper4.setText("");
         marks.setText("");

         s1.setVisibility(View.GONE);
         s2.setVisibility(View.GONE);
         s3.setVisibility(View.GONE);
         bs1.setVisibility(View.VISIBLE);
         bs2.setVisibility(View.VISIBLE);
         bs3.setVisibility(View.VISIBLE);

         addButton.setVisibility(View.VISIBLE);
         saveButton.setVisibility(View.VISIBLE);
         howManyAdditions--;
         shiftArrays(valForDelete);
     }
     public void shiftArrays(int k)
     {
         xIndex[k]="-";
         xMarks[k]="-";
         xMain[k]="-";
         xs1[k]="-";
         xs2[k]="-";
         xs3[k]="-";
         uiRef();

     }


public void editTable(int keyValue)
{
    valForDelete=keyValue;
    tableEditButton.setVisibility(View.VISIBLE);
    bs1.setVisibility(View.GONE);
    bs2.setVisibility(View.GONE);
    bs3.setVisibility(View.GONE);
    s1.setVisibility(View.VISIBLE);
    s2.setVisibility(View.VISIBLE);
    s3.setVisibility(View.VISIBLE);
    saveButton.setVisibility(View.GONE);
    addButton.setVisibility(View.GONE);
    Button deleteButton=(Button)findViewById(R.id.deleteButton);
    deleteButton.setVisibility(View.VISIBLE);

    if(keyValue>4)
    {
        keyValue=4-Integer.parseInt(xIndex[4])-keyValue;
        editIndex=keyValue;
    }
    else editIndex=keyValue;

    marks.setText(xMarks[keyValue],TextView.BufferType.EDITABLE);
    paper1.setText(xMain[keyValue],TextView.BufferType.EDITABLE);
    paper2.setText(xs1[keyValue],TextView.BufferType.EDITABLE);
    paper3.setText(xs2[keyValue],TextView.BufferType.EDITABLE);
    paper4.setText(xs3[keyValue],TextView.BufferType.EDITABLE);

}
public void pageAdd2(View view)
    {

        bs3.setVisibility(View.GONE);
        s3.setVisibility(View.VISIBLE);
        s3.requestFocus();
    }
public void pageAdd1(View view)
{

    bs2.setVisibility(View.GONE);
    s2.setVisibility(View.VISIBLE);
    s2.requestFocus();
}

public void pageAdd(View view){
         bs1.setVisibility(View.GONE);
         s1.setVisibility(View.VISIBLE);
         s1.requestFocus();
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
