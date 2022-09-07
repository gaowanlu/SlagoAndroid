package SlagoService.UserData.Userprofile;

import javax.servlet.http.HttpServletRequest;

public class UserProfileData {
    public String newProfile;
    public String reqId;
    public UserProfileData(HttpServletRequest req){
        newProfile=req.getParameter("newProfile");
        reqId=req.getParameter("id");
    }
    public boolean updateProfile(String id){
        if(id==null||newProfile==null){return false;}
        return UserProfileDB.updateProfile(id,newProfile);
    }
}
