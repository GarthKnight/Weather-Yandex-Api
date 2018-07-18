package com.fasten.test.dagger;

import android.content.Context;


public class ComponentManager {
    private static ComponentManager ourInstance;
    private static AppComponentHelper appComponentHelper = new AppComponentHelper();

    public static ComponentManager get() {
        if (ourInstance == null) {
            throw new IllegalStateException("Not initialized");
        }
        return ourInstance;
    }

    private ComponentManager() {
    }

    public static void init(Context context) {
        if (ourInstance != null) {
            throw new IllegalStateException("Already initialized");
        }
        ourInstance = new ComponentManager();
        appComponentHelper.build(context);

        ourInstance.appComponent = appComponentHelper.getAppComponent();
    }

    private AppComponent appComponent;



    public AppComponent getAppComponent() {
        return appComponent;
    }
}
