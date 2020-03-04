package com.example.evoting.ui.voterelectionlist;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evoting.R;
import com.example.evoting.adapter.ElectionListAdapter;
import com.example.evoting.adapter.VoterElectionListAdapter;
import com.example.evoting.models.ElectionListDataView;
import com.example.evoting.models.ElectionListInfoVo;
import com.example.evoting.utils.AllSharedPrefernces;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoterElectionlistFragment extends Fragment implements DataInterface, VoterElectionListAdapter.onItemClickListner {

    Webservice_Volley volley;

    RecyclerView recyclerPost;

    AllSharedPrefernces allSharedPrefernces = null;

    public VoterElectionlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_election_list, container, false);

        allSharedPrefernces = new AllSharedPrefernces(getActivity());

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

                        VoterElectionListAdapter adapter = new VoterElectionListAdapter(electionListInfoVo.getResponse(), this);
                        recyclerPost.setAdapter(adapter);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setItemClickListener(int pos, ElectionListDataView electionListDataView) {

    }
}
