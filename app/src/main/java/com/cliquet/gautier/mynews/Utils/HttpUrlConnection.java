package com.cliquet.gautier.mynews.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlConnection {

    public static String startHttpRequest(String urlString) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            //Decalre an URL Connection
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Open IputStream to Connection
            connection.connect();
            InputStream in = connection.getInputStream();

            //Downland and decode the string response
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (MalformedURLException exception) {

        } catch (IOException exception) {

        } catch (Exception e) {

        }
        return stringBuilder.toString();
    }
}
