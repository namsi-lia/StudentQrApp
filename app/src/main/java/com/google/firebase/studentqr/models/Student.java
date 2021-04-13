package com.google.firebase.studentqr.models;

public class Student {
    String mAvatarUrl;
    String mClass;
    String mEmail;
    String mRoleName;
    int mUserId;
    String mUserName;
    public Student(String avatarUrl, String studentClass, String email, String roleName, int userId, String userName) {
        this.mAvatarUrl = avatarUrl;
        this.mClass = studentClass;
        this.mEmail = email;
        this.mRoleName = roleName;
        this.mUserId = userId;
        this.mUserName = userName;

    }
    public String getImageUrl(){
        return mAvatarUrl;
    }

    public String getClasses(){
        return mClass;
    }

    public String getEmail(){
        return mEmail;
    }

    public String getRoleName(){
        return mRoleName;
    }

    public int getUserId(){
        return mUserId;
    }

    public String getUserName(){
        return mUserName;
    }
}


