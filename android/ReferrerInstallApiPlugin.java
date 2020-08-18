package br.com.fdmartins.plugins.referrerinstall;


import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import android.os.RemoteException;

import android.util.Log;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;

import java.net.URLDecoder;
import java.util.HashMap;

public class ReferrerInstallApiPlugin extends CordovaPlugin {

    public static final String LOG_NAME = "ReferrerInstallApiPlugin";
    public static final String GET_REFERRER = "getReferrer";

    InstallReferrerClient mReferrerClient;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
     
        if (GET_REFERRER.equals(action)) {
            //String variable = args.getString(0);
            this.getReferrer(callbackContext);
            return true;
        }

        return false;
    }
    

    
    
    private void getInstallReferrerV(CallbackContext callbackContext) throws RemoteException {
        
        ReferrerDetails response = mReferrerClient.getInstallReferrer();
        String referrerData = response.getInstallReferrer();

        //long referrerClickTime = response.getReferrerClickTimestampSeconds();
        //long appInstallTime = response.getInstallBeginTimestampSeconds();
        //boolean instantExperienceLaunched = response.getGooglePlayInstantParam();

        callbackContext.success(referrerData);
    }



    private void getReferrer(CallbackContext callbackContext) {

        try{

            mReferrerClient = InstallReferrerClient.newBuilder(cordova.getActivity()).build();

            InstallReferrerStateListener installReferrerStateListener = new InstallReferrerStateListener() 
            {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    Log.e(LOG_NAME, "onInstallReferrerSetupFinished()");


                    switch (responseCode) {
                        case InstallReferrerClient.InstallReferrerResponse.OK:
                            //Connected to the service.

                            
                            try{
                                getInstallReferrerV(callbackContext);
                                
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                
                                callbackContext.error("Falhou.");
                            }
                            
                            
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                            // API not available on the current app.
                            Log.e(LOG_NAME, "API not available on the current app.");
                            callbackContext.error("API not available.");
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                            // Connection could not be established.
                            Log.e(LOG_NAME, "Connection could not be established.");
                            callbackContext.error("Connection could not be established.");
                            break;
                    }
                    
                }
                @Override
                public void onInstallReferrerServiceDisconnected() {
                    // Try to restart the connection calling the startConnection() method.
                    Log.e(LOG_NAME, "onInstallReferrerServiceDisconnected");
                    callbackContext.error("onInstallReferrerServiceDisconnected");
                }
            };


            mReferrerClient.startConnection(installReferrerStateListener);
            

           }catch(Exception e) {
                e.printStackTrace();
                callbackContext.error("GENERAL FAIL");
           }

           return;

    }

    
}
