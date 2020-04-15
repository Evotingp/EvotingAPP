package com.example.evoting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.widget.Toast;

import com.example.evoting.adapter.AllCandidateListAdapter;
import com.example.evoting.models.CandidateListData;
import com.example.evoting.models.CandidateListResponse;
import com.example.evoting.utils.AESEncyption;
import com.example.evoting.utils.AllSharedPrefernces;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.MainThreadExecutor;
import com.example.evoting.utils.Webservice_Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static androidx.biometric.BiometricConstants.ERROR_NEGATIVE_BUTTON;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AllCandidatelistActivity extends AppCompatActivity implements DataInterface, AllCandidateListAdapter.onItemClickListner {

    Webservice_Volley volley;

    RecyclerView recyclerPost;

    AllSharedPrefernces allSharedPrefernces = null;

    String id = "";
    Boolean isElectionStarted;

    private BiometricPrompt biometricPrompt = null;
    private Executor executor = new MainThreadExecutor();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_candidatelist);

        if (biometricPrompt == null)
            biometricPrompt = new BiometricPrompt(this, executor, callback);

        id = getIntent().getStringExtra("id");
        isElectionStarted = getIntent().getBooleanExtra("isElectionStarted", false);

        allSharedPrefernces = new AllSharedPrefernces(this);

        volley = new Webservice_Volley(this, this);

        recyclerPost = (RecyclerView) findViewById(R.id.recyclerPost);
        recyclerPost.setLayoutManager(new LinearLayoutManager(this));

        recyclerPost.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        String url = Constants.Webserive_Url + "get_all_candidate";

        HashMap<String, String> params = new HashMap<>();
        params.put("Election_Id", id);

        volley.CallVolley(url, params, "get_all_candidate");
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {
        try {

            if (tag.equalsIgnoreCase("save_vote")) {
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            } else {
                CandidateListResponse candidateListResponse = new Gson().fromJson(jsonObject.toString(), CandidateListResponse.class);
                if (candidateListResponse != null) {
                    if (candidateListResponse.getResponse() != null) {
                        if (candidateListResponse.getResponse().size() > 0) {
                            AllCandidateListAdapter adapter = new AllCandidateListAdapter(candidateListResponse.getResponse(), this, isElectionStarted);
                            recyclerPost.setAdapter(adapter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isHardwareSupported(Context context) {
        FingerprintManagerCompat fingerprintManager =  FingerprintManagerCompat.from(context);
        return fingerprintManager.isHardwareDetected();
    }

    boolean isFingerprintAvailable(Context context) {
        FingerprintManagerCompat fingerprintManager =  FingerprintManagerCompat.from(context);
        return fingerprintManager.hasEnrolledFingerprints();
    }

    CandidateListData selectedCandidate = null;


    @Override
    public void setItemClickListener(int pos, CandidateListData candidateListData) {

        selectedCandidate = candidateListData;

        startAuth();


    }

    public void goForVote() {

         String data = selectedCandidate.getCandidateId() + "||" + selectedCandidate.getPartyName() + "||" + selectedCandidate.getFirstName() + " " + selectedCandidate.getMiddleName() + " " + selectedCandidate.getLastName();
        try {

            String encData = AESEncyption.encrypt(data);
            String url = Constants.Webserive_Url + "save_vote";
            HashMap<String, String> params = new HashMap<>();

            params.put("Election_Id", id);
            params.put("Voter_Id", allSharedPrefernces.getCustomerNo());
            params.put("vote_data", encData);
            params.put("vote_date_time", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));

            volley.CallVolley(url, params, "save_vote");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void startAuth() {

        try {
            if (!isHardwareSupported(this)) {
                toast("Your Device does not have a Fingerprint Sensor");

//                goForVote();

                return;
            }

            if (isFingerprintAvailable(this)) {
                toast("Register at least one fingerprint in Settings");
            }

            BiometricPrompt.PromptInfo promptInfo = buildBiometricPrompt();
            biometricPrompt.authenticate(promptInfo);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private BiometricPrompt.PromptInfo buildBiometricPrompt() {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authenticate Yourself")
                .setDescription("Touch your finger on the finger print sensor to authorise yourself .")
                .setNegativeButtonText("Cancel")
                .build();
    }


    private BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            if (errorCode == ERROR_NEGATIVE_BUTTON && biometricPrompt != null)
                biometricPrompt.cancelAuthentication();
            toast(errString.toString());
        }

        @Override
        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            toast("Authentication succeed");

            goForVote();
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            toast("Application did not recognize the placed finger print. Please try again!");
            biometricPrompt.cancelAuthentication();
        }
    };


    /*public void startAuth() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!fingerprintManager.isHardwareDetected()){
                *//**
                 * An error message will be displayed if the device does not contain the fingerprint hardware.
                 * However if you plan to implement a default authentication method,
                 * you can redirect the user to a default authentication activity from here.
                 * Example:
                 * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
                 * startActivity(intent);
                 *//*
                Toast.makeText(this, "Your Device does not have a Fingerprint Sensor", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if (!keyguardManager.isKeyguardSecure()) {

            Toast.makeText(this,
                    "Lock screen security not enabled in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,
                    "Fingerprint authentication permission not enabled",
                    Toast.LENGTH_LONG).show();

            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!fingerprintManager.hasEnrolledFingerprints()) {

                // This happens when no fingerprints are registered.
                Toast.makeText(this,
                        "Register at least one fingerprint in Settings",
                        Toast.LENGTH_LONG).show();
                return;
            }
        }


        generateKey();

        if (cipherInit()) {
            cryptoObject =
                    new FingerprintManager.CryptoObject(cipher);

            FingerprintHandler helper = new FingerprintHandler(this);
            helper.startAuth(fingerprintManager, cryptoObject);
        }


    }

    private KeyGenerator keyGenerator;
    private static final String KEY_NAME = "example_key";
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;

    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(new
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
            }
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    class FingerprintHandler extends
            FingerprintManager.AuthenticationCallback {

        private CancellationSignal cancellationSignal;
        private Context appContext;

        public FingerprintHandler(Context context) {
            appContext = context;
        }
        public void startAuth(FingerprintManager manager,
                              FingerprintManager.CryptoObject cryptoObject) {

            cancellationSignal = new CancellationSignal();

            if (ActivityCompat.checkSelfPermission(appContext,
                    Manifest.permission.USE_FINGERPRINT) !=
                    PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }

        @Override
        public void onAuthenticationError(int errMsgId,
                                          CharSequence errString) {
            Toast.makeText(appContext,
                    "Authentication error\n" + errString,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId,
                                         CharSequence helpString) {
            Toast.makeText(appContext,
                    "Authentication help\n" + helpString,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(appContext,
                    "Authentication failed.",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationSucceeded(
                FingerprintManager.AuthenticationResult result) {

            Toast.makeText(appContext,
                    "Authentication succeeded.",
                    Toast.LENGTH_LONG).show();
        }

    }*/


}
