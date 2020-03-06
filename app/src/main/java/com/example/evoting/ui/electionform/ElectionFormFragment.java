package com.example.evoting.ui.electionform;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evoting.CandidateElectionFormActivity;
import com.example.evoting.CandidateForgotPasswordActivity;
import com.example.evoting.CandidateLoginActivity;
import com.example.evoting.R;
import com.example.evoting.utils.AllSharedPrefernces;
import com.example.evoting.utils.Commonfunction;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class ElectionFormFragment extends Fragment implements DataInterface {

    public ElectionFormFragment() {
        // Required empty public constructor
    }

    TextView txt_PartyName, txt_CanName, txt_Address;
    ImageView img_partylogo;
    FloatingActionButton btn_edit;

    Webservice_Volley volley;

    JSONObject Data = null;

    AllSharedPrefernces allSharedPrefernces = null;

    View root = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_election_form, container, false);

        txt_PartyName = (TextView) root.findViewById(R.id.txt_PartyName);
        txt_CanName = (TextView) root.findViewById(R.id.txt_CanName);
        txt_Address = (TextView) root.findViewById(R.id.txt_Address);
        img_partylogo = (ImageView) root.findViewById(R.id.img_partylogo);

        btn_edit = (FloatingActionButton) root.findViewById(R.id.btn_edit);

        allSharedPrefernces = new AllSharedPrefernces(getActivity());

        volley = new Webservice_Volley(getActivity(), this);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CandidateElectionFormActivity.class);
                startActivity(i);
            }
        });

        String url = Constants.Webserive_Url + "get_candidate_participation";

        HashMap<String, String> params = new HashMap<>();

        params.put("Candidate_Id", allSharedPrefernces.getCustomerNo());

        volley.CallVolley(url, params, "get_candidate_participation");
        return root;
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {

        try {

            JSONArray jsonArray = jsonObject.getJSONArray("response");

            if (jsonArray.length() > 0) {
                JSONObject jobj = jsonArray.getJSONObject(0);

                if (jobj != null) {

                    Picasso.get().load(Constants.IMAGE_Url + jobj.getString("Party_Logo")).into(img_partylogo);

                    txt_PartyName.setText(jobj.getString("Party_Name"));
                    txt_CanName.setText(jobj.getString("First_Name") + " " + jobj.getString("Middle_Name") +" "+jobj.getString("Last_Name"));
                    txt_Address.setText(jobj.getString("Address") + " " + jobj.getString("City") + " " + jobj.getString( "State") + " " + jobj.getString( "ZipCode"));

                    btn_edit.setVisibility(View.GONE);

                }
                else {
                    btn_edit.setVisibility(View.VISIBLE);
                }
            }
            else {
                btn_edit.setVisibility(View.VISIBLE);
            }


        }
        catch (Exception e ){
            e.printStackTrace();
        }

    }
}
