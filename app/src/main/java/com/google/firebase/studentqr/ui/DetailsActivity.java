package com.google.firebase.studentqr.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.studentqr.R;
import com.google.firebase.studentqr.adapters.StudentDetailsListAdapter;
import com.google.firebase.studentqr.models.Student;
import com.google.firebase.studentqr.services.StudentService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailsActivity  extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.timeTextView) TextView mDetailsTextView;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.timeButton) Button mTimeButton;

    private StudentDetailsListAdapter mAdapter;
    public static final String TAG = DetailsActivity.class.getSimpleName();
    public ArrayList<Student> mStudentDetails = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState, Context context, ArrayList<Student> details) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String scanResult = intent.getStringExtra("scan-result");
        mDetailsTextView.setText("The students Id is" + scanResult);
        getDetails(scanResult, context, details);
        mTimeButton.setOnClickListener(this);
    }

    public void getDetails(String scanResult, Context context, ArrayList<Student> details) {
        final StudentService studentService = new StudentService();

        StudentService.findStudent(scanResult, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mStudentDetails = studentService.processResults(response);
                DetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter = new StudentDetailsListAdapter(getApplicationContext(),mStudentDetails, context, details);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);

                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mTimeButton ) {
            Intent scanIntent = getIntent();
            String scanResult = scanIntent.getStringExtra("scan-result");


            TimeZone tz = TimeZone.getTimeZone("GMT+3:00");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());
            Intent intent = new Intent(DetailsActivity.this, TimeConfirmation.class);
            intent.putExtra("dtTime", nowAsISO);
            intent.putExtra("studentId", scanResult);

            startActivity(intent);
        }
    }
}


