package com.chaolemen.shoppingclm.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.chaolemen.shoppingclm.R;

public class PersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        Toast.makeText(this, "个人信息", Toast.LENGTH_SHORT).show();
    }
}
