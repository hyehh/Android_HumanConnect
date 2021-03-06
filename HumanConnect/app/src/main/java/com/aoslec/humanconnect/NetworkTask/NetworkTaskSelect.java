package com.aoslec.humanconnect.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aoslec.humanconnect.Bean.AddressBook;
import com.aoslec.humanconnect.Bean.Member;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTaskSelect extends AsyncTask<Integer, String, Object> {

    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<AddressBook> addressBooks = null;
    String where = null;

    public NetworkTaskSelect(Context context, String mAddr, String where){
        this.context = context;
        this.mAddr = mAddr;
        this.addressBooks = addressBooks;
        this.addressBooks = new ArrayList<AddressBook>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        Log.v("Message", "onPreExecute");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get.....");
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v("Message", "doInBackground");
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        // ?????? ?????? ?????? ???????????? ??? ???????????? ???????????? result??? ????????? ????????? ??????
        String result = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                // ??????????????????!!
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String strLine = bufferedReader.readLine();
                    if(strLine == null) break;
                    stringBuffer.append(strLine + "\n");
                }
                if (where.equals("selectAll")) {
                    parserSelect(stringBuffer.toString());
                } else if(where.equals("delete")){
                    result = parserAction(stringBuffer.toString());
                }else if(where.equals("update")){
                    result = parserAction(stringBuffer.toString());
                }else if(where.equals("select")){
                    parserSelect2(stringBuffer.toString());
                } else {
                    result = parserAction(stringBuffer.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(where.equals("selectAll")){
            return addressBooks;
        }else if(where.equals("delete")){
            return result;
        }else if(where.equals("update")){
            return result;
        }else if (where.equals("select")){
            return addressBooks;
        }else {
            return result;
        }
    }

    private String parserAction(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
            Log.v("Message", returnValue);
            // {"result" : "ok"} ????????? ???????????? ??????????????? ??????
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }

    private void parserSelect(String str){
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("addressBooks_info"));
            addressBooks.clear();

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int acode = jsonObject1.getInt("acode");
                String name = jsonObject1.getString("name");
                int phone = jsonObject1.getInt("phone");
                String email = jsonObject1.getString("email");
                String filePath = jsonObject1.getString("filePath");

                AddressBook addressBook = new AddressBook(acode, name, phone, email, filePath);
                addressBooks.add(addressBook);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserSelect2(String str){
        try {
            Log.v("Message", "parserSelect2");
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("acode_info"));
            addressBooks.clear();

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int acode = jsonObject1.getInt("acode");

                AddressBook addressBook = new AddressBook(acode);
                addressBooks.add(addressBook);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
