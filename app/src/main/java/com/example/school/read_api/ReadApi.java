package com.example.school.read_api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.school.ui.admin_login.AdminLoginFragment;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ReadApi {

    private RequestQueue queue ;
    private String url ;
    public Context context ;

    public static ReadApi readApi = new ReadApi();
    private ReadApi(){}

    public void requestqueue(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getClasses(ServerCallback callback) {
        final List<ApiClasses> apiClassesList = new ArrayList<>();

        url = ApiConst.GET_CLASSES_URL ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray classes  = response.getJSONArray("classes");
                            for (int i = 0 ; i<classes.length() ; i++){
                                JSONObject  object = classes.getJSONObject(i);
                                ApiClasses apiClasses = new ApiClasses();
                                apiClasses.setId(object.getString("id"));
                                apiClasses.setName(object.getString("name"));
                                apiClassesList.add(apiClasses);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(apiClassesList);
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ttt", "onErrorResponse: "+ error.getLocalizedMessage());
                }
        });
        queue.add(request);
    }

    public void getSubject(String class_id , ServerCallback callback){
        final List<ApiSubjects> apiSubjectsList = new ArrayList<>();

        url = ApiConst.GET_SUBJECT_URL + class_id ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray subjects  = response.getJSONArray("subjects");
                            for (int i = 0 ; i<subjects.length() ; i++){
                                JSONObject  object = subjects.getJSONObject(i);
                                ApiSubjects apiSubjects = new ApiSubjects();
                                apiSubjects.setId(object.getString("id"));
                                apiSubjects.setName(object.getString("name"));
                                apiSubjects.setImage(object.getString("image"));
                                apiSubjects.setClasses_id(object.getString("classes_id"));
                                apiSubjectsList.add(apiSubjects);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(apiSubjectsList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ttt", "onErrorResponse: "+ error.getLocalizedMessage());
                }
        });
        queue.add(request);
    }

    public void getLesssons(String classes_id , String subject_id , ServerCallback callback){
        final List<ApiLessons> apiLessonsList = new ArrayList<>();

        url = ApiConst.GET_LESSONS_URL + "?classes_id=" + classes_id + "&subjects_id=" + subject_id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray lessons  = response.getJSONArray("lessons");
                            for (int i = 0 ; i<lessons.length() ; i++){
                                JSONObject  object = lessons.getJSONObject(i);
                                ApiLessons apiLessons = new ApiLessons();
                                apiLessons.setId(object.getString("id"));
                                apiLessons.setTitle(object.getString("title"));
                                apiLessons.setDetails(object.getString("detail"));
                                apiLessons.setImages(object.getString("image"));
                                apiLessons.setKind_id(object.getString("kind_id"));
                                apiLessonsList.add(apiLessons);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(apiLessonsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    public void getKindClass(String kind_id , ServerCallback callback){
        final String[] kind = {""};

        url = ApiConst.GET_KIND_URL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray kinds  = response.getJSONArray("kinds");
                            for (int i = 0 ; i<kinds.length() ; i++){
                                JSONObject  object = kinds.getJSONObject(i);
                                if ( object.getString("id").equals(kind_id)){
                                    kind[0] = object.getString("type");
                                    callback.onSuccess(kind[0]);
                                    return;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    public void getKinds(ServerCallback callback){
        url = ApiConst.GET_KIND_URL;
        List<ApiKind> kinds = new ArrayList<>();
        final ApiKind[] kind = new ApiKind[1];

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array  = response.getJSONArray("kinds");
                            for (int i = 0 ; i<array.length() ; i++){
                                JSONObject  object = array.getJSONObject(i);
                                kind[0] = new ApiKind();
                                kind[0].setId(object.getString("id"));
                                kind[0].setType(object.getString("type"));
                                kinds.add(kind[0]);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(kinds);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    public void login(String email , String password , ServerCallback callback){
        url = ApiConst.LOGIN_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Log.d("ttt", "onResponse: ");
                    if (object.getBoolean("status")){
                        Gson gson = new Gson();
                        ApiUser user = gson.fromJson(String.valueOf(object.getJSONObject("user")), ApiUser.class);
                        callback.onSuccess(user);
                    }else{
                        Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        queue.add(request);
    }

    public void contactUs(String email , String mobile , String name , String message){

        if (AdminLoginFragment.user == null){
            Toast.makeText(context, "you need to login .", Toast.LENGTH_SHORT).show();
            return;
        }

        url = ApiConst.CONTACT_US;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ttt", "onResponse: " + response.toString());
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("status")){
                        Toast.makeText(context, "تم الإرسال .", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("name" , name);
                params.put("message" , message);
                params.put("type" , "0");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String , String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + AdminLoginFragment.user.getAccess_token());
                return map;
            }
        };
        queue.add(request);
    }

    public void add_Lessons(String title , String image , String details , String classes_id , String subject_id , String kind_id){
        url = ApiConst.POST_LESSONS;

//      WebService.loading(AddNewProductType_2_Activity.this, true);
        AsyncHttpClient client = new AsyncHttpClient();
        String BASE_URL = url;

        RequestParams requestParams = new RequestParams();
        try {
            requestParams.put("image",new File(image));
            requestParams.put("title", title);
            requestParams.put("detail" , details);
            requestParams.put("classes_id" , classes_id);
            requestParams.put("subjects_id" , subject_id);
            requestParams.put("user_id" , AdminLoginFragment.user.getId());
            requestParams.put("kind_id" , kind_id);
        }catch (Exception e){
            Log.d("ttt", "add_Lessons: " + e.getMessage());
        }

        Header_Async(client);

        client.post(BASE_URL, requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("", "onSuccess: ");
                        Log.e("responseBody",responseBody.toString());
                        Toast.makeText(context, "تم اضافة الدرس .", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("ttt", "onFailure: error");
                    }
                }
        );

    }

    public void add_News(String title , String image , String description){
        url = ApiConst.POST_NEWS;

        AsyncHttpClient client = new AsyncHttpClient();
        String BASE_URL = url;

        RequestParams requestParams = new RequestParams();
        try {
            requestParams.put("image",new File(image));
            requestParams.put("title", title);
            requestParams.put("description" , description);
        }catch (Exception e){
            Log.d("ttt", "add_news: " + e.getMessage());
        }

        client.addHeader("Authorization", "Bearer " + AdminLoginFragment.user.getAccess_token());
        client.addHeader("Accept" , "application/json");

        client.post(BASE_URL, requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("", "onSuccess: ");
                        Log.e("responseBody",responseBody.toString());
                        Toast.makeText(context, "تم اضافة الخبر .", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("ttt", "onFailure: error");
                    }
                }
        );

    }

    public void getTeachers(ServerCallback callback) {
        final List<ApiTeacher> apiTeacherList = new ArrayList<>();
        final ApiTeacher[] apiTeacher = new ApiTeacher[1];

        url = ApiConst.GET_TEACHERS ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray teachers  = response.getJSONArray("teachers");
                            for (int i = 0 ; i<teachers.length() ; i++){
                                JSONObject  object = teachers.getJSONObject(i);
                                apiTeacher[0] = new ApiTeacher();
                                Gson gson = new Gson();
                                apiTeacher[0] = gson.fromJson(String.valueOf(object), ApiTeacher.class);
                                apiTeacherList.add(apiTeacher[0]);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(apiTeacherList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    public void getNews(ServerCallback callback) {
        final List<ApiNews> apiNewsList = new ArrayList<>();

        url = ApiConst.GET_NEWS ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray news1  = response.getJSONArray("News");
                            for (int i = 0 ; i<news1.length() ; i++){
                                JSONObject  object = news1.getJSONObject(i);
                                ApiNews apiNews = new ApiNews();
                                Gson gson = new Gson();
                                apiNews = gson.fromJson(String.valueOf(object), ApiNews.class) ;
                                apiNewsList.add(apiNews);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(apiNewsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    public void getLessonsDetails( String id_lesson , ServerCallback callback) {
        url = ApiConst.GET_LESSONS_DETAILS + id_lesson;
//        HashMap<String , String> map = new HashMap<>();
        List<ApiComments> commentsList = new ArrayList<>();
//        final ApiComments[] comment = {new ApiComments()};

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONObject("LessonsDetails").getJSONArray("comments");
                            for (int i = 0 ; i<array.length() ; i++){
//                            map.put(array.getJSONObject(i).getString("comment") , array.getJSONObject(i).getString("user_id"));
                                JSONObject user = array.getJSONObject(i).getJSONObject("user");
                                ApiComments comment = new ApiComments();
                                comment.setComment_body(array.getJSONObject(i).getString("comment"));
                                comment.setId(array.getJSONObject(i).getString("id"));
                                comment.setUser_name(user.getString("name"));
                                comment.setUser_image(user.getString("image_profile"));
                                commentsList.add(comment);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(commentsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    public void signUp(String email , String password , String mobile , String name , ServerCallback callback){
        url = ApiConst.SIGNUP;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("status")){
                        callback.onSuccess(object.getBoolean("status"));
                    }else{
                        Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("mobile", mobile);
                params.put("name", name);
                return params;
            }
        };
        queue.add(request);
    }

    public void add_comment(String id_lesson , String comment , ServerCallback callback){
        url = ApiConst.POST_COMMENTS;

        if (AdminLoginFragment.user == null){
            Toast.makeText(context, "you need to login .", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AdminLoginFragment.user.getType().equals("0")){
            Toast.makeText(context, "سجل بحساب ولي امر لاضافة التعليق", Toast.LENGTH_SHORT).show();
        }

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                    if (object.getBoolean("status")){
                        callback.onSuccess(object.getBoolean("status"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ttt", "onErrorResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("comment", comment);
                params.put("lesson_id", id_lesson);
                params.put("user_id" , AdminLoginFragment.user.getId());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + AdminLoginFragment.user.getAccess_token());
                headers.put("Accept" , "application/json");
                return headers ;
            }
        };
        queue.add(request);
    }

    public static void Header_Async(AsyncHttpClient client) {

//        client.addHeader("Accept-Language", Hawk.get("lang").toString());
        client.addHeader("Authorization", "Bearer " + AdminLoginFragment.user.getAccess_token());
    }

}

class ApiConst {

    public static String GET_CLASSES_URL = "http://test.hexacit.com/api/getClasses";
    public static String GET_SUBJECT_URL = "http://test.hexacit.com/api/getSubjects?class_id=";
    public static String GET_LESSONS_URL = "http://test.hexacit.com/api/getLessons";
    public static String GET_KIND_URL = "http://test.hexacit.com/api/getKinds";
    public static String LOGIN_URL = "http://test.hexacit.com/api/login";
    public static String CONTACT_US = "http://test.hexacit.com/api/contactUs";
    public static String POST_NEWS = "http://test.hexacit.com/api/postNews";
    public static String POST_LESSONS = "http://test.hexacit.com/api/postLessons";
    public static String GET_TEACHERS = "http://test.hexacit.com/api/getTeachers";
    public static String GET_NEWS = "http://test.hexacit.com/api/getNews";
    public static String SIGNUP = "http://test.hexacit.com/api/signUp";
    public static String GET_LESSONS_DETAILS = "http://test.hexacit.com/api/getLessonsDetails?id=";
    public static String POST_COMMENTS = "http://test.hexacit.com/api/postComments";
}
