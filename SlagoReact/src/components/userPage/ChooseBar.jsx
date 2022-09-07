import React, { Component } from "react";
import "../../css/ChooseBar.css";
class ChooseBar extends Component {
  state = {
    listArray: this.props.obj.list,
    current: this.props.obj.current,
  };
  call = (e) => {
    let old = this.state.current + "";
    this.setState({ current: e });
    let rp = { old: old, current: e };
    this.props.call(rp);
  };
  render() {
    return (
      <div className="ChooseBar-container">
        {this.state.listArray.map((v) => {
          let s1 = "ChooseBar-block";
          if (v[0] === this.state.current) {
            s1 += " ChooseBar-choosedStyle";
          }
          let imgurl = "/img/" + v[1];
          return (
            <div
              onClick={() => {
                this.call(v[0]);
              }}
              key={v}
              className={s1}
            >
              <img
                style={{ width: "20px", height: "20px", marginRight: "10px" }}
                src={imgurl}
                alt={v[1]}
              />
              {v[0]}
            </div>
          );
        })}
      </div>
    );
  }
}

export default ChooseBar;
