import React from 'react';
import { Navigate } from 'react-router-dom';
import { connect } from "react-redux";

class Profile extends React.Component {

  render() {
    const { user: currentUser } = this.props;

    if (!currentUser) {
      return <Navigate to="/login" />;
    }

    return (
      <div className="container">
        <header>
          <h3>
            Профиль <strong>{currentUser.name}</strong>
          </h3>
        </header>
        <p>
        <strong>Токен JWT: </strong>
            {/* вообще токен нельзя выводить на веб-странице, но для ознакомления он будет отображён на странице профиля пользователя */}
            {currentUser.token}
        </p>
        <p>
            <strong>Id: </strong>
            {currentUser.id}
        </p>
        <p>
            <strong>Логин: </strong>
            {currentUser.username}
        </p>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { user } = state.auth;
  return {
    user
  };
}

export default connect(mapStateToProps)(Profile);