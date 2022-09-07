/* eslint-disable jsx-a11y/alt-text */
import React, { Component } from "react";
import getuserid from "../../comon/userid";
import axios from "axios";
import UserDataContext from "./../../comon/context/UserDataContext";
class HeadImgChange extends Component {
  static contextType = UserDataContext; //个人信息上下文
  state = {
    changState: "未修改",
    currentInputValue: undefined,
    canChange: true,
  };
  getHeadImgURL = () => {
    return (
      `apis/getUserHeadImg?id=${getuserid()}&tempid=` +
      this.context.getUserData("headimgVersionCode")
    );
  };
  componentDidMount() {
    console.log(this.inputImage);
  }
  submitForm = (formData) => {
    axios
      .request({
        url: "/apis/setHeadImg",
        method: "post",
        header: { "Content-Type": "multipart/form-data" },
        data: formData,
      })
      .then((response) => {
        if (response.data.result === "true") {
          this.setState({ changState: "以上传成功", canChange: true });
        }
      });
  };
  inputChang = () => {
    let input = this.inputImage;
    let currentInputValue = undefined;
    let formFile = new FormData();
    //检测图像文件是否选择
    if (
      input.files !== undefined &&
      input.files.length > 0 &&
      input.files &&
      input.files[0]
    ) {
      if (input.files[0].getAsDataURL) {
        currentInputValue = input.files[0].getAsDataURL;
      } else {
        currentInputValue = window.URL.createObjectURL(input.files[0]);
      }
      formFile.append("headImg", input.files[0]); //加入文件对象
      this.submitForm(formFile);
    } else if (input.value) {
      currentInputValue = input.value;
      formFile.append("headImg", input); //加入文件对象
      this.submitForm(formFile);
    }
    if (currentInputValue) {
      this.setState({
        currentInputValue,
        changState: "请勿离开、正在上传",
        canChange: false,
      });
    }
  };
  imgClick = (e) => {
    if (this.state.canChange) {
      this.inputImage.click();
    }
  };
  render() {
    let headimgurl = this.state.currentInputValue || this.getHeadImgURL();
    let container = {
      display: "flex",
      with: "100%",
      flexWrap: "wrap",
      justifyContent: "center",
      padding: "1em",
      height: "fit-content",
    };
    return (
      <React.Fragment>
        <div className="ViewAll-container   animate__animated animate__fadeIn">
          <div style={container}>
            <img
              onClick={this.imgClick}
              src={headimgurl}
              style={{ width: "250px", height: "250px", objectFit: "cover" }}
              alt="none"
            />
            <input
              type="file"
              onChange={this.inputChang}
              ref={(el) => (this.inputImage = el)}
              accept="image/*"
              style={{ display: "none" }}
            />
            <div
              className="UserDataPage-fragment-right-text"
              style={{
                width: "100%",
                marginTop: "1em",
                textAlign: "center",
                padding: "0.5em",
                borderRadius: "0.25em",
              }}
            >
              修改状态:{this.state.changState}
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default HeadImgChange;
