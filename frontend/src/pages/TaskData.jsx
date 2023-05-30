import React, { useState, useEffect }  from 'react';

import http from "../http-common";

import { Navigate, useParams } from 'react-router-dom';
import {connect} from "react-redux";

import { Link } from 'react-router-dom';

function TaskData(props) {

    const { id } = useParams();

    const [task, setTask] = useState({
        id: id,
        title: "",
        description: "",
        file_name: "",
        mime_type: ""
    });

    const [filePath, setFilePath] = useState("");

    const [submitted, setSubmitted] = useState(false);

    useEffect(() => {
        function getTask() {
            http
                .get("/taskFile/" + id)
                .then(response => {
                    if (response.data) {
                        const taskData = response.data.task;
                        setTask({
                            ...task,
                            title: taskData.title,
                            description: taskData.description,
                            file_name: taskData.fileName,
                            mime_type: taskData.mimeType
                        });
                        const link = getLink(response.data.file, taskData.mimeType);
                        setFilePath(link);
                    }
                })
                .catch(e => {
                    console.log(e);
                });
        }
        getTask();
    },[]);

    // генерация ссылки из строки base64
    // сгенерированная ссылка действительна, только пока открыта страница
    function getLink(base64, mime_type){
        console.log(mime_type)
        var byteCharacters = atob(base64);
        var byteNumbers = new Array(byteCharacters.length);
        for (var i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        var byteArray = new Uint8Array(byteNumbers);
        var file = new Blob([byteArray], { type:  mime_type });
        var fileURL = URL.createObjectURL(file);
        return fileURL;
    }

    function handleChangeTitle(event) {
        setTask({
            ...task,
            title: event.target.value
        });
    }

    function handleChangeDescription(event) {
        setTask({
            ...task,
            description: event.target.value
        });
    }

    function handleSubmit(event) {
        event.preventDefault();
        var data = {
            title: task.title,
            description: task.description,
            user_id: props.user.id
        };
        http
            .post("/updateTask/" + task.id, data)
            .then(() => {
                setSubmitted(true);
            })
            .catch(e => {
                console.log(e);
            });
    }

    function deleteTask() {
        http
            .post("/deleteTask/" + task.id)
            .then(() => {
                setSubmitted(true);
            })
            .catch(e => {
                console.log(e);
            });
    }

    return (
        !submitted ? (
            <div className="col-sm-5">
                <form onSubmit={handleSubmit}>
                    <div className="form-group mb-3">
                        <input type="text" name="title" value={task.title} placeholder="Заголовок задачи" onChange={handleChangeTitle} className="form-control"/>
                    </div>
                    <div className="form-group mb-3">
                        <input type="text" name="description" value={task.description} placeholder="Описание задачи" onChange={handleChangeDescription} className="form-control"/>
                    </div>
                    <div className="form-group mb-3">
                         {task.mime_type.includes("image") && <img src={filePath} style={{ width: '200px' }} />}
                    </div>

                    <a href={filePath} target="_blank" rel="noopener noreferrer">Открыть файл</a>
                    <div className="row g-2 mt-1">
                        <div className="col-auto">
                            <input type="submit" value="Обновить" className="btn btn-success"/>
                        </div>
                        <div className="col-auto">
                            <button className="btn btn-danger" onClick={deleteTask}>Удалить</button>
                        </div>
                    </div>

                </form>
            </div>
        )
        : <Navigate to="/myTasks" />
    )

}

function mapStateToProps(state) {
    const { user } = state.auth;
    return {
        user
    };
}

export default connect(mapStateToProps)(TaskData);