package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aoslec.humanconnect.NetworkTask.NetworkTaskSelect;
import com.aoslec.humanconnect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddressBookInsertActivity extends AppCompatActivity {

    String macIP, pw, urlAddr, urlAddr2, urlAddr3, iname, iemail = null;
    int mid, iphone = 0;
    Button choose, back, complete;
    EditText name, phone, email;
//    ImageView imageView;
//    private final int REQ_CODE_SELECT_IMAGE = 100;
//    private String img_path = new String();
//    private String urlAddr = "http://" + macIP + ":8080/humanconnect/test.jsp";;
//    private Bitmap image_bitmap_copy = null;
//    private Bitmap image_bitmap = null;
//    private String imageName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book_insert);
        setTitle("연락처 등록");
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .permitDiskReads()
//                .permitDiskWrites()
//                .permitNetwork().build());

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        pw = intent.getStringExtra("pw");
        mid = intent.getIntExtra("mid", 0);

//        choose = findViewById(R.id.insert_choose_btn);
        back = findViewById(R.id.insert_back);
        complete = findViewById(R.id.insert_complete);
        name = findViewById(R.id.insert_name);
        phone = findViewById(R.id.insert_phone);
        email = findViewById(R.id.insert_email);

        complete.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);

//        imageView = findViewById(R.id.insert_iv);
//
//        imageView.setOnClickListener(ivClickListener);
//        choose.setOnClickListener(ivClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            iname = name.getText().toString();
            iemail = email.getText().toString();
            iphone = Integer.parseInt(phone.getText().toString());

            urlAddr = "http://" + macIP + ":8080/humanconnect/addressBookInsert.jsp?";
            urlAddr = urlAddr + "name=" + iname + "&phone=" + iphone + "&email=" + iemail;
            Log.v("Message", urlAddr);

            urlAddr2 = "http://" + macIP + ":8080/humanconnect/addressBookInsert2.jsp?";
            urlAddr2 = urlAddr2 + "mid=" + mid;
            Log.v("Message", urlAddr2);
            switch (v.getId()){
                case R.id.insert_back:
                    intent = new Intent(AddressBookInsertActivity.this, MainActivity.class);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("mid", mid);
                    startActivity(intent);
                    break;
                case R.id.insert_complete:
                    String result = connectInsertData();
                    String result2 = connectInsertData2();

                    if (result.equals("1")){
                        Toast.makeText(AddressBookInsertActivity.this, "연락처가 추가되었습니다", Toast.LENGTH_SHORT).show();
                        if (result2.equals("1")){
                            Toast.makeText(AddressBookInsertActivity.this, "연락처가 추가되었습니다", Toast.LENGTH_SHORT).show();
                            intent = new Intent(AddressBookInsertActivity.this, MainActivity.class);
                            intent.putExtra("macIP", macIP);
                            intent.putExtra("mid", mid);
                            startActivity(intent);
                            break;
                        }else {
                            Toast.makeText(AddressBookInsertActivity.this, "입력이 실패 되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddressBookInsertActivity.this, "입력이 실패 되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private String connectInsertData(){
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTaskSelect networkTask = new NetworkTaskSelect(AddressBookInsertActivity.this, urlAddr, "insert");
            Object obj = networkTask.execute().get();
            // 1이 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private String connectInsertData2(){
        String result2 = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTaskSelect networkTask = new NetworkTaskSelect(AddressBookInsertActivity.this, urlAddr2, "insert");
            Object obj = networkTask.execute().get();
            // 1이 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result2 = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result2;
    }
//    View.OnClickListener ivClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.insert_iv:
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
//                    break;
//                case R.id.insert_choose_btn:
//                    DoFileUpload(urlAddr, img_path);
//                    Toast.makeText(getApplicationContext(), "이미지 전송 성공", Toast.LENGTH_SHORT).show();
//                    Log.d("Send", "Success");
//            }
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        Toast.makeText(getBaseContext(), "resultCode : " + data, Toast.LENGTH_SHORT).show();
//
//        if (requestCode == REQ_CODE_SELECT_IMAGE) {
//            if (resultCode == AddressBookInsertActivity.RESULT_OK) {
//                try {
//                    img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.
//                    Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
//                    //이미지를 비트맵형식으로 반환
//                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                    int reWidth = (int) (getWindowManager().getDefaultDisplay().getWidth());
//                    int reHeight = (int) (getWindowManager().getDefaultDisplay().getHeight());
//
//                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
//                    image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
//                    ImageView image = (ImageView) findViewById(R.id.imageView);  //이미지를 띄울 위젯 ID값
//                    image.setImageBitmap(image_bitmap_copy);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public String getImagePathToUri(Uri data) {
//        //사용자가 선택한 이미지의 정보를 받아옴
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(data, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//
//        //이미지의 경로 값
//        String imgPath = cursor.getString(column_index);
//        Log.d("test", imgPath);
//
//        //이미지의 이름 값
//        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
//        Toast.makeText(AddressBookInsertActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
//        this.imageName = imgName;
//
//        return imgPath;
//    }//end of getImagePathToUri()
//
//    public void DoFileUpload(String apiUrl, String absolutePath) {
//        HttpFileUpload(apiUrl, "", absolutePath);
//    }
//
//    String lineEnd = "\r\n";
//    String twoHyphens = "--";
//    String boundary = "*****";
//
//    public void HttpFileUpload(String urlString, String params, String fileName) {
//        try {
//
//            FileInputStream mFileInputStream = new FileInputStream(fileName);
//            URL connectUrl = new URL(urlString);
//            Log.d("Test", "mFileInputStream  is " + mFileInputStream);
//
//            // HttpURLConnection 통신
//            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setUseCaches(false);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//            // write data
//            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
//            dos.writeBytes(lineEnd);
//
//            int bytesAvailable = mFileInputStream.available();
//            int maxBufferSize = 1024;
//            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
//
//            byte[] buffer = new byte[bufferSize];
//            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
//
//            Log.d("Test", "image byte is " + bytesRead);
//
//            // read image
//            while (bytesRead > 0) {
//                dos.write(buffer, 0, bufferSize);
//                bytesAvailable = mFileInputStream.available();
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
//            }
//
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//            // close streams
//            Log.e("Test", "File is written");
//            mFileInputStream.close();
//            dos.flush();
//            // finish upload...
//
//            // get response
//            InputStream is = conn.getInputStream();
//
//            StringBuffer b = new StringBuffer();
//            for (int ch = 0; (ch = is.read()) != -1; ) {
//                b.append((char) ch);
//            }
//            is.close();
//            Log.e("Test", b.toString());
//
//
//        } catch (Exception e) {
//            Log.d("Test", "exception " + e.getMessage());
//            // TODO: handle exception
//        }
//    } // end of HttpFileUpload()

}