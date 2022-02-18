package info.ribosoft.mywork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import info.ribosoft.mywork.HttpConn.RecyclerListaOrariAdapter;
import info.ribosoft.mywork.HttpConn.RecyclerOrari;
import info.ribosoft.mywork.HttpConn.RetrofitListaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettimanaleActivity extends AppCompatActivity {
    private ArrayList<RecyclerOrari> recyclerOrariListaArray;
    private RecyclerListaOrariAdapter recyclerListaOrariAdapter;
    private RecyclerView courseOrariSett;
    private Calendar calendar;
    private String urlWeb, strIdOperatore;
    private String strDataInizio, strDataFine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settimanale);

        Context context = getApplicationContext();
        urlWeb = context.getString(R.string.urlWeb);

        // creating new array list
        recyclerOrariListaArray = new ArrayList<>();
        courseOrariSett = findViewById(R.id.rcwListaSettimana);

        Intent intent = getIntent();
        strIdOperatore = intent.getStringExtra("id_operatore");
        String strOperatore = intent.getStringExtra("operatore");

        TextView txtSettOperat = findViewById(R.id.txtSettOperat);
        TextView txtSettCurrDay = findViewById(R.id.txtSettCurrDay);

        txtSettOperat.setText(strOperatore);

        SimpleDateFormat sdfOggi = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strCurrData = sdfOggi.format(new Date());
        txtSettCurrDay.setText(strCurrData);

        // the fields are initialized with the current date and time
        calendar = Calendar.getInstance();

        // reads the times of the current week
        leggiOrariSettimanali(context, strCurrData.substring(6,10) +
            strCurrData.substring(3,5) + strCurrData.substring(0,2));

        ImageButton ibtnSettCambiaData = findViewById(R.id.ibtnSettCambiaData);
        ibtnSettCambiaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // allows the user to select a date
                gestioneData(SettimanaleActivity.this);}
        });
    }

    // reads the weekly hours indicated by the user
    private void leggiOrariSettimanali(Context context, String strDataCurr) {
        // pass our base url to the retrofit2 constructor
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(urlWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        // instantiate our retrofit API class
        RetrofitListaAPI retrofitListaAPI = retrofit.create(RetrofitListaAPI.class);

        // based on the date selected by the user, monday (start) and sunday (end) are calculated
        calcolaLun();

        // requests the weekly timetables for the operator
        Call<ArrayList<RecyclerOrari>> call = retrofitListaAPI.getListaSett("2",
            strIdOperatore, strDataInizio, strDataFine, strDataCurr);

        // asynchronously send the request
        call.enqueue(new Callback<ArrayList<RecyclerOrari>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerOrari>> call,
                Response<ArrayList<RecyclerOrari>> response) {
                if (response.isSuccessful()) {
                    // adds the data to our array list
                    recyclerOrariListaArray = response.body();
                    for (int i=0; i<7; i++) {
                        recyclerListaOrariAdapter = new
                            RecyclerListaOrariAdapter(recyclerOrariListaArray, context);
                        // set the layout manager for our recycler view
                        LinearLayoutManager manager = new LinearLayoutManager(context);
                        // setting layout manager for our recycler view
                        courseOrariSett.setLayoutManager(manager);
                        if (i<recyclerOrariListaArray.size()) {
                            // set the adapter to our recycler view
                            courseOrariSett.setAdapter(recyclerListaOrariAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerOrari>> call, Throwable t) {
                Log.i("******CONTROLLO", "*******************FAILURE");
            }
        });
    }

    // allows the user to select a date
    private void gestioneData(Context context) {
        int giorno, mese, anno;

        giorno = calendar.get(Calendar.DAY_OF_MONTH);
        mese = calendar.get(Calendar.MONTH);
        anno = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
            new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);
                // reads the weekly hours indicated by the user
                leggiOrariSettimanali(context, String.format(Locale.ITALIAN, "%04d%02d%02d",
                    year, month+1, day));
            }
        }, anno, mese, giorno);
        datePickerDialog.show();
    }

    // based on the date selected by the user, monday (start) and sunday (end) are calculated
    private void calcolaLun() {
        int dayWeek;

        // returns the week number of the requested date
        dayWeek = calendar.get(Calendar.DAY_OF_WEEK)-2;
        if (dayWeek < 0) dayWeek = 6;
        // calculate monday
        calendar.add(Calendar.DAY_OF_MONTH, dayWeek*-1);
        strDataInizio = String.format(Locale.ITALIAN, "%04d%02d%02d",
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1,
            calendar.get(Calendar.DAY_OF_MONTH));
        // calculates Sunday
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        strDataFine = String.format(Locale.ITALIAN, "%04d%02d%02d",
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1,
            calendar.get(Calendar.DAY_OF_MONTH));
        // returns to the date selected by the user
        calendar.add(Calendar.DAY_OF_MONTH, -6+dayWeek);
    }
}