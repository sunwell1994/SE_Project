package com.naman14.timber.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.naman14.timber.Data;
import com.naman14.timber.R;
import com.naman14.timber.bean.MyUser;
import com.naman14.timber.utils.NavigationUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin on 2015/12/16.
 */
public class LoginActivity extends Activity /*implements View.OnClickListener*/{
    private static final String DEBUG_TAG = "LoginActivity";
    private Button btn_login;
    private Button btn_regist;
    private Button btn_forget_pwd;
    private EditText username_edit;
   private EditText password_edit;
    private CheckBox rem_pwd;
   private String username = "";
   private String password = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.i("helllllllllllllllllllll", "LoginCreate! ");
//
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        Bmob.initialize(this, "89ede6b8f4ccbc623027a8e08d754797");

        final Context context =this;
        //rem_pwd = (CheckBox)findViewById(R.id.rem_checkbox);

        Button btn_login = (Button)findViewById(R.id.signin_button);
        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

               String username = ((EditText)findViewById(R.id.username_edit)).getText().toString();
               String password = ((EditText)findViewById(R.id.password_edit)).getText().toString();
                Log.e("username", username);
                Log.e("password", password);

                BmobUser bmobUser = new BmobUser();
                bmobUser.setUsername(username);
                bmobUser.setPassword(password);
                bmobUser.login(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        NavigationUtils.navigateToMainActivity(LoginActivity.this);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getApplication(),
                                R.string.passwd_error, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

       Button btn_regist = (Button)findViewById(R.id.btn_login_regist);
        btn_regist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateToRegisterActivity(LoginActivity.this);
            }
        });
    }


}
