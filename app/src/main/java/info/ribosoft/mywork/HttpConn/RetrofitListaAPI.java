package info.ribosoft.mywork.HttpConn;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitListaAPI {
    @FormUrlEncoded
    @POST("mywork.php")
    Call<ArrayList<RecyclerLogin>> getOperatore(
        @Field("sel_menu") String sel_menu,
        @Field("cod_operatore") String cod_operatore);

    @FormUrlEncoded
    @POST("mywork.php")
    Call<ArrayList<RecyclerOrari>> getListaSett(
        @Field("sel_menu") String sel_menu,
        @Field("id_operatore") String id_operatore,
        @Field("data_inizio") String data_inizio,
        @Field("data_fine") String data_fine,
        @Field("data_sel") String data_sel);
}
