package com.mvp.mobile_art.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterListKerjaEdit;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.mvp.mobile_art.Model.Array.ArrayBulan;
import com.mvp.mobile_art.Model.Basic.MyTask;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.ReviewOrder;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.OrderResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OrderRepo;
import com.mvp.mobile_art.Route.Repositories.ReviewOrderRepo;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.View.Fragment.FragmentMembermini;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 30/07/17.
 */

public class PemesananActiveActivity extends ParentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterListKerjaEdit rec_Adapter;
    private FragmentMembermini fragmentMembermini;

    private EditText mulaitime, mulaidate, selesaitime, selesaidate, total, cttn, profesi, worktime, alamat;
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private Order order = new Order();
    private Toolbar toolbar;

    private Button btnextra, kembali, terima;
    private ImageButton btnlocation;
    private TextView estimasitext, tugastext;

    private Calendar calendar = Calendar.getInstance();
    private Calendar waktumulai = new GregorianCalendar();
    private Calendar waktuselesai = new GregorianCalendar();
    private Calendar batasmulai = new GregorianCalendar();
    private Calendar batasselesai = new GregorianCalendar();
    private Calendar tempcalendar = new GregorianCalendar();
    private ArrayBulan arrayBulan = new ArrayBulan();
    private StaticData staticData;
    private boolean sdgbrlgsg = false;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_active);
        Intent intent = getIntent();
        order = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ORDER_EXTRA), Order.class);
        staticData = ((MasterCleanApplication)getApplication()).getGlobalStaticData();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mulaitime = (EditText) findViewById(R.id.pmsa_et_mulaitime);
        mulaidate = (EditText) findViewById(R.id.pmsa_et_mulaidate);
        selesaitime = (EditText) findViewById(R.id.pmsa_et_selesaitime);
        selesaidate = (EditText) findViewById(R.id.pmsa_et_selesaidate);
        profesi = (EditText) findViewById(R.id.profesi);
        worktime = (EditText) findViewById(R.id.worktime);
        alamat = (EditText) findViewById(R.id.pmsa_et_alamat);
        cttn = (EditText) findViewById(R.id.pmsa_et_catatan);
        total = (EditText) findViewById(R.id.pmsa_et_total);
        btnextra = (Button) findViewById(R.id.pmsa_btn_extra);
        terima = (Button) findViewById(R.id.pmsa_btn_terima);
        kembali = (Button) findViewById(R.id.pmsa_btn_kembali);
        btnlocation = (ImageButton) findViewById(R.id.btnlocation);
        tugastext = (TextView) findViewById(R.id.pmsa_tv_tugas);
        recyclerView = (RecyclerView) findViewById(R.id.pmsa_rec_listkerja);

        getuser(order.getMember_id());

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_pemesanan);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
    }
    public void loadtampilan(){
        try{
            mulaitime.setText(timeFormat.format(getdateFormat.parse(order.getStart_date())));
            mulaidate.setText(costumedateformat(getdateFormat.parse(order.getStart_date())));
            selesaitime.setText(timeFormat.format(getdateFormat.parse(order.getEnd_date())));
            selesaidate.setText(costumedateformat(getdateFormat.parse(order.getEnd_date())));
        }
        catch (ParseException pe){

        }

        profesi.setText(staticData.getJobs().get(order.getJob_id()-1).getJob());
        worktime.setText(staticData.getWaktu_kerjas().get(order.getWork_time_id()-1).getWork_time());
        alamat.setText(order.getContact().getAddress());
        cttn.setText(order.getRemark());
        total.setText(setRP(order.getCost()));

        //listkerja
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerjaEdit(this);
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setDefaulttask(staticData.getMyTasks());
        rec_Adapter.setList(order.getOrder_task_list());

        if (order.getStatus() == 1){
            checkselesai();
            checksedangberlangsung();
        }
        else if (order.getStatus() == 0){
            checkexpired();
        }

        switch (order.getWork_time_id()){
            case 1:
//                estimasitext.setText("Jam");
                recyclerView.setVisibility(View.VISIBLE);
                tugastext.setVisibility(View.VISIBLE);
                break;
            case 2:
//                estimasitext.setText("Hari");
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
            case 3:
//                estimasitext.setText("Bulan");
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
        }
        switch (order.getJob_id()){
            case 1:
                break;
            case 2:
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
            case 3:
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
            case 4:
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
        }
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terimaorder(order.getId());
            }
        });
        switch (order.getStatus()){
            case 0:
                btnextra.setText("Tolak");
                terima.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (sdgbrlgsg)
                    btnextra.setText("Selesaikan");
                else btnextra.setText("Kirim Pesan");
                //selesaikan dari member
                break;
            case 2:
//                btnextra.setText("Hapus");
                btnextra.setVisibility(View.GONE);
                break;
            case 3:
                btnextra.setText("Lihat Review");
                break;
            case 4:
//                btnextra.setText("Hapus");
                btnextra.setVisibility(View.GONE);
                break;
            case 5:
                btnextra.setText("Laporkan");
//                btnextra.setVisibility(View.GONE);
                break;
        }
        btnextra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (order.getStatus()){
                    case 0:
//                        Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan.", Toast.LENGTH_SHORT).show();
                        tolakorder(order.getId());
                        break;
                    case 1:
                        if (sdgbrlgsg){
                            abuildermessage("Anda akan menyatakan pemesanan selesai.","Konfirmasi");
                            abuilder.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    gantimystatus(1);
                                }
                            });
                            abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            showalertdialog();
                        }else {
                            Intent intent1 = new Intent(getApplicationContext(), TulisPesanActivity.class);
                            intent1.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(order.getArt()));
                            startActivity(intent1);
                        }
                        break;
                    case 2:
//                        abuildermessage("Hapus riwayat pemesanan ini?","Konfirmasi");
//                        abuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                deleteorder();
//                            }
//                        });
//                        abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        showalertdialog();
                        break;
                    case 3:
                        getreview();
//                        Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan.", Toast.LENGTH_SHORT).show();
//                        Intent intent2 = new Intent(getApplicationContext(), ReviewActivity.class);
//                        intent2.putExtra(ConstClass.ORDER_EXTRA, GsonUtils.getJsonFromObject(order));
//                        startActivity(intent2);
                        break;
                    case 4:
//                        abuildermessage("Hapus riwayat pemesanan ini?","Konfirmasi");
//                        abuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                deleteorder();
//                            }
//                        });
//                        abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        showalertdialog();
                        break;
                    case 5:
                        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                        intent.putExtra("target", GsonUtils.getJsonFromObject(order.getMember()));
                        startActivity(intent);
                        break;
                }
            }
        });
        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewLocationActivity.class);
                intent.putExtra("location", order.getContact().getLocation());
                intent.putExtra("alamat", order.getContact().getAddress());
                startActivity(intent);
            }
        });
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
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number);
        return tempp;
    }
    public void terimaorder(final Integer id){
        abuildermessage("Anda yakin ingin menerima pesanan ini?", "Konfirmasi");
        abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call<List<Order>> callerjadwal = APIManager.getRepository(OrderRepo.class).getordersByArtstatus(order.getArt_id(), 1);
                callerjadwal.enqueue(new APICallback<List<Order>>() {
                    @Override
                    public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                        super.onSuccess(call, response);
                        if (validasijadwal(response.body())){
                            gantistatus(1);
                        }
                    }

                    @Override
                    public void onError(Call<List<Order>> call, Response<List<Order>> response) {
                        super.onError(call, response);
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
            }
        });
        abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        showalertdialog();
    }
    public void tolakorder(final Integer id){
        abuildermessage("Anda yakin ingin menolak pesanan ini?", "Konfirmasi");
        abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initProgressDialog("Sedang melakukan validasi");
                showDialog();
                Call<Order> caller = APIManager.getRepository(OrderRepo.class).getorderById(order.getId());
                caller.enqueue(new APICallback<Order>() {
                    @Override
                    public void onSuccess(Call<Order> call, Response<Order> response) {
                        super.onSuccess(call, response);
                        dismissDialog();
                        order = response.body();
                        if (order.getStatus() == 0) {
                            gantistatus(4);
                        }else {
                            Toast.makeText(getApplicationContext(),"Pemesanan sudah tidak dapat ditolak", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call<Order> call, Response<Order> response) {
                        super.onError(call, response);
                        dismissDialog();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        super.onFailure(call, t);
                        dismissDialog();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        showalertdialog();
        swipeRefreshLayout.setRefreshing(false);
    }
    public boolean validasijadwal(List<Order> orders){
        try {
            waktumulai.setTime(getdateFormat.parse(order.getStart_date()));
            waktuselesai.setTime(getdateFormat.parse(order.getEnd_date()));
        } catch (ParseException e) {

        }
        for (int n=0;n<orders.size();n++){
            try {
                batasmulai.setTime(getdateFormat.parse(orders.get(n).getStart_date()));
                batasselesai.setTime(getdateFormat.parse(orders.get(n).getEnd_date()));
                batasmulai.add(Calendar.HOUR_OF_DAY, -1);
                batasselesai.add(Calendar.HOUR_OF_DAY, 1);
            } catch (ParseException e) {

            }
            if (waktumulai.after(batasmulai) && waktumulai.before(batasselesai))
                return false;
            if (waktuselesai.after(batasmulai) && waktuselesai.before(batasselesai))
                return false;
        }
        return true;
    }
    public void gantistatus(Integer status){
        initProgressDialog("Sedang memperoses");
        showDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("status", status.toString());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchorderById(order.getId().toString(), map);
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                finish();
            }

            @Override
            public void onError(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String costumedateformat(Date date){
//        String hari = arrayHari.getArrayList().get(Integer.parseInt(hariFormat.format(date)));
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date))-1);
        // Senin, Januari 30
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
    }
    public void checkselesai(){
        calendar = Calendar.getInstance();
        try {
            waktuselesai.setTime(getdateFormat.parse(order.getEnd_date()));
        } catch (ParseException e) {

        }
        if (calendar.after(waktuselesai)){
            if (order.getStatus_member() == 1 && order.getStatus_art() == 1) {
                abuildermessage("Pemesanan ini sudah selesai. ", "Pemberitahuan");
                abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gantistatus(3);
                    }
                });
                showalertdialog();
            } else {
                waktuselesai.add(Calendar.HOUR_OF_DAY, 1);
                if (calendar.after(waktuselesai)){
                    if (order.getStatus_member() == 0 || order.getStatus_art() == 0) {
                        abuildermessage("Pemesanan ini tidak dikonfirmasi oleh salah satu pihak member atau asisten, silahkan laporkan masalah ini pada tab Riwayat>Pemesanan>Laporkan.", "Pemberitahuan");
                        abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gantistatus(5);
                            }
                        });
                        showalertdialog();
                    }
                }
                //else jika 1 jam setelah selesai, sebelum expired selesai
            }
        }
    }
    public void checkexpired(){
        calendar = Calendar.getInstance();
        try {
            waktumulai.setTime(getdateFormat.parse(order.getStart_date()));
        } catch (ParseException e) {

        }
        if (calendar.after(waktumulai)){
            abuildermessage("Pemesanan ini sudah tidak dapat diterima. Pemesanan ini dibatalkan.", "Pemberitahuan");
            abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    gantistatus(2);
                }
            });
            showalertdialog();
        }
    }
    public void checksedangberlangsung(){
        calendar = Calendar.getInstance();
        try {
            waktumulai.setTime(getdateFormat.parse(order.getStart_date()));
//            waktumulai.add(Calendar.MINUTE, -10); // bisa mulai kerja 10 sebelum waktunya
        } catch (ParseException e) {

        }
        if (calendar.after(waktumulai)){
            sdgbrlgsg = true;
            if (order.getStatus_art().equals(1)) {
                rec_Adapter.setStatus(false);
                btnextra.setEnabled(false);
                btnextra.setVisibility(View.GONE);
            }
            else {
                rec_Adapter.setStatus(true);
            }
        }
    }
    public void updateliststatus(Integer taskid, Integer status){
        HashMap<String,String> map = new HashMap<>();
        map.put("status", status.toString());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchordertasklistbyid(taskid, map);
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
            }

            @Override
            public void onError(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void gantimystatus(Integer status){
        initProgressDialog("Sedang memperoses");
        showDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("status_art", status.toString());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchorderById(order.getId().toString(), map);
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                finish();
            }

            @Override
            public void onError(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void reload(){
        swipeRefreshLayout.setRefreshing(true);
        Call<Order> caller = APIManager.getRepository(OrderRepo.class).getorderById(order.getId());
        caller.enqueue(new APICallback<Order>() {
            @Override
            public void onSuccess(Call<Order> call, Response<Order> response) {
                super.onSuccess(call, response);
                order = response.body();
                loadtampilan();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Call<Order> call, Response<Order> response) {
                super.onError(call, response);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void getreview(){
        initProgressDialog("Sedang memperoses...");
        showDialog();
        Call<ReviewOrder> caller = APIManager.getRepository(ReviewOrderRepo.class).getriview(order.getId());
        caller.enqueue(new APICallback<ReviewOrder>() {
            @Override
            public void onSuccess(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onSuccess(call, response);
                dismissDialog();
                Intent intent2 = new Intent(getApplicationContext(), ReviewActivity.class);
                intent2.putExtra(ConstClass.ORDER_EXTRA, GsonUtils.getJsonFromObject(order));
                startActivity(intent2);
                //goto review activity
            }

            @Override
            public void onNotFound(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onNotFound(call, response);
                dismissDialog();

            }

            @Override
            public void onError(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ReviewOrder> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getApplicationContext(),"Belum ada review",Toast.LENGTH_SHORT).show();
                dismissDialog();
//                Toast.makeText(getApplicationContext(),"Koneksi bermasalah, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getuser(Integer id){
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(id.toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                loadmini(response.body());
                reload();
                dismissDialog();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                abuildermessage("Koneksi bermasalah. Coba lagi?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getuser(order.getMember_id());
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }
    public void loadmini(User member){
        fragmentMembermini = new FragmentMembermini();
        Bundle b = new Bundle();
        b.putString(ConstClass.MEMBER_EXTRA, GsonUtils.getJsonFromObject(member));
        fragmentMembermini.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_member, fragmentMembermini).commit();
    }
}
