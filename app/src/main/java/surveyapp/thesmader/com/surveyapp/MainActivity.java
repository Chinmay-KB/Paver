package surveyapp.thesmader.com.surveyapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public String subjectCode;
    public String yearValue;
    public String semesterValue;
    public String email;
    public RadioGroup stream1,stream2;
    String midendsem,stream;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        midendsem="Mid Semester";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stream="B.Tech";
        stream1=(RadioGroup)findViewById(R.id.stream1);
        stream2=(RadioGroup)findViewById(R.id.stream2);
        mAuth = FirebaseAuth.getInstance();


    }
    public void addMSListener(View view)
    {
        RadioGroup midend=(RadioGroup)findViewById(R.id.sem);
        int selectedId=midend.getCheckedRadioButtonId();
        RadioButton rb=findViewById(selectedId);
        midendsem=rb.getText().toString();
        Toast.makeText(MainActivity.this,
                rb.getText(), Toast.LENGTH_SHORT).show();
    }
    public void streamChoice1(View view)
    {

       int selectedId=stream1.getCheckedRadioButtonId();
        RadioButton rb=findViewById(selectedId);
        stream=rb.getText().toString();
        stream2.clearCheck();
        Toast.makeText(MainActivity.this,
                rb.getText(), Toast.LENGTH_SHORT).show();
    }
    public void streamChoice2(View view)
    {

        int selectedId=stream2.getCheckedRadioButtonId();
        RadioButton rb=findViewById(selectedId);
        stream=rb.getText().toString();
        stream1.clearCheck();
        Toast.makeText(MainActivity.this,
                rb.getText(), Toast.LENGTH_SHORT).show();
    }


    public void dataEntry(View view) {
        entryActivity x = new entryActivity();

        EditText editText = (EditText) findViewById(R.id.subject_code);
        String subjectCode = editText.getText().toString();
        Spinner year_select = (Spinner) findViewById(R.id.year_select);
        yearValue = year_select.getSelectedItem().toString();
        Spinner semester_select = (Spinner) findViewById(R.id.semester_select);
        semesterValue = semester_select.getSelectedItem().toString();
        x.scode = subjectCode;
        x.semesterValue = semesterValue;
        x.yearValue = yearValue;
        FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();;
        if (yearValue.equals("Choose Year") || semesterValue.equals("Choose Semester") || midendsem==null || stream==null)
            Toast.makeText(getApplicationContext(), "Please provide appropriate input", Toast.LENGTH_SHORT).show();
        else {
            user.put("Subject Code",subjectCode.toUpperCase());
            user.put("Year",yearValue);
            user.put("Semester",semesterValue);
            user.put("Stream",stream);
            user.put("Mid or End sem",midendsem);
            db.collection(users.getEmail())
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("FirestoreDemo", "DocumentSnapshot added with ID " + documentReference.getId());

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FirestoreDemo", "Error adding document", e);
                        }
                    });
            Intent i=new Intent(getApplicationContext(),entryActivity.class);
            i.putExtra("subject",subjectCode);
            i.putExtra("year",yearValue);
            i.putExtra("semester",semesterValue);
            i.putExtra("stream",stream);
            i.putExtra("MidEnd",midendsem);
            i.putExtra("source","main");
            startActivity(i);
        }

    }
}