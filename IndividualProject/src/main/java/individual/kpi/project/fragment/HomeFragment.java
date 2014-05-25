package individual.kpi.project.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import individual.kpi.project.R;

//import android.support.v4.app.Fragment;

/**
 * Created by masrina on 3/8/14.
 */
public class HomeFragment extends Fragment {
    public HomeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.item_lists, container, false);
        return rootView;
    }

}
