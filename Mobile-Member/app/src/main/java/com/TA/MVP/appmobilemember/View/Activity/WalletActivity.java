package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterWallet;
import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 19/06/2017.
 */

public class WalletActivity extends ParentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterWallet rec_Adapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_wallet);
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterWallet();
        rec_Adapter.setWallets(((MasterCleanApplication) getApplication()).getGlobalStaticData().getWallets());
        rec_Adapter.setcontext(this);
        recyclerView.setAdapter(rec_Adapter);


        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top Up");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.menu_isi_code:
//                doStartActivity(WalletActivity.this, WalletVoucherActivity.class);
//                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
