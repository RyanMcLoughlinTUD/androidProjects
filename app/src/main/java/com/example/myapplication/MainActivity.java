package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private TextView outputText;
    private ImageView imageView2;
    private static final String TAG = "IT472";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputText = findViewById(R.id.outputText);
        imageView2 = findViewById(R.id.imageView2);
    }

    public void runTask (View v) {
// VERSION 1a
// Running a task in a separate thread
        Thread thread = new Thread(() -> {
            Log.i(TAG, "Thread Started");
            try {
                loadImageInBackground();
// simulated long-running task
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Thread Finished");
        });
        thread.start();

    }

    public void generateContent(View view) {
        outputText.setText("Random number: \n"+ Math.random());
    }
    public void loadImageInBackground() {
        // Creates a service to execute threads
        ExecutorService service = Executors.newSingleThreadExecutor();
        // Creates a handle to recover the result from the main (UI) thread
        Handler handler = new Handler(Looper.getMainLooper());

        service.execute(new Runnable() {
            @Override
            public void run() {
                // Background work (e.g., read image)

                Bitmap bitmap = ReadImage.readImage("https://miro.medium.com/v2/resize:fit:640/format:webp/1*6tg6lq7Qz1CLFBBu2Q4nDw.png");
                        // Update UI on the main (UI) thread using Handler
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Fill this function to set the image in your UI
                                setImage(bitmap);
                            }
                        });
            }
        });
    }
    public void setImage(Bitmap bitmap){
        showToast("setImage has run");
        imageView2.setImageBitmap(bitmap);
    }
    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }

}
