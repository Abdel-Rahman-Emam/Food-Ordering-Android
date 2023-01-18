package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class signInActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText email,password;
    Button signinBtn, registerBtn;
    ProgressDialog pDialog;
    String[] arr = {"1","3","4"};
    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailEnter);
        password = findViewById(R.id.passwordEnter);
        registerBtn = findViewById(R.id.registerBtn);
        signinBtn = findViewById(R.id.signinBtn);
        pDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerScreen = new Intent(signInActivity.this, registerActivity.class);
                registerScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(registerScreen);
            }
        });
        /*mUser= FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null){
            Intent i = new Intent(signInActivity.this, MainActivity.class);
            startActivity(i);
        }*/
    }

    private void LogIn() {
        String getEmail = email.getText().toString();
        String getPassword = password.getText().toString();
        if(!isValidEmail(getEmail) || !getEmail.endsWith("@eng.asu.edu.eg")){
            email.setError("Email is invalid");
        }else if(getPassword.isEmpty()){
            password.setError("Please enter a password");
        }
        else {
            pDialog.setMessage("Signing in..");
            pDialog.setTitle("Sign In");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            mAuth.signInWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pDialog.dismiss();
                        Intent mainActivity = new Intent(signInActivity.this,MainActivity.class);
                        //mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(mainActivity);
                        Toast.makeText(signInActivity.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        pDialog.dismiss();
                        Toast.makeText(signInActivity.this,"Credentials Wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
