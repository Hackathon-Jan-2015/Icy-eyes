package food.hackathon.clouder.pocketfridge;

import java.util.Date;

/**
 * Created by Evan on 2015/1/31.
 */
public class FoodData {

    public String title;
    public String place;
    public String time;
    public Date expiring_date;
    public String category;

    public FoodData(String m_title, String m_place, String m_time, Date m_expiring_date,String m_category){
        title = m_title;
        place = m_place;
        time = m_time;
        category = m_category;
        expiring_date = m_expiring_date;
    }
    public String getPlace(){
        return place;
    }

}



