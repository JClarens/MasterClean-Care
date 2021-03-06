package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class DokumenTambahanActivity extends ParentActivity {
    private Toolbar toolbar;
    private ImageView imgfotoktp;
    private Button btnsimpan, btnbatal;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumentambahan);

        imgfotoktp = (ImageView) findViewById(R.id.doctmbh_iv_fotoktp);
        btnsimpan = (Button) findViewById(R.id.doctmbh_btn_simpan);
        btnbatal = (Button) findViewById(R.id.doctmbh_btn_batal);

        imgfotoktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dosomething
            }
        });
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        context = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_doctmbh);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
