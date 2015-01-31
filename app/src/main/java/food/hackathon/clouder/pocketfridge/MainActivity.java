package food.hackathon.clouder.pocketfridge;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    ArrayList<View> viewList;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    ImageButton upperFridge, downerFridge;
    ListView upperList, downerList;
    MyExpandableListItemAdapter ulistAdapter,dlistAdapter;
    AlphaInAnimationAdapter alphaInAnimationAdapter;


    private static final int login=0;
    private String m_objectID = null ;
    private ArrayList<FoodData> upperFoodList = new ArrayList<FoodData>();
    private ArrayList<FoodData> downerFoodList = new ArrayList<FoodData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);

        LayoutInflater mInflater = getLayoutInflater().from(this);

        View v1 = mInflater.inflate(R.layout.fragment_main, null);
        View v2 = mInflater.inflate(R.layout.fragment_upper, null);
        View v3 = mInflater.inflate(R.layout.fragment_downer, null);

        viewList = new ArrayList<View>();
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);
        mViewPager.setAdapter(new MyViewPagerAdapter(viewList));
        mViewPager.setCurrentItem(0);

        upperFridge = (ImageButton) v1.findViewById(R.id.upperButton);
        downerFridge = (ImageButton) v1.findViewById(R.id.downerButton);
        upperList = (ListView) v2.findViewById(R.id.upper_ListView);
        downerList = (ListView) v3.findViewById(R.id.downer_ListView);
        setUpButton();
        setUpListView();
    }

    private void setUpButton(){

        upperFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1,true);
            }
        });
        downerFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2,true);
            }
        });


    }
    private void setUpListView(){

        ulistAdapter = new MyExpandableListItemAdapter(
                this, downerFoodList);
        alphaInAnimationAdapter = new AlphaInAnimationAdapter(ulistAdapter);
        alphaInAnimationAdapter.setAbsListView(upperList);
        assert alphaInAnimationAdapter.getViewAnimator() != null;
        alphaInAnimationAdapter.getViewAnimator().setInitialDelayMillis(500);
        upperList.setAdapter(alphaInAnimationAdapter);

        dlistAdapter = new MyExpandableListItemAdapter(
                this, downerFoodList);
        alphaInAnimationAdapter = new AlphaInAnimationAdapter(dlistAdapter);
        alphaInAnimationAdapter.setAbsListView(downerList);
        assert alphaInAnimationAdapter.getViewAnimator() != null;
        alphaInAnimationAdapter.getViewAnimator().setInitialDelayMillis(500);
        downerList.setAdapter(alphaInAnimationAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivityForResult(intent, login);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case login:
                if(resultCode == ConstantVariable.RESULT_LOGIN){
                    Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
                    setUpUserData(data.getExtras().getString("HashCode"));
                }

                else if(resultCode == ConstantVariable.RESULT_SIGNUP){
                    Toast.makeText(this, "Signed up", Toast.LENGTH_SHORT).show();
                    createUserClass(data.getExtras().getString("HashCode"));
                }
                break;
        }
    }

    private void setUpUserData(String hashCode){

        Log.e("Debug",hashCode);

        m_objectID = hashCode;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(m_objectID);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    upperFoodList.clear();
                    downerFoodList.clear();
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for(ParseObject parseObject :scoreList){
                        if(parseObject.getString("Title").equals("Default")) {
                            FoodData foodData = new FoodData(parseObject.getString("Title"), parseObject.getString("place"), parseObject.getString("Time"), parseObject.getDate("ExpiringDate"), parseObject.getString("Category"));
                            switch (foodData.getPlace()) {
                                case "U":
                                    upperFoodList.add(foodData);
                                    break;
                                case "D":
                                    downerFoodList.add(foodData);
                                    break;
                            }
                        }
                    }
                    updateListView();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
    private void updateListView(){

        ulistAdapter.notifyDataSetChanged();
        dlistAdapter.notifyDataSetChanged();

    }
    private void createUserClass(String hashCode){
        m_objectID = hashCode;

        final ParseObject p = new ParseObject(m_objectID);

        p.put("Title", "Default");
        p.put("Place", "Default");
        p.put("EstablishDate","Default");
        p.put("ExpiringDate","Default");
        p.put("Category","Default");
        p.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                // We have to use utility classes to add behavior to our ParseObjects.

            }
        });
    }
}
