package com.mother.dryyourmother;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String PurposeFilePath = Environment.getExternalStorageDirectory().getPath()+"/DCIM/100ANDRO/a.d";
        final String OriginalFilePath = Environment.getExternalStorageDirectory().getPath()+"/Download/a.d";

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 無權限，向使用者請求
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            READ_EXTERNAL_STORAGE},REQUEST_EXTERNAL_STORAGE);
        }

        Button BtnCopy = (Button)findViewById(R.id.btn_copy);
        BtnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    //Delete original file
                    //String filepath= Environment.getExternalStorageDirectory().getPath()+"/DCIM/100ANDRO/DSC_0001.JPG";
                    File file = new File(PurposeFilePath);
                    System.out.println(file);

                    if(file.exists())
                    {
                        System.out.println("Exists");
                        Boolean result = file.delete();
                        System.out.println(result);
                    }

                    //Copy file

                    File PurposeFile = new File(PurposeFilePath);
                    File OriginalFile = new File(OriginalFilePath);

                    InputStream in = new FileInputStream(OriginalFile);
                    OutputStream out = new FileOutputStream(PurposeFile);

                    byte[] buf = new byte[1024];

                    int len;

                    while ((len = in.read(buf)) > 0) {

                        out.write(buf, 0, len);

                    }

                    in.close();

                    out.close();
                }
                catch(Exception e)
                {

                    System.out.println(e);
                }


            }
        });
    }
}
