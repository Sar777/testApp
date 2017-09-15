package by.orion.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://devcandidates.alef.im/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TestService service = retrofit.create(TestService.class);
        service.images().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                List<String> images = response.body();
                if (images == null) {
                    return;
                }

                ViewGroup viewGroup = findViewById(R.id.gridlayout_main);
                if (viewGroup == null) {
                    return;
                }

                Context context = MainActivity.this;
                for (String imageUrl : images) {
                    ImageView imageView = newImageView(context);
                    viewGroup.addView(imageView);

                    Glide.with(context)
                            .load(imageUrl)
                            .into(imageView);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    private static ImageView newImageView(@NonNull Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400, 400);
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }
}
