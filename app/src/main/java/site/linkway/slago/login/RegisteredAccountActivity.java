package site.linkway.slago.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import site.linkway.slago.R;
import site.linkway.slago.activityCollector.BaseActivity;
import site.linkway.slago.http.AccountSecurity.Http_checkUser;
import site.linkway.slago.http.AccountSecurity.Http_registerNewCount;
import site.linkway.slago.http.AccountSecurity.Http_sendVerificationCode;
import site.linkway.slago.utils.CountDownTimerUtils;

import java.util.Hashtable;

import es.dmoral.toasty.Toasty;

import static java.sql.Types.NULL;

public class RegisteredAccountActivity extends BaseActivity {

    private View back;
    private TextView titlebar_title;
    private EditText userEmail;
    private EditText userName;
    private EditText newPassword;
    private EditText againPassword;
    private Button registeredButton;
    private TextView getNum;
    private EditText verifyEmailCode;
    private static final int sendEmailCode = 1;
    //Hander
    private Handler handler;
    //初始化handler
    @SuppressLint("HandlerLeak")
    private void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        Toasty.info(RegisteredAccountActivity.this, "注册成功！", Toasty.LENGTH_SHORT,true).show();
                        Intent intent= new Intent(RegisteredAccountActivity.this, LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivity(intent);
                        break;
                    case 2:
                        Toasty.error(RegisteredAccountActivity.this, "电子邮箱已经存在！", Toasty.LENGTH_SHORT,true).show();
                        break;
                    case 3:
                        Toasty.error(RegisteredAccountActivity.this, "昵称已经存在！", Toasty.LENGTH_SHORT,true).show();
                        break;
                    case 4:
                        Toasty.error(RegisteredAccountActivity.this, "密码不相同！", Toasty.LENGTH_SHORT,true).show();
                        break;
                    case 5:
                        Toasty.error(RegisteredAccountActivity.this, "注册失败！", Toasty.LENGTH_SHORT,true).show();
                    default:
                        break;
                }
            }
        };
    }

    private void initElement() {
        //返回按钮back=(TextView)
        back = findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title = findViewById(R.id.titlebar_title);
        userEmail = (EditText) findViewById(R.id.import_user_email);
        userName = (EditText) findViewById(R.id.import_user_name);
        newPassword = (EditText) findViewById(R.id.import_user_password);
        againPassword = (EditText) findViewById(R.id.again_user_password);
        registeredButton = (Button) findViewById(R.id.registered_button);
        getNum = (TextView) findViewById(R.id.registered_send_num);
        verifyEmailCode = (EditText) findViewById(R.id.verify_email_code);
    }

    private void bindActionForElement(){
        //设置标题栏文字
        titlebar_title.setVisibility(View.INVISIBLE);
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });

        //发送电子邮箱
        getNum.setOnClickListener((View)->{
            Intent intent = new Intent(RegisteredAccountActivity.this, CaptchaActivity.class);
            startActivityForResult(intent, NULL);
        });

        registeredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = newPassword.getText().toString();
                String email = userEmail.getText().toString();
                String code = verifyEmailCode.getText().toString();
                String name = userName.getText().toString();
                Log.e("!!! ", pwd + " " + email + " " + code + " " + name);
                new Thread(){
                    @Override
                    public void run() {
                        if(!Http_checkUser.get("email", email)){
                            if(!Http_checkUser.get("name", name)){
                                //判断两次密码是否一致
                                if(newPassword.getText().toString().equals(againPassword.getText().toString())){
                                    Hashtable<String,Object> registerNewCount= Http_registerNewCount.push(email,pwd,code,name);
                                    boolean result1=(Boolean) registerNewCount.get("result");//是否成功
                                    String info1=(String) registerNewCount.get("info");//错误信息
                                    System.out.println(result1+" "+info1 + " " +
                                            userEmail.getText().toString() + "" +
                                            verifyEmailCode.getText().toString()+  "" +
                                            userName.getText().toString());
                                    Message message=new Message();
                                    if(result1){
                                        //Toast提示:注册成功
                                        message.what=1;
                                    }
                                    else{
                                        //Toast提示:注册失败
                                        message.what=5;
                                    }
                                    handler.sendMessage(message);

                                }
                                else{
                                    //Toast提示:密码不相同
                                    Message message=new Message();
                                    message.what=4;
                                    handler.sendMessage(message);
                                }
                            }
                            else{
                                //Toast提示:昵称已经存在
                                Message message=new Message();
                                message.what=3;
                                handler.sendMessage(message);
                            }
                        }
                        else{
                            //Toast提示:电子邮箱已经存在
                            Message message=new Message();
                            message.what=2;
                            handler.sendMessage(message);
                        }
                    }
                }.start();
            }
        });
    }

    //倒计时器
    private void countDown(){
        //millisInFuture：倒计时的总时长
        //countDownInterval：每次的间隔时间  单位都是毫秒
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getNum, 10000, 1000);
        mCountDownTimerUtils.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case sendEmailCode:
                countDown();
                new Thread(){
                    @Override
                    public void run() {
                        //发验证码
                        Hashtable<String,Object> sendVerificationCode= Http_sendVerificationCode.push(userEmail.getText().toString());
                        boolean result2=(Boolean) sendVerificationCode.get("result");//是否发送成功
                        System.out.println(result2+" ");
                    }
                }.start();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_account);
        initElement();
        bindActionForElement();
        initHandler();
    }
}