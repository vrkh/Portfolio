import React from 'react';
import {Link} from 'react-router-dom';

import auth from "../actions/auth";
import {connect} from "react-redux";

class Header extends React.Component {

    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);
        this.state = {
            currentUser: undefined
        };
    }

    componentDidMount() {
        const user = this.props.user;
        if (user) {
            this.setState({currentUser: user});
        }
    }

    logOut() {
        this.props.dispatch(auth.logout());
        window.location.reload();
    }

    render() {
        const {user: currentUser} = this.props;
        return (
            <nav className="navbar navbar-project navbar-expand-lg navbar-light" style={{background: '#64bbe3'}}>
                {currentUser && (
                    <div className="ms-3">
                        <Link className="navbar-brand" to="/portfolio">Портфель</Link>
                    </div>
                )}

                {currentUser ? (
                    <div className="ml-auto">
                        <Link className="navbar-brand btn" to="/profile">{currentUser.username}</Link>
                        <button className="navbar-brand btn" onClick={this.logOut}>Выйти</button>
                    </div>
                ) : (
                    <div className="ml-auto">
                        <Link to="/register"
                              className="nav-link navbar-brand btn navbar-brand-button">Регистрация</Link>
                        <Link to="/login" className="nav-link navbar-brand btn navbar-brand-button">Вход в
                            систему</Link>
                    </div>
                )}
            </nav>
        );
    }
}

function mapStateToProps(state) {
    const {user} = state.auth;
    return {
        user
    };
}

export default connect(mapStateToProps)(Header);