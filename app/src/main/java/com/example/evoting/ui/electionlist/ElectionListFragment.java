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
import android.widget.Button;
import android.widget.Toast;

import com.example.evoting.AddPostActivity;
import com.example.evoting.R;
import com.example.evoting.adapter.ElectionListAdapter;
import com.example.evoting.adapter.MyListAdapter;
import com.example.evoting.models.ElectionListDataView;
import com.example.evoting.models.ElectionListInfoVo;
import com.example.evoting.models.FeedPostResponseVo;
import com.example.evoting.utils.AllSharedPrefernces;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
public class ElectionListFragment extends Fragment implements DataInterface, ElectionListAdapter.onItemClickListner {

    Webservice_Volley volley;

    RecyclerView recyclerPost;

    AllSharedPrefernces allSharedPrefernces = null;

    public ElectionListFragment() {
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

            if (tag.equalsIgnoreCase("get_candidate_participation")) {

                JSONArray jsonArray = jsonObject.getJSONArray("response");

                if (jsonArray.length() > 0) {

                    JSONObject jobj = jsonArray.getJSONObject(0);

                    if (jobj.has("DOB")) {

                        String dob = jobj.getString("DOB");
                        String is_docApprove = jobj.getString("Is_DocApprove");

                        if (is_docApprove.equalsIgnoreCase("1")) {

                            Date dobDate = parseDate(dob);
                            Date curDate = new Date();

                            int diff = getDiffYears(dobDate, curDate);


                            if (selectedElection != null) {

                                if (diff < selectedElection.getMinAge()) {

                                    Toast.makeText(getActivity(), "YOu are not elligible", Toast.LENGTH_LONG).show();

                                } else {

                                    String url = Constants.Webserive_Url + "election_request";

                                    HashMap<String, String> params = new HashMap<>();

                                    params.put("Election_Id", "" + selectedElection.getId());
                                    params.put("Cid", allSharedPrefernces.getCustomerNo());
                                    params.put("Is_Approve", "0");

                                    volley.CallVolley(url, params, "election_request");

                                }

                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Your documents are not approved.", Toast.LENGTH_LONG).show();
                        }


                    }


                } else {
                    Toast.makeText(getActivity(), "Please Fill Participation form first.", Toast.LENGTH_LONG).show();
                }

            } else if (tag.equalsIgnoreCase("election_request")) {

                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

            } else {

                ElectionListInfoVo electionListInfoVo = new Gson().fromJson(jsonObject.toString(), ElectionListInfoVo.class);

                if (electionListInfoVo != null) {

                    if (electionListInfoVo.getResponse() != null) {

                        if (electionListInfoVo.getResponse().size() > 0) {

                            ElectionListAdapter adapter = new ElectionListAdapter(electionListInfoVo.getResponse(), this);
                            recyclerPost.setAdapter(adapter);

                        }

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date parseDate(String dt) {

        try {

            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy hh:mm aa");

            Date date = input.parse(dt);

            return date;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    ElectionListDataView selectedElection;

    @Override
    public void setItemClickListener(int pos, ElectionListDataView electionListDataView) {

        selectedElection = electionListDataView;

        String url = Constants.Webserive_Url + "get_candidate_participation";
        HashMap<String, String> params = new HashMap<>();

        params.put("Candidate_Id", allSharedPrefernces.getCustomerNo());

        volley.CallVolley(url, params, "get_candidate_participation");


    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR)) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
}
