package com.google.firebase.studentqr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.studentqr.R;
import com.google.firebase.studentqr.models.Student;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentDetailsListAdapter extends RecyclerView.Adapter<StudentDetailsListAdapter.StudentViewHolder> {
    private ArrayList<Student> mStudentDetails;
    private Context mContext;

    public StudentDetailsListAdapter(Context applicationContext, ArrayList<Student> mStudentDetails, Context context, ArrayList<Student> details) {
        mContext = context;

    }

    @NonNull
    @Override
    public StudentDetailsListAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_details_list_item, parent, false);
        StudentViewHolder viewHolder = new StudentViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull StudentDetailsListAdapter.StudentViewHolder holder, int position) {
        holder.bindStudent(mStudentDetails.get(position));

    }


    @Override
    public int getItemCount() {
        return mStudentDetails.size();

    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.idTextView) TextView mStudentIdTextView;
        @BindView(R.id.studentImageView) ImageView mStudentImageView;
        @BindView(R.id.studentNameTextView) TextView mStudentNameTextView;
        @BindView(R.id.classTextView) TextView mClassTextView;
        @BindView(R.id.emailTextView) TextView mEmailTextView;
        @BindView(R.id.roleNameTextView) TextView mRoleNameTextView;

        @BindView(R.id.arrivalTime) TextView mArrivalTime;
        @BindView(R.id.arrivalDate) TextView mArrivalDate;


        private Context mContext;


        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindStudent(Student student) {
            mStudentIdTextView.setText("The Students ID is: "+String.valueOf(student.getUserId()));
            Picasso.with(mContext).load(student.getImageUrl()).into(mStudentImageView);
            mStudentNameTextView.setText(student.getUserName());
            mClassTextView.setText(student.getClasses());
            mEmailTextView.setText(student.getEmail());
            mRoleNameTextView.setText(student.getRoleName());


            long currentTime= System.currentTimeMillis();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(currentTime);
            Calendar showTime= Calendar.getInstance(TimeZone.getTimeZone("%1$tI:%1$tM:%1$tS %1$Tp"));
            mArrivalTime.setText((CharSequence) showTime);

            Date dt = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(dt);
            mArrivalDate.setText(formattedDate);
        }
        }
    }

