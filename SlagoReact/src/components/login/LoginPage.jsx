import React, { Component } from "react";
import "../../css/LoginPage.css";
import LoginForm from "./LoginForm";
class LoginPage extends Component {
  state = {};
  toWhere = (choose, where) => {
    if ("push" === choose) {
      this.props.history.push(where);
    } else if ("replace" === choose) {
      this.props.history.replace(where);
    }
  };
  render() {
    return (
      <React.Fragment>
        <div className="slago-width-100 slago-flex-jc-ac">
          <div className="LoginPage-container">
            <div className="LoginPage-left">
              <img
                src="./img/pexels-rachel-claire-4846299.jpg"
                className="LoginPage-leftimg animate__animated animate__zoomIn"
                alt=""
              ></img>
            </div>
            <div className="LoginPage-right">
              <div className="LoginPage-right-title animate__animated animate__zoomIn">
                {" "}
                美好正在发生 你好 世界!
              </div>
              <div className="LoginPage-form-container animate__animated animate__zoomIn">
                <LoginForm toWhere={this.toWhere}></LoginForm>
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default LoginPage;
