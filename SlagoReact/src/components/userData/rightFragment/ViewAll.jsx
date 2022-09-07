import React, { Component } from "react";
import cookies from "react-cookies";
import axios from "axios";
import getuserid from "../../comon/userid";
import UserDataContext from "../../comon/context/UserDataContext";
class ViewAll extends Component {
  static contextType = UserDataContext;
  state = {
    userid: cookies.load("id"),
    HeadImgUrl:
      "/apis/getUserHeadImg?id=" +
      getuserid() +
      "&tempid=",
    getData: {
      sex: "",
      profile: "",
      name: "",
      achive: "",
    },
  };
  componentDidMount() {
    let getData = { ...this.state.getData };
    const requestList = ["getUserName", "getUserSex", "getUserProfile"];
    const updateList = ["name", "sex", "profile"];
    const responseList = ["name", "result", "result"];
    //先尝试从上下文获取,获取失败再发起网络请求
    getData.achive = `${this.context.getUserData(
      "likeNum"
    )} 获赞 ${this.context.getUserData("aboutNum")} 关注 
${this.context.getUserData("fansNum")} 粉丝`;
    getData.profile = this.context.getUserData("profile");
    getData.sex = this.context.getUserData("sex");
    getData.name = this.context.getUserData("name");
    this.setState({ getData }); //render

    for (let i = 0; i < requestList.length; i++) {
      //判断是否需要请求 有内容则不进行请求
      console.log(getData[updateList[i]]);
      if (getData[updateList[i]] !== "") {
        continue;
      }
      axios
        .get("/apis/" + requestList[i] + "?id=" + this.state.userid)
        .then((response) => {
          getData[updateList[i]] = response.data[responseList[i]];
          this.setState({ getData: getData });
          //更新上下文数据
          this.context.updateUserData(
            updateList[i],
            response.data[responseList[i]]
          );
          console.log("更新上下文", updateList[i]);
        })
        .catch((error) => {});
    }

    //判断是否要请求 当为空时需要请求
    if (getData.achive === "") {
      axios
        .get("/apis/getLikeAboutFans?id=" + this.state.userid)
        .then((response) => {
          const { likeNum, aboutNum, fansNum } = response.data;
          getData.achive = `${likeNum} 获赞 ${aboutNum} 关注 ${fansNum} 粉丝`;
          this.setState({ getData });
          //更新上下文数据
          this.context.updateUserData("likeNum", likeNum);
          this.context.updateUserData("aboutNum", aboutNum);
          this.context.updateUserData("fansNum", fansNum);
        })
        .catch((error) => {});
    }
  }
  render() {
    let tempStyle = {
      padding: "0.25em",
      fontWeight: "500",
      fontSize: "1.1em",
    };
    return (
      <React.Fragment>
        {/* <UserDataContext.Consumer>
                {userDataContext=>{} }这样写可以嵌套多个Context 即消费多个Context*/}
        <div className="ViewAll-container   animate__animated animate__fadeIn">
          <img
            className="ViewAll-headimg"
            src={this.state.HeadImgUrl+this.context.getUserData("headimgVersionCode")}
            alt=""
          ></img>
          <div style={{ ...tempStyle, marginTop: "1em" }}>
            账号: {this.state.userid}
          </div>
          <div style={tempStyle}>性别: {this.state.getData.sex}</div>
          <div style={tempStyle}>昵称: {this.state.getData.name}</div>
          <div style={tempStyle}>简介: {this.state.getData.profile}</div>
          <div style={tempStyle}>个人成就: {this.state.getData.achive}</div>
        </div>
        {/* }
            </UserDataContext.Consumer> */}
      </React.Fragment>
    );
  }
}

export default ViewAll;
