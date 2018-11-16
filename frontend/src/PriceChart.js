import React, { Component } from 'react';
import { LineChart, Line, YAxis, Tooltip, Legend } from 'recharts';
import SockJsClient from 'react-stomp';
// import rn from 'random-number';
import axios from 'axios';

// const serverUrl = 'http://localhost:8080';
const serverUrl = 'http://sbi-lb-1358644542.us-west-2.elb.amazonaws.com';

// const data = [
//       {name: 'Page A', uv: 4000, pv: 2400, amt: 2400},
//       {name: 'Page B', uv: 3000, pv: 1398, amt: 2210},
//       {name: 'Page C', uv: 2000, pv: 9800, amt: 2290},
//       {name: 'Page D', uv: 2780, pv: 3908, amt: 2000},
//       {name: 'Page E', uv: 1890, pv: 4800, amt: 2181},
//       {name: 'Page F', uv: 2390, pv: 3800, amt: 2500},
//       {name: 'Page G', uv: 3490, pv: 4300, amt: 2100},
// ];

// var gen = rn.generator({
//   min:  5000, max:  5200, integer: true
// })

class PriceChart extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: []
    };
  }

  componentWillMount() {
    // axios.get(serverUrl +'/last_thirty_minutes_data?pair=ETHBTC')
    axios.get(serverUrl +'/last_five_data?pair=ETHBTC')
    .then(function (response) {
      this.updateData(response.data);
    }.bind(this))
    
    // var data = [];
    // for(var i=0; i < 100 ; i++) {
    //   data.push({name: '' + i, binance: gen(), hitbtc: gen(), gemini: gen() })
    // }
    // this.setState({ data: data });
  }

  updateData(data) {
    console.log(data);

    var newData = [];
    for (var i = data.length-1; i >= 0; i--) {
      var node = this.createNode(data[i]);
      newData.push(node);
    }
    this.setState({ data: newData });
  }

  createNode(data) {
    var binance = 0;
    var gemini = 0;
    var hitbtc = 0;
    var exchanges = data.exchanges;
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
    var ticktime = data.ticktime;
    
    return {name: ticktime, binance: binance, hitbtc: hitbtc, gemini: gemini };
    
  }

  onMessageReceive = (data, topic) => {
    console.log('onMessageReceive' + data);
    var newData = this.state.data;
    for (var i = 0; i < data.length; i++) {
      if(data[i].pair === 'ETHBTC') {
        var node = this.createNode(data[i]);
        newData.push(node);
      }
    }
    console.log(newData);
    this.setState({ data: newData });
  }

  render() {
    return (
      <div>
        <SockJsClient url={ serverUrl + "/price_checker" } topics={["/topic/getLatestTickerData"]}
                onMessage={ this.onMessageReceive } ref={ (client) => { this.clientRef = client }}
                onConnect={ () => { this.setState({ clientConnected: true }) } }
                onDisconnect={ () => { this.setState({ clientConnected: false }) } }
                debug={ false }/>
        <LineChart width={500} height={300} data={this.state.data.slice()}
              margin={{top: 5, right: 30, left: 20, bottom: 5}}>
          <Tooltip/>
          <Legend />
          <YAxis type="number" domain={[0.0317, 0.032]} />
          <Line type="monotone" dataKey="binance" stroke="#8884d8" dot={null} activeDot={{r: 8}}/>
          <Line type="monotone" dataKey="hitbtc" stroke="#82ca9d" dot={null}/>
          <Line type="monotone" dataKey="gemini" stroke="red" dot={null}/>
        </LineChart>
      </div>
    );
  }
}

export default PriceChart;