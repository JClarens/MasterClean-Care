package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterPesanMasuk;
import com.TA.MVP.appmobilemember.Model.Basic.Message;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.MessageRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentPesanMasuk extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPesanMasuk rec_Adapter;
    private List<Message> messages = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pesan_masuk, container, false);

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_pesan);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterPesanMasuk();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setPesan(messages);

        String userid = String.valueOf(GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class).getId());
        Call<List<Message>> caller = APIManager.getRepository(MessageRepo.class).getallmsgfromreciverid(userid);
        caller.enqueue(new APICallback<List<Message>>() {
            @Override
            public void onSuccess(Call<List<Message>> call, Response<List<Message>> response) {
                super.onSuccess(call, response);
                rec_Adapter.setPesan(response.body());
            }

            @Override
            public void onUnauthorized(Call<List<Message>> call, Response<List<Message>> response) {
                super.onUnauthorized(call, response);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });


        return _view;
    }
}
