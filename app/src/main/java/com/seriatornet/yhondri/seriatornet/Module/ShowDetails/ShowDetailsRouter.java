package com.seriatornet.yhondri.seriatornet.Module.ShowDetails;

import android.content.Context;
import android.content.Intent;

import com.seriatornet.yhondri.seriatornet.Module.Episodes.EpisodesActivity;
import com.seriatornet.yhondri.seriatornet.Module.Main.Views.MainActivity;

public class ShowDetailsRouter implements ShowDetailsWireframe {

    private Context context;

    public ShowDetailsRouter(Context context) {
        this.context = context;
    }

    @Override
    public void goToEpisodesList() {
        Intent episodesIntent = new Intent(context, EpisodesActivity.class);
        context.startActivity(episodesIntent);
    }
}
