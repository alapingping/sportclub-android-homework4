package com.sports.sportclub;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sports.sportclub.DataModel.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private BmobUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(this, "5fad9f2543ffa83e56155a46398d6ede");

        current_user = BmobUser.getCurrentUser();
        if(current_user != null){
            jump2main();
        }
        else{
            //设置下划线
            TextView forget_text = findViewById(R.id.forget_text);
            forget_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            //设置监听
            forget_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LoginActivity.this,"该功能未开放",Toast.LENGTH_LONG).show();
                }
            });

            TextView signup_text = findViewById(R.id.register_text);
            signup_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            signup_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    //登陆按钮的跳转
    public void onClickSignin(View view) {
        EditText userEmail_input = findViewById(R.id.userEmail_input);
        EditText password_input = findViewById(R.id.password_input);

        String userEmail = userEmail_input.getText().toString();
        String password = password_input.getText().toString();


        current_user = new BmobUser();
        current_user.setPassword(password);
        current_user.setUsername(userEmail);
        current_user.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser user, BmobException e) {
                if(e == null){
                    showmsg("登陆成功");
                    jump2main();
                }
                else{
                    showmsg(e.getMessage().toString());
                }
            }
        });

//        current_user.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e != null){
//                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

//        BmobQuery<User> query = new BmobQuery<>();
//        query.getObject()

    }

    public boolean Validation(User user){

        return false;
    }


    public void showmsg(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    public void jump2main(){
        Intent intent = new Intent(this,navigationActivity.class);
        startActivity(intent);
        finish();
    }

}
