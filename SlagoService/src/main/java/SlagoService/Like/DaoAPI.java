package SlagoService.Like;

import java.util.List;

public interface DaoAPI {
    String likePost(String myid,String postid);
    String unlikePost(String myid,String postid);
    List<String> getLikeLists(String myid, String page, String pagesize);
}
