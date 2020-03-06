package com.example.evoting.ui.voterprofile;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evoting.AddPostActivity;
import com.example.evoting.CandidateProfileEdit;
import com.example.evoting.R;
import com.example.evoting.VoterProfileActivity;
import com.example.evoting.VoterProfileEditActivity;
import com.example.evoting.utils.AllSharedPrefernces;
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
import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoterProfileFragment extends Fragment implements DataInterface {


    public VoterProfileFragment() {
        // Required empty public constructor
    }

    TextView txt_nameV, txt_emailV, txt_phoneV, txt_addressV, txt_cityV, txt_stateV, txt_dobV, txt_EditUserV;
    CircleImageView img_profile;
    FloatingActionButton EditImg_Profile;

    Webservice_Volley volley;

    JSONObject Data = null;

    AllSharedPrefernces allSharedPrefernces = null;

    View root = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_voter_profile, container, false);

        txt_nameV = (TextView) root.findViewById(R.id.txt_nameV);
        txt_emailV = (TextView) root.findViewById(R.id.txt_emailV);
        txt_phoneV = (TextView) root.findViewById(R.id.txt_phoneV);
        txt_addressV = (TextView) root.findViewById(R.id.txt_addressV);
        txt_cityV = (TextView) root.findViewById(R.id.txt_cityV);
        txt_stateV = (TextView) root.findViewById(R.id.txt_stateV);
        txt_dobV = (TextView) root.findViewById(R.id.txt_dobV);
        txt_EditUserV = (TextView) root.findViewById((R.id.txt_EditUserV));

        img_profile = (CircleImageView) root.findViewById(R.id.img_profile);
        EditImg_Profile = (FloatingActionButton) root.findViewById(R.id.EditImg_Profile);

        allSharedPrefernces = new AllSharedPrefernces(getActivity());

        volley = new Webservice_Volley(getActivity(), this);

        EditImg_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomPickerDialog();

            }
        });

        txt_EditUserV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), VoterProfileEditActivity.class);
                i.putExtra("data", Data.toString());
                startActivity(i);
            }
        });

        String url = Constants.Webserive_Url + "get_voterprofile";
        HashMap<String, String> params = new HashMap<>();
        params.put("Vid", allSharedPrefernces.getCustomerNo());
        volley.CallVolley(url, params, "get_voterprofile");

        return root;
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {
        try {
            if (tag.equalsIgnoreCase("update_cphoto")) {

                if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                    JSONObject jsonObject1 = new JSONObject(allSharedPrefernces.getCustomerData());

                    if (jsonObject1 != null) {

                        jsonObject1.put("Vphoto", imagePath);

                        allSharedPrefernces.setCustomerData(jsonObject1.toString());

                    }
                }

            } else {

                if (jsonObject.getString("status").equalsIgnoreCase("200")) {


                    JSONArray RESPONSE = jsonObject.getJSONArray("response");

                    if (RESPONSE.length() > 0) {
                        Data = RESPONSE.getJSONObject(0);
                        txt_nameV.setText(Data.getString("Vname"));
                        txt_emailV.setText(Data.getString("Vemail"));
                        txt_phoneV.setText(Data.getString("Vph"));
                        txt_addressV.setText(Data.getString("Vaddress"));
                        txt_cityV.setText(Data.getString("Vcity"));
                        txt_stateV.setText(Data.getString("Vstate"));
                        txt_dobV.setText(Data.getString("Vdob"));

                        Picasso.get().load(Constants.IMAGE_Url + Data.getString("Vphoto")).into(img_profile);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ClickOnVoterEdit(View view) {
        Intent i = new Intent(getActivity(), VoterProfileEditActivity.class);
        i.putExtra("data", Data.toString());
        startActivity(i);
    }

    public void showBottomPickerDialog() {

        try {

            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            bottomSheetDialog.setContentView(R.layout.imagepickerdialog);

            ImageView imgCancel = (ImageView) bottomSheetDialog.findViewById(R.id.imgCancel);
            LinearLayout ll_camera = (LinearLayout) bottomSheetDialog.findViewById(R.id.ll_camera);
            LinearLayout ll_gallery = (LinearLayout) bottomSheetDialog.findViewById(R.id.ll_gallery);


            ll_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyImage.openCameraForImage(getActivity(), 1);
                    bottomSheetDialog.dismiss();
                }
            });

            ll_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyImage.openGallery(getActivity(), 1);
                    bottomSheetDialog.dismiss();

                }
            });


            imgCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bottomSheetDialog.dismiss();

                }
            });


            bottomSheetDialog.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                if (imageFiles.size() > 0) {

                    img_profile.setImageURI(Uri.fromFile(imageFiles.get(0)));

                    try {
                        uploadPhoto(imageFiles.get(0));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    String imagePath = "";

    private void uploadPhoto(File myFile) throws FileNotFoundException {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Log.v("File Size", "origial Length===>" + myFile.length());

        if (myFile != null) {
            Log.v("File ", "with compress===>");
            params.put("userPhoto", myFile);
        }

        ResponseHandlerInterface handler = new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


                Toast.makeText(getActivity(), "Upload fail", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                        Toast.makeText(AddPostActivity.this, responseString, Toast.LENGTH_LONG).show();

                try {

                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject != null) {
                        String path = jsonObject.getString("filename");

                        String url = Constants.Webserive_Url + "update_vphoto";
                        HashMap<String, String> params = new HashMap<>();
                        params.put("Vphoto", path);
                        params.put("Vid", allSharedPrefernces.getCustomerNo());

                        volley.CallVolley(url, params, "update_vphoto");


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        client.post(Constants.Webserive_Url + "photo", params, handler);

    }


}
