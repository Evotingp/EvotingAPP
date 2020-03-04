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

import com.android.volley.Request;
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

import org.json.JSONException;
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

public class AddPostActivity extends AppCompatActivity implements DataInterface {

    TextView edd_Title, edd_Description;
    ImageView imageView;
    FloatingActionButton AddImg_Post;
    Button btn_add;

    String imagePath;

    Webservice_Volley volley;

    JSONObject Data = null;

    AllSharedPrefernces allSharedPrefernces = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        edd_Title = (TextView) findViewById(R.id.edd_Title);
        edd_Description = (TextView) findViewById(R.id.edd_Description);
        imageView = (ImageView) findViewById(R.id.imageView);
        AddImg_Post = (FloatingActionButton) findViewById(R.id.AddImg_Post);
        btn_add = (Button) findViewById(R.id.btn_add);

        allSharedPrefernces = new AllSharedPrefernces(this);

        volley = new Webservice_Volley(this, this);

        AddImg_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomPickerDialog();

            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Commonfunction.checkString(edd_Title.getText().toString())) {
                    edd_Title.setError("PLEASE ENTER THE TITLE");
                    return;
                }

                if (!Commonfunction.checkString(edd_Description.getText().toString())) {
                    edd_Description.setError("PLEASE ENTER THE DESCRIPTION");
                    return;
                }


                String url = Constants.Webserive_Url + "add_feedpost";
                HashMap<String, String> params = new HashMap<>();

                params.put("Title", edd_Title.getText().toString());
                params.put("Description", edd_Description.getText().toString());
                params.put("Image", imagePath);
                params.put("PostedDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                params.put("Cid", allSharedPrefernces.getCustomerNo());
                volley.CallVolley(url, params, "add_feedpost");
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
                    EasyImage.openCameraForImage(AddPostActivity.this, 1);
                    bottomSheetDialog.dismiss();
                }
            });

            ll_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EasyImage.openGallery(AddPostActivity.this, 1);
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

                    imageView.setImageURI(Uri.fromFile(imageFiles.get(0)));

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

                Toast.makeText(AddPostActivity.this, "Upload fail", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                        Toast.makeText(AddPostActivity.this, responseString, Toast.LENGTH_LONG).show();

                try {

                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject != null) {
                        imagePath = jsonObject.getString("filename");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        client.post(Constants.Webserive_Url + "photo", params, handler);

    }


}
