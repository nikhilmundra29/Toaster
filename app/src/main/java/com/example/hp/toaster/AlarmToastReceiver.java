package com.example.hp.toaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;




public class AlarmToastReceiver extends BroadcastReceiver {


    /*Bundle extras = getIntent().getExtras();
    String s=null;
    */
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,  "Solve me", Toast.LENGTH_SHORT).show();
    }

    /*public Intent getIntent() {
        if(extras!=null)
            s = extras.getString("key");
        return intent;
    }*/
}
