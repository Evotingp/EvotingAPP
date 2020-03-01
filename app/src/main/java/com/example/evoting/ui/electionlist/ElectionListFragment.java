package com.example.evoting.ui.electionlist;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evoting.AddPostActivity;
import com.example.evoting.R;
import com.example.evoting.adapter.ElectionListAdapter;
import com.example.evoting.adapter.MyListAdapter;
import com.example.evoting.models.ElectionListInfoVo;
import com.example.evoting.models.FeedPostResponseVo;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElectionListFragment extends Fragment implements DataInterface {
    Webservice_Volley volley;

    RecyclerView recyclerPost;


    public ElectionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_election_list, container, false);

        volley = new Webservice_Volley(getActivity(), this);

        recyclerPost = (RecyclerView) root.findViewById(R.id.recyclerPost);
        recyclerPost.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerPost.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        String url = Constants.Webserive_Url + "get_election_list";

        HashMap<String, String> params = new HashMap<>();

        volley.CallVolley(url, params, "get_election_list");

        return root;
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {
        try {

            ElectionListInfoVo electionListInfoVo = new Gson().fromJson(jsonObject.toString(), ElectionListInfoVo.class);

            if (electionListInfoVo != null) {

                if (electionListInfoVo.getResponse() != null) {

                    if (electionListInfoVo.getResponse().size() > 0) {

                        ElectionListAdapter adapter = new ElectionListAdapter(electionListInfoVo.getResponse());
                        recyclerPost.setAdapter(adapter);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
