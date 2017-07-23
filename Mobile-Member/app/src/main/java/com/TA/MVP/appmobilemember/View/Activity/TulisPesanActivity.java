package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.MyMessageResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.MessageRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class TulisPesanActivity extends ParentActivity {
    private EditText nama, sub, msg;
    private Button batal, kirim;
    private Toolbar toolbar;
    private MyMessage myMessage;
    private User art;
    private User user;
    private Integer targetid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulispesan);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        nama = (EditText) findViewById(R.id.tp_et_reciever);
        sub = (EditText) findViewById(R.id.tp_et_subject);
        msg = (EditText) findViewById(R.id.tp_et_pesan);
        batal = (Button) findViewById(R.id.tp_btn_btl);
        kirim = (Button) findViewById(R.id.tp_btn_krm);

        Intent i = getIntent();
        if (i.getStringExtra("msg") != null){
            myMessage = GsonUtils.getObjectFromJson(i.getStringExtra("msg"), MyMessage.class);
            nama.setText(myMessage.getSender().getName());
            sub.setText(myMessage.getSubject());
            targetid = myMessage.getSender().getId();
        }
        else if (i.getStringExtra(ConstClass.ART_EXTRA) != null){
            art = GsonUtils.getObjectFromJson(i.getStringExtra(ConstClass.ART_EXTRA), User.class);
            nama.setText(art.getName());
            targetid = art.getId();
        }

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abuildermessage("Kirim pesan?", "Pesan");
                abuilder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendmessage();
                    }
                });
                abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                showalertdialog();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_pesan);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void sendmessage(){
        HashMap<String,String> map = new HashMap<>();
        map.put("sender_id", user.getId().toString());
        map.put("receiver_id", targetid.toString());
        map.put("subject", sub.getText().toString());
        map.put("message", msg.getText().toString());
        map.put("status", "0");
        Call<MyMessageResponse> caller = APIManager.getRepository(MessageRepo.class).postmessage(map);
        caller.enqueue(new APICallback<MyMessageResponse>() {
            @Override
            public void onSuccess(Call<MyMessageResponse> call, Response<MyMessageResponse> response) {
                super.onSuccess(call, response);
                finish();
            }

            @Override
            public void onFailure(Call<MyMessageResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}