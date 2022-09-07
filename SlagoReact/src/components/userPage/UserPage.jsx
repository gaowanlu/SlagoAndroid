import React, { Component } from 'react';
import ChooseBar from './ChooseBar';
import TopShow from './TopShow';
import '../../css/UserPage.css';
import Fans from './Fans';
import About from './About';
import Posts from './Posts';
import Collection from './Collection';
import cookies from "react-cookies";
class UserPage extends Component {
  state = {
    id: cookies.load("id"),
    ChooseBar: {
      list: [
        ["帖子", "blog.png"],
        ["关注", "myabout.png"],
        ["粉丝", "myfans.png"],
        ["收藏", "mycollection.png"],
      ],
      current: "关注",
    },
  };
  constructor() {
    super();
    this.fansPage = <Fans />;
    this.aboutPage = <About toWhere={this.toWhere} />;
    this.postsPage = <Posts id={this.state.id}/>;
    this.collectionPage = <Collection />;
  }
  componentDidMount() {}
  toWhere = (choose, where) => {
    if ("push" === choose) {
      this.props.history.push(where);
    } else if ("replace" === choose) {
      this.props.history.replace(where);
    }
  };
  ChooseBarEvent = (e) => {
      console.log(e);
      let { ChooseBar } = this.state;
      ChooseBar.current = e.current;
      this.setState({ ChooseBar });
  };
  bottomContent = () => {
    const { current } = this.state.ChooseBar;
    if (current === "粉丝") {
      return this.fansPage;
    } else if (current === "关注") {
      return this.aboutPage;
    } else if (current === "帖子") {
      return this.postsPage;
    } else if (current === "收藏") {
      return this.collectionPage;
    }
  };
  render() {
    return (
      <React.Fragment>
        <div
          style={{ width: "100%", display: "flex", justifyContent: "center" }}
        >
          {/* 版心 */}
          <div style={{ width: "1283px", backgroundColor: "#ffffff" }}>
            {/* 用户个人信息 */}
            <TopShow id={this.state.id}></TopShow>
            {/* 选择栏 */}
            <ChooseBar
              obj={this.state.ChooseBar}
              call={this.ChooseBarEvent}
            ></ChooseBar>
            {/* 主题内容 */}
            {this.bottomContent()}
          </div>
        </div>
      </React.Fragment>
    );
  }
}
 
export default UserPage;
