package com.example.laundryuas.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundryuas.R;

public class SupportActivity extends AppCompatActivity {

    private EditText edtSubject, edtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        edtSubject = findViewById(R.id.edtSubject);
        edtMessage = findViewById(R.id.edtMessage);

        ConstraintLayout btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipient = "rafli.412020017@civitas.ukrida.ac.id";
                String subject = edtSubject.getText().toString().trim();
                String message = edtMessage.getText().toString().trim();

                sendEmail(recipient, subject, message);
            }
        });
    }

    private void sendEmail(String recipient, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(intent, "Choose a client"));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}