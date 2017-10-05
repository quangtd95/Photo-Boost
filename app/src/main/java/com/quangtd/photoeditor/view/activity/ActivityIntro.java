package com.quangtd.photoeditor.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.quangtd.photoeditor.MainActivity;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.utils.SharedPreferencesUtils;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class ActivityIntro extends MaterialIntroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.first_slide_background)
                .buttonsColor(R.color.first_slide_buttons)
                .image(R.mipmap.img_office)
                .title("Welcome to GNAS!")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.second_slide_background)
                .buttonsColor(R.color.second_slide_buttons)
                .title("Photo Editor")
                .description("Easy & Quick")
                .build());


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.third_slide_background)
                        .buttonsColor(R.color.third_slide_buttons)
                        .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        .neededPermissions(new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO})
                        .image(R.mipmap.img_equipment)
                        .title("13PFIEV3")
                        .description("Quang - Sang - Nhan")
                        .build(),
                new MessageButtonBehaviour(v -> showMessage("Try its!"), "Photo Editor"));

    }

    @Override
    public void onFinish() {
        super.onFinish();
        SharedPreferencesUtils.getInstance(this).setBool(getString(R.string.key_save_first_open_app), true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}