import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../../css/HeadNavBar.css";
import ErrorBoundary from "./ErrorBoundary";

import cookies from "react-cookies";
import axios from "axios";
class HeadNavBar extends Component {
  state = {
    userid: cookies.load("id"),
  };
  getUserid() {
    let userid = cookies.load("id");
    if (userid === "") {
      userid = undefined;
    }
    return userid;
  }
  componentDidMount() {
    //设置axios拦截器【全局使用】---------
    axios.interceptors.response.use(
      (sucess) => {
        if (sucess.data.status !== 200 && sucess.data.status !== "200") {
          //清空cookies
          cookies.remove("id");
          cookies.remove("SlagoSession");
          this.toWhere("replace", "/Login");
        }
        return sucess;
      },
      (error) => {
        //console.log("axios 错误响应 拦截",error);
        //this.toWhere("replace","/Login");
        return Promise.reject();
      }
    ); //--------------------------------
    //验证身份
    axios.get("/SlagoService_Authentication").then((response) => {});
    //更新cookie userid
    let userid = this.getUserid();
    this.setState({ userid });
    if (!this.state.userid) {
      this.toWhere("replace", "/Login");
    }
  }
  componentDidUpdate() {}
  toWhere = (choose, where) => {
    if ("push" === choose) {
      this.props.history.push(where);
    } else if ("replace" === choose) {
      this.props.history.replace(where);
    }
  };
  render() {
    let HeadImgUrl = "/apis/getUserHeadImg?id=" + this.getUserid();
    return (
      <ErrorBoundary>
        <React.Fragment>
          <div
            className="HeadNavBar-headerContainer"
            style={{ zIndex: "1000" }}
          >
            <div
              onClick={() => {
                this.toWhere("replace", "/");
              }}
              className="HeadNavBar-logodiv"
            >
              Slago
            </div>
            {this.getUserid() && (
              <React.Fragment>
                <div
                  className="HeadNavBar-chooseBlock"
                  onClick={() => {
                    this.toWhere("push", "/Find/default");
                  }}
                >
                  发现
                </div>
                <div
                  className="HeadNavBar-chooseBlock"
                  onClick={() => {
                    this.toWhere("push", "/UserPage");
                  }}
                >
                  我的
                </div>
              </React.Fragment>
            )}

            <div className="HeadNavBar-rightResponseContainer">
              {!this.getUserid() && (
                <React.Fragment>
                  <div
                    onClick={() => {
                      this.toWhere("replace", "/Login");
                    }}
                    className="HeadNavBar-chooseBlock"
                  >
                    登录
                  </div>
                </React.Fragment>
              )}
              {this.getUserid() && (
                <React.Fragment>
                  <img
                    onClick={() => {
                      this.toWhere("push", "/UserData");
                    }}
                    className="HeadNavBar-headimg"
                    src={HeadImgUrl}
                    alt={this.getUserid()}
                  ></img>
                </React.Fragment>
              )}
            </div>
          </div>
          <div className="HeadNavBar-blankspace"></div>
        </React.Fragment>
      </ErrorBoundary>
    );
  }
}

export default HeadNavBar;
