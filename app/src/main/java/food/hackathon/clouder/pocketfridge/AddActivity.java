package food.hackathon.clouder.pocketfridge;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddActivity extends ActionBarActivity {

    private Spinner spinner;
    private ArrayAdapter<String> CategoryList;
    private Context mContext;
    private EditText titleText,mdText,expText,placeText;
    private Button confirmButton;
    private String[] category = {"Vegetable","Fruit", "Meat", "fish", "Bread","Dairy","JunkFood","Others"};
    private String m_category = "Vegetable";
    private Activity m_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        m_activity = this;
        mContext = this.getApplicationContext();
        spinner = (Spinner)findViewById(R.id.spinner);
        titleText = (EditText)findViewById((R.id.edit_title));
        mdText = (EditText)findViewById((R.id.edit_md));
        expText = (EditText)findViewById((R.id.edit_exp));
        placeText = (EditText)findViewById((R.id.edit_place));
        confirmButton = (Button) findViewById(R.id.confirm_button);
        CategoryList = new ArrayAdapter<String>(AddActivity.this,
                android.R.layout.simple_spinner_item, category);
        spinner.setAdapter(CategoryList);

        //Reaction after choose an item in spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                m_category = category[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                if(titleText.getText()==null){
                    titleText.setError("Cannot be empty");
                    cancel = true;
                }
                if(mdText.getText()==null){
                    mdText.setError("Cannot be empty");
                    cancel = true;
                }if(expText.getText()==null){
                    expText.setError("Cannot be empty");
                    cancel = true;
                }
                if(placeText.getText()==null){
                    placeText.setError("Cannot be empty");
                    cancel = true;
                }
                if(!cancel){
                    ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

                    if (activeNetwork != null && activeNetwork.isConnected()) {
                        ParseObject gameScore = new ParseObject(ConstantVariable.m_objectID);
                        gameScore.put("Title", titleText.getText().toString());
                        gameScore.put("Place", placeText.getText().toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String currentTime = sdf.format(new Date());
                        gameScore.put("EstablishDate", currentTime);
                        gameScore.put("ExpiringDate", expText.getText().toString());
                        gameScore.put("Category", m_category);
                        try {
                            gameScore.save();
                            finish();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(m_activity,"add automatically when network connect",Toast.LENGTH_LONG).show();
                        ParseObject gameScore = new ParseObject(ConstantVariable.m_objectID);
                        gameScore.put("Title", titleText.getText().toString());
                        gameScore.put("Place", titleText.getText().toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String currentTime = sdf.format(new Date());
                        gameScore.put("EstablishDate", currentTime);
                        gameScore.put("ExpiringDate", expText.getText().toString());
                        gameScore.put("Category", m_category);
                        gameScore.saveEventually();
                        finish();
                    }

                }
            }
        });

    }
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

            int p = group.indexOfChild((RadioButton) findViewById(checkedId));
            int count = group.getChildCount();
            switch (checkedId) {
                case R.id.l:

                    break;
                case R.id.m:

                    break;
                case R.id.s:

                    break;

            }

        }

    };

}
