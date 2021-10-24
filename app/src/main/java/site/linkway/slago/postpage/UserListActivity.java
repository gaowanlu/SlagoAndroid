package site.linkway.slago.postpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import site.linkway.slago.R;
import site.linkway.slago.SlagoDB.UserData;
import site.linkway.slago.http.About.Http_aboutList;
import site.linkway.slago.http.About.Http_fanslist;
import site.linkway.slago.http.UserData.Http_getUserName;
import site.linkway.slago.recyclerView.Adapter.LeftHeadimgRightNameAdapter;

public class UserListActivity extends AppCompatActivity {
    private View back;
    private TextView titlebar_title;
    private RecyclerView userslist;
    private LeftHeadimgRightNameAdapter userAdapter;
    private List<UserData> usersData=new ArrayList<>();//适配器每个item的数据
    private SmartRefreshLayout refreshLayout;
    private RefreshLayout refreshlayouttop;//顶部
    private RefreshLayout refreshlayoutbootom;//底部加载更多

    private String MODEL="ABOUT";


    /*动态分页读取用户的帖子信息*/
    private int nowPage=0;
    private static final int PAGESIZE=10;
    Handler HANDLER=new Handler((Message msg) -> {
        switch (msg.what){
            case 1:
                userAdapter.notifyDataSetChanged();
                if(refreshlayouttop!=null)
                    refreshlayouttop.finishRefresh(0);
                if(refreshlayoutbootom!=null)
                    refreshlayoutbootom.finishLoadMore(0);
                break;
            default:;
        }
        return true;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        //读取UI模式:关注列表  粉丝列表
        MODEL=getIntent().getStringExtra("MODEL");
        if(!MODEL.equals("ABOUT")&&!MODEL.equals("FANS")){MODEL="ABOUT";}
        initElement();
        bindEvent();
        initList();
    }
    private void initElement(){
        //返回按钮back=(TextView)
        back=findViewById(R.id.layout_titlebar).findViewById(R.id.titlebar_combar_back);
        //获取标题栏标题
        titlebar_title=findViewById(R.id.titlebar_title);
        userslist=findViewById(R.id.aboutlist_recyclerview);
        refreshLayout=findViewById(R.id.aboutlist_refreshLayout);
    }
    //初始化recylerview
    private void initList() {
        StaggeredGridLayoutManager layoutManager1=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        userslist.setLayoutManager(layoutManager1);
        userAdapter=new LeftHeadimgRightNameAdapter(usersData);
        userslist.setAdapter(userAdapter);
        //初始化列表数据
        refreshLayout.autoRefresh();
    }
    private void bindEvent(){
        //设置标题栏文字
        if(MODEL.equals("ABOUT")) {
            titlebar_title.setText("我的关注");
        }else if(MODEL.equals("FANS")){
            titlebar_title.setText("我的粉丝");
        }
        back.setClickable(true);//设置为textview可点击的
        //绑定标题栏内的返回按钮字体，作为返回上级事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//结束活动
            }
        });

        refreshLayout.setOnRefreshListener((RefreshLayout refreshlayout)-> {
            this.refreshlayouttop=refreshlayout;
            //重新加载数据
            nowPage=0;
            loadData(true);
        });

        //SmartRefreshLayout控件的加载
        refreshLayout.setOnLoadMoreListener((RefreshLayout refreshlayout) ->{
            this.refreshlayoutbootom=refreshlayout;
            //加载数据并处理重复项
            loadData(false);
        });
    }

    private void loadData(boolean clear){
        nowPage++;
        //拉取10个
        new Thread(()->{
            List<String> userids=new ArrayList<>();
            if(MODEL.equals("ABOUT")) {
                userids = Http_aboutList.fetch(nowPage, PAGESIZE);
            }else if(MODEL.equals("FANS")){
                userids = Http_fanslist.fetch(nowPage, PAGESIZE);
            }
            List<String> names=new ArrayList<>();
            for(int i=0;i<userids.size();i++){
                String result= Http_getUserName.fetch(userids.get(i));
                if(null!=result){
                    names.add(result);
                }else{
                    names.add("");
                }
            }
            if(userids.size()==names.size()){
                if(clear==true){
                    usersData.clear();
                }
                //封装为UserData对象
                for(int i=0;i<userids.size();i++){
                    UserData userData=new UserData();
                    userData.setName(names.get(i));
                    userData.setUserid(userids.get(i));
                    usersData.add(userData);
                }
            }
            Message message=new Message();
            message.what=1;
            HANDLER.sendMessage(message);
        }).start();
    }
}