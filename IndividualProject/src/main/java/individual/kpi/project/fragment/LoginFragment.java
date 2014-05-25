package individual.kpi.project.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import individual.kpi.project.R;

//import android.support.v4.app.Fragment;

/**
 * Created by masrina on 3/8/14.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    public LoginFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_logout, container, false);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Button signOutButton = (Button) getActivity().findViewById(R.id.plus_sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                signOut();
            }
        });
    }
}
