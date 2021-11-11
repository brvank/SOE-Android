package com.example.soe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.HintRequest;

public class LogInActivity extends AppCompatActivity {

    //initializing the views
    private EditText etPhoneNumber;
    private TextView tvFooter, tvSignUP, tvLogin;
    private ImageView ivSelectNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //instantiating the views
        etPhoneNumber = findViewById(R.id.tv_phone_number_login);
        tvFooter = findViewById(R.id.tv_footer_login);
        tvSignUP = findViewById(R.id.tv_signup_login);
        tvLogin = findViewById(R.id.tv_login_login);
        ivSelectNumber = findViewById(R.id.iv_select_number_login);

        //TODO: to set the tvFooter with the values of company name and rights reserved
        //tvFooter.setText(String.valueOf(R.string.copyright));

        ivSelectNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNumber();
            }
        });

        tvSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ProfileEditActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //to open a dialog fragment to select numbers
        checkNumber();
    }

    private void checkNumber() {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();


        PendingIntent intent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        try
        {
            startIntentSenderForResult(intent.getIntentSender(), 1, null, 0, 0, 0,new Bundle());
        }
        catch (IntentSender.SendIntentException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            // Obtain the phone number from the result
            Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);
            etPhoneNumber.setText(credentials.getId().substring(3)); //get the selected phone number
//Do what ever you want to do with your selected phone number here

        }
        else if (requestCode == 1 && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE)
        {
            // *** No phone numbers available ***
            Toast.makeText(LogInActivity.this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }


    }
}