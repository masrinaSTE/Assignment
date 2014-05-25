package individual.kpi.project.fragment;


import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import individual.kpi.project.R;

/**
 * Created by masrina on 3/8/14.
 */
public class MyFavouriteFragment extends Fragment {
    public MyFavouriteFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_myfav, container, false);
        return rootView;
    }
}
