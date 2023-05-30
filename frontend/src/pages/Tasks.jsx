import React from 'react';

import Task from '../components/task/Task';

import http from "../http-common";

import { Link } from 'react-router-dom';

class Tasks extends React.Component {
  state = {
    tasks: [],
  };

  // обработчик, который срабатывает до вызова render()
  componentDidMount() {
    http
        .get("/tasks")
        .then(response => {
          console.log(response.data)
          this.setState({ tasks: response.data })
        })
        .catch(e => {
          console.log(e);
        });
  }

  render() {
    const { tasks } = this.state;

    return (
        <div className="col-sm-4 mt-2">
          <div className="list-group">
            {
              tasks.map((task, i) => {
                return (
                    <>
                      <Task key={i} i={i+1} title={task.title} description={task.description}/>
                    </>
                )
              })
            }
          </div>
        </div>
    )
  }
}

export default Tasks;