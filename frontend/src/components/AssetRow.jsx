import {Box, Collapse, IconButton, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@mui/material";

import {IconArrowUp, IconArrowDown} from '@tabler/icons-react';
import {useState} from "react";

function AssetRow(props) {
    const {row} = props;
    const [open, setOpen] = useState(false);

    function getDate(unix) {
        let t = new Date(unix);
        return t.toLocaleDateString();
    }

    return <>
        <TableRow sx={{'& > *': {borderBottom: 'unset'}}}>
            <TableCell>
                <IconButton
                    aria-label="expand row"
                    size="small"
                    onClick={() => setOpen(!open)}
                >
                    {open ? <IconArrowUp/> : <IconArrowDown/>}
                </IconButton>
            </TableCell>
            <TableCell component="th" scope="row">{row.asset.name}</TableCell>
            <TableCell align="right"></TableCell>
            <TableCell align="right"></TableCell>
            <TableCell align="right">₽{row.sum_price}<br/><span color={"gray"}>{row.lotCount}</span></TableCell>
            <TableCell align="right">{row.avg_price}</TableCell>
            <TableCell align="right"></TableCell>
            <TableCell align="right"></TableCell>
        </TableRow>
        <TableRow>
            <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                <Collapse in={open} timeout="auto" unmountOnExit>
                    <Box sx={{margin: 1}}>
                        <Typography variant="h6" gutterBottom component="div">
                            История транзакций
                        </Typography>
                        <Table size="small" aria-label="purchases">
                            <TableHead>
                                <TableRow>
                                    <TableCell>Дата</TableCell>
                                    <TableCell>Количество</TableCell>
                                    <TableCell>Цена</TableCell>
                                    <TableCell>Запись</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {row.transactions.map((historyRow) => (
                                    <TableRow key={historyRow.date}>
                                        <TableCell component="th" scope="row">
                                            {getDate(historyRow.date)}
                                        </TableCell>
                                        <TableCell>{historyRow.lotCount}</TableCell>
                                        <TableCell>{historyRow.price}</TableCell>
                                        <TableCell>
                                            {historyRow.note}
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Box>
                </Collapse>
            </TableCell>
        </TableRow>
    </>
}

export default AssetRow;