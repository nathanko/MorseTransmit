package com.nathanko.morsetransmit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    String message;
    String morsemsg;
    int flashMs;
    final int FLASH_MS_MIN = 200;
    EditText messageEditText;
    TextView morseTextView;
    SeekBar seekBar;
    ProgressBar progressBar;
    boolean active;


    private CameraManager mCameraManager;
    private String mCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageEditText = (EditText) findViewById(R.id.message_edittext);
        morseTextView = (TextView) findViewById(R.id.morse_textview);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        message = "";
        morsemsg = "";
        flashMs = FLASH_MS_MIN;
        morseTextView.setText(morsemsg);
        active = true;

        messageEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                message = messageEditText.getText().toString();
                morsemsg = "";
                if (message.length() > 0) {
                    for (int i = 0; i < message.length(); i++) {
                        morsemsg += convert(message.toLowerCase().charAt(i)) + " ";
                    }
                }
                progressBar.setVisibility(View.VISIBLE);
                morseTextView.setText(morsemsg);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Check if device supports flash
        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {

            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                    System.exit(0);
                }
            });
            alert.show();
            return;
        }

        //create mCameraManager object
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }


    public void transmitListener(View v) {
        progressBar.setMax(morsemsg.length());
        setProgressBar(0);
        transmit();
    }

    public void transmit(){
        if (seekBar.getProgress() > FLASH_MS_MIN) {
            flashMs = seekBar.getProgress();
        }


        for (int i = 0; i < morsemsg.length(); i++) {
            setProgressBar(i+1);
            Log.v("CODE", i+" "+progressBar.getProgress());
            /*if (morsemsg.charAt(i) == '-') {
                flash(3 * flashMs);
            } else if (morsemsg.charAt(i) == '.') {
                flash(flashMs);
            } else {
                delay(flashMs);
            }
            delay(flashMs);*/
        }
    }

    public void setProgressBar(int i){
        progressBar.setProgress(i);
    }

    public void flash(int ms) {
        try {
            turnOnFlash();
        } catch (Exception e) {
            e.printStackTrace();
        }

        delay(ms);

        try {
            turnOffFlash();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test(View v) {
        Log.v("CODE", "test");
    }

    public void turnOnFlash() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void turnOffFlash() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public String convert(char letter) {
        String code = "";
        switch (letter) {
            case 'a': {
                code += ".-";
                break;
            }
            case 'b': {
                code += "-...";
                break;
            }
            case 'c': {
                code += "-.-.";
                break;
            }
            case 'd': {
                code += "-..";
                break;
            }
            case 'e': {
                code += ".";
                break;
            }
            case 'f': {
                code += "..-.";
                break;
            }
            case 'g': {
                code += "--.";
                break;
            }
            case 'h': {
                code += "....";
                break;
            }
            case 'i': {
                code += "..";
                break;
            }
            case 'j': {
                code += ".---";
                break;
            }
            case 'k': {
                code += "-.-";
                break;
            }
            case 'l': {
                code += ".-..";
                break;
            }
            case 'm': {
                code += "--";
                break;
            }
            case 'n': {
                code += "-.";
                break;
            }
            case 'o': {
                code += "---";
                break;
            }
            case 'p': {
                code += ".--.";
                break;
            }
            case 'q': {
                code += "--.-";
                break;
            }
            case 'r': {
                code += ".-.";
                break;
            }
            case 's': {
                code += "...";
                break;
            }
            case 't': {
                code += "-";
                break;
            }
            case 'u': {
                code += "..-";
                break;
            }
            case 'v': {
                code += "...-";
                break;
            }
            case 'w': {
                code += ".--";
                break;
            }
            case 'x': {
                code += "-..-";
                break;
            }
            case 'y': {
                code += "-.--";
                break;
            }
            case 'z': {
                code += "--..";
                break;
            }
            case ' ': {
                code += "  "; //space is a delay between words
                break;
            }
            default: {
            }
        }
        return code;
    }

    @Override
    protected void onDestroy() {
        Log.v("CODE", "onDestroy");
        super.onDestroy();
        turnOffFlash();
    }
    @Override
    protected void onStop() {
        Log.v("CODE", "onStop");
        super.onStop();
        try {
            turnOffFlash();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        Log.v("CODE", "onPause");
        super.onPause();
        try {
            turnOffFlash();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}