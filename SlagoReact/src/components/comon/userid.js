import cookies from 'react-cookies';
function getuserid(){
    let userid=cookies.load("id");
    if(userid===""||!userid){
        userid=undefined;
    }
    return userid;
}
export default getuserid;
