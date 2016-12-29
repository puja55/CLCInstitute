package clc.datacubesoftech.clcinstitute.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import clc.datacubesoftech.clcinstitute.R;
import clc.datacubesoftech.clcinstitute.sharedPreferences.LoginID;
import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by User on 14/11/2016.
 */
public class Fragment_Feedback extends Fragment {
    EditText ed_id,ed_name,ed_msg;
    Button btn_submit;
    LoginID loginID;
    Context context;
    TextView name;
    CircleImageView feedback_profile;
    String  result_profile1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        loginID= LoginID.getInstance();
        result_profile1="http://clcresult.in//application//assets//upload//171031.jpg";
        /* result_stuname=loginID.getStdName(getActivity());*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.feedback,container,false);
        name=(TextView)view.findViewById(R.id.std_name);
        ed_id = (EditText)view. findViewById(R.id.feedback_stuid);
        ed_name = (EditText)view. findViewById(R.id.feedback_name);
        ed_msg = (EditText)view. findViewById(R.id.feedback_message);
        btn_submit=(Button)view.findViewById(R.id.feedback_submit);
        feedback_profile=(CircleImageView)view.findViewById(R.id.feedbck_profileimg);
        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String id = ed_id.getText().toString();
                if (!isValidId(id)) {
                    ed_id.setError("Invalid Id");
                }

                final String name = ed_name.getText().toString();
                if (!isValidName(name)) {
                    ed_name.setError("Invalid Name");
                }

                final String msg = ed_msg.getText().toString();
                if (!isValidMsg(msg)) {
                    ed_msg.setError("Invalid Message");
                }

            }

        });

        name.setText("SAWAN KUMAR MEENA");
        Picasso.with(context)
                .load(result_profile1).fit()
                .placeholder(R.drawable.logo_clc).fit() // optional
                .error(R.drawable.logo_clc).fit()
                .into(feedback_profile);

        return view;
    }

    private boolean isValidId(String id) {
        if (id != null && id.length() > 3) {
            return true;
        }
        return false;
    }

    // validating password with retype password
    private boolean isValidName(String name) {
        if (name != null && name.length() > 3) {
            return true;
        }
        return false;
    }
    private boolean isValidMsg(String msg) {
        if (msg != null && msg.length() > 3) {
            return true;
        }
        return false;
    }
}
