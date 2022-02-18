package info.ribosoft.mywork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import info.ribosoft.mywork.HttpConn.RecyclerLogin;
import info.ribosoft.mywork.HttpConn.RetrofitListaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    ProgressBar pgrLogin;
    private ArrayList<RecyclerLogin> recyclerLoginArrayList;
    private String urlWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context context = getApplicationContext();
        urlWeb = context.getString(R.string.urlWeb);

        pgrLogin = findViewById(R.id.pgrLogin);
        EditText edtLogin = findViewById(R.id.edtLogin);
        edtLogin.requestFocus();

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgrLogin.setVisibility(View.VISIBLE);
                int SPLASH_TIME_OUT = 1000;
                new Handler().postDelayed(() -> {
                    if (edtLogin.length()==0 || edtLogin.length()<5) {
                        Toast.makeText(getApplicationContext(),
                            "Codice Operatore non corretto", Toast.LENGTH_LONG).show();
                    } else {
                        String txtLogin = edtLogin.getText().toString();
                        // check operator code and read related data
                        leggiDatiOp(txtLogin);
                    }
                }, SPLASH_TIME_OUT);
            }
        });
    }

    // check operator code and read related data
    private void leggiDatiOp(String strCodOp) {
        // // pass our base url to the retrofit2 constructor
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(urlWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        // instantiate our retrofit API class
        RetrofitListaAPI retrofitListaAPI = retrofit.create(RetrofitListaAPI.class);

        // requests the weekly timetables for the operator
        Call<ArrayList<RecyclerLogin>> call = retrofitListaAPI.getOperatore("1", strCodOp);

        // asynchronously send the request
        call.enqueue(new Callback<ArrayList<RecyclerLogin>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerLogin>> call,
                Response<ArrayList<RecyclerLogin>> response) {
                if (response.isSuccessful()) {
                    // adds the data to our array list
                    recyclerLoginArrayList = response.body();
                    // reads the data referring to the selected operator
                    RecyclerLogin rcOperatore = recyclerLoginArrayList.get(0);
                    String strIdOperatore = rcOperatore.getId_Operatore();
                    String strCognome = rcOperatore.getCognome() + " " + rcOperatore.getNome();
                    String strOre = rcOperatore.getOre();

                    // enters the parameters to pass to the activity
                    Intent intent = new Intent(getApplicationContext(), SettimanaleActivity.class);
                    intent.putExtra("id_operatore", strIdOperatore);
                    intent.putExtra("operatore", strCognome);
                    intent.putExtra("ore", strOre);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerLogin>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Codice Operatore errato",
                    Toast.LENGTH_LONG).show();
                pgrLogin.setVisibility(View.INVISIBLE);
            }
        });
    }
}