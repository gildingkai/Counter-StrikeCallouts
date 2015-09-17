package ampr.counter_strikecallouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by TwitchRat on 8/25/2015.
 */


public class SelectMap extends AppCompatActivity{

    String[] maps = {"Dust2","Inferno","Mirage"};
    private String[] mapNames = {
            "dust2","inferno","mirage",

    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);



            GridView gridview = (GridView) findViewById(R.id.mapselectgrid);
            gridview.setAdapter(new ImageAdapter(this));

            gridview.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mapAct = new Intent(SelectMap.this, Countdown.class);
                    mapAct.putExtra("mapName", mapNames[position]);
                    mapAct.putExtra("testType", "dotTest");
                    SelectMap.this.startActivity(mapAct);
                }



            });





    }



}





