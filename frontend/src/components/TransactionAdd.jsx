import {useEffect, useMemo, useState} from "react";

import {
    Box,
    Button,
    InputAdornment,
    MenuItem,
    Select,
    TextField,
    ToggleButton,
    ToggleButtonGroup,
    Typography
} from "@mui/material";
import http from "../http-common";

function TransactionAdd(props) {
    const [type, setType] = useState("1");
    const [assets, setAssets] = useState([]);
    const [selectedAssetID, setSelectedAssetID] = useState("");
    const [countAsset, setCountAsset] = useState(0);
    const [costAsset, setCostAsset] = useState(0);
    const [dateAsset, setDateAsset] = useState(0);
    const [noteAsset, setNoteAsset] = useState("");

    useEffect(() => {
        http
            .get("/asset/getAll", {})
            .then((response) => {
                setAssets(response.data)
                setSelectedAssetID(response.data[0].id ?? "")
            })
            .catch(e => {
                console.log(e);
            });
    }, [])

    function addTransaction() {
        http
            .post("/transaction/add", {
                vector: type == "1",
                lotCount: countAsset,
                price: costAsset,
                fee: 0,
                note: noteAsset,
                date: dateAsset,
                asset_id: selectedAssetID,
                portfolio_id: props.portfolio.id
            })
            .then(() => {
                props.addHandler();
            })
            .catch(e => {
                console.log(e);
            });
    }

    const menuItems = useMemo(() => assets.map(i => <MenuItem key={i.id} id={i.id}
                                                              value={i.id}>{i.name}</MenuItem>), [assets])

    return <Box width={"80%"}>
        <Typography variant="h5" component="h2">
            Добавить транзакцию
        </Typography>
        <ToggleButtonGroup
            orientation={"horizontal"}
            exclusive
            value={type}
            onChange={(e) => setType(e.target.value)}
        >
            <ToggleButton value="1">
                Купить
            </ToggleButton>
            <ToggleButton value="0">
                Продать
            </ToggleButton>
        </ToggleButtonGroup>
        <Box display={"flex"} gap={"5%"}>
            <Select
                value={selectedAssetID}
                onChange={(e) => setSelectedAssetID(e.target.value)}
            >
                {menuItems}
            </Select>
            <TextField
                type={"date"}
                value={dateAsset}
                onChange={(e) => setDateAsset(e.target.value)}
            />
        </Box>
        <Box display={"flex"} gap={"5%"}>
            <TextField
                value={countAsset}
                onChange={(e) => setCountAsset(e.target.value)}
                label={"Количество"}
            />
            <TextField
                value={costAsset}
                onChange={(e) => setCostAsset(e.target.value)}
                label={"Цена за монету"}
                type={"number"}
                InputProps={{
                    startAdornment:
                        <InputAdornment disableTypography position="start">
                            ₽</InputAdornment>,
                }}
            />
        </Box>
        <TextField
            value={noteAsset}
            label={"Заметка о транзакции"}
            onChange={(e) => setNoteAsset(e.target.value)}
        />
        <TextField
            value={countAsset * costAsset}
            disabled
            label={"Общий расход"}
        />
        <Box>
            <Button variant={"outlined"} color={"success"} onClick={addTransaction}>Добавить</Button>
        </Box>
    </Box>
}

export default TransactionAdd;