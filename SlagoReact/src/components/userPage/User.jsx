import React, { Component } from 'react';
import { connect } from 'react-redux';
import ChooseBar from "./ChooseBar";
import TopShow from "./TopShow";
import Posts from "./Posts";
import Collection from "./Collection";
class User extends Component {
  state = {
    id: this.props.match.params.id,
    ChooseBar: {
      list: [
        ["帖子", "blog.png"],
        ["收藏", "mycollection.png"],
      ],
      current: "帖子",
    },
    };
    constructor(props) {
        super(props);
        this.postsPage = <Posts id={this.state.id}/>;
        this.collectionPage = <Collection />;
    };
    ChooseBarEvent = (e) => {
        console.log(e);
        let { ChooseBar } = this.state;
        ChooseBar.current = e.current;
        this.setState({ ChooseBar });
   };
  bottomContent = () => {
    const { current } = this.state.ChooseBar;
    if (current === "帖子") {
      return this.postsPage;
    } else if (current === "收藏") {
      return this.collectionPage;
    }
  };
  render() {
    return (
      <React.Fragment>
        <div
          style={{
            width: "100%",
            display: "flex",
            justifyContent: "center",
          }}
        >
          {/* 版心 */}
          <div style={{ width: "1283px", backgroundColor: "#ffffff" }}>
            {/* 用户个人信息 */}
            <TopShow id={this.state.id}></TopShow>
            {/* pager选择栏 */}
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

export default User;