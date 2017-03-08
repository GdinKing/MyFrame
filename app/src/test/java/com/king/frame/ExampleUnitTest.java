package com.king.frame;

import com.google.gson.GsonBuilder;
import com.king.frame.bean.Book;
import com.king.frame.config.NetUrl;
import com.king.frame.service.model.RetrofitService;
import com.king.frame.utils.FileUtil;
import com.king.frame.utils.TimeUtil;

import org.junit.Test;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void time_isCorrect() throws Exception {
        String dateBegin = "2017-03-02 12:00:00";
        Date begin = TimeUtil.secondStrToDate(dateBegin);
        System.out.print(TimeUtil.timeMillisToStr(System.currentTimeMillis()));
        System.out.print(new Date(System.currentTimeMillis()));
    }

    @Test
    public void testFileSize() throws Exception {
        long size = 202624164122L;
        System.out.println(FileUtil.formatFileSize(size));

    }

    @Test
    public void testLoadBook(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetUrl.URL_GET_BOOK)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<Book> call = service.getSearchBook("金瓶梅","",0,1);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                System.out.print(response.body().toString());
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });

    }
}