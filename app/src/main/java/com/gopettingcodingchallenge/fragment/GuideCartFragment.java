package com.gopettingcodingchallenge.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gopettingcodingchallenge.AppController;
import com.gopettingcodingchallenge.R;
import com.gopettingcodingchallenge.adapter.GuideCartAdapter;
import com.gopettingcodingchallenge.adapter.GuideListAdapter;
import com.gopettingcodingchallenge.model.Guide;
import com.gopettingcodingchallenge.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideCartFragment extends Fragment {

    RecyclerView rvListOfGuide;
    public GuideCartFragment() {
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
        initRecyclerView();
        getGuides();
    }

    private void getGuides() {
        rvListOfGuide.setAdapter(new GuideCartAdapter(Constants.cart,getActivity(),rvListOfGuide));
    }

    private void initRecyclerView() {
        rvListOfGuide.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
}
