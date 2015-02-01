package food.hackathon.clouder.pocketfridge;

/**
 * Created by Evan on 2015/1/31.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MyExpandableListItemAdapter extends
        ExpandableListItemAdapter<FoodData> {
    private final Context mContext;
    private final BitmapCache mMemoryCache;
    /**
     * Creates a new ExpandableListItemAdapter with the specified list, or
     * an empty list if items == null.
     */
    public MyExpandableListItemAdapter(final Context context,
                                       final ArrayList<FoodData> items) {

        super(context, R.layout.expandablelistitem_card,
                R.id.expandablelistitem_card_title,
                R.id.expandablelistitem_card_content, items);
        mContext = context;
        mMemoryCache = new BitmapCache();

    }

    @Override
    public View getTitleView(final int position, final View convertView,
                             final ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(
                R.layout.expandablelistitem_title, null);
        TextView tv = (TextView) v.findViewById(R.id.item_Title);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        switch(getItem(position).category){
            case "Vegetable":
                imageView.setImageResource(R.drawable.veg);
                break;
            case "Fruit":
                imageView.setImageResource(R.drawable.fru);
                break;
            case "Meat":
                imageView.setImageResource(R.drawable.meat);
                break;
            case "Fish":
                imageView.setImageResource(R.drawable.fish);
                break;
            case "Bread":
                imageView.setImageResource(R.drawable.bread);
                break;
            case "Dairy":
                imageView.setImageResource(R.drawable.milk);
                break;
            case "Junkfood":
                imageView.setImageResource(R.drawable.pizza);
                break;
            case "Others":
                imageView.setImageResource(R.drawable.egg);
                break;
            case "Default":
                break;
        }
        if (tv == null) {
            tv = new TextView(mContext);
        }
        tv.setText(getItem(position).title);
        tv.setTextColor(Color.BLACK);
        tv.setAlpha(0.87f);
        tv.setTextSize(35);

        tv = (TextView) v.findViewById(R.id.item_Date);
        tv.setText(getItem(position).time);
        tv.setTextColor(Color.BLACK);
        tv.setAlpha(0.54f);
        tv.setTextSize(14);
        Button addButton = (Button) v.findViewById(R.id.Addbutton);
        addButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                    notifyDataSetChanged();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery(ConstantVariable.m_objectID);
                    query.fromLocalDatastore();
                    query.getInBackground(getItems().get(position).objectID, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                object.unpinInBackground();
                            } else {
                                Log.e("Debug",e.toString());
                            }
                        }
                    });
                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery(ConstantVariable.m_objectID);
                    query2.getInBackground(getItems().get(position).objectID, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.deleteInBackground();
                        } else {
                            Log.e("Debug",e.toString());
                        }
                    }
                    });
                    remove(position);
            }

        });

        return v;
    }

    @Override
    public View getContentView(final int position, final View convertView,
                               final ViewGroup parent) {

        View v = LayoutInflater.from(mContext).inflate(
                R.layout.expandablelistitem_content, parent, false);

        TextView textView = (TextView) v.findViewById(R.id.contentTextView);
        textView.setText(getItem(position).category);
        textView.setTextColor(Color.BLACK);
        textView.setAlpha(0.53f);

        return v;
    }

    private void addBitmapToMemoryCache(final int key, final Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(final int key) {
        return mMemoryCache.get(key);
    }



}
