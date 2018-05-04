package com.seriatornet.yhondri.seriatornet.Module.ShowDetails;

import android.content.Context;
import android.content.Intent;

import com.seriatornet.yhondri.seriatornet.Model.AppKey;
import com.seriatornet.yhondri.seriatornet.Module.Episodes.EpisodesActivity;
import com.seriatornet.yhondri.seriatornet.Module.Main.Views.MainActivity;

public class ShowDetailsRouter implements ShowDetailsWireframe {

    private Context context;

    public ShowDetailsRouter(Context context) {
        this.context = context;
    }

    @Override
    public void goToEpisodesList(int showId) {
        Intent episodesIntent = new Intent(context, EpisodesActivity.class);

        episodesIntent.putExtra(AppKey.SHOW_ID, showId);

        context.startActivity(episodesIntent);
    }
}
