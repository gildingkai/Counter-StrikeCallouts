package ampr.counter_strikecallouts;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by TwitchRat on 9/16/2015.
 */
public class Result extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int ans = getIntent().getExtras().getInt("answers");
        int questions = getIntent().getExtras().getInt("questions");
        String test = getIntent().getExtras().getString("testtype");

        TextView attempts = (TextView) findViewById(R.id.attempts);
        attempts.setText("Questions attempted: "+ questions);

        TextView correct = (TextView) findViewById(R.id.correct);
        correct.setText("Correct answers given: " + ans);


        TextView ratio = (TextView) findViewById(R.id.percentage);
        ratio.setText("Percentage Correct: " + (ans*100.0f)/questions);

    }
}
