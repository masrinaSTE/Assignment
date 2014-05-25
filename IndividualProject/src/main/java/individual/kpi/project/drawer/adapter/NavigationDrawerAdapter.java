package individual.kpi.project.drawer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import individual.kpi.project.R;
import individual.kpi.project.drawer.model.NavigationDrawerItem;

/**
 * Created by masrina on 3/8/14.
 */
public class NavigationDrawerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NavigationDrawerItem> navigationDrawerItems;

    public NavigationDrawerAdapter(Context context, ArrayList<NavigationDrawerItem> navigationDrawerItems){
        this.context = context;
        this.navigationDrawerItems = navigationDrawerItems;
    }

    @Override
    public int getCount(){
        return navigationDrawerItems.size();
    }

    @Override
    public Object getItem(int position){
        return navigationDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        imgIcon.setImageResource(navigationDrawerItems.get(position).getIcon());
        txtTitle.setText(navigationDrawerItems.get(position).getTitle());

        //display count and check whether it set visible or not
        if(navigationDrawerItems.get(position).getCounterVisibility()){
            txtCount.setText(navigationDrawerItems.get(position).getCount());
        }else{
            //hide the counter view
            txtCount.setVisibility(View.GONE);
        }
        return convertView;
    }
}
