package com.example.evoting.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evoting.R;
import com.example.evoting.adapter.MyListAdapter;
import com.example.evoting.models.FeedPostResponseVo;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class HomeFragment extends Fragment implements DataInterface {

    Webservice_Volley volley;

    RecyclerView recyclerPost;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        volley = new Webservice_Volley(getActivity(),this);

        recyclerPost = (RecyclerView)root.findViewById(R.id.recyclerPost);
        recyclerPost.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerPost.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        String url = Constants.Webserive_Url + "get_feedpost";

        HashMap<String,String> params = new HashMap<>();

        volley.CallVolley(url,params,"get_feedpost");

        return root;
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {

        try {

            FeedPostResponseVo feedPostResponseVo = new Gson().fromJson(jsonObject.toString(),FeedPostResponseVo.class);

            if (feedPostResponseVo != null) {

                if (feedPostResponseVo.getResponse() != null) {

                    if (feedPostResponseVo.getResponse().size() > 0) {

                        MyListAdapter adapter = new MyListAdapter(feedPostResponseVo.getResponse());
                        recyclerPost.setAdapter(adapter);


                    }


                }

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}