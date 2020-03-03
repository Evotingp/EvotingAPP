package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ClickOnVoter(View view) {
        Intent i = new Intent(MainActivity.this,VoterLoginActivity.class);
        startActivity(i);
    }

    public void ClickOnCandidate(View view) {
        Intent i = new Intent(MainActivity.this,CandidateLoginActivity.class);
        startActivity(i);
    }
}
