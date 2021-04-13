package com.google.firebase.studentqr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.studentqr.R;
import com.google.firebase.studentqr.services.StudentService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TimeConfirmation  extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,View.OnClickListener {
    @BindView(R.id.planets_spinner) Spinner mSpinner;
    @BindView(R.id.dateButton) Button mDateButton;
    @BindView(R.id.dateTextView) TextView mSelectedDateTextView;
    @BindView(R.id.filterButton) Button mFilteredList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_time_confirmation);
        ButterKnife.bind(this);


        mSpinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);

        Intent intent = getIntent();
        String dtAndTime = intent.getStringExtra("dtTime");
        String studentId = intent.getStringExtra("studentId");

        postTimeToApi(studentId, dtAndTime);

        mFilteredList.setOnClickListener(this);

    }

    private void postTimeToApi(String studentId, String dtAndTime) {
        final StudentService studentService = new StudentService();

        StudentService.postTimeRequest(studentId, dtAndTime, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mFilteredList ) {
            Intent intent = new Intent(TimeConfirmation.this, FilteredResults.class);
            String classId = mSpinner.getSelectedItem().toString();
            String queryDate = mSelectedDateTextView.getText().toString();
            intent.putExtra("dateSelected", queryDate);
            intent.putExtra("classId", classId);
            startActivity(intent);
        }
    }

   // public void onItemSelected(AdapterView<?> parent, View view, int position, long id, int pos) {
        // An item was selected. You can retrieve the selected item using

  //  }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectdClass = parent.getItemAtPosition(pos).toString();
        Toast.makeText(TimeConfirmation.this, selectdClass, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
