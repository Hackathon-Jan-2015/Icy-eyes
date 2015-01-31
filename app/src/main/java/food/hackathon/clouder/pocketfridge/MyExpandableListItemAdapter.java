package food.hackathon.clouder.pocketfridge;

/**
 * Created by Evan on 2015/1/31.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

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
        //setActionViewResId(R.id.expandablelistitem_card_title);

    }

    @Override
    public View getTitleView(final int position, final View convertView,
                             final ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(
                R.layout.expandablelistitem_title, null);
        TextView tv = (TextView) v.findViewById(R.id.item_Title);
        if (tv == null) {
            tv = new TextView(mContext);
        }
        tv.setText(getItem(position).title);
        tv.setTextColor(Color.BLACK);
        tv.setAlpha(0.87f);
        tv.setTextSize(16);

        tv = (TextView) v.findViewById(R.id.item_Date);
        tv.setText(getItem(position).time);
        tv.setTextColor(Color.BLACK);
        tv.setAlpha(0.54f);
        tv.setTextSize(14);
        Button addButton = (Button) v.findViewById(R.id.Addbutton);
        addButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                    remove(position);
                    notifyDataSetChanged();
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
