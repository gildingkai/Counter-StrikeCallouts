package ampr.counter_strikecallouts;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by TwitchRat on 8/25/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageBut;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageBut = new ImageView(mContext);
            imageBut.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageBut.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageBut.setPadding(8, 8, 8, 8);
        } else {
            imageBut = (ImageView) convertView;
        }

        imageBut.setImageResource(mThumbIds[position]);




        return imageBut;
    }



    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.dust2, R.drawable.inferno,
            R.drawable.mirage,
    };

    private String[] mapNames = {
            "dust2","inferno","mirage",

    };
}