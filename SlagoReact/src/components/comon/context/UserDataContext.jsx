import React from 'react';
/*用户信息上下文*/
const UserDataContext=React.createContext();//React.createContext()的参数为上下文默认值
UserDataContext.displayName="UserDataContext";
export default UserDataContext;