package com.example.ocrappthird;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.MediaPlayer;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.Handler;
        import android.os.VibrationEffect;
        import android.os.Vibrator;
        import android.util.Base64;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.ajts.androidmads.library.SQLiteToExcel;
        import com.example.ocrappthird.databse.DatabaseHelper;
        import com.example.ocrappthird.entities.Visitor;
        import com.example.ocrappthird.helper.BitmapHelper;

        import java.io.ByteArrayOutputStream;
        import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    EditText resultED,mobileEdOne,mobileEdTwo,emailEdOne,emailEdTwo,webEdOne,webEdTwo,addressEd,companyEd,nameEd,cardNoteEd;
    Button savecardBtn;
    DatabaseHelper db;
    Context context;
    Bundle b;
    AlertDialog.Builder builder;
    ImageView  imageView;
    byte[] imageBytes;
    String imgstr;


    SQLiteToExcel sqliteToExcel;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context=DetailsActivity.this;
        b=getIntent().getBundleExtra("bundle");
        //imageBytes=getIntent().getByteArrayExtra(Visitor.COLUMN_IMAGE);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Card Details");


        db = new DatabaseHelper(context);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEd=(EditText)findViewById(R.id.nameEd);
        mobileEdOne=(EditText)findViewById(R.id.mobileEdOne);
        mobileEdTwo=(EditText)findViewById(R.id.mobileEdTwo);
        emailEdOne=(EditText)findViewById(R.id.emailEdOne);
        emailEdTwo=(EditText)findViewById(R.id.emailEdTwo);
        webEdOne=(EditText)findViewById(R.id.webEdOne);
        webEdTwo=(EditText)findViewById(R.id.webEdTwo);
        addressEd=(EditText)findViewById(R.id.address);
        companyEd=(EditText)findViewById(R.id.companyName);
        cardNoteEd=(EditText)findViewById(R.id.cardnote);
        savecardBtn=(Button)findViewById(R.id.saveBtn);
        imageView=(ImageView)findViewById(R.id.transferedImage);


        nameEd.setText(b.getString(Visitor.COLUMN_NAME));
        mobileEdOne.setText(b.getString(Visitor.COLUMN_MOB_ONE));
        mobileEdTwo.setText(b.getString(Visitor.COLUMN_MOB_TWO));
        emailEdOne.setText(b.getString(Visitor.COLUMN_EMAIL_ONE));
        emailEdTwo.setText(b.getString(Visitor.COLUMN_EMAIL_TWO));
        webEdOne.setText(b.getString(Visitor.COLUMN_WEB_ONE));
        webEdTwo.setText(b.getString(Visitor.COLUMN_WEB_TWO));
        addressEd.setText(b.getString(Visitor.COLUMN_ADDRESS));
        companyEd.setText(b.getString(Visitor.COLUMN_COMPANY));
        cardNoteEd.setText(b.getString(Visitor.COLUMN_CARD_NOTE));


        nameEd.requestFocus();
        //imgstr=b.getString("img");

        //byte[] decodedString = Base64.decode(imgstr, Base64.DEFAULT);
        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //imageView.setImageBitmap(getImage(imageBytes));

        imageBytes=getBytes(BitmapHelper.getInstance().getBitmap());
        imageView.setImageBitmap(BitmapHelper.getInstance().getBitmap());



      /*  visitor.setName("Amol");
        visitor.setMobone("9921538083");
        visitor.setMobtwo("7045154988");
        visitor.setEmailone("mailtobodhare@gmail.com");
        visitor.setEmailtwo("abodhare461@gmail.com");
        visitor.setWebone("www.stagnetworks.com");
        visitor.setWebtwo("www.mumbai39.com");
        visitor.setAddress("Ramnagar,Aurangabad");
        visitor.setCompany("Stag Networks Pvt Ltd.");
*/
        savecardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Visitor visitor=new Visitor();

                visitor.setName(nameEd.getText().toString());
                visitor.setMobone(mobileEdOne.getText().toString());
                visitor.setMobtwo(mobileEdTwo.getText().toString());
                visitor.setEmailone(emailEdOne.getText().toString());
                visitor.setEmailtwo(emailEdTwo.getText().toString());
                visitor.setWebone(webEdOne.getText().toString());
                visitor.setWebtwo(webEdTwo.getText().toString());
                visitor.setAddress(addressEd.getText().toString());
                visitor.setCompany(companyEd.getText().toString());
                visitor.setCardnote(cardNoteEd.getText().toString());
                visitor.setImageByteArray(imageBytes);

                if(b.getBoolean("insert"))
                {
                    saveDataInDb(visitor);
                }
                else
                {

                    int id=b.getInt(Visitor.COLUMN_ID);
                    Visitor dbVisitor=db.getVisitor(id);

                    if(dbVisitor.getName().equals(visitor.getName())&&dbVisitor.getMobone().equals(visitor.getMobone())
                            &&dbVisitor.getMobtwo().equals(visitor.getMobtwo())&&dbVisitor.getMobtwo().equals(visitor.getMobtwo())&&
                            dbVisitor.getEmailone().equals(visitor.getEmailone())&&dbVisitor.getEmailtwo().equals(visitor.getEmailtwo())&&
                            dbVisitor.getWebone().equals(visitor.getWebone())&&dbVisitor.getWebtwo().equals(visitor.getWebtwo())&&
                            dbVisitor.getAddress().equals(visitor.getAddress())&&dbVisitor.getCompany().equals(visitor.getCompany())&&
                            dbVisitor.getCardnote().equals(visitor.getCardnote()))
                    {

                    }

                    else
                    {
                        updateDataInDb(visitor,id);
                    }


                }

                /*ArrayList<Visitor> alistVisitor=db.getAllVisitors();

                if(alistVisitor.size()==0)
                    {


                    }
                else
                    {


                    if(check(alistVisitor,visitor))
                        {
                            Toast.makeText(context, "Data Already Exist", Toast.LENGTH_SHORT).show();
                        }
                    else
                        {
                            saveDataInDb(visitor);

                        }

                    }
*/


            }
        });

        if(MainActivity.loadingDialog.isShowing())
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.loadingDialog.cancel();
                }
            },1000);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.deleteRecord)
        {
            //deleteRecord();
            showDialog();
        }

        else {
        onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveDataInDb(Visitor visitor) {
        //  App.barcodeFormat = rawResult.getBarcodeFormat().toString();
        //H.log("line166", "isExecuted.");
        //App.barcodeString = string;

        //H.log("line172", "isExecuted.");


        if (db.insertVisitor(visitor) > 0) {

            showSuccessPopUp();
            finish();
            startActivity(new Intent(DetailsActivity.this, MainActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(context, "Failed to insert", Toast.LENGTH_SHORT).show();
        }

        //setCount();
    }
    private void updateDataInDb(Visitor visitor,int id)
    {
        if (db.updateVisitor(visitor,id) > 0) {
            //showSuccessPopUp();
            vibrate();
            Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(DetailsActivity.this, MainActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }

    }
    private void showSuccessPopUp() {
        vibrateAndBip();

       /* final ImageView imageView = findViewById(R.id.imageView);
        final TextView textView = findViewById(R.id.scanStatus);
        imageView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
        imageView.setColorFilter(getResources().getColor(R.color.drawer_border));
        textView.setText("Barcode scanned successfully.");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        }, 1000);*/
    }
    private void vibrateAndBip() {
        MediaPlayer.create(this, R.raw.beep).start();
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(350);
        }
    }
    private void vibrate()
    {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(333, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(333);
        }
    }
    private  void deleteRecord()
    {
        if (b.get(Visitor.COLUMN_ID) == null) {
            Toast.makeText(context, "card not saved", Toast.LENGTH_SHORT).show();
        }
        else
        {
              int value=db.deleteCard((int)b.get(Visitor.COLUMN_ID));

        if(value>0)
        {
            Toast.makeText(context, "card deleted", Toast.LENGTH_SHORT).show();
            finish();

            startActivity(new Intent(DetailsActivity.this, MainActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(context, "card not deleted", Toast.LENGTH_SHORT).show();
            //return;
        }

        }

    }
    private  void showDialog()
    {
        builder = new AlertDialog.Builder(this);
        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to Delte this card ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteRecord();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 0, stream);
        return stream.toByteArray();
    }
    private  boolean check(ArrayList<Visitor> alistVisitor,Visitor visitor)
    {
        for (int i = 0; i < alistVisitor.size(); i++) {
            Visitor dbVisitor = alistVisitor.get(i);

            if ((dbVisitor.getName().contains(visitor.getName())&&!visitor.getName().isEmpty()) ||
                    (dbVisitor.getEmailone().contains(visitor.getEmailone())&&!visitor.getEmailone().isEmpty()) ||
                    (dbVisitor.getMobone().contains(visitor.getMobone())&&!visitor.getMobone().isEmpty())) {
                //Toast.makeText(context, "Data Already Exist", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public static void showSnackBar(View view, String message) {
        //Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
