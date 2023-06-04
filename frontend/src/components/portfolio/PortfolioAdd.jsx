import {useState} from "react";

import {Box, Button, TextField, Typography} from "@mui/material";
import http from "../../http-common";

function PortfolioAdd(props) {
    const [portfolioName, setPortfolioName] = useState("");

    function addPortfolio() {
        http
            .post("/portfolio/add", {
                name: portfolioName,
                about: "",
                user_id: props.user_id
            })
            .then(() => {
                props.addHandler();
                setPortfolioName("");
            })
            .catch(e => {
                console.log(e);
            });
    }

    return <Box>
        <Typography variant="h5" component="h2">
            Создать новый портфель
        </Typography>
        <TextField
            required
            variant="outlined"
            label="Название портфеля"
            value={portfolioName}
            onChange={(e) => setPortfolioName(e.target.value)}
        />
        <Box>
            <Button variant={"outlined"} color={"success"} onClick={addPortfolio}>Добавить</Button>
        </Box>
    </Box>
}

export default PortfolioAdd;