package app.rsprmobile.registro;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeringatanLogin extends Fragment {
    Button btnIntentLogin;

    public PeringatanLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewPeringatan = inflater.inflate(R.layout.fragment_peringatan_login, container, false);

        btnIntentLogin = (Button) viewPeringatan.findViewById(R.id.btnIntentLogin);

        btnIntentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(getActivity(), Login.class);
                startActivity(intentLogin);
            }
        });

    return viewPeringatan;
    }

    public interface OnFragmentInteractionListener {
    }

}
