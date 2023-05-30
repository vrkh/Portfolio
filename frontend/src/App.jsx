import React from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';

import Header from './layout/Header'
import Tasks from './pages/Tasks'
import UserTasks from './pages/UserTasks'
import AddTask from './pages/AddTask'
import TaskData from './pages/TaskData'
import Portfolio from './pages/Portfolio'

import Login from "./components/authorization/Login";
import Register from "./components/authorization/Register";
import Profile from "./components/authorization/Profile";
import {connect} from "react-redux";

class App extends React.Component {
    render() {

        return (
            <div>
                <BrowserRouter>
                    <Header/>
                    <Routes>
                        <Route path='/listTasks' element={<Tasks/>}/>
                        <Route path='/myTasks' element={<UserTasks/>}/>
                        <Route path='/addTask' element={<AddTask/>}/>
                        <Route path="/task/:id" element={<TaskData/>}/>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/register" element={<Register/>}/>
                        <Route path="/profile" element={<Profile/>}/>
                        <Route path="/portfolio" element={<Portfolio/>}/>
                    </Routes>
                </BrowserRouter>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const {user} = state.auth;
    return {
        user
    };
}

// передача данных к другим компонентам
export default connect(mapStateToProps)(App);
