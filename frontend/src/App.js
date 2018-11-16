import React, { Component } from 'react';
import PriceTable from './PriceTable';
import PriceChart from './PriceChart';
import './App.css';
import Grid from '@material-ui/core/Grid';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <div style={{paddingTop: '20px'}}>
            Table
          </div>
        <Grid item xs={12}>
          <PriceTable/>
        </Grid>
          
          <div >
            
          </div>
          <div style={{paddingTop: '30px'}}>
            ETHBTC starting from last 5 pips
          </div>
          <div style={{paddingTop: '10px', 
            backgroundColor: 'white',
            boxSizing: 'border-box',
            fontSize: '10px',
            color: 'black'
          }}>
            <PriceChart/>
          </div>
        </header>
        
      </div>
    );
  }
}

export default App;
