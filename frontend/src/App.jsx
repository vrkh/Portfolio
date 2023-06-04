import React from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';

import Header from './layout/Header'

import Portfolio from './pages/Portfolio'

import Login from "./components/authorization/Login";
import Register from "./components/authorization/Register";
import Profile from "./components/authorization/Profile";

import {connect} from "react-redux";

function App() {
    return <div>
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/profile" element={<Profile/>}/>

                <Route path="/portfolio" element={<Portfolio/>}/>
            </Routes>
        </BrowserRouter>
    </div>
}

function mapStateToProps(state) {
    const {user} = state.auth;
    return {
        user
    };
}

export default connect(mapStateToProps)(App);
