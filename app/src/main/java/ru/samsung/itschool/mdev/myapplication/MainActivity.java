package ru.samsung.itschool.mdev.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button btn, btn2;
    private Handler h;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        tv = findViewById(R.id.textView3);
        // Handler позволяет отправлять сообщения в другие потоки
        // Looper - запускаетт цикл обработки сообщений
        // getMainLooper() - цикл в главном потоке (UI)
        h = new Handler(Looper.getMainLooper())  {
            @Override
            public void handleMessage(@NonNull Message msg) {
                tv.setText("Сделано операций: "+ msg.what);
            }
        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=1;i <= 10; i++) {
                            doSlow();
                            h.sendEmptyMessage(i);
                             // так делать не надо
                            // tv.setText("Сделано операций: "+ i);
                            // плохо!!! нельзя менять UI из другого потока
                            Log.d("RRR","Сделано операций: "+ i);
                        }
                    }
                });
                thread.start();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RRRR", "Какой-то текст!!!!");
            }
        });
    }

    public void doSlow() {
        try {
           // Thread.sleep(10000);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}