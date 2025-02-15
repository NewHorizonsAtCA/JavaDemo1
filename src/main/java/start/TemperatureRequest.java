/*
John Bunk
First test client project
*/
package start;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TemperatureRequest {
    public String Request() {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String result;
        try {
            HttpGet httpget = new HttpGet("https://api.weather.gov/stations/KCMA/observations/latest?require_qc=false");

            //System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            
            String responseBody = httpclient.execute(httpget, responseHandler);
            result = responseBody;
        } catch (IOException io) {
            result = "Error";
        }
        
        try {
            httpclient.close();
        } catch (IOException io) {
            result = "Error";
        }       
        
        return result;
    } 
    
    public String ParseTemperature(String response) {
        String result = "Error";
        try {
            // Parse the JSON string
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response);

            // Access JSON data
            JSONObject props = (JSONObject)jsonObject.get("properties");
            JSONObject tempObj = (JSONObject)props.get("temperature");
            Double tempValue = (Double) tempObj.get("value");
            result = Double.toString(tempValue);
        } catch (ParseException e) {
            e.printStackTrace();
        } 
        
        return result;
    }
}
