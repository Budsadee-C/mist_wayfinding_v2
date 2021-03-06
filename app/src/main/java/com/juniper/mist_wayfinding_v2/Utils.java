package com.juniper.mist_wayfinding_v2;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;


/**
 * Created by anubhava on 02/04/18.
 */

public class Utils {

    /**
     * Check Internet is on or off
     */

    public static boolean isNetworkAvailable(Context context) {
        boolean isNet = false;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo result = connectivityManager.getActiveNetworkInfo();
                if (result != null && result.isConnectedOrConnecting()) {
                    isNet = true;
                }
            }
        }
        return isNet;
    }

    /*checking if location service
      is enabled */

    public static boolean isLocationServiceEnabled(Context context) {
        boolean gps_enabled = false, network_enabled = false;

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            try {
                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
                //do nothing...
            }

            try {
                network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
                //do nothing...
            }
        }
        return gps_enabled || network_enabled;
    }

    public static String getEnvironment(String envType) {
        String env = "";
        // set the environment string to return
        if (envType.equalsIgnoreCase("P")) {
            env = "Production";
        } else if (envType.equalsIgnoreCase("E")) {
            env = "EU";
        } else if (envType.equalsIgnoreCase("S")) {
            env = "Staging";
        } else if (envType.equals("G")) {
            env = "GCP-Production";
        } else if (envType.equals("g")) {
            env = "GCP-Staging";
        }
        // return the environment string
        return env;
    }

    public static boolean isEmptyString(String value) {
        return TextUtils.isEmpty(value) || value.equalsIgnoreCase("null");
    }

    /**
     * Get distance between two points.
     */
    public static double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static boolean isEmpty(Object data) {
        return data == null;
    }
}
