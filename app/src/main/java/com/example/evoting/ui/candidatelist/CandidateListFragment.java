package com.example.evoting.ui.candidatelist;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evoting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CandidateListFragment extends Fragment {


    public CandidateListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidate_list, container, false);
    }

}
