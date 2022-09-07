import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from 'react-router-dom';
import {App} from './App.jsx';
import './index.css';
import 'normalize.css';

//视图层
ReactDOM.render(
<BrowserRouter>
    <App/>
</BrowserRouter>,document.getElementById('root'));
