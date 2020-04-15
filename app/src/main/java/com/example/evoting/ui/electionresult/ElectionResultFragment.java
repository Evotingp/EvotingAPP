package com.example.evoting.ui.electionresult;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evoting.R;
import com.example.evoting.adapter.ElectionResultAdapter;
import com.example.evoting.models.ElectionResultResponse;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElectionResultFragment extends Fragment implements DataInterface {

    public ElectionResultFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerElectionResult;
    Webservice_Volley volley;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_election_result, container, false);

        recyclerElectionResult = (RecyclerView)rootview.findViewById(R.id.recyclerElectionResult);
        recyclerElectionResult.setLayoutManager(new LinearLayoutManager(getActivity()));

        volley = new Webservice_Volley(getActivity(), this);

        String url = Constants.Webserive_Url + "get_result";
        HashMap<String, String> params = new HashMap<>();
        volley.CallVolley(url, params, "get_result");

        return rootview;
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {

        try {

            if (tag.equalsIgnoreCase("get_result")) {
                ElectionResultResponse electionResultResponse = new Gson().fromJson(jsonObject.toString(),ElectionResultResponse.class);
                if (electionResultResponse.getResponse() != null) {
                    if (electionResultResponse.getResponse().size() > 0) {
                        ElectionResultAdapter adapter = new ElectionResultAdapter(electionResultResponse.getResponse());
                        recyclerElectionResult.setAdapter(adapter);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
