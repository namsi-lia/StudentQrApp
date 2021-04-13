package com.google.firebase.studentqr.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.studentqr.models.FinalStudent;
import com.google.firebase.studentqr.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinalStudentListAdapter extends RecyclerView.Adapter<FinalStudentListAdapter.FilteredViewHolder> {
    private Context mContext;
    private ArrayList<FinalStudent> mFilteredStudents;

    public FinalStudentListAdapter(Context applicationContext, ArrayList<FinalStudent> mFilteredStudents, Context context, ArrayList<FinalStudent> filteredDetails) {
        mContext = context;
    }

    @NonNull
    @Override
    public FinalStudentListAdapter.FilteredViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filteredlistitems, parent, false);
        FilteredViewHolder viewHolder = new FilteredViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilteredViewHolder holder, int position) {
        holder.bindFilteredStudent(mFilteredStudents.get(position));

    }
    @Override
    public int getItemCount() {
        return mFilteredStudents.size();

    }

    public class FilteredViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userImageView) ImageView mStudentImageView;
        @BindView(R.id.studentNameTextView) TextView mStudentNameTextView;
        @BindView(R.id.filteredIdTextView) TextView mStudentIdTextView;
        @BindView(R.id.classTextView) TextView mClassTextView;
        @BindView(R.id.emailTextView) TextView mEmailTextView;
        @BindView(R.id.roleNameTextView) TextView mRoleNameTextView;

        private Context mContext;

        public FilteredViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        @SuppressLint("SetTextI18n")
        public void bindFilteredStudent(FinalStudent finalStudent) {
            Picasso.with(mContext).load(finalStudent.getImageUrl()).into(mStudentImageView);
            mStudentNameTextView.setText(finalStudent.getUserName());
            mStudentIdTextView.setText("Student ID: "+String.valueOf(finalStudent.getUserId()));
            mClassTextView.setText(finalStudent.getClasses());
            mEmailTextView.setText(finalStudent.getEmail());
            mRoleNameTextView.setText(finalStudent.getRoleName());
        }
    }
}
