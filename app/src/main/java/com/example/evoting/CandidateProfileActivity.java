package com.example.evoting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyLog;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CandidateProfileActivity extends AppCompatActivity implements DataInterface {

    TextView txt_nameC, txt_emailC, txt_phoneC,txt_addressC,txt_cityC, txt_stateC, txt_dobC;
    CircleImageView img_profile;
    FloatingActionButton EditImg_Profile;

    Webservice_Volley volley;

    JSONObject Data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        txt_nameC=(TextView)findViewById(R.id.txt_nameC);
        txt_emailC=(TextView)findViewById(R.id.txt_emailC);
        txt_phoneC=(TextView)findViewById(R.id.txt_phoneC);
        txt_addressC=(TextView)findViewById(R.id.txt_addressC);
        txt_cityC=(TextView)findViewById(R.id.txt_cityC);
        txt_stateC=(TextView)findViewById(R.id.txt_stateC);
        txt_dobC=(TextView)findViewById(R.id.txt_dobC);

        img_profile=(CircleImageView)findViewById(R.id.img_profile);
        EditImg_Profile=(FloatingActionButton)findViewById(R.id.EditImg_Profile);

        volley = new Webservice_Volley(this,this);

        EditImg_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomPickerDialog();

            }
        });

        String url = Constants.Webserive_Url + "get_candidateprofile";
        HashMap<String,String> params = new HashMap<>();
        params.put("Cid","1");
        volley.CallVolley(url,params,"get_candidateprofile");
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {
        try {

            if (jsonObject.getString("status").equalsIgnoreCase("200")){


                JSONArray RESPONSE = jsonObject.getJSONArray("response");

                if(RESPONSE.length()>0)
                {
                    Data=RESPONSE.getJSONObject(0);
                    txt_nameC.setText(Data.getString("Cname"));
                    txt_emailC.setText(Data.getString("Cemail"));
                    txt_phoneC.setText(Data.getString("Cph"));
                    txt_addressC.setText(Data.getString("Caddress"));
                    txt_cityC.setText(Data.getString("Ccity"));
                    txt_stateC.setText(Data.getString("Cstate"));
                    txt_dobC.setText(Data.getString("Cdob"));

                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void ClickOnCandidateEdit(View view) {
        Intent i = new Intent(CandidateProfileActivity.this,CandidateProfileEdit.class);
        i.putExtra("data",Data.toString());
        startActivity(i);
    }

    public void showBottomPickerDialog() {

        try {

            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.imagepickerdialog);

            ImageView imgCancel = (ImageView)bottomSheetDialog.findViewById(R.id.imgCancel);
            LinearLayout ll_camera = (LinearLayout)bottomSheetDialog.findViewById(R.id.ll_camera);
            LinearLayout ll_gallery = (LinearLayout)bottomSheetDialog.findViewById(R.id.ll_gallery);



            ll_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyImage.openCameraForImage(CandidateProfileActivity.this,1);
                    bottomSheetDialog.dismiss();
                }
            });

            ll_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyImage.openGallery(CandidateProfileActivity.this,1);
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


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                if (imageFiles.size() > 0) {

                    img_profile.setImageURI(Uri.fromFile(imageFiles.get(0)));

                }

            }
        });
    }
}
