import {useEffect, useState} from "react";
import {
    Box,
    Button,
    Grid,
    IconButton,
    Modal,
    Paper, Table,
    TableBody,
    TableCell, TableContainer,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";

import http from "../http-common";
import {connect} from "react-redux";

import PortfolioAdd from "../components/portfolio/PortfolioAdd";
import PortfolioEdit from "../components/portfolio/PortfolionEdit";
import TransactionAdd from "../components/TransactionAdd";
import AssetRow from "../components/AssetRow";

function Portfolio(props) {

    const [data, setData] = useState([]);
    const [assets, setAssets] = useState(undefined);

    const [portfolio, setPortfolio] = useState({});

    const [totalUser, setTotalUser] = useState(0);

    useEffect(() => {
        http
            .get("/user/getTotal/" + props.user.id)
            .then(response => {
                setPortfolio({
                    index: 0,
                    total: response.data
                })
                setTotalUser(response.data ?? 0)
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
                setPortfolio(
                    {
                        ...response.data[portfolio.index - 1],
                        index: portfolio.index
                    }
                )
            })
            .catch(e => {
                console.log(e);
            });
    }

    useEffect(() => {
        if (portfolio.id)
            http
                .get("/portfolio_asset/getAllByPortfolioID/" + portfolio.id)
                .then(response => {
                    console.log(response.data);
                    setAssets(response.data)
                })
                .catch(e => {
                    console.log(e);
                });
    }, [portfolio]);

    function selectPortfolio(ind) {
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

    const [openModalAddPortfolio, setOpenModalAddPortfolio] = useState(false);
    const [openModalEditPortfolio, setOpenModalEditPortfolio] = useState(false);
    const [openModalAddTransaction, setOpenModalAddTransaction] = useState(false);

    return <>
        <Grid
            justifyContent={"center"}
            marginX={"10%"}
            marginTop={"20px"}
            width={"80%"}
            container
            spacing={5}
        >
            <Grid item xs={1}>
                <div className={"portfolio" + (portfolio.index === 0 ? " selected" : "")}
                     onClick={() => selectPortfolio(0)}>
                    <div>DashBoard</div>
                    <span>₽{totalUser}</span>
                </div>
                {
                    data.map((row, index) => (
                        <div key={`portfolion-${index}`}
                             className={"portfolio" + (portfolio.index === index + 1 ? " selected" : "")}
                             onClick={() => selectPortfolio(index + 1)}>
                            <div>{row.name}</div>
                            <span>₽{row.total}</span>
                        </div>
                    ))
                }
            </Grid>

            <Grid item xs={10}>
                <Grid
                    className={"d-flex"}
                    flexDirection={"column"}
                >
                    <Box className={"d-flex"}
                         justifyContent="space-between"
                         marginBottom={10}
                    >
                        <Box width={3 / 10}>
                            <div>Баланс:</div>
                            <Typography variant={"h5"}>₽{portfolio.total}</Typography>
                        </Box>
                        <Box className="buttons" width={4 / 10}>
                            {
                                portfolio.index === 0 ?
                                    (<Button variant={"contained"} color={"primary"}
                                             onClick={() => setOpenModalAddPortfolio(true)}>Добавить
                                        портфель</Button>)
                                    :
                                    (<>
                                        <Button variant={"contained"} color={"primary"}
                                                onClick={() => setOpenModalAddTransaction(true)}>Добавить&nbsp;транзакцию</Button>
                                        <Button variant={"contained"} color={"primary"}
                                                onClick={() => setOpenModalEditPortfolio(true)}>Редактировать&nbsp;портфель</Button>
                                    </>)
                            }
                        </Box>
                    </Box>
                    <Box>
                        <Typography variant={"h4"} marginBottom={3}>Активы</Typography>

                        <TableContainer component={Paper}>
                            <Table aria-label="collapsible table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell></TableCell>
                                        <TableCell>Название</TableCell>
                                        <TableCell align="right">Цена</TableCell>
                                        <TableCell align="right">24часа</TableCell>
                                        <TableCell align="right">В портфеле</TableCell>
                                        <TableCell align="right">Средняя цена</TableCell>
                                        <TableCell align="right">Прибыли/Потери</TableCell>
                                        <TableCell align="right"></TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {assets && assets.map((row) => (
                                        <AssetRow key={row.asset.name} row={row}/>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Box>
                </Grid>
            </Grid>
        </Grid>
        <Modal open={openModalAddPortfolio} onClose={() => setOpenModalAddPortfolio(false)}
               className={"modal_portfolio"}>
            <PortfolioAdd user_id={props.user.id} addHandler={
                () => {
                    getPortfolio();
                    setOpenModalAddPortfolio(false)
                }
            }></PortfolioAdd>
        </Modal>
        <Modal open={openModalEditPortfolio} onClose={() => setOpenModalEditPortfolio(false)}
               className={"modal_portfolio"}>
            <PortfolioEdit user_id={props.user.id} portfolio={portfolio} editHandler={
                () => {
                    getPortfolio();
                    setOpenModalEditPortfolio(false)
                }
            }></PortfolioEdit>
        </Modal>
        <Modal open={openModalAddTransaction} onClose={() => setOpenModalAddTransaction(false)}
               className={"modal_portfolio modal_portfolio_transaction"}>
            <TransactionAdd user_id={props.user.id} portfolio={portfolio} addHandler={
                () => {
                    getPortfolio();
                    setOpenModalAddTransaction(false)
                }
            }></TransactionAdd>
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