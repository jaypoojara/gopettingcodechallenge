package com.gopettingcodingchallenge.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gopettingcodingchallenge.AppController;
import com.gopettingcodingchallenge.R;
import com.gopettingcodingchallenge.adapter.GuideListAdapter;
import com.gopettingcodingchallenge.model.Guide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideListFragment extends Fragment {

    RecyclerView rvListOfGuide;
    ProgressBar progressBar;
    public GuideListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvListOfGuide = (RecyclerView) view.findViewById(R.id.rvListOfGuide);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        initRecyclerView();
        getGuides();
    }

    private void getGuides() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Guide> call = AppController.getInstance().getApiInterface().getGuides();
        call.enqueue(new Callback<Guide>() {
            @Override
            public void onResponse(Call<Guide> call, Response<Guide> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body()!=null){
                    rvListOfGuide.setAdapter(new GuideListAdapter(response.body(),getActivity(),rvListOfGuide));
                }
            }

            @Override
            public void onFailure(Call<Guide> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void initRecyclerView() {
        rvListOfGuide.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
}
