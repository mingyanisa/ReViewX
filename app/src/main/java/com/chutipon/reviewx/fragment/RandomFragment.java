package com.chutipon.reviewx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chutipon.reviewx.R;
import com.chutipon.reviewx.manager.RandomMovieManager;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class RandomFragment extends Fragment implements RandomMovieManager.onLoad {
    private static final String TAG = "RandomFragment";
    private static RandomFragment instance;
    private FrameLayout frameLayout;
    private TextView textView;
    private ImageView img;

    public static RandomFragment getInstance() {
        if (instance == null) {
            instance = new RandomFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        RandomMovieManager.getInstance().load(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_random, container, false);
        initInstance(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstance(View rootView, Bundle savedInstanceState) {
        frameLayout = rootView.findViewById(R.id.loading_screen);
        img = rootView.findViewById(R.id.movieImage);
        textView = rootView.findViewById(R.id.movieName);
    }

    @Override
    public void onLoadComplete() {
        frameLayout.setVisibility(View.GONE);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(true)
                .build();
        Picasso.with(getContext())
                .load(RandomMovieManager.getInstance().getMovieSuggestionInfoDao().getPosterPath())
                .resize(350, 350)
                .centerCrop()
                .transform(transformation)
                .into(img);
        textView.setText(RandomMovieManager.getInstance().getMovieSuggestionInfoDao().getTitle());
    }
}
