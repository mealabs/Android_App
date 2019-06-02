package com.home_automation.mealabs.android_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HomeAutomation homeAutomation = retrofit.create(HomeAutomation.class);

        Call<List<RoomStatus>> call = homeAutomation.getRoomStatuses();

        ((Call) call).enqueue(new Callback<List<RoomStatus>>() {
            @Override
            public void onResponse(Call<List<RoomStatus>> call, Response<List<RoomStatus>> response) {

                if (!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<RoomStatus> roomStatuses = response.body();

                for (RoomStatus status : roomStatuses){
                    String content = "";
                    content += "ID: " + status.getId() + "\n";
                    content += "User ID: " + status.getUserId() + "\n";
                    content += "Title: " + status.getTitle() + "\n";
                    content += "Text: " + status.getText() +"\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<RoomStatus>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }
}
