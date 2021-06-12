package com.trinhtien2212.findhomerental.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadJSONObject{
    private static volatile ReadJSONObject instance;
    public ReadJSONObject(){};
    public static  synchronized ReadJSONObject getInstance(){
        if(instance == null){
            instance = new ReadJSONObject();
        }
        return instance;
    }
    public String readJson(String link){
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(link);
            InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line = "";
            while((line = bufferedReader.readLine()) !=null){
                json.append(line);
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }



}
