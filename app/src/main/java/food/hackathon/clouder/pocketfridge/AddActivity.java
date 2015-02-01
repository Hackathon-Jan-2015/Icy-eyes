package food.hackathon.clouder.pocketfridge;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddActivity extends ActionBarActivity {

    private Spinner spinner;
    private ArrayAdapter<String> CategoryList;
    private Context mContext;
    private EditText titleText;
    private RadioGroup place_group,size_group;
    private Button confirmButton,mdButton,expButton;;
    private String[] category = {"Vegetable","Fruit", "Meat", "Fish", "Bread","Dairy","JunkFood","Others"};
    private String m_category = "Vegetable";
    private String m_place = "U";
    private Calendar  m_Calendar= Calendar.getInstance();
    private Activity m_activity;
    private ImageButton imageButton;
    private boolean mdboolean =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        m_activity = this;
        mContext = this.getApplicationContext();
        spinner = (Spinner)findViewById(R.id.spinner);
        titleText = (EditText)findViewById((R.id.edit_title));
        mdButton=(Button) findViewById(R.id.md_button);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/dd/MM");
        String currentTime = sdf.format(new Date());
        mdButton.setText(currentTime);
        expButton=(Button) findViewById(R.id.exp_button);
        expButton.setText(currentTime);

        place_group=(RadioGroup)findViewById(R.id.place_radio);
        size_group=(RadioGroup)findViewById(R.id.size_radio);

        size_group.setOnCheckedChangeListener(listener1);
        place_group.setOnCheckedChangeListener(listener2);
        confirmButton = (Button) findViewById(R.id.confirm_button);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType("image/*");

                Intent destIntent = Intent.createChooser(intent, "Select Image");

                startActivityForResult(destIntent, 1);
            }
        });
        CategoryList = new ArrayAdapter<String>(AddActivity.this,
                android.R.layout.simple_spinner_item, category);
        spinner.setAdapter(CategoryList);

        //Reaction after choose an item in spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                m_category = category[position];
                switch (position){
                    case 0:
                        imageButton.setBackgroundResource(R.drawable.veg);
                        break;
                    case 1:
                        imageButton.setBackgroundResource(R.drawable.fru);
                        break;
                    case 2:
                        imageButton.setBackgroundResource(R.drawable.meat);
                        break;
                    case 3:
                        imageButton.setBackgroundResource(R.drawable.fish);
                        break;
                    case 4:
                        imageButton.setBackgroundResource(R.drawable.bread);
                        break;
                    case 5:
                        imageButton.setBackgroundResource(R.drawable.milk);
                        break;
                    case 6:
                        imageButton.setBackgroundResource(R.drawable.pizza);
                        break;
                    case 7:
                        imageButton.setBackgroundResource(R.drawable.egg);
                        break;
                }
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

                if(!cancel){
                    ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

                    if (activeNetwork != null && activeNetwork.isConnected()) {
                        ParseObject gameScore = new ParseObject(ConstantVariable.m_objectID);
                        gameScore.put("Title", titleText.getText().toString());
                        gameScore.put("Place", m_place);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String currentTime = sdf.format(new Date());
                        gameScore.put("EstablishDate", currentTime);
                        gameScore.put("ExpiringDate", expButton.getText().toString());
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
                        gameScore.put("ExpiringDate", expButton.getText().toString());
                        gameScore.put("Category", m_category);
                        gameScore.saveEventually();
                        finish();
                    }

                }
            }
        });
        mdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdboolean=true;
                DatePickerDialog dialog =

                        new DatePickerDialog(AddActivity.this,

                                datepicker,

                                m_Calendar.get(Calendar.YEAR),

                                m_Calendar.get(Calendar.MONTH),

                                m_Calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });



        expButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdboolean=false;
                DatePickerDialog dialog =

                        new DatePickerDialog(AddActivity.this,

                                datepicker,

                                m_Calendar.get(Calendar.YEAR),

                                m_Calendar.get(Calendar.MONTH),

                                m_Calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });


    }

    DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener()

    {



        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)

        {

            m_Calendar.set(Calendar.YEAR, year);

            m_Calendar.set(Calendar.MONTH, monthOfYear);

            m_Calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy/MM/dd"; //In which you need put here

            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);

            if(mdboolean) {
                mdButton.setText(sdf.format(m_Calendar.getTime()));
                mdboolean=false;
            }
            else{
                expButton.setText(sdf.format(m_Calendar.getTime()));
            }

        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        InputStream ImageStream = null;

       /*Checking if the activity that was triggered was the ImageGallery
         If so then requestCode will match the LOAD_IMAGE_RESULTS value
         If the resultCode is RESULT_OK &
         There is some data that we know that image was picked*/
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                //Let's read the picked image -its URI
                Uri pickedImage = data.getData();


                //Let's read the image path using content resolver
                ImageStream = getContentResolver().openInputStream(pickedImage);

                //Now let's set the GUI ImageView data with data read from the picked file
                Bitmap selectedImage = BitmapFactory.decodeStream(ImageStream);

                switch (averageARGB(selectedImage)){
                    case 1 :
                        m_category = "Meat";
                        spinner.setSelection(2);
                        break;
                    case 2:
                        m_category = "Vegetable";
                        spinner.setSelection(0);
                        break;
                    case 3:
                        break;
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (ImageStream != null) {
                    try {
                        ImageStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    private int averageARGB(Bitmap pic) {
        int A, R, G, B;
        A = R = G = B = 0;
        int pixelColor;
        int width = pic.getWidth();
        int height = pic.getHeight();
        int size = width * height;
        int count =0;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixelColor = pic.getPixel(x, y);

                if((Color.red(pixelColor) > Color.green(pixelColor) +30) && (Color.red(pixelColor) > Color.blue(pixelColor) +30))
                    count +=1;

                if((Color.green(pixelColor) > Color.red(pixelColor) +30) && (Color.green(pixelColor) > Color.blue(pixelColor) +30))
                    count -=1;

            }
        }
        if (count > size*0.3)
            return 1;// meat
        if (count < -size*0.3)
            return 2;// veg
        return 3;// others
    }
    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // TODO Auto-generated method stub


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

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // TODO Auto-generated method stub

            int p = group.indexOfChild((RadioButton) findViewById(checkedId));
            int count = group.getChildCount();
            switch (checkedId) {
                case R.id.u:
                    m_category = "U";
                    break;
                case R.id.d:
                    m_category = "D";
                    break;


            }

        }

    };

}
