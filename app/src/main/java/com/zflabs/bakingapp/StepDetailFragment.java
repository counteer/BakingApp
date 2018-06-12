package com.zflabs.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.zflabs.bakingapp.data.Steps;
import com.zflabs.bakingapp.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    @BindView(R.id.step_detail_description)
    TextView description;

    @BindView(R.id.video_view)
    PlayerView playerView;

    @BindView(R.id.next_button)
    Button nextButton;

    private SimpleExoPlayer player;

    private Long position;

    private Steps steps;

    private boolean lastStep;

    public StepDetailFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            outState.putLong("position", player.getCurrentPosition());
        } else {
            outState.putLong("position", 0L);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        String stepsString = args.getString("step");
        Steps steps = JsonUtils.getStep(stepsString);
        this.steps = steps;
        this.lastStep = args.getBoolean("last");
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail_view, container, false);
        ButterKnife.bind(this, rootView);
        description.setText(steps.getDescription());
        if (RecipeHowtoActivity.twoPane || lastStep) {
            nextButton.setVisibility(View.GONE);
        }

        if (savedInstanceState != null) {
            position = savedInstanceState.getLong("position", 0L);
        }

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        playerView.setPlayer(player);

        // Measures bandwidth during playback. Can be null if not required.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "yourApplicationName"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(steps.getVideoURL()));
        player.prepare(videoSource);
//        player.getCurrentPosition();
        if (position != null) player.seekTo(position);
        return rootView;
    }
}
