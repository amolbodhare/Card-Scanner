package com.example.ocrappthird;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.MessageBox;
import com.adoisstudio.helper.Session;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.ocrappthird.commen.App;
import com.example.ocrappthird.databse.DatabaseHelper;
import com.example.ocrappthird.entities.Visitor;
import com.example.ocrappthird.helper.BitmapHelper;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static  final int CAMEA_REQUEST_CODE=200;
    private static  final int STORAGE_REQUEST_CODE=400;
    private static  final int IMAGE_PICK_GALLERY_CODE=1000;
    private static  final int IMAGE_PICK_CAMERA_CODE=1001;
    String cameraPermission[];
    String storagePermission[];
    private long l;
    DatabaseHelper db;
    boolean insert;

    Uri image_uri;
    EditText resultED,mobileEdOne,mobileEdTwo,emailEdOne,emailEdTwo,webEdOne,webEdTwo,addressEd,companyEd,noteEd,nameEd,testingDataEd;
    ImageView imv;
    Button saveBtn;
    private  static  final String TAG=MainActivity.class.getName();
    Bundle bundle;
    ListAdapter adapter;
    ListView listView;
    ArrayList<String> arrayList= new ArrayList<>();

    ArrayList<Visitor> modelClasses =new ArrayList<>();
    private RecyclerView recyclerView;
    SearchView searchView;
    public static LoadingDialog loadingDialog;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    Bitmap bitmap;
    byte[] byteArray;


    SQLiteToExcel sqliteToExcel;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=MainActivity.this;
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Card Scanner");
        actionBar.setSubtitle("Scan Visitor Cards");
        db=new DatabaseHelper(context);
        resultED=findViewById(R.id.resultEd);

        loadingDialog=new LoadingDialog(context,false);
        imv=(ImageView)findViewById(R.id.imv);
        mobileEdOne=(EditText)findViewById(R.id.mobileEdOne);
        mobileEdTwo=(EditText)findViewById(R.id.mobileEdTwo);
        emailEdOne=(EditText)findViewById(R.id.emailEdOne);
        emailEdTwo=(EditText)findViewById(R.id.emailEdTwo);
        webEdOne=(EditText)findViewById(R.id.webEdOne);
        webEdTwo=(EditText)findViewById(R.id.webEdTwo);
        addressEd=(EditText)findViewById(R.id.address);
        companyEd=(EditText)findViewById(R.id.companyName);
        noteEd=(EditText)findViewById(R.id.cardNote);
        nameEd=(EditText)findViewById(R.id.nameEd);
        saveBtn=(Button) findViewById(R.id.saveBtn);
        testingDataEd=(EditText)findViewById(R.id.testingData);
        listView=(ListView)findViewById(R.id.list_view);

        searchView=(SearchView)findViewById(R.id.searchView);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        saveBtn.setOnClickListener(this);



        /*arrayList.add("January");
        arrayList.add("February");
        arrayList.add("March");
        arrayList.add("April");
        arrayList.add("May");
        arrayList.add("June");
        arrayList.add("July");
        arrayList.add("August");
        arrayList.add("September");
        arrayList.add("October");
        arrayList.add("November");
        arrayList.add("December");*/


       /* modelClasses.add(new ModelClass("Amol","9921538083"));
        modelClasses.add(new ModelClass("Poonam","7045154988"));
        modelClasses.add(new ModelClass("Shital","9823480690"));
        modelClasses.add(new ModelClass("Komal","9595275846"));
        modelClasses.add(new ModelClass("Ramesh","9823480690"));
        modelClasses.add(new ModelClass("Priyanka","7276256325"));*/
        modelClasses=db.getAllVisitors();



        //adapter= new ListAdapter(arrayList);
        final MainAdapter adapter=new MainAdapter(context,modelClasses);
        recyclerView.setAdapter(adapter);
        //listView.setAdapter(adapter);

        searchView.setActivated(true);
        searchView.setQueryHint("Type your keyword here");
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });*/


        cameraPermission=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.addImage)
        {
            loadingDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mobileEdOne.getText().clear();
                    mobileEdTwo.getText().clear();
                    emailEdOne.getText().clear();
                    emailEdTwo.getText().clear();
                    webEdOne.getText().clear();
                    webEdTwo.getText().clear();
                    addressEd.getText().clear();
                    companyEd.getText().clear();
                    noteEd.getText().clear();
                    nameEd.getText().clear();
                    testingDataEd.getText().clear();

                    showImageImportDialog();

                }
            },1200);

        }
        /*if(id==R.id.exportRecord)
        {
            exportDatabase();
            //deleteRecord();
            //showDialog();
        }*/
        if(id==R.id.shareRecord)
        {

            exportDatabase();

            if(App.isInternetAvailable(context))
            {
                loadingDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shareExcel();
                    }
                },2000);

                //shareExcel();
            }
            else
            {
                H.showMessage(context,"connect to internet");
            }

            //deleteRecord();
            //showDialog();
        }
        if(id==R.id.logoutAccount)
        {

            showLogOutAlert();
            /*exportDatabase();

            if(App.isInternetAvailable(context))
            {
                loadingDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shareExcel();
                    }
                },2000);

                //shareExcel();
            }
            else
            {
                H.showMessage(context,"connect to internet");
            }*/

            //deleteRecord();
            //showDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareExcel() {
        try {

            String filelocation= Environment.getExternalStorageDirectory().getPath() + "/Backup/cards.xls";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            String message="File to be shared is .";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Cards Report");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse( "file://"+filelocation));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.setData(Uri.parse("mailto:mailtobodhare@gmail.com"));   //insert your email address("mailto:csent.company@gmail.com")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            if(loadingDialog.isShowing())
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.cancel();
                    }
                },2000);

            }

        } catch(Exception e)  {
            System.out.println("is exception raises during sending mail"+e);
            if(loadingDialog.isShowing())
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.cancel();
                    }
                },2000);

            }
        }
    }

    private void showImageImportDialog() {
        String[] items={"Camera","Gallery"};
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0)
                {
                    if(! checkCameraPermission())
                    {

                        requestCameraPermission();
                    }
                    else
                    {
                        pickCamera();
                    }

                }
                if(which==1)
                {
                    if(! checkStoragePermission())
                    {
                        requestStoragePermission();
                    }
                    else
                    {
                        pickGallery();
                    }

                }
            }
        });
        dialog.create().show();

        if(loadingDialog.isShowing())
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.cancel();
                }
            },500);

        }


    }

    private void pickGallery() {
        Intent b=new Intent(Intent.ACTION_PICK);
        b.setType("image/*");
        startActivityForResult(b,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        ContentValues contentValues=new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"NewPic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Image to Text");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);

    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {

        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return  result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMEA_REQUEST_CODE);
    }


    private boolean checkCameraPermission() {
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean result1= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return  result && result1;
    }

    private  void exportDatabase()
    {

        File file = new File(directory_path);   //check if there any file in this directory path
        if (!file.exists()) {
            file.mkdirs();
        }

        //Toast.makeText(context, "Export database", Toast.LENGTH_SHORT).show();
        // Export SQLite DB as EXCEL FILE
        sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DatabaseHelper.DATABASE_NAME, directory_path);
        sqliteToExcel.exportAllTables("cards.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                //Utils.showSnackBar(view, "Successfully Exported");
                //Toast.makeText(MainActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "No Export", Toast.LENGTH_SHORT).show();
                //Utils.showSnackBar(view, "Not Exported");
            }
        });

    }

    /*private boolean checkSelfPermission() {

        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==( PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== (PackageManager.PERMISSION_GRANTED);
        return  result && result1;
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case CAMEA_REQUEST_CODE:
                if(grantResults.length>0)
                {

                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && writeStorageAccepted)
                    {
                        pickCamera();
                    }
                    else
                    {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if(grantResults.length>0)
                {

                    boolean writeStorageAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(writeStorageAccepted)
                    {
                        pickGallery();
                    }
                    else
                    {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==IMAGE_PICK_GALLERY_CODE)
            {
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).start(this);

            }
            if(requestCode==IMAGE_PICK_CAMERA_CODE)
            {
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON).start(this);

            }

            if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            {
                CropImage.ActivityResult result=CropImage.getActivityResult(data);
                if(resultCode ==RESULT_OK)
                {
                    Uri resultUri=result.getUri();
                    imv.setImageURI(resultUri);
                    BitmapDrawable bitmapDrawable=(BitmapDrawable) imv.getDrawable();
                    bitmap=bitmapDrawable.getBitmap();
                    BitmapHelper.getInstance().setBitmap(bitmap);

                    //byteArray=getBytes(bitmap);

                    deleteFile(resultUri);

                    TextRecognizer textRecognizer=new TextRecognizer.Builder(getApplicationContext()).build();
                    if(!textRecognizer.isOperational())
                    {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        bundle=new Bundle();
                        //bundle.putByteArray("image",byteArray);
                        //String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        //bundle.putString("img",encodedImage);
                        Frame frame=new Frame.Builder().setBitmap(bitmap).build();
                        SparseArray<TextBlock> items=textRecognizer.detect(frame);

                        StringBuilder resultBuilder=new StringBuilder();

                        StringBuilder sb=new StringBuilder();

                        ArrayList<String> stringlist=new ArrayList<String>();
                        StringBuilder testsb=new StringBuilder();

                        for(int b=0;b<items.size();b++)
                        {
                            TextBlock myItem = items.valueAt(b);
                            String s = myItem.getValue();

                            testsb.append(s);
                            //testsb.append("\n");

                            if (s.contains("\n")) {
                                String lines[] = s.split("\n");
                                int lines_len = lines.length;
                                resultBuilder.append(s);
                                System.out.print(lines_len);
                                resultBuilder.append(s);

                                for (int j = 0; j < lines_len; j++)
                                {
                                    String item = lines[j];

                                    if(j==0)
                                    {

                                        if (!isMobNo(item)&& !isContainsDigit(item)&&item.contains("")&& ! item.contains("@")&& ! item.contains("www"))
                                        {
                                            if (nameEd.getText().toString().isEmpty())
                                            {
                                                nameEd.setText(item);
                                                bundle.putString(Visitor.COLUMN_NAME,nameEd.getText().toString());
                                            }
                                        }
                                    }
                                    if (item.contains("@")) {
                                        if (emailEdOne.getText().toString().isEmpty()) {
                                            emailEdOne.setText(item);
                                            bundle.putString(Visitor.COLUMN_EMAIL_ONE,emailEdOne.getText().toString());

                                        } else if (emailEdTwo.getText().toString().isEmpty()) {
                                            emailEdTwo.setText(item);
                                            bundle.putString(Visitor.COLUMN_EMAIL_TWO,emailEdTwo.getText().toString());
                                        }

                                    }

                                    if ((item.length() > 9 && item.length() < 22 && isContainsDigit(item)&& isMobNo(item))||item.startsWith("Tel.:")||item.startsWith("m:")||item.startsWith("b:")||item.startsWith("M:")
                                            ||item.startsWith("T:")||item.startsWith("Phone:")||item.startsWith("phone:")||item.startsWith("TEL :")||item.startsWith("TEL:")
                                            ||item.startsWith("tel:")||item.startsWith("Tel.:")||item.startsWith("Phone:")||item.startsWith("phone :")||item.startsWith("MOB:")||item.startsWith("MOB :"))
                                    {
                                        if (mobileEdOne.getText().toString().isEmpty()) {
                                            mobileEdOne.setText(item);
                                            bundle.putString(Visitor.COLUMN_MOB_ONE,mobileEdOne.getText().toString());
                                        } else if (mobileEdTwo.getText().toString().isEmpty()) {
                                            mobileEdTwo.setText(item);
                                            bundle.putString(Visitor.COLUMN_MOB_TWO,mobileEdTwo.getText().toString());
                                        }
                                    }
                                    if (isStartWithWWW(item)) {
                                        if (webEdOne.getText().toString().isEmpty()) {
                                            webEdOne.setText(item);
                                            bundle.putString(Visitor.COLUMN_WEB_ONE,webEdOne.getText().toString());
                                        } else if (emailEdTwo.getText().toString().isEmpty()) {
                                            webEdTwo.setText(item);
                                            bundle.putString(Visitor.COLUMN_WEB_TWO,webEdTwo.getText().toString());
                                        }

                                    }
                                    if (isEndsWithLtd(item)) {
                                        if (companyEd.getText().toString().isEmpty()) {
                                            companyEd.setText(item);
                                            bundle.putString(Visitor.COLUMN_COMPANY,companyEd.getText().toString());
                                        }

                                    }
                                    if(item.contains(",") && ! isMobNo(item)|| item.contains("-") && ! isMobNo(item))
                                    {
                                        sb.append(item);
                                        addressEd.setText(sb);
                                        bundle.putString(Visitor.COLUMN_ADDRESS,addressEd.getText().toString());
                                    }

                                }
                            }
                            else
                            {
                                int len = s.length();

                                System.out.print(s);
                                System.out.print(len);
                                String value=myItem.getValue();

                                Log.v(TAG, myItem.getValue());

                                System.out.print(value);

                                resultBuilder.append(myItem.getValue());


                                if (!isMobNo(myItem.getValue())&& !isContainsDigit(myItem.getValue())&&myItem.getValue().contains(""))
                                {
                                    if (nameEd.getText().toString().isEmpty())
                                    {
                                        nameEd.setText(myItem.getValue());
                                        bundle.putString(Visitor.COLUMN_NAME,nameEd.getText().toString());
                                    }
                                }

                                if ((myItem.getValue().length() > 9 && myItem.getValue().length() < 22 && isContainsDigit(myItem.getValue())&& isMobNo(myItem.getValue()))||myItem.getValue().startsWith("Tel.:")||myItem.getValue().startsWith("m:")||myItem.getValue().startsWith("b:")||myItem.getValue().startsWith("M:")
                                        ||myItem.getValue().startsWith("T:")||myItem.getValue().startsWith("Phone:")||myItem.getValue().startsWith("phone:")||myItem.getValue().startsWith("TEL :")||myItem.getValue().startsWith("TEL:")
                                        ||myItem.getValue().startsWith("tel:")||myItem.getValue().startsWith("Tel.:")||myItem.getValue().startsWith("Phone:")||myItem.getValue().startsWith("phone :")||myItem.getValue().startsWith("MOB:")||myItem.getValue().startsWith("MOB :"))
                                {
                                    if (mobileEdOne.getText().toString().isEmpty()) {
                                        mobileEdOne.setText(myItem.getValue());
                                        bundle.putString(Visitor.COLUMN_MOB_ONE,mobileEdOne.getText().toString());
                                    } else if (mobileEdTwo.getText().toString().isEmpty()) {
                                        mobileEdTwo.setText(myItem.getValue());
                                        bundle.putString(Visitor.COLUMN_MOB_TWO,mobileEdTwo.getText().toString());
                                    }
                                }

                                if (myItem.getValue().contains("@")&& !isStartWithWWW(myItem.getValue())) {
                                    if (emailEdOne.getText().toString().isEmpty()) {
                                        emailEdOne.setText(myItem.getValue());
                                        bundle.putString(Visitor.COLUMN_EMAIL_ONE,emailEdOne.getText().toString());
                                    } else if (emailEdTwo.getText().toString().isEmpty()) {
                                        emailEdTwo.setText(myItem.getValue());
                                        bundle.putString(Visitor.COLUMN_EMAIL_TWO,emailEdTwo.getText().toString());
                                    }

                                }

                                if (isStartWithWWW(myItem.getValue())) {
                                    if (webEdOne.getText().toString().isEmpty()) {
                                        webEdOne.setText(myItem.getValue());
                                        bundle.putString(Visitor.COLUMN_WEB_ONE,webEdOne.getText().toString());
                                    } else if (emailEdTwo.getText().toString().isEmpty()) {
                                        webEdTwo.setText(myItem.getValue());
                                        bundle.putString(Visitor.COLUMN_WEB_TWO,webEdTwo.getText().toString());

                                    }

                                }
                                if (isEndsWithLtd(myItem.getValue())) {
                                    if (companyEd.getText().toString().isEmpty()) {
                                        companyEd.setText(myItem.getValue());
                                        bundle.putString(Visitor.COLUMN_COMPANY,companyEd.getText().toString());
                                    }

                                }

                                if(myItem.getValue().contains(",") && ! isMobNo(myItem.getValue())|| myItem.getValue().contains("-") && ! isMobNo(myItem.getValue()))
                                {
                                    sb.append(myItem.getValue());
                                    addressEd.setText(sb);
                                    bundle.putString(Visitor.COLUMN_ADDRESS,addressEd.getText().toString());

                                }
                                //sb.append(myItem.getValue());
                                //sb.append("\n");
                            }//else
                        }//for
                        testsb.append(testsb);
                        resultED.setText(resultBuilder.toString());

                        testingDataEd.setText(testsb);


                        insert=true;

                        bundle.putString("name",nameEd.getText().toString());
                        bundle.putString("emailone",emailEdOne.getText().toString());
                        bundle.putString("emailtwo",emailEdTwo.getText().toString());
                        bundle.putString("mobone",mobileEdOne.getText().toString());
                        bundle.putString("mobtwo",mobileEdTwo.getText().toString());
                        bundle.putString("webone",webEdOne.getText().toString());
                        bundle.putString("webtwo",webEdTwo.getText().toString());
                        bundle.putString("address",addressEd.getText().toString());
                        bundle.putString("company",companyEd.getText().toString());
                        bundle.putString("cardnote",companyEd.getText().toString());
                        bundle.putBoolean("insert",insert);


                        Intent i=new Intent(MainActivity.this,DetailsActivity.class);



                        i.putExtra("bundle",bundle);
                        //i.putExtra(Visitor.COLUMN_IMAGE,byteArray);
                        startActivity(i);

                    }

                }
                else  if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
                {
                    Exception error=result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();

                }

            }

        }
    }

    public  boolean isContainsDigit(String sample)
    {
        boolean val=false;
        char[] chars = sample.toCharArray();
        //StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                //sb.append(c);
                val=true;
                return val;
            }
        }
        return false;
    }
    public  boolean isMobNo(String sample)
    {
        boolean val=true;
        char[] chars = sample.toCharArray();
        //StringBuilder sb = new StringBuilder();
        String lastTenDigits = "";
        //substring containing last 4 characters
        String str = sample.replaceAll("\\s", "");

        if (str.length() >= 10)
        {
            lastTenDigits = str.substring(str.length() - 10);
            boolean numeric = true;

            try {
                Double num = Double.parseDouble(lastTenDigits);
            } catch (NumberFormatException e) {
                numeric = false;
            }

            if(numeric)
                val=true;
            else
                val=false;
        }
        else
        {
            lastTenDigits = str;
            val=false;
        }
        return val;
    }
    public  boolean isStartWithWWW(String sample)
    {
        boolean val=false;

        if(sample.startsWith("www."))
        {
            val=true;
            return  val;
        }

        return val;
    }
    public  boolean isEndsWithLtd(String sample)
    {
        boolean val=false;

        if(sample.endsWith("Ltd")||sample.endsWith("Ltd.")||sample.endsWith(".Ltd")||sample.endsWith(".ltd")||
                sample.endsWith("ltd.")||sample.endsWith("Limited")||sample.endsWith("LTD,")||sample.endsWith("limited")||sample.endsWith("ltd,"))
        {
            val=true;
            return  val;
        }

        return val;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.saveBtn)
        {
            Intent i=new Intent(MainActivity.this,DetailsActivity.class);
            i.putExtra("bundle",bundle);
            startActivity(i);
        }

    }
    private void handleExit() {
        /*if (homeFragment.isVisible()) {
            if (System.currentTimeMillis() - l < 700) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            } else {
                H.showMessage(this, "Press again to exit.");
                l = System.currentTimeMillis();
            }
        }*/
        if (System.currentTimeMillis() - l < 700) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            finish();
        } else {
            //H.showMessage(this, "Press again to exit.");
            Toast.makeText(this, "Press again to exit.", Toast.LENGTH_SHORT).show();
            l = System.currentTimeMillis();
        }
    }

    public void deleteFile(Uri uri)
    {
        if(uri==null)
        {

        }
        else {

            /*File fdelete = new File(uri.getPath());
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    System.out.println("file Deleted :" + image_uri.getPath());
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("file not Deleted :" + image_uri.getPath());
                    Toast.makeText(this, " Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }*/
            try {
                getApplicationContext().getContentResolver().delete(image_uri, null, null);
                //return true;
            } catch (Throwable e) {
                e.printStackTrace();
                Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                //return false;
            }
        }

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        handleExit();
    }


    /* public class ListAdapter extends BaseAdapter implements Filterable {

         List<String> mData;
         List<String> mStringFilterList;
         ValueFilter valueFilter;
         private LayoutInflater inflater;

         public ListAdapter(List<String> cancel_type) {
             mData=cancel_type;
             mStringFilterList = cancel_type;
         }


         @Override
         public int getCount() {
             return mData.size();
         }

         @Override
         public String getItem(int position) {
             return mData.get(position);
         }

         @Override
         public long getItemId(int position) {
             return position;
         }

         @Override
         public View getView(int position, View convertView, final ViewGroup parent) {
 *//*
            if (inflater == null) {
                inflater = (LayoutInflater) parent.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }*//*


                convertView = LayoutInflater.from(getApplicationContext()).
                        inflate(R.layout.row_item, parent, false);


            // get current item to be displayed

            ((TextView)convertView.findViewById(R.id.stringName)).setText(mData.get(position));
            //RowItemBinding rowItemBinding = DataBindingUtil.inflate(inflater, R.layout.row_item, parent, false);
            //rowItemBinding.stringName.setText()s;


            //return rowItemBinding.getRoot();
            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (valueFilter == null) {
                valueFilter = new ValueFilter();
            }
            return valueFilter;
        }

        private class ValueFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    List<String> filterList = new ArrayList<>();
                    for (int i = 0; i < mStringFilterList.size(); i++) {
                        if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                            filterList.add(mStringFilterList.get(i));
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = mStringFilterList.size();
                    results.values = mStringFilterList;
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mData = (List<String>) results.values;
                notifyDataSetChanged();
            }

        }
    }*/
    public  class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> implements Filterable
    {
        private  Context context;
        private  List<Visitor> mc;
        private  List<Visitor> exampleListFull;



        public  MainAdapter(Context context,ArrayList<Visitor> modelClasses)
        {
            this.context=context;
            this.mc=modelClasses;
            exampleListFull=new ArrayList<>(modelClasses);
        }

        @NonNull
        @Override
        public MainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
            return  new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MainAdapter.MyViewHolder holder, int position) {
            holder.tvName.setText(mc.get(position).getName());
            holder.tvMobOne.setText(mc.get(position).getMobone());
            holder.tvEmailOne.setText(mc.get(position).getEmailone());


            byte[] bitmapdata=mc.get(position).getImageByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);

            /*Glide.with(context)
                    .asBitmap()
                    //.load(mImageUrls.get(position))
                    .load(bitmap)
                    .into(holder.imageView);*/

            holder.imageView.setImageBitmap(bitmap);

            holder.cardView.setTag(mc.get(position).getId());



            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    loadingDialog.show();
                    final Handler handler =new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            int id=(int)v.getTag();
                            Visitor visitor=db.getVisitor(id);

                            final Bundle bundle=new Bundle();

                            insert=false;
                            bundle.putInt(Visitor.COLUMN_ID,visitor.getId());
                            bundle.putString(Visitor.COLUMN_NAME,visitor.getName());
                            bundle.putString(Visitor.COLUMN_MOB_ONE,visitor.getMobone());
                            bundle.putString(Visitor.COLUMN_MOB_TWO,visitor.getMobtwo());
                            bundle.putString(Visitor.COLUMN_EMAIL_ONE,visitor.getEmailone());
                            bundle.putString(Visitor.COLUMN_EMAIL_TWO,visitor.getEmailtwo());
                            bundle.putString(Visitor.COLUMN_WEB_ONE,visitor.getWebone());
                            bundle.putString(Visitor.COLUMN_WEB_TWO,visitor.getWebtwo());
                            bundle.putString(Visitor.COLUMN_ADDRESS,visitor.getAddress());
                            bundle.putString(Visitor.COLUMN_COMPANY,visitor.getCompany());
                            bundle.putBoolean("insert",insert);
                            byte[] imgByteArray=visitor.getImageByteArray();

                            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByteArray, 0, imgByteArray.length);
                            BitmapHelper.getInstance().setBitmap(bitmap);


                            //String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            //bundle.putString("img",encodedImage);
                            //bundle.putByteArray("image",getBytes(bitmap));

                            Handler handler1=new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent i=new Intent(MainActivity.this,DetailsActivity.class);
                                    i.putExtra("bundle",bundle);
                                    //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    //SharedPreferences.Editor editor = pref.edit();

                                    //i.putExtra(Visitor.COLUMN_IMAGE,byteArray);
                                    startActivity(i);
                                    //loadingDialog.cancel();
                                }
                            },1000);
                        }
                    },1000);


                }
            });
        }

        @Override
        public int getItemCount() {
            return mc.size();
        }

        @Override
        public Filter getFilter() {
            return exampleFIlter;
        }
        private Filter exampleFIlter =new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Visitor> filteredList=new ArrayList<>();
                if(constraint==null || constraint.length()==0)
                {
                    filteredList.addAll(exampleListFull);
                }
                else
                {
                    String filterPattern=constraint.toString().toLowerCase().trim();
                    for(Visitor visitor:exampleListFull)
                    {
                        if(visitor.getName().toLowerCase().contains(filterPattern)
                                ||visitor.getEmailone().toLowerCase().contains(filterPattern)
                                ||visitor.getMobone().toLowerCase().contains(filterPattern)
                        )
                        {
                            filteredList.add(visitor);
                        }
                    }
                }
                FilterResults results=new FilterResults();
                results.values=filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mc.clear();
                mc.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };

        public  class  MyViewHolder extends RecyclerView.ViewHolder
        {

            private  TextView tvName,tvMobOne,tvEmailOne;
            ImageView imageView;
            CardView cardView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView=itemView.findViewById(R.id.card_view);
                tvName=itemView.findViewById(R.id.nameTv);
                tvMobOne=itemView.findViewById(R.id.mobOneTv);
                tvEmailOne=itemView.findViewById(R.id.emailOneTv);
                imageView=itemView.findViewById(R.id.foodImg);
            }


        }
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    private void eraseAppData() {
        Session session = new Session(context);
        session.clear();
        db.deleteAllCards();
        //new Session(context).addString(P.usertoken, "");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //((MainActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        finish();
    }
    public void showLogOutAlert() {
        MessageBox.showYesNoMessage(this, "alert",
                "Do  you  really  want  to  Exit?",
                "yes", "no", new MessageBox.OnYesNoListener() {
            @Override
            public void onYesNo(boolean isYes) {
                if (isYes)
                {

                        MessageBox.showYesNoMessage(MainActivity.this, "warning",
                                "You  will  lose  your  all  the  Records. " +
                                "\n\nExport  the  Excel  file  if  not  exported  before  Logout.\n\nYou  still  want  to  Logout  ?",
                                "No", "Yes", new MessageBox.OnYesNoListener() {
                            @Override
                            public void onYesNo(boolean isYes) {
                                if (!isYes)
                                    eraseAppData();
                                    //H.showMessage(context,"he kay aahe?");
                            }
                        });

                }
            }
        });
    }

}
