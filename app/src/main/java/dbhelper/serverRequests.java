package dbhelper;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;   
import org.json.simple.parser.*;

import dbhelper.guiStuff.SimpleCookieJar;

/**
 * a bunch of commands towards server
 * @author James Dai
 * @version 5/29/24
 */
public class serverRequests {
    
    private String reqs = "http://34.16.12.139:8080";
    private OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(new SimpleCookieJar()).build();

    /**
     * sends login request to server
     * @param username username
     * @param password password
     * @return status of the request
     */
    public Boolean loginUser(String username, String password){
        try{
            RequestBody body = new FormBody.Builder().add("username", username).add("password", password).build();
            Request request = new Request.Builder()
                .url(reqs + "/login")
                //.method("POST", body)
                .post(body)
                .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());
            if(response.code() == 200){
                response.close();
                return true;
            }
            response.close();
            return false;
            
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
    }

    /**
     * sends sign up request to server
     * @param username username
     * @param password password
     * @param email email
     * @return successful or not
     */
    public int RegisterUser(String username, String password, String email){
        try{
            JSONObject obj = new JSONObject();
            obj.put("username", username);
            obj.put("password", password);
            obj.put("email", email);
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(obj.toJSONString(), JSON);
            Request request = new Request.Builder()
                .url(reqs + "/register")
                .post(body)
                .build();
            Response response = client.newCall(request).execute();
            int res = Integer.parseInt(response.body().string());
            response.close();
            return res;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    /**
     * sends changes password request
     * @param newPassword new password
     * @param oldPassword old password
     * @return success or not
     */
    public Boolean ChangePassword(String newPassword, String oldPassword){
        try{
            JSONObject obj = new JSONObject();
            obj.put("newPassword", newPassword);
            obj.put("oldPassword", oldPassword);
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(obj.toJSONString(), JSON);
            Request request = new Request.Builder()
                .url(reqs + "/user/changePass")
                .post(body)
                .build();
            Response response = client.newCall(request).execute();
            Boolean result = response.body().string().equals("true");
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * adds api key request
     * @param key api key
     * @return successful or not
     */
    public Boolean addKey(String key){
        try{
            JSONObject obj = new JSONObject();
            obj.put("key", key);
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(obj.toJSONString(), JSON);
            Request request = new Request.Builder()
                .url(reqs + "/user/addKeys")
                .post(body)
                .build();
            Response response = client.newCall(request).execute();
            Boolean result = response.body().string().equals("true");
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * places bid request
     * @param key auction id of auction
     * @return success or not
     */
    public Boolean placeBid(String key){
        try{
            JSONObject obj = new JSONObject();
            obj.put("bid", key);
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(obj.toJSONString(), JSON);
            Request request = new Request.Builder()
                .url(reqs + "/user/placeBid")
                .post(body)
                .build();
            Response response = client.newCall(request).execute();
            Boolean result = response.body().string().equals("true");
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * gets list of auctions
     * @return list of auctions
     */
    public ArrayList<String> getAuctions(){
        try{
            
            Request request = new Request.Builder()
                .url(reqs + "/user/auctions")
                .get()
                .build();
            Response response = client.newCall(request).execute();
            if(response.code() != 200){
                response.close();
                return null;
            }
            ArrayList<String> result = new ArrayList<String>();
            String str = response.body().string();

            //System.out.println(str + " alkjdsflkajlskdfjlkasfd WHHWHWHWHW");
            JSONParser p = new JSONParser();
            JSONObject obj = (JSONObject) p.parse(str);

            JSONArray keys = (JSONArray)obj.get("auctions");

            for(int i = 0; i < keys.size(); i++){
                result.add((String)keys.get(i));
            }
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * list of public, old, found uuids
     * @return list
     */
    public LinkedList<String> getOldAuctions(){
        try{
            
            Request request = new Request.Builder()
                .url(reqs + "/user/oldAuctions")
                .get()
                .build();
            Response response = client.newCall(request).execute();
            if(response.code() != 200){
                return null;
            }
            LinkedList<String> result = new LinkedList<String>();
            String str = response.body().string();

            //System.out.println(str + " alkjdsflkajlskdfjlkasfd WHHWHWHWHW");
            JSONParser p = new JSONParser();
            JSONObject obj = (JSONObject) p.parse(str);

            JSONArray keys = (JSONArray)obj.get("auctions");

            for(int i = 0; i < keys.size(); i++){
                result.add((String)keys.get(i));
            }
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * gets list of all purchased uuids
     * @return list of uuids
     */ 
    public LinkedList<String> getPurchases(){
        try{
            
            Request request = new Request.Builder()
                .url(reqs + "/user/purchases")
                .get()
                .build();
            Response response = client.newCall(request).execute();
            if(response.code() != 200){
                return null;
            }
            LinkedList<String> result = new LinkedList<String>();
            String str = response.body().string();

            //System.out.println(str + " alkjdsflkajlskdfjlkasfd WHHWHWHWHW");
            JSONParser p = new JSONParser();
            JSONObject obj = (JSONObject) p.parse(str);

            JSONArray keys = (JSONArray)obj.get("purchases");

            for(int i = 0; i < keys.size(); i++){
                result.add((String)keys.get(i));
            }
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * gets token count
     * @return amount of tokens
     */
    public long getTokens(){
        try{
            
            Request request = new Request.Builder()
                .url(reqs + "/user/tokenCount")
                .get()
                .build();
            Response response = client.newCall(request).execute();
            if(response.code() != 200){
                response.close();
                return -1;
            }
            long result;
            String str = response.body().string();

            //System.out.println(str + " alkjdsflkajlskdfjlkasfd WHHWHWHWHW");
            JSONParser p = new JSONParser();
            JSONObject obj = (JSONObject) p.parse(str);
            result = (long)obj.get("tokens");
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * gets list of api keys
     * @return list of api keys
     */
    public ArrayList<String> getKeys(){
        try{
            
            Request request = new Request.Builder()
                .url(reqs + "/user/sendKeys")
                .get()
                .build();
            Response response = client.newCall(request).execute();
            if(response.code() != 200){
                response.close();
                return null;
            }
            ArrayList<String> result = new ArrayList<String>();
            String str = response.body().string();

            //System.out.println(str + " alkjdsflkajlskdfjlkasfd WHHWHWHWHW");
            JSONParser p = new JSONParser();
            JSONObject obj = (JSONObject) p.parse(str);

            JSONArray keys = (JSONArray)obj.get("keys");

            for(int i = 0; i < keys.size(); i++){
                result.add((String)keys.get(i));
            }
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * a list of uuids to process
     * @return list to process
     */
    public ArrayList<String> getList(){
        try{
            
            Request request = new Request.Builder()
                .url(reqs + "/user/recieveList")
                .get()
                .build();
            Response response = client.newCall(request).execute();
            if(response.code() != 200){
                response.close();
                return null;
            }
            ArrayList<String> result = new ArrayList<String>();
            String str = response.body().string();

            //System.out.println(str + " alkjdsflkajlskdfjlkasfd WHHWHWHWHW");
            JSONParser p = new JSONParser();
            JSONObject obj = (JSONObject) p.parse(str);

            JSONArray keys = (JSONArray)obj.get("ids");

            for(int i = 0; i < keys.size(); i++){
                result.add((String)keys.get(i));
            }
            response.close();
            return result;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * sends processed uuids to server
     * @param result list of processed uuids
     * @return successful or not
     */
    public Boolean addList(ArrayList<String> result){
        try{
            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray();
            for(int i = 0; i < result.size(); i++){
                arr.add(result.get(i));
            }
            obj.put("result", arr);
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(obj.toJSONString(), JSON);
            Request request = new Request.Builder()
                .url(reqs + "/user/addList")
                .post(body)
                .build();
            Response response = client.newCall(request).execute();
            //System.out.println("wow" + response.body().string() + " " + response.code());
            boolean res = response.body().string().equals("true");
            response.close();
            return res;
            //System.out.println("wow" + response.body().string() + " " + response.code());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
