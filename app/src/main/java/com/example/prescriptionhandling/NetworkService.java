package com.example.prescriptionhandling;

import android.webkit.CookieManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class NetworkService {


    public String getRequest(String url) {

        try {
            HttpsURLConnection urlConnection = getConnection(url, "GET");
            if (urlConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                return "error code " + urlConnection.getResponseCode();
            }
            return readStream(urlConnection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    private HttpsURLConnection getConnection(String url, String method) throws MalformedURLException {
        URL request_url = new URL(url);
        HttpsURLConnection urlConnection = null;
        try {
//            if (!isHttps(url)) {
//                throw new ConnectException("you have to use SSL certifacated url!");
//            }
            urlConnection = (HttpsURLConnection) request_url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setReadTimeout(60 * 1000);
            urlConnection.setConnectTimeout(60 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("X-Environment", "android");
            urlConnection.setRequestProperty("User-Agent", "EventCart");
            urlConnection.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            urlConnection.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }

    private boolean isHttps(String url) {
        return url.startsWith("https://");
    }

    private static String readStream(InputStream is) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("US-ASCII")));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            total.append(line);
        }
        if (reader != null) {
            reader.close();
        }
        return total.toString();
    }

}
