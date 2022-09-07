package site.linkway.slago.activityCollector;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activityList=new ArrayList<>();
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity:activityList){
            activity.finish();
        }
    }
}
