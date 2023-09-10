// done by Samantha Lok
package com.example.bottomnavigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    ImageView logo, background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        logo = findViewById(R.id.splash_screen);

        logo.animate().translationY(1400).setDuration(1000).setStartDelay(3000);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0, // Starting angle (in degrees)
                360, // Ending angle (in degrees)
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point X coordinate (relative to the image view width)
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point Y coordinate (relative to the image view height)
        );

        // Set the duration of the animation (in milliseconds)
        rotateAnimation.setDuration(3500); // 2 seconds


        // Start the animation
        logo.startAnimation(rotateAnimation);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Close the current activity
        }, 5000); // Delay in milliseconds (3 seconds in this case)



    }
}
