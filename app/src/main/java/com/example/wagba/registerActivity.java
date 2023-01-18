package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registerActivity extends AppCompatActivity {
    EditText email,password,name,phone;
    Button registerBtn, signinBtn;
    ProgressDialog pDialog;
    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailEnter);
        password = findViewById(R.id.passwordEnter);
        name = findViewById(R.id.nameEnter);
        phone = findViewById(R.id.phoneEnter);
        registerBtn = findViewById(R.id.registerBtn);
        signinBtn = findViewById(R.id.signinBtnReg);
        pDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Authenticate();
            }
        });
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginScreen = new Intent(registerActivity.this,signInActivity.class);
                loginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginScreen);
            }
        });
    }

    private void Authenticate() {
        String getEmail = email.getText().toString();
        String getPassword = password.getText().toString();
        String getPhone = phone.getText().toString();
        String getName = name.getText().toString();
        ProfileDatabase pdb = Room.databaseBuilder(getBaseContext(), ProfileDatabase.class,"profile-database").allowMainThreadQueries().build();

        if(!isValidEmail(getEmail) || !getEmail.endsWith("@eng.asu.edu.eg")){
            email.setError("Email is invalid");
        }else if(getPassword.isEmpty() || !isValidPassword(getPassword)){
            password.setError("Please enter a stronger password");
        }
        else{
            pDialog.setMessage("Registering..");
            pDialog.setTitle("Registration");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            mAuth.createUserWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Profile newUser = new Profile(getEmail,getPassword,getName,getPhone);
                        pdb.daoProfile().insertAll(newUser);
                        pDialog.dismiss();
                        Intent loginScreen = new Intent(registerActivity.this,signInActivity.class);
                        loginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(loginScreen);
                        Toast.makeText(registerActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        pDialog.dismiss();
                        Toast.makeText(registerActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}