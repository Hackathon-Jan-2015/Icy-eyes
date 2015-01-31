package food.hackathon.clouder.pocketfridge;

/**
 * Created by Evan on 2015/1/31.
 */
        import com.parse.Parse;
        import android.app.Application;

public class MyApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "Pib4FM4DWqZnFxxaDygKtxKoM1aYFMxcQOWWQrNG", "GB0fzdKGqMrrOXaypCNDHfgIOK2cEVVGOse3vD6N");

    }

}

