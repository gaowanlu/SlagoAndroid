package SlagoService.Follow;

import java.util.List;

public interface DaoAPI {
    String following(String myid,String other);
    String unFollow(String myid,String other);
    List<String> getFansLists(String myid, String page, String pagesize);
    List<String> getAboutLists(String myid, String page, String pagesize);
}
