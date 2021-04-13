package com.google.firebase.studentqr.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.studentqr.R;
import com.google.firebase.studentqr.adapters.FinalStudentListAdapter;
import com.google.firebase.studentqr.models.FinalStudent;
import com.google.firebase.studentqr.services.StudentService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FilteredResults  extends AppCompatActivity {
    @BindView(R.id.filteredRecyclerView)
    RecyclerView filteredRecyclerView;
    @BindView(R.id.filteredStudentsTextView)
    TextView mFilteredResultsTextView;

    private FinalStudentListAdapter filterAdapter;
    public ArrayList<FinalStudent> mFilteredStudents = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState, Context context, ArrayList<FinalStudent> filteredDetails) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_results);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String queryDate = intent.getStringExtra("dateSelected");
        String classId = intent.getStringExtra("classId");

        mFilteredResultsTextView.setText("These are the results for the date " + queryDate + " and class " + classId);

        getFilteredList(classId, queryDate, context, filteredDetails);
    }

    private void getFilteredList(String classId, String queryDate, Context context, ArrayList<FinalStudent> filteredDetails) {
        final StudentService studentService = new StudentService();

        StudentService.filterAttendance(classId, queryDate, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mFilteredStudents = studentService.processFilteredResults(response);

                FilteredResults.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        filterAdapter = new FinalStudentListAdapter(getApplicationContext(), mFilteredStudents, context, filteredDetails);
                        filteredRecyclerView.setAdapter(filterAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FilteredResults.this);
                        filteredRecyclerView.setLayoutManager(layoutManager);
//                        filteredRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });

    }
}