package SlagoService.UserData.UserSex;


import javax.servlet.http.HttpServletRequest;

public class UserSexData {
    public String newSex;
    public String reqId;
    public UserSexData(HttpServletRequest req){
        newSex=req.getParameter("newSex");
        reqId=req.getParameter("id");
    }
    public boolean update(String id){
        if(newSex==null){return false;}
        return UserSexDB.updateSex(id,newSex);
    }
}
