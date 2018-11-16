import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from 'axios';
import NumberFormat from 'react-number-format';
import SockJsClient from 'react-stomp';

// const serverUrl = 'http://localhost:8080';
const serverUrl = 'http://sbi-lb-1358644542.us-west-2.elb.amazonaws.com';
const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
  },
  table: {
    minWidth: 700,
  },
});

class PriceTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      classes: props.classes,
      rows: [],
      data: []
    };
  }

  createRowData(id, pair, binance, gemini, hitbtc, timestamp) {
    var myArray = [binance, gemini, hitbtc],
    low = myArray[0],
    high = myArray[0]; 

    for (var i = 1, l = myArray.length; i < l; ++i) {
      if (myArray[i] > high) {
          high = myArray[i];
      } else if (myArray[i] < low) {
          low = myArray[i];
      }
    }

    var difference = high - low;
    binance = <NumberFormat value={binance} displayType={'text'} decimalScale={8} fixedDecimalScale/>;
    gemini = <NumberFormat value={gemini} displayType={'text'} decimalScale={8} fixedDecimalScale/>;
    hitbtc = <NumberFormat value={hitbtc} displayType={'text'} decimalScale={8} fixedDecimalScale/>;
    difference = <NumberFormat value={difference} displayType={'text'} decimalScale={8} fixedDecimalScale/>;
    return { id, pair, binance, gemini, hitbtc, difference, timestamp };
  }

  updateData(data) {
    console.log(data);

    var newData = [];
    for (var i = 0; i < data.length; i++) {
      var pair = this.createPair(data[i]);
      newData.push(pair);
    }
    this.setState({ rows: newData });
  }

  createPair(pairData) {
    var binance = 0;
    var gemini = 0;
    var hitbtc = 0;
    var exchanges = pairData.exchanges;
    for (var k = 0; k < exchanges.length; k++) {
      if(exchanges[k].exchange === "binance") {
        binance = exchanges[k].price;
      }
      if(exchanges[k].exchange === "gemini") {
        gemini = exchanges[k].price;
      }
      if(exchanges[k].exchange === "hitbtc") {
        hitbtc = exchanges[k].price;
      }
    }
    
    var rowData = this.createRowData(pairData.pair, pairData.pair, binance, gemini, hitbtc, pairData.ticktime)
    return rowData;
  }

  componentWillMount() {
    axios.get(serverUrl +'/current_prices')
    .then(function (response) {
      this.updateData(response.data);
    }.bind(this))
  }

  onMessageReceive = (data, topic) => {
    // console.log(data);
    // console.log(topic);
    this.updateData(data);
  }

  render() {
    return (
      <div style={{marginLeft:'5px', marginRight:'5px'}}>
      <Paper className={this.state.classes.root}>
        <SockJsClient url={ serverUrl + "/price_checker" } topics={["/topic/getLatestTickerData"]}
                onMessage={ this.onMessageReceive } ref={ (client) => { this.clientRef = client }}
                onConnect={ () => { this.setState({ clientConnected: true }) } }
                onDisconnect={ () => { this.setState({ clientConnected: false }) } }
                debug={ false }/>
        <Table className={this.state.classes.root}>
          <TableHead>
            <TableRow>
              <TableCell className={this.state.classes.tableCell}>Pair</TableCell>
              <TableCell className={this.state.classes.tableCell} numeric>Binance</TableCell>
              <TableCell className={this.state.classes.tableCell} numeric>Gemini</TableCell>
              <TableCell className={this.state.classes.tableCell} numeric>Hitbtc</TableCell>
              <TableCell className={this.state.classes.tableCell} numeric>Difference</TableCell>
              <TableCell className={this.state.classes.tableCell}>Timestamp</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {this.state.rows.map(row => {
              return (
                <TableRow key={row.id}>
                  <TableCell className={this.state.classes.tableCell} component="th" scope="row" >
                    {row.pair}
                  </TableCell>
                  <TableCell className={this.state.classes.tableCell} numeric>{row.binance}</TableCell>
                  <TableCell className={this.state.classes.tableCell} numeric>{row.gemini}</TableCell>
                  <TableCell className={this.state.classes.tableCell} numeric>{row.hitbtc}</TableCell>
                  <TableCell className={this.state.classes.tableCell} numeric>{row.difference}</TableCell>
                  <TableCell className={this.state.classes.tableCell}>{row.timestamp}</TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </Paper>
      </div>
    );
  }
}

PriceTable.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PriceTable);