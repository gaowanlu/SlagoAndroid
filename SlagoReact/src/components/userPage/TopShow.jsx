import React, { Component } from 'react';
import cookies from 'react-cookies';
import axios from 'axios';
class TopShow extends Component {
    state = {
        userAchive: "",
        username: '',
        userprofile: '',
        fans: '',
        like: '',
        about: '',
    }
    componentDidMount() {
        //获取昵称\简介\粉丝获赞关注
        let { username } = this.state;
        axios.get("/apis/getUserName?id=" + this.props.id).then(response => {
            username = response.data.name;
            this.setState({ username });
        });
        //简介
        axios
            .get("/apis/getUserProfile?id=" + this.props.id)
            .then((response) => {
                this.setState({ userprofile: response.data.result });
            })
            .catch((error) => { });
        //粉丝获赞关注
        axios
            .get("/apis/getLikeAboutFans?id=" + this.props.id)
            .then((response) => {
                const { likeNum, aboutNum, fansNum } = response.data;
                this.setState({ fans: fansNum, like: likeNum, about: aboutNum });
            })
            .catch((error) => { });
    }
    render() {
        return (
            <div style={{ width: "100%", position: "relative" }}>
                <img
                    style={{
                        width: "100%",
                        height: "200px",
                        objectFit: "cover",
                        position: "absolute",
                        zIndex: "1",
                    }}
                    src="/img/pexels-rachel-claire-4846299.jpg"
                    alt=""
                ></img>
                <div
                    style={{
                        width: "100%",
                        backgroundColor: "#00000044",
                        zIndex: "5",
                        position: "absolute",
                        top: "20px",
                        padding: "10px 0px 10px 0px",
                        color: "#fafafa",
                    }}
                >
                    <div
                        style={{
                            display: "flex",
                            justifyContent: "center",
                            width: "100%",
                        }}
                    >
                        <img
                            style={{
                                width: "56px",
                                height: "56px",
                                objectFit: "cover",
                                borderRadius: "28px",
                            }}
                            src={"/apis/getUserHeadImg?id=" + this.props.id}
                            alt=""
                        ></img>
                    </div>
                    <div
                        style={{
                            display: "flex",
                            justifyContent: "center",
                            width: "100%",
                            fontSize: "0.8em",
                            marginTop: "5px",
                            color: "#fafafa",
                        }}
                    >
                        {this.state.username}
                    </div>
                    <div
                        style={{
                            display: "flex",
                            justifyContent: "center",
                            width: "100%",
                            fontSize: "0.7em",
                            marginTop: "5px",
                            color: "#fafafa",
                        }}
                    >
                        简介:{this.state.userprofile}
                    </div>
                    <div
                        style={{
                            display: "flex",
                            justifyContent: "flex-end",
                            width: "100%",
                            fontSize: "0.7em",
                            marginTop: "10px",
                            paddingRight: "20px",
                            color: "#fafafa",
                        }}
                    >
                        {this.state.fans} 粉丝 {this.state.like} 获赞 {this.state.about}{" "}
                        关注
                    </div>
                </div>
            </div>
        );
    }
}

export default TopShow;