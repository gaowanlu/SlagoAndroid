import React, { Component, Suspense } from "react";
import { Route } from "react-router-dom";
import  styled from 'styled-components';

import  HeadNavBar from "./components/comon/HeadNavBar";
import  Footer from "./components/comon/Footer";
import LazyLoading from './components/comon/component/LazyLoading';

const  LoginPage = React.lazy(()=>import("./components/login/LoginPage")); 
const  UserDataPage = React.lazy(()=>import("./components/userData/UserDataPage"));
const  IndexPage = React.lazy(()=>import("./components/index/IndexPage"));
const  UserPage = React.lazy(()=>import("./components/userPage/UserPage"));
const  NewPostPage = React.lazy(()=>import( "./components/newPost/NewPostPage"));
const  AccountSecurityPage = React.lazy(()=>import("./components/accountSecurity/AccountSecurityPage"));
const  User = React.lazy(()=>import("./components/userPage/User"));
const  FindPage = React.lazy(()=>import("./components/find/FindPage"));

class App extends Component {
  render() {
    return (
      <React.Fragment>
        <Suspense fallback={<LazyLoading src={"/img/loading.gif"}/>}>
          <header>
            <Route
              path="/"
              render={(props) => <HeadNavBar {...props}> </HeadNavBar>}
            />
          </header>
          <MainContainer minHeight={window.screen.availHeight + "px"}>
              <Route
                Route
                path="/Login"
                exact
                render={(props) => <LoginPage {...props} />}
              ></Route>
              <Route
                path="/UserData"
                exact
                render={(props) => <UserDataPage {...props} />}
              ></Route>
              <Route
                Route
                path="/UserPage"
                exact
                render={(props) => <UserPage {...props} />}
              ></Route>
              <Route
                path="/NewPostPage"
                exact
                render={(props) => <NewPostPage {...props} />}
              ></Route>
              <Route
                Route
                path="/AccountSecurityPage"
                exact
                render={(props) => <AccountSecurityPage {...props} />}
              ></Route>
              <Route
                Route
                path="/User/:id"
                exact
                render={(props) => <User {...props} />}
              ></Route>
              <Route
                Route
                path="/Find/:child1"
                exact
                render={(props) => <FindPage {...props} />}
              ></Route>
              <Route
                path="/"
                exact
                render={(props) => <IndexPage {...props} />}
              ></Route>
          </MainContainer>
          <footer>
            <Route
              Route
              path="/"
              render={(props) => <Footer {...props} />}
            ></Route>
          </footer>
        </Suspense>
      </React.Fragment>
    );
  }
}


const MainContainer=styled.main`
    min-height:${(props)=>props.minHeight};
    width:100%;
`;

export {App};
