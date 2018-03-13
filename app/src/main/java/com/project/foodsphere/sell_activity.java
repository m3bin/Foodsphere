package com.project.foodsphere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class sell_activity extends AppCompatActivity {

    Button uploadbtn, choosebtn;
    String pid;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private Spinner spinner;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_activity);

        //image choose
        choosebtn = (Button) findViewById(R.id.prod_btnChoose);
        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();
            }
        });
        //upload code
        uploadbtn = (Button) findViewById(R.id.prod_btn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             uploadImage();
            }
        });
    }

    public void chooseimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();

            img = (ImageView) findViewById(R.id.img);
            Glide.with(getApplicationContext()).load(filePath).into(img);

        }
    }

    private void uploadImage() {

        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if (filePath != null) {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference ref2 = database.getReference("products");
            pid = ref2.push().getKey();
            final Map products = new HashMap();
            EditText pnameEditText = (EditText) findViewById(R.id.prod_name);
            String pname = pnameEditText.getText().toString();

            EditText pdescEditText = (EditText) findViewById(R.id.prod_desc);
            String pdesc = pdescEditText.getText().toString();

            spinner = findViewById(R.id.prod_category);
            String pcategory = spinner.getSelectedItem().toString();

            EditText ppriceEditText = (EditText) findViewById(R.id.prod_price);
            String pprice = ppriceEditText.getText().toString();

            EditText pmeasurementEditText = (EditText) findViewById(R.id.prod_measurement);
            String pmeasurement = pmeasurementEditText.getText().toString();

            EditText pquantityEditText = (EditText) findViewById(R.id.prod_quantity);
            String pquantity = pquantityEditText.getText().toString();

            EditText plocationEditText = (EditText) findViewById(R.id.prod_location);
            String plocation = plocationEditText.getText().toString();

            products.put("name", pname);
            products.put("description", pdesc);
            products.put("category", pcategory);
            products.put("price", pprice);
            products.put("measurement", pmeasurement);
            products.put("quantity", pquantity);
            products.put("location", plocation);
            products.put("key",pid);

            StorageReference ref = storageReference.child("images/"+pid);
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(sell_activity.this, "Upload Successful ", Toast.LENGTH_SHORT).show();
                            Uri imageurl = taskSnapshot.getDownloadUrl();
                            String url = imageurl.toString();
                            products.put("image",url);
                            ref2.child(pid).setValue(products);
                            Intent intent = new Intent(sell_activity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(sell_activity.this, "Upload Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }
}