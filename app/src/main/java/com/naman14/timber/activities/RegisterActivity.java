package com.naman14.timber.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.naman14.timber.Data;
import com.naman14.timber.R;
import com.naman14.timber.bean.MyUser;
import com.naman14.timber.utils.ClassPathResource;
import com.naman14.timber.utils.NavigationUtils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by admin on 2015/12/17.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

    private TextView tv_top_title;
    private Button btn_title_left, btn_title_right, btn_reg, btn_get_checkcode;
    private EditText name_edit, et_accountNo, email_edit, pwd_edit, pwdConfirm_edit, checkCode_edit, phoneNum_edit;
    private RadioGroup rgroup;
    private RadioButton genderRb;
    private GestureDetector mGestureDetector;

    private String nameVal;
    private String accountVal;
    private String emailVal;
    private String pwdVal;
    private String pwdConfirmVal;
    private String phoneVal;
    private String checkCodeVal;
    private Boolean genderVal;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        initView();
    }

    private void initView(){
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        tv_top_title.setText(R.string.register_title);

        btn_title_right = (Button) findViewById(R.id.btn_title_right);
        btn_title_right.setVisibility(View.GONE);

        btn_title_left = (Button) findViewById(R.id.btn_title_left);
        btn_title_left.setOnClickListener(this);

        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(this);

        btn_get_checkcode = (Button) findViewById(R.id.btn_getCode);
        btn_get_checkcode.setOnClickListener(this);


        name_edit = (EditText)findViewById(R.id.name_edit);

        et_accountNo = (EditText)findViewById(R.id.accountNo_edit);

        checkCode_edit = (EditText) findViewById(R.id.checkCode_edit);

        email_edit = (EditText)findViewById(R.id.email_edit);

        phoneNum_edit = (EditText) findViewById(R.id.mobile_edit);

        pwd_edit = (EditText)findViewById(R.id.regPwd_edit);

        rgroup = (RadioGroup)findViewById(R.id.radioGroup);
        pwdConfirm_edit = (EditText)findViewById(R.id.pwdConfirm_edit);


        pwd_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {



            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // TODO Auto-generated method stub
                pwdVal = pwd_edit.getText().toString();
                if (!hasFocus) {
                    if (pwdVal.length() <6 ||pwdVal.length() >15)
                        Toast.makeText(getApplication(), R.string.passwd_length, Toast.LENGTH_SHORT).show();
                }
            }
        });

        pwdConfirm_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // TODO Auto-generated method stub
                pwdConfirmVal = pwdConfirm_edit.getText().toString();
                if (!hasFocus) {
                    if (!pwdConfirmVal.equals(pwdVal))
                        Toast.makeText(getApplication(), R.string.passwd_identical, Toast.LENGTH_SHORT).show();
                }
            }
        });
        phoneNum_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // TODO Auto-generated method stub
                phoneVal = phoneNum_edit.getText().toString();
                if (!hasFocus) {
                    if (phoneVal.length() !=11)
                        Toast.makeText(getApplication(), R.string.phone_length, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){

            case R.id.btn_title_left:
                RegisterActivity.this.finish();
                break;
            case R.id.btn_getCode:
                phoneVal = phoneNum_edit.getText().toString().trim();
                checkCodeVal=checkCode_edit.getText().toString().trim();
                BmobSMS.requestSMSCode(this, phoneVal, "ShareWay", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            Log.i("checkCodeSuccess:", "code" + integer);
                            Toast.makeText(getApplication(),
                                    "验证码已经发送到"+phoneVal, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;



            case R.id.btn_reg:
                nameVal = name_edit.getText().toString().trim();
                accountVal = et_accountNo.getText().toString().trim();
                emailVal = email_edit.getText().toString().trim();
                phoneVal = phoneNum_edit.getText().toString().trim();
                pwdVal = pwd_edit.getText().toString().trim();
                pwdConfirmVal = pwdConfirm_edit.getText().toString().trim();
                checkCodeVal=checkCode_edit.getText().toString().trim();
                //genderVal = genderRb.getText().toString();

                if(((RadioButton)rgroup.getChildAt(0)).isChecked() == true)
                    genderVal = Boolean.TRUE;
                else
                    genderVal = Boolean.FALSE;


                MyUser myUser =new MyUser();
                myUser.setUsername(accountVal);
                myUser.setPassword(pwdVal);
                myUser.setNick(nameVal);
                myUser.setSex(genderVal);
                myUser.setMobilePhoneNumber(phoneVal);
                myUser.setEmail(emailVal);
                BmobSMS.verifySmsCode(this, phoneVal, checkCodeVal, new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplication(),
                                    "验证通过", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(),
                                    "验证失败" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myUser.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplication(),
                                R.string.register_success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getApplication(),
                               "注册失败"+s, Toast.LENGTH_SHORT).show();
                    }
                });


                NavigationUtils.navigateToLoginActivity(RegisterActivity.this);


                break;
        }
    }


}
