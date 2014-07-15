package individual.kpi.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import individual.kpi.project.R;

import java.util.ArrayList;

/**
 * Created by masrina on 7/10/14.
 */
public class TitleNavigationAdapter extends BaseAdapter {
    private TextView title;
    private ArrayList<individual.kpi.project.model.SpinnerNavItem> spinnerNavItems;
    private Context context;
    public TitleNavigationAdapter(Context context, ArrayList<individual.kpi.project.model.SpinnerNavItem> spinnerNavItems){
        this.spinnerNavItems = spinnerNavItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return spinnerNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_list_title, null);
        }
        title = (TextView) convertView.findViewById(R.id.spinner_title);
        title.setText(spinnerNavItems.get(position).getTitle());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_list_title, null);
        }
        title = (TextView) convertView.findViewById(R.id.spinner_title);
        title.setText(spinnerNavItems.get(position).getTitle());
        return convertView;
    }
}
