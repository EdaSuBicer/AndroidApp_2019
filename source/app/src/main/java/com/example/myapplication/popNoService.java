package com.example.myapplication;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.util.DisplayMetrics;

public class popNoService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_no_service);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));
    }
}
