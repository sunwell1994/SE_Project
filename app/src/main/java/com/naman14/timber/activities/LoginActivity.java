package com.naman14.timber.activities;

import android.app.Activity;
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
import com.naman14.timber.utils.NavigationUtils;

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



        //rem_pwd = (CheckBox)findViewById(R.id.rem_checkbox);

        Button btn_login = (Button)findViewById(R.id.signin_button);
        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //username_edit = (EditText)findViewById(R.id.username_edit);
                //password_edit = (EditText)findViewById(R.id.password_edit);

               String username = ((EditText)findViewById(R.id.username_edit)).getText().toString();
               String password = ((EditText)findViewById(R.id.password_edit)).getText().toString();
                Log.e("username", username);
                Log.e("password",password);
                if (doLogin(username,password))
                {
                    Log.e("djdwed2", "CORRECT!");
                    NavigationUtils.navigateToMainActivity(LoginActivity.this);

                }else{
                    Toast.makeText(getApplication(), R.string.passwd_error, Toast.LENGTH_SHORT).show();
                }

            }
        });

       Button btn_regist = (Button)findViewById(R.id.btn_login_regist);
        btn_regist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateToRegisterActivity(LoginActivity.this);
            }
        });

        //btn_forget_pwd = (Button)findViewById(R.id.btn_forget_pwd);
        //btn_forget_pwd.setOnClickListener(this);

      //  initView();


    }

   /* private void initView(){
        rem_pwd = (CheckBox)findViewById(R.id.rem_checkbox);
        btn_login = (Button)findViewById(R.id.signin_button);
        btn_login.setOnClickListener(this);

        btn_regist = (Button)findViewById(R.id.btn_login_regist);
        btn_regist.setOnClickListener(this);

        btn_forget_pwd = (Button)findViewById(R.id.btn_forget_pwd);
        btn_forget_pwd.setOnClickListener(this);

        username_edit = (EditText)findViewById(R.id.username_edit);
        password_edit = (EditText)findViewById(R.id.password_edit);

        //initLogin();
    }*/

  /* public void initLogin() {

        String[] usernames = dbHelper.queryUserOrMapInfo("user_table");

        if (usernames.length > 0) {
            final String tempName = usernames[usernames.length - 1];
            username_edit.setText(tempName);
            username_edit.setSelection(tempName.length());
            final String tempPwd = dbHelper.queryPasswordByName(tempName);
            password_edit.setText(tempPwd);
            int checkFlag = dbHelper.queryIsSavedByName(tempName);

            if (checkFlag == 0) {
                rem_pwd.setChecked(false);
            } else if (checkFlag == 1) {

                rem_pwd.setChecked(true);
                doLogin(tempName, tempPwd);
            }
        }

    }*/
   /* @Override
    public void onClick(View v) {
        final Intent intent_register;
        switch (v.getId()) {
            case R.id.signin_button:

			//username = username_edit.getText().toString().trim();
			//password = password_edit.getText().toString().trim();
			//btn_login.setEnabled(false);
            //doLogin(username, password);
                final Intent intent_entry;
                intent_entry = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent_entry);
                finish();
                break;

            case R.id.btn_login_regist:
                //intent_register = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent_register);
                finish();
                break;

            default:
                break;
        }
    }*/



    public boolean doLogin(String username, String password){
        boolean flag=false;
        Log.e("okkkkkkkk","dedeod");
        for(int i =0 ; i< Data.getLength();i++)
        {
            if (username.equals(Data.getUsername(i)))
            {
                if (password.equals(Data.getPsswd(i))){
                    flag =true;
                    break;
                }
            }
        }

        return  flag;
    }

    /*@Override
    public void onSuccess(String tag, String response) {
        if (doLogin(username,password)) {
            //String sessionID = HttpUtil.parseJson(response, BLConstants.KEY_SESSION);
            //App.setCookie(BLConstants.KEY_SESSION + "=" + sessionID);
            final Intent intent_entry;
            //if (!username.equals("") && !password.equals("")) {
              //  dbHelper.insertOrUpdate(username, password, 1);
            //}
            intent_entry = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent_entry);
            finish();
        }
    }*/

    //@Override

   /* public void refreshUI() {
        btn_login.setEnabled(true);
    }*/




}
