import React from 'react';

import Task from '../components/task/Task';

import http from "../http-common";

import { Link } from 'react-router-dom';
import {connect} from "react-redux";

import UserService from "../services/user.service";

class UserTasks extends React.Component {
  state = {
    tasks: [],
    displayContent: false
  };
  // обработчик, который срабатывает до вызова render()
  componentDidMount() {
      this.setState({ displayContent: this.props.user ? true : false });

      if (this.props.user){
          http
              .get("/tasks/userId=" + this.props.user.id)
              .then(response => {
                  this.setState({ tasks: response.data })
              })
              .catch(e => {
                  console.log(e);
              });
      }
  }

  render() {
    const { tasks } = this.state;

    return (
        <>
            {this.state.displayContent ? (
                    <div className="col-sm-4 mt-2">
                        <Link to={`/addTask`}>Добавить задачу </Link>
                        <div className="list-group">
                            {
                                tasks.map((task, i) => {
                                    return (
                                        <>
                                            <Link to={`/task/${task.id}`} param1={task.id}>
                                                <Task key={i} i={i + 1} title={task.title} description={task.description}/>
                                            </Link>
                                        </>
                                    )
                                })
                            }
                        </div>
                    </div>
                )
                : "Контент доступен только авторизованным пользователям"
            }
        </>

    )
  }
}
function mapStateToProps(state) {
    const { user } = state.auth;
    return {
        user
    };
}

export default connect(mapStateToProps)(UserTasks);
