import React, { Component } from "react";
import axios from "axios";
import "../../css/UserRow.css";

/*组件描述:
展示用户列表的item,及每一行
接口
id:用户id
cancelAttention(id):右侧按钮点击事件
rightbuttonText:右侧按钮字体内容
*/
class UserRow extends Component {
  state = {
    id: this.props.id,
    username: "",
    rightButtonClassName:
      "slago-button slago-bgcolor-f8 slago-textcolor-grey",
  };
  componentDidMount() {
    axios
      .get("/apis/getUserName?id=" + this.state.id)
      .then((response) => {
        let { username } = this.state;
        username = response.data.name;
        this.setState({ username });
      })
      .catch((e) => {});
  }
  //右侧按钮动画
  changeButtonClassName = () => {
    let changeTo = "";
    if (
      this.state.rightButtonClassName ===
      "slago-button slago-bgcolor-f8 slago-textcolor-grey"
    ) {
      changeTo +=
        "slago-button slago-bgcolor-pink slago-textcolor-white animate__animated animate__pulse";
    } else {
      changeTo += "slago-button slago-bgcolor-f8 slago-textcolor-grey";
    }
    this.setState({ rightButtonClassName: changeTo });
  };
  render() {
    //头像请求地址
    const headimgURL = "/apis/getUserHeadImg?id=" + this.state.id;
    return (
      <div className="UserRow-container">
        <div
          className="slago-height-100 slago-flex-al-ce slago-grow-1 slago-hover-p"
          onClick={() => this.props.userRowClick(this.state.id)}
        >
          <img
            src={headimgURL}
            className="UserRow-headimg"
            alt={this.state.id}
          ></img>
          <span style={{ marginLeft: "10px" }}>{this.state.username}</span>
        </div>
        {/* 右侧按钮*/}
        {this.props.rightbuttonText && this.props.rightbuttonText !== "" && (
          <button
            className={this.state.rightButtonClassName}
            style={{ marginRight: "0.4rem", fontSize: "0.6rem" }}
            onClick={() => {
              this.props.cancelAttention(this.state.id);
            }}
            onMouseEnter={this.changeButtonClassName}
            onMouseLeave={this.changeButtonClassName}
          >
            {this.props.rightbuttonText}
          </button>
        )}
      </div>
    );
  }
}

export default UserRow;
