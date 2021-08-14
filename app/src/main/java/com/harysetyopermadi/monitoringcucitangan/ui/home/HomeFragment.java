package com.harysetyopermadi.monitoringcucitangan.ui.home;



import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.harysetyopermadi.monitoringcucitangan.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
//ngodingb listnya disini
    TextView stssatua,stssatul,stssatus;
    ImageView stssemuasatu;
   // TextView consatua,consatus,consatul;

    static ArcProgress satuair,satulimbah,satusabun;

    static int satuairValue=0,satusabunValue=0,satulimbahValue=0;

    static String satuairJSON,satulimbahJSON,satusabunJSON;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        stssatua=root.findViewById(R.id.statussatua);
        stssatus=root.findViewById(R.id.statussatus);
        stssatul=root.findViewById(R.id.statussatul);

        satuair=root.findViewById(R.id.airsatu);
        satusabun=root.findViewById(R.id.sabunsatu);
        satulimbah=root.findViewById(R.id.limbahsatu);

        stssemuasatu=root.findViewById(R.id.statussemuasatu);






        String urlsatuair= "https://api.thingspeak.com/channels/1387872/feeds/last.json?api_key=";
        String apikeysatuair="0YVZ0HPP0MFOM43B";
        String urlsatulimbah= "https://api.thingspeak.com/channels/1427716/feeds/last.json?api_key=";
        String apikeysatulimbah="QOZRN7FW8ZCPER2A";
        String urlsatusabun= "https://api.thingspeak.com/channels/1427720/feeds/last.json?api_key=";
        String apikeysatusabun="D4B4MMCDML8QN123";
        final HomeFragment.UriApi uriapi01 =new UriApi();

        uriapi01.setUri(urlsatuair,apikeysatuair,urlsatulimbah,apikeysatulimbah,urlsatusabun,apikeysatusabun);
        Timer timer=new Timer();
        TimerTask tasknew = new TimerTask() {
            @Override
            public void run() {
                LoadJSONsatu task = new LoadJSONsatu();
                task.execute(uriapi01.getUrisatua());
                LoadJSONsatuL tasksatul =new LoadJSONsatuL();
                tasksatul.execute(uriapi01.getUrisatul());
                LoadJSONsatus tasksatus =new LoadJSONsatus();
                tasksatus.execute(uriapi01.getUrisatus());
                

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String consatua =stssatua.getText().toString();
                        String consatus =stssatus.getText().toString();
                        String consatul =stssatul.getText().toString();



                        if (consatua.equals("Normal") && consatus.equals("Normal") && consatul.equals("Normal")){
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_check_circle_24);
                        }if (consatua.equals("loading") && consatus.equals("loading") && consatul.equals("loading")) {
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_autorenew_24);
                        }if (consatua.equals("Kurang") && consatus.equals("Normal") && consatul.equals("Normal")) {
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_error_24);
                        }if (consatua.equals("Normal") && consatus.equals("Kurang") && consatul.equals("Normal")) {
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_error_24);
                        }if (consatua.equals("Normal") && consatus.equals("Normal") && consatul.equals("Penuh")) {
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_error_24);
                        }if (consatua.equals("Kurang") && consatus.equals("Kurang") && consatul.equals("Normal")) {
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_error_24);
                        }if (consatua.equals("Kurang") && consatus.equals("Normal") && consatul.equals("Penuh")) {
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_error_24);
                        }if (consatua.equals("Normal") && consatus.equals("Kurang") && consatul.equals("Penuh")) {
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_error_24);
                        }if (consatua.equals("Kurang") && consatus.equals("Kurang") && consatul.equals("Penuh")) {
                            stssemuasatu.setImageResource(R.drawable.ic_baseline_error_24);
                        }
                    }
                });
            }


        }; timer.scheduleAtFixedRate(tasknew,1*2000,1*2000);
        








        return root;
    }

    public class UriApi {
        private String uria,urlsatua,apikeysatua,urisatul,urlsatul,apikeysatul,urisatus,urlsatus,apikeysatus;
        public void setUri(String urlsatua, String apikeysatua, String urlsatul, String apikeysatul,String urlsatus, String apikeysatus) {
        this.urlsatua=urlsatua;
        this.apikeysatua=apikeysatua;
        this.uria=urlsatua+apikeysatua;

        this.urlsatul=urlsatul;
        this.apikeysatul=apikeysatul;
        this.urisatul=urlsatul+apikeysatul;

        this.urlsatus=urlsatus;
        this.apikeysatus=apikeysatus;
        this.urisatus=urlsatus+apikeysatus;
        }

        protected String getUrisatua(){
            return uria;
        }
        protected String getUrisatul(){
            return urisatul;
        }
        protected String getUrisatus(){
            return urisatus;
        }

    }
    private class LoadJSONsatu extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            return getText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject json = new JSONObject(result);


                satuairJSON = String.format("%s", json.getString("field1"));



            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            int itung,hasil;
            itung=Integer.parseInt(""+satuairJSON+"");
            hasil=((14-itung)*100)/14;

            satuair.setProgress(hasil);
            Log.d("VarX", ""+satuairJSON);

            if (hasil<=20){
                stssatua.setText("Kurang");
                stssatua.setTextColor(Color.parseColor("#ffffff"));
                stssatua.setBackgroundResource(R.drawable.warning);
            }if (hasil>20){
                stssatua.setText("Normal");
                stssatua.setTextColor(Color.parseColor("#ffffff"));
                stssatua.setBackgroundResource(R.drawable.normal);
            }

            try
            {   if(satuairJSON!=null) {
                satuairValue = Integer.parseInt(satuairJSON);
            }
            }
            catch(NumberFormatException nfe){}

        }
    }

    private class LoadJSONsatuL extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            return getText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject json = new JSONObject(result);


                satulimbahJSON = String.format("%s", json.getString("field1"));



            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            int itung,hasil;
            itung=Integer.parseInt(""+satulimbahJSON+"");
            hasil=((14-itung)*100)/14;

            satulimbah.setProgress(hasil);
            Log.d("VarX", ""+satulimbahJSON);
            if (hasil<70){
                stssatul.setText("Normal");
                stssatul.setTextColor(Color.parseColor("#ffffff"));
                stssatul.setBackgroundResource(R.drawable.normal);

            }if (hasil>=70){
                stssatul.setText("Penuh");
                stssatul.setTextColor(Color.parseColor("#ffffff"));
                stssatul.setBackgroundResource(R.drawable.warning);
            }

            try
            {   if(satulimbahJSON!=null) {
                satulimbahValue = Integer.parseInt(satulimbahJSON);
            }
            }
            catch(NumberFormatException nfe){}

        }
    }

    private class LoadJSONsatus extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            return getText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject json = new JSONObject(result);


                satusabunJSON = String.format("%s", json.getString("field1"));



            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            int itung,hasil;
            itung=Integer.parseInt(""+satusabunJSON+"");
            hasil=((14-itung)*100)/14;

            satusabun.setProgress(hasil);
            Log.d("VarX", ""+satusabunJSON);
            if (hasil<=20){
                stssatus.setText("Kurang");
                stssatus.setTextColor(Color.parseColor("#ffffff"));
                stssatus.setBackgroundResource(R.drawable.warning);
            }if (hasil>20){
                stssatus.setText("Normal");
                stssatus.setTextColor(Color.parseColor("#ffffff"));
                stssatus.setBackgroundResource(R.drawable.normal);
            }

            try
            {   if(satusabunJSON!=null) {
                satusabunValue = Integer.parseInt(satusabunJSON);
            }
            }
            catch(NumberFormatException nfe){}

        }
    }


    private static String getText(String strUrl) {
        String strResult = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            strResult = readStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResult;
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}