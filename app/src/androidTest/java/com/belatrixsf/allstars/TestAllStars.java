package com.belatrixsf.allstars;

/**
 * Created by Luis on 22/05/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.content.pm.PackageManager;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestAllStars {
    private static final String BASIC_SAMPLE_PACKAGE = "com.belatrixsf.allstars";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String EMAIL = "sinfante";
    private static final String PASSWORD = "belatrix";

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen(){
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        mDevice.pressHome();

        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        //Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        //Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        //Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    @Test
    public void testChangeText_sameActivity() {
        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "username")).setText(EMAIL);
        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "password")).setText(PASSWORD);
        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "log_in")).click();

        UiObject2 uiRanking = mDevice.wait(Until.findObject(By.text("Ranking")), 1000);
        mDevice.findObject(By.text("Ranking")).click();

        UiObject2 uiContacts = mDevice.wait(Until.findObject(By.text("Contacts")), 500);
        mDevice.findObject(By.text("Contacts")).click();

        /*UiObject2 uiKeywords = mDevice.wait(Until.findObject(By.text("Keywords")), 500);
        mDevice.findObject(By.text("Keywords")).click();*/

        UiObject2 uiMore = mDevice.wait(Until.findObject(By.desc("More options")), 500);
        mDevice.findObject(By.desc("More options")).click();

        UiObject2 uiLogout = mDevice.wait(Until.findObject(By.text("Logout")), 500);
        mDevice.findObject(By.text("Logout")).click();

        UiObject2 uiYes = mDevice.wait(Until.findObject(By.text("Yes")), 500);
        mDevice.findObject(By.text("Yes")).click();


    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName(){
        //Create launcher intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        //Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }




}
