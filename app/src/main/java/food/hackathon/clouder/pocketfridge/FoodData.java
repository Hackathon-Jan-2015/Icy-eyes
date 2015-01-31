package food.hackathon.clouder.pocketfridge;

import java.util.Date;

/**
 * Created by Evan on 2015/1/31.
 */
public class FoodData {

    public String title;
    public String place;
    public String time;
    public String expiring_date;
    public String category;
    public String objectID;

    public FoodData(String m_title, String m_place, String m_time, String m_expiring_date,String m_category, String m_objectID){
        title = m_title;
        place = m_place;
        time = m_time;
        category = m_category;
        expiring_date = m_expiring_date;
        objectID = m_objectID;
    }
    public String getPlace(){
        return place;
    }

}



