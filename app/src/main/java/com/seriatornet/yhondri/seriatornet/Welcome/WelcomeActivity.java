package com.seriatornet.yhondri.seriatornet.Welcome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.seriatornet.yhondri.seriatornet.R;

public class WelcomeActivity extends AppCompatActivity {

    private WelcomePresentation presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WelcomeWireframe router = new WelcomeRouter(this);
        presenter = new WelcomePresenter(router);
    }

    public void onStart(View view) {
        presenter.onStartClicked();
    }

    public void onRegister(View view) {
        presenter.onRegisterClicked();
    }
}
