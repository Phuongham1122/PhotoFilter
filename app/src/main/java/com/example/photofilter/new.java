package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private EditText editText;
    Button t1,t2,t3,t4,t5,t6;
    Drawable cropDefault;
    Drawable cropHover ;
    Drawable lightDefault;
    Drawable lightHover;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.crop);
        t2 = findViewById(R.id.changeLight);
        t3 = findViewById(R.id.item7);
        t4 = findViewById(R.id.item8);
        cropDefault = ContextCompat.getDrawable(this,R.drawable.crop_24);
        cropHover = ContextCompat.getDrawable(this,R.drawable.crop_hover);
        lightDefault = ContextCompat.getDrawable(this,R.drawable.light_mode_24);
        lightHover = ContextCompat.getDrawable(this, R.drawable.light_hover);
//        Drawable now = ContextCompat.getDrawable(this,R.drawable.crop_hover);
        t1.setOnClickListener(view -> {
            t1.setCompoundDrawablesWithIntrinsicBounds(cropHover, null,null, null);
            t2.setCompoundDrawablesWithIntrinsicBounds(lightDefault,null,null,null);
        });
        t2.setOnClickListener(view -> {
            t1.setCompoundDrawablesWithIntrinsicBounds(cropDefault, null,null, null);
            t2.setCompoundDrawablesWithIntrinsicBounds(lightHover,null,null,null);
        });
        seekBar = findViewById(R.id.seekBar);
        editText = findViewById(R.id.editText);

        ImageView colorImage = findViewById(R.id.colorImage);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        int colors[] = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.WHITE, Color.TRANSPARENT,Color.DKGRAY};
        drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        drawable.setColors(colors);
        colorImage.setBackground(drawable);

        // Đặt giá trị mặc định cho kích thước văn bản của EditText
        editText.setTextSize(16);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Khi giá trị của SeekBar thay đổi, cập nhật kích thước văn bản của EditText
                int textSize = progress + 16; // Tăng kích thước bắt đầu từ 16sp
                editText.setTextSize(textSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần thực hiện gì khi bắt đầu theo dõi chạm SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Không cần thực hiện gì khi kết thúc theo dõi chạm SeekBar
            }
        });
        textColors();
        appearItemColor();
        
    }

    private void appearItemColor() {
        linearLayout = findViewById(R.id.colorItems);
        ImageView button = findViewById(R.id.colorImage);

        // Load animations from XML
        slideInLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideOutRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVisible) {
                    // Slide in the LinearLayout from the left
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout.startAnimation(slideInLeftAnimation);
                    isVisible = true;
                } else {
                    // Slide out the LinearLayout to the right
                    linearLayout.startAnimation(slideOutRightAnimation);
                    linearLayout.setVisibility(View.INVISIBLE);
                    isVisible = false;
                }
            }
        });
    }

    TextView white, sky, orange, aqua, gray, red, yellow;
    private LinearLayout linearLayout;
    private Animation slideInLeftAnimation;
    private Animation slideOutRightAnimation;
    private boolean isVisible = false;
    private void textColors() {
        ImageView colorImage = findViewById(R.id.colorImage);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        int colors[] = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.WHITE, Color.TRANSPARENT,Color.DKGRAY};
        drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        drawable.setColors(colors);
        colorImage.setBackground(drawable);
        white = findViewById(R.id.whiteColor);
        setColor(white, Color.WHITE);
        aqua = findViewById(R.id.aquaColor);
        setColor(aqua, Color.rgb(127,255,212));
        orange = findViewById(R.id.orangeColor);
        setColor(orange,Color.rgb(249,158,74));
        sky = findViewById(R.id.skyColor);
        setColor(sky,Color.rgb(25,171,230));
        gray = findViewById(R.id.grayColor);
        setColor(gray,Color.GRAY);
        red = findViewById(R.id.redColor);
        setColor(red,Color.RED);
        yellow = findViewById(R.id.yellowColor);
        setColor(yellow,Color.YELLOW);
        white.setOnClickListener(v -> {
            pickColor(white);
        });
        aqua.setOnClickListener(v -> {
            pickColor(aqua);
        });
        orange.setOnClickListener(view -> {
            pickColor(orange);
        });
        sky.setOnClickListener(view -> {
            pickColor(sky);
        });
        gray.setOnClickListener(view -> {
            pickColor(gray);
        });
        red.setOnClickListener(view -> {
            pickColor(red);
        });
        yellow.setOnClickListener(view -> {
            pickColor(yellow);
        });
    }

    private void setColor(TextView textView, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        textView.setBackground(drawable);
    }
    private void pickColor(TextView textView){
        TextView text = findViewById(R.id.textTest);
        GradientDrawable colorSet = (GradientDrawable) textView.getBackground();
        ColorStateList backGroundColors = colorSet.getColor();
        int backGroundColor = backGroundColors.getDefaultColor();
        text.setTextColor(backGroundColor);
        int ccl = text.getTextColors().getDefaultColor();
    }
}
