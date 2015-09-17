package ampr.counter_strikecallouts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Created by TwitchRat on 8/27/2015.
 */
public class RunDotTest extends FragmentActivity{

    Handler testHand;
    ImageView dot;
    Button but1;
    Button but2;
    Button but3;
    Button but4;
    TextView timerText;
    gameCountDownTimer cTimer;
    TestData.Question curQuestion;
    private int correctcount;
    private int questioncount;

    int timeUp;
    String playerAns;

    List<TestData.Question> questions;
    ArrayList<Integer> ints;

    private ImageView green;
    private ImageView red;


    Random ran;

    private final long startTime = 10000;
    private final long interval = 1000;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_dot_test);
        String textRet = getIntent().getExtras().getString("mapName");
        int resId = getResources().getIdentifier(textRet, "drawable", getPackageName());
        ImageView mapImg = (ImageView) findViewById(R.id.mapImg);
        mapImg.setBackgroundResource(R.drawable.dust2over);
        dot = (ImageView) findViewById(R.id.redDot);
        timerText = (TextView) findViewById(R.id.timer);
        but1 = (Button) findViewById(R.id.button1);
        but2 = (Button) findViewById(R.id.button2);
        but3 = (Button) findViewById(R.id.button3);
        but4 = (Button) findViewById(R.id.button4);
        //fadeOut = new AlphaAnimation(0,1);
        //fadeOut.setInterpolator(new AccelerateInterpolator());
        //fadeOut.setStartOffset(1000);
        //fadeOut.setDuration(1000);
        green = (ImageView) findViewById(R.id.greenGlow);
        red = (ImageView) findViewById(R.id.redGlow);


        timeUp = 0;
        int answered = 0;
        testHand = new Handler();
        ran = new Random();

        TestData tests = new TestData();
        InputStream ins = this.getResources()
                .openRawResource(R.raw.dust2);
        try {
            questions = tests.parse(ins);
        }catch(XmlPullParserException | IOException e2){
        Log.v("MYAPP","exception",e2);

        }
        cTimer = new gameCountDownTimer(startTime, interval);

        new runTests().execute();
        //testHand.post(runTest);


        cTimer.start();
        /*TextView texts = (TextView) findViewById(R.id.text3);
        texts.setText(textRet);*/










    }


/*
AsyncTask which generates a new array of question answers to display on a background thread.
 Chages buttons to reflect new ansers on UI thred and selects button to display correct answer.

 */
    private class runTests extends AsyncTask<Void, Void, Void>{
        protected Void doInBackground(Void... params) {

            ints = new ArrayList<Integer>();
            curQuestion = questions.get(ran.nextInt(questions.size()));
            ints = getShuffle(curQuestion.incorrect.length);
            return null;
        }

        @Override
        protected void onPostExecute(Void v){






            but1.setText(curQuestion.incorrect[ints.get(0)]);
            but2.setText(curQuestion.incorrect[ints.get(1)]);
            but3.setText(curQuestion.incorrect[ints.get(2)]);
            but4.setText(curQuestion.incorrect[ints.get(3)]);
            switch(ran.nextInt(4)){
                case(0):
                    but1.setText(curQuestion.answer);
                    break;
                case(1):
                    but2.setText(curQuestion.answer);
                    break;
                case(2):
                    but3.setText(curQuestion.answer);
                    break;
                case(3):
                    but4.setText(curQuestion.answer);
                    break;


            }





        }

        }




/*
Creates an array of integers counting up to the given max value and performs a shuffle of these
integer values.
Returns the shuffled array.

 */
    private ArrayList<Integer> getShuffle(int max){
        ArrayList<Integer> ints = new ArrayList<Integer>();

        for(int count = 0;count < max;count++){

            ints.add(count);

        }
        Collections.shuffle(ints);
        return ints;


    }
/*
Method called by answer buttons on activity screen being clicked.  Assigns the users answer
based on the button clicked to call the method, and checks the answer against the correct
answer.
Calls the glowbulb() animation handler based on the result of this comparison and calls to set a
new set of question answers along with a new question.

 */
    public void chooseAnswer(View v){
        switch (v.getId()) {
            case (R.id.button1):
                playerAns = but1.getText().toString();

                break;
            case (R.id.button2):
                playerAns = but2.getText().toString();

                break;
            case (R.id.button3):
                playerAns = but3.getText().toString();

                break;
            case (R.id.button4):
                playerAns = but4.getText().toString();

                break;
        }


        int isCorrect = checkAnswer(playerAns,curQuestion.answer);

        if(isCorrect == 1){
            glowBulb(green);
            correctcount++;

        }else{
            glowBulb(red);
        }

        questioncount++;
        setNewAnswer(v);


    }






    private void glowBulb(ImageView _color)
    {
        final ImageView color = _color;

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

        alphaAnimation.setStartOffset(1000);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {
                color.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                color.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        color.startAnimation(AnimationUtils.loadAnimation(
                getBaseContext(), android.R.anim.fade_out));
    }



    /*
    Checks the two given string against one another, if they match returns 1, else return 0.
     */
    private int checkAnswer(String player, String cor){
        if(player.equals(cor)){

            return 1;

        }
        return 0;


    }

    /*
    Call the runTests AsyncTask to update the UI with a new set of questions.
     */
    public void setNewAnswer(View v){
        new runTests().execute();
    }
    

    public class gameCountDownTimer extends CountDownTimer {
        public gameCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            timerText.setText("Time's up!");
            Intent result = new Intent(RunDotTest.this, Result.class);
            result.putExtra("answers", correctcount);
            result.putExtra("questions", questioncount);
            result.putExtra("testtype", "dot");
            RunDotTest.this.startActivity(result);

        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            timerText.setText("Time:" + (millisUntilFinished/1000));


        }

    }
}


