package individual.kpi.project.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import individual.kpi.project.R;
import individual.kpi.project.ServiceHandler;
import individual.kpi.project.activity.BlogPostContent;

//import android.support.v4.app.Fragment;

/**
 * Created by masrina on 3/8/14.
 */
public class HomeFragment extends android.app.Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>{
//    private FragmentActivity listFragment;
    private ProgressDialog progressDialog;
    private static String url = "http://chinwyejin.com/wp_api/v1/posts?per_page=20&post_type=post";
    private final static String TAG_POST = "posts";
    private final static String TAG_ID = "id";
    private final static String TAG_TYPE = "type";
    private final static String TAG_TITLE = "title";
    private final static String TAG_CONTENT = "content";
    private static final int LOADER_ID = 1;
    private LoaderManager.LoaderCallbacks<Cursor> mCallback;
    private SimpleCursorAdapter mAdapter;

    // posts JSON array
    JSONArray posts = null;
    ListView listView;
    // HashMap for ListView
    ArrayList<HashMap<String, String>> postList;
    Context context;
    ElementsListClickHandler handler;
    public HomeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        context = getActivity();

        View rootView = inflater.inflate(R.layout.item_lists, container, false);
        Bundle element = this.getArguments();
        postList = new ArrayList<HashMap<String, String>>();

        // ListView on ItemClickListener
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting value from selected item
                String post_title = ((TextView) view.findViewById(R.id.post_title)).getText().toString();
                String blog_content =  ((TextView) view.findViewById(R.id.blog_content)).getText().toString();

                // Start activity_content
                Intent i = new Intent(context.getApplicationContext(), BlogPostContent.class);
                i.putExtra(TAG_TITLE, post_title);
                i.putExtra(TAG_CONTENT, blog_content);
                startActivity(i);
            }
        });

        new GetPostTitle().execute();

        return rootView;
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
    {
        handler.onHandleElementClick(position);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class GetPostTitle extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            // Display progress dialog
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params){
            // Creating service handler class instance
            ServiceHandler serviceHandler = new ServiceHandler();

            // Making a request to url and get response
            String jsonStr = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            Log.d("JSON response: ", jsonStr);
            if(jsonStr != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    // Get JSONArray node
                    posts = jsonObject.getJSONArray(TAG_POST);
                    // looping through all posts
                    for(int i=0; i< posts.length(); i++){
                        JSONObject object = posts.getJSONObject(i);
                        String type = object.getString(TAG_TYPE);
                        String id = object.getString(TAG_ID);
                        String title = object.getString(TAG_TITLE);
                        String content = object.getString(TAG_CONTENT);
                        String replaceContent = content.replaceAll("\n", "<br>");
                        // temporary HashMap for single post
                        HashMap<String, String> post = new HashMap<String, String>();
                        Log.i("type", type);
                        Log.i("content", content);
                        // add each value to HashMap key => value
                        post.put(TAG_ID, id);
                        post.put(TAG_TITLE, title);
                        post.put(TAG_CONTENT, replaceContent);

                        // add post to postList
                        if(type.contains("post")) {
                            postList.add(post);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.e("ServiceHandler", "couldn't get any data from url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            // update parsed JSON into ListView
            ListView listView1 = (ListView) getActivity().findViewById(R.id.list);
            ListAdapter listAdapter = new SimpleAdapter(context, postList,
                    R.layout.list_item, new String[]{
                    TAG_TITLE, TAG_CONTENT}, new int[]{R.id.post_title, R.id.blog_content});
            listView1.setAdapter(listAdapter);
        }
    }
    public interface ElementsListClickHandler
    {
        public void onHandleElementClick(int position);
    }

}
