import {useEffect, useState} from "react";
import {Box, Button, Grid, Modal, TextField, Typography} from "@mui/material";
import http from "../http-common";
import {connect} from "react-redux";

function Portfolio(props) {

    const [data, setData] = useState([]);

    const [portfolio, setPortfolio] = useState({});
    const [portfolioName, setPortfolioName] = useState("");

    const [totalUser, setTotalUser] = useState(0);

    useEffect(() => {
        http
            .get("/user/getTotal/" + props.user.id)
            .then(response => {console.log(props.user.id)
                setPortfolio({
                    index: 0,
                    total: response.data
                })
                setTotalUser(response.data)
            })
            .catch(e => {
                console.log(e);
            });

        getPortfolio();
    }, [])

    function getPortfolio() {
        http
            .get("/portfolio/getAllByUserID/" + props.user.id)
            .then(response => {
                setData(response.data)
            })
            .catch(e => {
                console.log(e);
            });
    }

    function selectedPortfolio(ind) {
        if (ind === 0) {
            setPortfolio({
                index: 0,
                total: totalUser
            })
        } else {
            setPortfolio(
                {
                    ...data[ind - 1],
                    index: ind
                }
            )
        }
    }

    function addPortfolio() {
        http
            .post("/portfolio/add", {
                name: portfolioName,
                about: "",
                user_id: props.user.id
            })
            .then(() => {
                getPortfolio();
                setOpenModalAddPortfolio(false)
            })
            .catch(e => {
                console.log(e);
            });
    }

    const [openModalAddPortfolio, setOpenModalAddPortfolio] = useState(false);

    return <>
        <Grid
            justifyContent={"center"}
            marginX={"10%"}
            marginTop={"20px"}
            container
            spacing={5}
        >
            <Grid item xs={1}>
                <div className={"portfolio" + (portfolio.index === 0 ? " selected" : "")}
                     onClick={() => selectedPortfolio(0)}>
                    <div>DashBoard</div>
                    <span>${totalUser}</span>
                </div>
                {
                    data.map((row, index) => (
                        <div key={`portfolion-${index}`}
                             className={"portfolio" + (portfolio.index === index + 1 ? " selected" : "")}
                             onClick={() => selectedPortfolio(index + 1)}>
                            <div>{row.name}</div>
                            <span>${row.total}</span>
                        </div>
                    ))
                }
            </Grid>

            <Grid item xs={10}>
                <Grid
                    className={"d-flex"}
                    justifyContent="space-between"
                >
                    <Box width={3 / 10}>
                        <div>Баланс:</div>
                        <Typography variant={"h5"}>${portfolio.total}</Typography>
                    </Box>
                    <Box width={3 / 10}>
                        <Button variant={"outlined"} color={"primary"} onClick={() => setOpenModalAddPortfolio(true)}>Добавить
                            портфель</Button>
                    </Box>
                </Grid>
            </Grid>
        </Grid>
        <Modal open={openModalAddPortfolio} onClose={() => setOpenModalAddPortfolio(false)}
               className={"modal_portfolio"}>
            <Box>
                <Typography variant="h5" component="h2" style={{fontFamily: 'Jura'}}>
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
        </Modal>
    </>
}

function mapStateToProps(state) {
    const {user} = state.auth;
    return {
        user
    };
}

export default connect(mapStateToProps)(Portfolio);