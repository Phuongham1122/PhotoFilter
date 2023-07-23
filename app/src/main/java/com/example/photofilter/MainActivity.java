package com.example.photofilter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<String> storagePermissionLauncher;
    final String storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //    final String storagePermission = Manifest.permission.ACCESS_FINE_LOCATION;
    TextView openPhotoBtn;
    TextView savePhotoBtn;
    com.google.android.material.floatingactionbutton.FloatingActionButton openPhotoStorage;
    LinearLayout layout;
    ImageView imageView;

    public BitmapDrawable ogBmp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        imageView = findViewById(R.id.displayPhoto);
        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                getPhoto();
            } else {
                respondToUserOnPermissionActions();
            }
        });

        photoStoreBtns();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.selectImage) {
                    Intent intent = new Intent(MainActivity.this,EditPhotoActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.exitApp) {
                    return true;
                }

                return false;
            }
        });


    }

    private void photoStoreBtns() {
        openPhotoStorage = findViewById(R.id.btnAddPhoto);
        openPhotoStorage.setOnClickListener(v -> storagePermissionLauncher.launch(storagePermission));
    }

    private void respondToUserOnPermissionActions() {
        if (ContextCompat.checkSelfPermission(this, storagePermission) == PackageManager.PERMISSION_GRANTED) {
            getPhoto();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println(shouldShowRequestPermissionRationale(storagePermission));
            if (shouldShowRequestPermissionRationale(storagePermission)) {
                new AlertDialog.Builder(this)
                        .setTitle("Requesting Permission")
                        .setMessage("Allow us to show and save photos")
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                storagePermissionLauncher.launch(storagePermission);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        }
    }

    private void getPhoto() {
        System.out.println("GET!");
        List<Photo> photos = new ArrayList<>();

        Uri libraryUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            libraryUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            libraryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

        try (Cursor cursor = getContentResolver().query(libraryUri, projection, null, null, sortOrder)) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
            int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
            int bucketIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID);
            int bucketNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                String date = cursor.getString(dateColumn);
                int size = cursor.getInt(sizeColumn);
                long bucketId = cursor.getLong(bucketIdColumn);
                String bucketName = cursor.getString(bucketNameColumn);

                Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                name = name.substring(0, name.lastIndexOf("."));

                Photo photo = new Photo(id, String.valueOf(uri), name, date, size, bucketId, bucketName);

                photos.add(photo);
            }
        }
        showPhotos(photos);
    }

    private void showPhotos(List<Photo> photos) {
        if(photos.size() > 0 ) {
            System.out.println(photos.size());
            PhotoBottomSheet bottomSheet = new PhotoBottomSheet(photos);
            bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
        }else {
        }
    }

}