package SlagoService.Collection;

import java.util.List;

public interface DaoAPI {
    String collectionPost(String myid,String postid);
    String uncollectionPost(String myid,String postid);
    List<String> getCollectionLists(String myid, String page, String pagesize);
}
