package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentStatusDisetujui;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentStatusPermintaan;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentStatusPending;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentStatusRiwayat;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class PagerAdapterStatus extends FragmentPagerAdapter{
    private Context thiscontext;
    public PagerAdapterStatus(FragmentManager fm, Context context){
        super(fm);
        thiscontext = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentStatusPermintaan();
            case 1:
                return new FragmentStatusPending();
            case 2:
                return new FragmentStatusDisetujui();
            case 3:
                return new FragmentStatusRiwayat();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return thiscontext.getResources().getString(R.string.pager_status_penawaran);
            case 1:
                return thiscontext.getResources().getString(R.string.pager_status_pending);
            case 2:
                return thiscontext.getResources().getString(R.string.pager_status_disetujui);
            case 3:
                return thiscontext.getResources().getString(R.string.pager_status_riwayat);
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
