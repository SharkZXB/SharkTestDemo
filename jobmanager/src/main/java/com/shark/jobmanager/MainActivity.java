package com.shark.jobmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.birbit.android.jobqueue.JobManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JobManager jobManager = App.getInstance().getJobManager();
        Button btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobManager.addJobInBackground(new ZJob("1"));
                jobManager.addJobInBackground(new ZJob("2"));
                jobManager.addJobInBackground(new ZJob("3"));
                jobManager.addJobInBackground(new ZJob("4"));
                jobManager.addJobInBackground(new ZJob("5"));
                jobManager.addJobInBackground(new ZJob("6"));
                jobManager.addJobInBackground(new ZJob("7"));
                jobManager.addJobInBackground(new ZJob("8"));
                jobManager.addJobInBackground(new ZJob("9"));
                jobManager.addJobInBackground(new ZJob("18"));
                jobManager.addJobInBackground(new ZJob("28"));
                jobManager.addJobInBackground(new ZJob("38"));
                jobManager.addJobInBackground(new ZJob("48"));
                jobManager.addJobInBackground(new ZJob("58"));
                jobManager.addJobInBackground(new ZJob("68"));
                jobManager.addJobInBackground(new ZJob("78"));
                jobManager.addJobInBackground(new ZJob("88"));
            }
        });
    }
}