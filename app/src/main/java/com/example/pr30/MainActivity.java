package com.example.pr30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.PendingIntent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    final int TASK1_CODE = 1;
    final int TASK2_CODE = 2;
    final int TASK3_CODE = 3;

    public final static int STATUS_START = 100;
    public final static int STATUS_FINISH = 200;

    public final static String PARAM_TIME = "time";
    public final static String PARAM_PINTENT = "pendingIntent";
    public final static String PARAM_RESULT = "result";

    TextView tvTask1;
    TextView tvTask2;
    TextView tvTask3;
    Button btnStart;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTask1 = (TextView) findViewById(R.id.tvTask1);
        tvTask1.setText("Task1");
        tvTask2 = (TextView) findViewById(R.id.tvTask2);
        tvTask2.setText("Task2");
        tvTask3 = (TextView) findViewById(R.id.tvTask3);
        tvTask3.setText("Task3");

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    public void onClick(View v) {
        PendingIntent pi;
        Intent ServiceIntent = new Intent(this, MyService.class).putExtra(PARAM_TIME, 7);

        // Создаем PendingIntent для Task1

        pi = createPendingResult(TASK1_CODE,  ServiceIntent, 0);
        // Создаем Intent для вызова сервиса, кладем туда параметр времени
        // исозданный PendingIntent

        ServiceIntent.putExtra(PARAM_PINTENT, pi);
        // стартуемсервис
        startService(ServiceIntent);

        Intent ServiceIntent2 = new Intent(this, MyService.class).putExtra(PARAM_TIME, 4);
        pi = createPendingResult(TASK2_CODE, ServiceIntent2, 0);

        ServiceIntent2.putExtra(PARAM_PINTENT, pi);
        startService(ServiceIntent2);


        Intent ServiceIntent3 = new Intent(this, MyService.class).putExtra(PARAM_TIME, 6);
        pi = createPendingResult(TASK3_CODE, ServiceIntent3, 0);
        ServiceIntent3.putExtra(PARAM_PINTENT, pi);
        startService(ServiceIntent3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "requestCode = "+ requestCode + ", resultCode = "
                + resultCode);

        // Ловим сообщения о старте задач
        if(resultCode == STATUS_START) {
            switch(requestCode) {
                case TASK1_CODE:
                tvTask1.setText("Task1 start");
                break;
                case TASK2_CODE:
                tvTask2.setText("Task2 start");
                break;
                case TASK3_CODE:
                tvTask3.setText("Task3 start");
                break;
            }
        }

        // Ловим сообщения об окончании задач
        if(resultCode == STATUS_FINISH) {
            int result = data.getIntExtra(PARAM_RESULT, 0);
            switch(requestCode) {
                case TASK1_CODE:
                tvTask1.setText("Task1 finish, result = "+ result);
                break;
                case TASK2_CODE:
                tvTask2.setText("Task2 finish, result = "+ result);
                break;
                case TASK3_CODE:
                tvTask3.setText("Task3 finish, result = "+ result);
                break;
            }
        }
    }


}
