package com.example.mvvmarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mvvmarchitecture.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Thread counterThread;
    int count = 0;
    boolean isCounterInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isCounterInProgress = false;
        counterThread = new Thread(() -> {
            while (isCounterInProgress) {
                try {
                    Thread.sleep(1000);
                    count++;

                    binding.tvCounter.post(() -> {
                        binding.tvCounter.setText("Counter:" + count);
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.btnStart.setOnClickListener(v -> {
            if (!isCounterInProgress) {
                isCounterInProgress = true;
                counterThread.start();
            }
        });

        binding.btnEnd.setOnClickListener(v -> {
            isCounterInProgress = false;
        });
    }
}