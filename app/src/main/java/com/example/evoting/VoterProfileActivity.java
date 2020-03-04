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

public class VoterProfileActivity extends AppCompatActivity implements DataInterface {

    TextView txt_nameV, txt_emailV, txt_phoneV, txt_addressV, txt_cityV, txt_stateV, txt_dobV;
    CircleImageView img_profile;
    FloatingActionButton EditImg_Profile;

    Webservice_Volley volley;

    JSONObject Data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_profile);

        txt_nameV = (TextView) findViewById(R.id.txt_nameV);
        txt_emailV = (TextView) findViewById(R.id.txt_emailV);
        txt_phoneV = (TextView) findViewById(R.id.txt_phoneV);
        txt_addressV = (TextView) findViewById(R.id.txt_addressV);
        txt_cityV = (TextView) findViewById(R.id.txt_cityV);
        txt_stateV = (TextView) findViewById(R.id.txt_stateV);
        txt_dobV = (TextView) findViewById(R.id.txt_dobV);

        img_profile = (CircleImageView) findViewById(R.id.img_profile);
        EditImg_Profile = (FloatingActionButton) findViewById(R.id.EditImg_Profile);

        volley = new Webservice_Volley(this, this);

        EditImg_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomPickerDialog();

            }
        });

        String url = Constants.Webserive_Url + "get_voterprofile";
        HashMap<String, String> params = new HashMap<>();
        params.put("Vid", "1");
        volley.CallVolley(url, params, "get_voterprofile");
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {

        try {

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

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ClickOnVoterEdit(View view) {
        Intent i = new Intent(VoterProfileActivity.this, VoterProfileEditActivity.class);
        i.putExtra("data", Data.toString());
        startActivity(i);
    }

    public void showBottomPickerDialog() {

        try {

            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.imagepickerdialog);

            ImageView imgCancel = (ImageView) bottomSheetDialog.findViewById(R.id.imgCancel);
            LinearLayout ll_camera = (LinearLayout) bottomSheetDialog.findViewById(R.id.ll_camera);
            LinearLayout ll_gallery = (LinearLayout) bottomSheetDialog.findViewById(R.id.ll_gallery);


            ll_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyImage.openCameraForImage(VoterProfileActivity.this, 1);
                    bottomSheetDialog.dismiss();
                }
            });

            ll_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyImage.openGallery(VoterProfileActivity.this, 1);
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
