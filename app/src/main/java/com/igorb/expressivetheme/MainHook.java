package com.igorb.expressivetheme;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {

    private static final String TAG = "ExpressiveDesignEnabler"; // Tag for logging
    private static final String TARGET_CLASS = "com.android.settingslib.widget.theme.flags.FeatureFlagsImpl";
    private static final String TARGET_FIELD = "isExpressiveDesignEnabled";

    private static final Set<String> RECOMMENDED_TARGETS = new HashSet<>(Arrays.asList(
            "com.android.settings",
            "com.google.android.permissioncontroller",
            "com.google.android.healthconnect.controller",
            "com.android.systemui",
            "com.google.android.repairmode",
            "com.android.devicediagnostics"
    ));

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        try {
            Class<?> featureFlagsClass = XposedHelpers.findClass(TARGET_CLASS, lpparam.classLoader);
            XposedHelpers.setStaticBooleanField(featureFlagsClass, TARGET_FIELD, true);
            XposedBridge.log(TAG + ": Successfully set " + TARGET_CLASS + "." + TARGET_FIELD + " to true in package: " + lpparam.packageName);

        } catch (XposedHelpers.ClassNotFoundError e) {

        } catch (NoSuchFieldError e) {
            XposedBridge.log(TAG + ": Field " + TARGET_FIELD + " not found in class " + TARGET_CLASS + " within package " + lpparam.packageName + ". Module may be incompatible with this app/ROM version.");
        } catch (Throwable t) {
            XposedBridge.log(TAG + ": An unexpected error occurred while attempting hook in package " + lpparam.packageName);
            XposedBridge.log(t);
        }
    }
}