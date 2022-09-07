package site.linkway.slago.recyclerView.Adapter;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {
    public int headimg;
    public String content;
    public String userid;
    public String postdate;
    public List<String> imgs;
    public int likeNum;
    public int collectionNum;
    public int commentNum;
    public boolean liked;
    public boolean collectioned;
    public String postid;

    @Override
    public String toString() {
        return "Post{" +
                "headimg=" + headimg +
                ", content='" + content + '\'' +
                ", userid='" + userid + '\'' +
                ", postdate='" + postdate + '\'' +
                ", imgs=" + imgs +
                ", likeNum=" + likeNum +
                ", collectionNum=" + collectionNum +
                ", commentNum=" + commentNum +
                ", liked=" + liked +
                ", collectioned=" + collectioned +
                ", postid='" + postid + '\'' +
                '}';
    }
}
