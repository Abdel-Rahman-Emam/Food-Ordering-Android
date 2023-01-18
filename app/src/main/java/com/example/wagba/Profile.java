package com.example.wagba;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile")
public class Profile {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="user_id")
    public int uid;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name="password")
    public String password;
    @ColumnInfo(name="user_name")
    public String userName;
    @ColumnInfo(name="phone_num")
    public String phoneNum;
    public Profile(String email, String password, String userName, String phoneNum) {
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.password = password;
    }
}
