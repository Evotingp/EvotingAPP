package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.evoting.R;
import com.example.evoting.adapter.AllCandidateListAdapter;
import com.example.evoting.adapter.ElectionListAdapter;
import com.example.evoting.models.CandidateListData;
import com.example.evoting.models.CandidateListResponse;
import com.example.evoting.models.ElectionListInfoVo;
import com.example.evoting.utils.AllSharedPrefernces;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class AllCandidatelistActivity extends AppCompatActivity implements DataInterface, AllCandidateListAdapter.onItemClickListner {

    Webservice_Volley volley;

    RecyclerView recyclerPost;

    AllSharedPrefernces allSharedPrefernces = null;

    String id = "";
    Boolean isElectionStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_candidatelist);

        id = getIntent().getStringExtra("id");
        isElectionStarted = getIntent().getBooleanExtra("isElectionStarted",false);

        allSharedPrefernces = new AllSharedPrefernces(this);

        volley = new Webservice_Volley(this, this);

        recyclerPost = (RecyclerView) findViewById(R.id.recyclerPost);
        recyclerPost.setLayoutManager(new LinearLayoutManager(this));

        recyclerPost.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        String url = Constants.Webserive_Url + "get_all_candidate";

        HashMap<String, String> params = new HashMap<>();
        params.put("Election_Id",id);

        volley.CallVolley(url, params, "get_all_candidate");

    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {

        CandidateListResponse candidateListResponse = new Gson().fromJson(jsonObject.toString(), CandidateListResponse.class);

        if (candidateListResponse != null) {

            if (candidateListResponse.getResponse() != null) {

                if (candidateListResponse.getResponse().size() > 0) {

                    AllCandidateListAdapter adapter = new AllCandidateListAdapter(candidateListResponse.getResponse(), this,isElectionStarted);
                    recyclerPost.setAdapter(adapter);

                }

            }

        }

    }

    @Override
    public void setItemClickListener(int pos, CandidateListData candidateListData) {

    }
}
