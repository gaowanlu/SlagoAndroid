package site.linkway.slago.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import site.example.slago.MainActivity;
import site.example.slago.R;
import site.example.slago.http.AccountSecurity.Http_changePwd;
import site.example.slago.http.AccountSecurity.Http_sendVerificationCode;
import site.example.slago.more.MorePageActivity;
import site.example.slago.utils.CountDownTimerUtils;


import java.util.Hashtable;

import es.dmoral.toasty.Toasty;

import static java.sql.Types.NULL;

public class ChangePasswordActivity extends BaseActivity {

    private String userEmail;
    private View back;
    private TextView titlebar_title;
    private TextView changUserEmail;
    private EditText newPassword;
    private EditText againNewPassword;
    private EditText verifyEmailCode;
    private TextView getNum;
    private Button emailVerifyButton;
    boolean result;
    String info;
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
                        Toasty.info(ChangePasswordActivity.this, "修改成功！", Toasty.LENGTH_SHORT).show();
                        Intent intent= new Intent(ChangePasswordActivity.this, LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivity(intent);
                        break;
                    case 2:
                        Toasty.error(ChangePasswordActivity.this,"修改失败！",Toast.LENGTH_SHORT).show();
                        break;
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
        changUserEmail = (TextView) findViewById(R.id.chang_user_email);
        newPassword = (EditText) findViewById(R.id.new_password);
        againNewPassword = (EditText) findViewById(R.id.again_new_password);
        getNum = (TextView) findViewById(R.id.change_send_num);
        verifyEmailCode = (EditText) findViewById(R.id.chang_verify_email_code);
        emailVerifyButton = (Button) findViewById(R.id.email_verify_button);
    }

    private void bindActionForElement(){
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");
        //设置标题栏文字
        titlebar_title.setText("找回密码");
        //设置上一步输入的电子邮箱
        changUserEmail.setText(userEmail);
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
            Intent data = new Intent(ChangePasswordActivity.this, CaptchaActivity.class);
            startActivityForResult(data, NULL);

        });

        emailVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InputCheck(newPassword.getText().toString(), againNewPassword.getText().toString(), verifyEmailCode.getText().toString())){
                    //Toasty.info(ChangePasswordActivity.this, "修改成功！", Toasty.LENGTH_SHORT).show();
                    changePassword();
                }
            }
        });
    }

    //检查输入
    private boolean InputCheck(String pwd1, String pwd2, String code){
        if(pwd1==null || pwd2==null){
            Toasty.error(ChangePasswordActivity.this, "密码不能为空！", Toasty.LENGTH_SHORT).show();
            return false;
        }
        if (pwd1.equals("") || pwd2.equals("")) {
            Toasty.error(ChangePasswordActivity.this,"密码不能为空！",Toasty.LENGTH_SHORT).show();
            return false;
        }
        if(!pwd1.equals(pwd2)) {
            Toasty.error(ChangePasswordActivity.this, "密码不相同！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(code==null){
            Toasty.error(ChangePasswordActivity.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (code.equals("")) {
            Toasty.error(ChangePasswordActivity.this,"验证码不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                        Hashtable<String,Object> sendVerificationCode = Http_sendVerificationCode.push(userEmail);
                        boolean result2=(Boolean) sendVerificationCode.get("result");//是否发送成功
                        System.out.println(result2+" ");
                    }
                }.start();
                break;
            default:
                break;
        }
    }

    private void changePassword(){
        String newPwd = newPassword.getText().toString();
        String againPwd = againNewPassword.getText().toString();
        new Thread(){
            @Override
            public void run() {
                Hashtable<String,Object> changePwd= Http_changePwd.push(userEmail, newPwd, verifyEmailCode.getText().toString());
                result=(Boolean) changePwd.get("result");
                info=(String) changePwd.get("info");//获得失败情况
                System.out.println(result+" "+info);

                Message message=new Message();
                if(result){
                    //Toast提示:修改成功
                    message.what=1;
                }
                else{
                    //Toast提示:修改失败
                    message.what=2;
                }
                handler.sendMessage(message);
            }
        }.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initElement();
        bindActionForElement();
        initHandler();
    }
}