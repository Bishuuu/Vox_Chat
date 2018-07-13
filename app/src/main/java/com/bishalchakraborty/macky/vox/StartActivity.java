package com.bishalchakraborty.macky.vox;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    Button login , regsiter ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        login = findViewById(R.id.login);
        regsiter = findViewById(R.id.needac);
        login.setOnClickListener(this);
        regsiter.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login :
                Intent intent = new Intent(StartActivity.this , LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.needac :
                Intent intent1 = new Intent(StartActivity.this , RegisterActivity.class);
                startActivity(intent1);
                break;
        }

    }
}
