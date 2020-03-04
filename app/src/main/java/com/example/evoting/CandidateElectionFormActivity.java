package com.example.evoting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CandidateElectionFormActivity extends AppCompatActivity implements DataInterface {

    TextView edd_FirstName, edd_MiddleName, edd_LastName, edd_Address, edd_State, edd_City, edd_ZipCode, edd_dob;
    ImageView fidimage, bidimage, faddressimg, baddressimg;
    FloatingActionButton AddImg_FID, AddImg_BID, AddImg_FAddress, AddImg_BAddress;
    Button btn_submit;

    String imagePath;

    Webservice_Volley volley;

    JSONObject Data = null;

    AllSharedPrefernces allSharedPrefernces = null;

    ImageView selected_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_election_form);

        edd_FirstName = (TextView) findViewById(R.id.edd_FirstName);
        edd_MiddleName = (TextView) findViewById(R.id.edd_MiddleName);
        edd_LastName = (TextView) findViewById(R.id.edd_LastName);
        edd_Address = (TextView) findViewById(R.id.edd_Address);
        edd_State = (TextView) findViewById(R.id.edd_State);
        edd_City = (TextView) findViewById(R.id.edd_City);
        edd_ZipCode = (TextView) findViewById(R.id.edd_ZipCode);
        edd_dob = (TextView) findViewById(R.id.edd_dob);
        fidimage = (ImageView) findViewById(R.id.fidimage);
        bidimage = (ImageView) findViewById(R.id.bidimage);
        faddressimg = (ImageView) findViewById(R.id.faddressimg);
        baddressimg = (ImageView) findViewById(R.id.baddressimg);
        AddImg_FID = (FloatingActionButton) findViewById(R.id.AddImg_FID);
        AddImg_BID = (FloatingActionButton) findViewById(R.id.AddImg_BID);
        AddImg_FAddress = (FloatingActionButton) findViewById(R.id.AddImg_FAddress);
        AddImg_BAddress = (FloatingActionButton) findViewById(R.id.AddImg_BAddress);

        btn_submit = (Button) findViewById(R.id.btn_submit);

        allSharedPrefernces = new AllSharedPrefernces(this);

        volley = new Webservice_Volley(this, this);

        AddImg_FID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_imageview = fidimage;

                showBottomPickerDialog();

            }
        });
        AddImg_BID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_imageview = bidimage;
                showBottomPickerDialog();

            }
        });
        AddImg_FAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_imageview = faddressimg;
                showBottomPickerDialog();

            }
        });
        AddImg_BAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_imageview = baddressimg;
                showBottomPickerDialog();

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Commonfunction.checkString(edd_FirstName.getText().toString())) {
                    edd_FirstName.setError("PLEASE ENTER FIRST NAME");
                    return;
                }
                if (!Commonfunction.checkString(edd_MiddleName.getText().toString())) {
                    edd_FirstName.setError("PLEASE ENTER MIDDLE NAME");
                    return;
                }
                if (!Commonfunction.checkString(edd_LastName.getText().toString())) {
                    edd_FirstName.setError("PLEASE ENTER LAST NAME");
                    return;
                }
                if (!Commonfunction.checkString(edd_Address.getText().toString())) {
                    edd_FirstName.setError("PLEASE ENTER ADDRESS");
                    return;
                }
                if (!Commonfunction.checkString(edd_State.getText().toString())) {
                    edd_FirstName.setError("PLEASE ENTER STATE NAME");
                    return;
                }
                if (!Commonfunction.checkString(edd_City.getText().toString())) {
                    edd_FirstName.setError("PLEASE ENTER CITY NAME");
                    return;
                }
                if (!Commonfunction.checkString(edd_ZipCode.getText().toString())) {
                    edd_FirstName.setError("PLEASE ENTER ZIPCODE");
                    return;
                }
                if (!Commonfunction.checkString(edd_dob.getText().toString())) {
                    edd_FirstName.setError("PLEASE ENTER DATE OF BIRTH");
                    return;
                }

                String url = Constants.Webserive_Url + "candidate_participation";
                HashMap<String, String> params = new HashMap<>();

                params.put("First_Name", edd_FirstName.getText().toString());
                params.put("Middle_Name", edd_MiddleName.getText().toString());
                params.put("Last_Name", edd_LastName.getText().toString());
                params.put("Address", edd_Address.getText().toString());
                params.put("State", edd_State.getText().toString());
                params.put("City", edd_City.getText().toString());
                params.put("ZipCode", edd_ZipCode.getText().toString());
                params.put("DOB", edd_dob.getText().toString());

                params.put("Front_Id", fidimage.getTag().toString());
                params.put("Back_Id", bidimage.getTag().toString());
                params.put("Front_Address", faddressimg.getTag().toString());
                params.put("Back_Address", faddressimg.getTag().toString());
                params.put("Is_Approve", "0");

                params.put("Candidate_Id", allSharedPrefernces.getCustomerNo());

                volley.CallVolley(url, params, "candidate_participation");
            }
        });

    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {
        Toast.makeText(this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
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
                    EasyImage.openCameraForImage(CandidateElectionFormActivity.this, 1);
                    bottomSheetDialog.dismiss();
                }
            });

            ll_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyImage.openGallery(CandidateElectionFormActivity.this, 1);
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

                    try {
                        uploadPhoto(imageFiles.get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    selected_imageview.setImageURI(Uri.fromFile(imageFiles.get(0)));

                }

            }
        });
    }

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

                imagePath = "";

                Toast.makeText(CandidateElectionFormActivity.this, "Upload fail", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                        Toast.makeText(AddPostActivity.this, responseString, Toast.LENGTH_LONG).show();

                try {

                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject != null) {
                        imagePath = jsonObject.getString("filename");

                        selected_imageview.setTag(imagePath);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        client.post(Constants.Webserive_Url + "photo", params, handler);

    }

}

