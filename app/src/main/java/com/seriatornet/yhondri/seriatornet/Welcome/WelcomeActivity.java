package com.seriatornet.yhondri.seriatornet.Welcome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.seriatornet.yhondri.seriatornet.R;

public class WelcomeActivity extends AppCompatActivity implements WelcomeView {

    private WelcomePresentation presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.welcome);

        WelcomeWireframe router = new WelcomeRouter(this);
        presenter = new WelcomePresenter(router, this, this);
        presenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onStart(View view) {
        presenter.onStartClicked();
    }

    public void onRegister(View view) {
        presenter.onRegisterClicked();
    }

    //region WelcomeView implementation

    @Override
    public void finishActivity() {
        finish();
    }

    //enregion
}
