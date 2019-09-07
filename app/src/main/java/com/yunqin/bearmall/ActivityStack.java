package com.yunqin.bearmall;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Master
 * @create 2018/8/17 13:44
 */
public class ActivityStack implements Application.ActivityLifecycleCallbacks {

    private final Stack<Activity> activityStack = new Stack<>();


    public Activity getTopAct() {
        return activityStack.peek();
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        joinActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        popActivity(activity);
    }


    private void joinActivity(Activity activity) {
        activityStack.add(activity);
    }

    private void popActivity(Activity activity) {
        if (activity != null) {
            this.activityStack.remove(activity);
        }
    }

    public final void finishActivity(Activity activity) {
        if (activity != null) {
            this.activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public final void finishActivity(Class<? extends Activity> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                if (!activity.isFinishing()) {
                    activity.finish();
                }
                break;
            }
        }
    }

    public final boolean hasActivity(Activity activity) {
        if (activity != null) {
            this.activityStack.remove(activity);
            if (!activity.isFinishing()) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasActivity(Class<? extends Activity> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                if (!activity.isFinishing()) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

}
