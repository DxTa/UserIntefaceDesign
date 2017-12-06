package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by daniel on 23/11/2017.
 */

//TODO build the screen
//- Notification/new screen after sending the leakage report
public class ReportSuccessActivity extends AppCompatActivity {

    @Bind(R.id.okButton) TextView _okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_success);

        ButterKnife.bind(this);

        _okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                finish();
            }
        });

    }
}
