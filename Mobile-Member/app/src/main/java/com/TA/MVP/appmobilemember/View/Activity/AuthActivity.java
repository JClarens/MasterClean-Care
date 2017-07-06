package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentLogin;

/**
 * Created by Zackzack on 08/06/2017.
 */

public class AuthActivity extends ParentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        doChangeFragment(new FragmentLogin());
    }

    public void doChangeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_auth, fragment).commit();
    }
}