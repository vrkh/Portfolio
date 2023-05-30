import React from 'react';

import http from "../http-common";

import { Navigate } from 'react-router-dom';
import { connect } from "react-redux";

class AddTask extends React.Component {
    constructor() {
        super();

        this.state = {
            title: "",
            description: "",
            submitted : false,
            file: null
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleFileChange = this.handleFileChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value});
    }

    handleFileChange(event) {
        console.log(event.target.files[0])
        this.setState({file: event.target.files[0]});
    };

    handleSubmit(event) {
        event.preventDefault();

        const formData = new FormData();
        formData.append("title", this.state.title);
        formData.append("description", this.state.description);
        formData.append("user_id", this.props.user.id);
        formData.append("file", this.state.file);

        http
            .post("/addTask", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            })
            .then(() => {
                this.setState({submitted: true});
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        if (this.state.submitted) {
            return <Navigate to="/myTasks" />;
        }
        return (
            <div className="col-sm-5">
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group mb-3">
                        <input type="text" name="title" value={this.state.title} placeholder="Заголовок задачи" onChange={this.handleChange} className="form-control"/>
                    </div>
                    <div className="form-group mb-3">
                        <input type="text" name="description" value={this.state.description} placeholder="Описание задачи" onChange={this.handleChange} className="form-control"/>
                    </div>
                    <div className="form-group mb-3">
                        <input className="form-control" type="file" id="file" onChange={this.handleFileChange} required/>
                    </div>
                    <input type="submit" value="Добавить" className="btn btn-success"/>
                </form>
            </div>
        )
    }
}

function mapStateToProps(state) {
    const { user } = state.auth;
    return {
        user
    };
}

export default connect(mapStateToProps)(AddTask);