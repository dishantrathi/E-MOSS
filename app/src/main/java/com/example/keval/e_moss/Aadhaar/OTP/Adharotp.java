package com.example.keval.e_moss.Aadhaar.OTP;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keval.e_moss.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Adharotp extends AppCompatActivity {
    FirebaseAuth auth;
    EditText e1, e2;
    Button b1;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verificarion_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adharotp);
        e1 = (EditText) findViewById(R.id.edt);
        e2 = (EditText) findViewById(R.id.edt_vcode);
        b1 = (Button) findViewById(R.id.btn_otp);
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificarion_code = s;
                Toast.makeText(getApplicationContext(), "Code sent to number", Toast.LENGTH_LONG).show();
            }

        };
    }

    public void send_sms(View v) {

        String number = e1.getText().toString();
        if (number.equals("439693560072")) {
            number = "+917016424634";
        }
        if (number.equals("997085470821")) {
            number = "+918141105858";
        }
        if (number.equals("302493252415")) {
            number = "+916353870925";
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, 60, TimeUnit.SECONDS, this, mCallback
        );
    }

    public void signInWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User sign in success", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void verify(View v) {
        String imput_code = e2.getText().toString();
        verifyPhoneNumber(verificarion_code, imput_code);
    }

    public void verifyPhoneNumber(String verifycode, String input_code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifycode, input_code);
        signInWithPhone(credential);
    }
}
