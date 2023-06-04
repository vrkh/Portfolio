import {useEffect, useState} from "react";

import {Box, Button, TextField, Typography} from "@mui/material";
import http from "../../http-common";

function PortfolioEdit(props) {
    const [portfolioName, setPortfolioName] = useState("");
    const [portfolioAbout, setPortfolioAbout] = useState("");

    useEffect(() => {
        setPortfolioName(props.portfolio.name);
        setPortfolioAbout(props.portfolio.about);
    }, [props.portfolio]);

    function updatePortfolio() {
        http
            .post("/portfolio/update/" + props.portfolio.id, {
                name: portfolioName,
                about: portfolioAbout
            })
            .then(() => {
                props.editHandler();
            })
            .catch(e => {
                console.log(e);
            });
    }

    function deletePortfolio() {
        http
            .post("/portfolio/delete/" + props.portfolio.id)
            .then(() => {
                props.editHandler();
            })
            .catch(e => {
                console.log(e);
            });
    }

    return <Box>
        <Typography variant="h5" component="h2">
            {props.portfolio.name}
        </Typography>
        <TextField
            required
            variant="outlined"
            label="Название портфеля"
            value={portfolioName}
            onChange={(e) => setPortfolioName(e.target.value)}
        />
        <TextField
            required
            multiline
            rows="3"
            variant="outlined"
            label="Описание портфеля"
            value={portfolioAbout}
            onChange={(e) => setPortfolioAbout(e.target.value)}
        />
        <Box className={"buttons"}>
            <Button variant={"outlined"} color={"success"} onClick={updatePortfolio}>Изменить</Button>
            <Button variant={"outlined"} color={"error"} onClick={deletePortfolio}>Удалить</Button>
        </Box>
    </Box>
}

export default PortfolioEdit;