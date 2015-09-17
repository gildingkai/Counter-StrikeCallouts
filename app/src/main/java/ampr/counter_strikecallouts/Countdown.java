package ampr.counter_strikecallouts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


/**
 * Created by TwitchRat on 8/28/2015.
 */
public class Countdown extends AppCompatActivity {



    Handler hands;
    TextView mainCount;
    int count;
    String textRet;
    String testType;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        textRet = getIntent().getExtras().getString("mapName");
        testType = getIntent().getExtras().getString("testType");

        mainCount = (TextView) this.findViewById(R.id.counter);
        count = 3;
        hands = new Handler();
        hands.post(countdown);


    }

    Runnable countdown = new Runnable() {




        public void run() {
            Message message = Message.obtain();
            mainCount.setText(String.valueOf(count));
            count--;
            if(count == 0){
                Intent mapAct = new Intent(Countdown.this, RunDotTest.class);
                mapAct.putExtra("mapName", textRet);
                mapAct.putExtra("testType", testType);
                Countdown.this.startActivity(mapAct);
                hands.removeCallbacks(countdown);

            }else {
                hands.postDelayed(this, 1000);
            }

        }

    };
}