package SlagoService.Recommend.About;

import java.util.List;

public interface DaoAPI {
    List<String> query(String userid,String size);
}
