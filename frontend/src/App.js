import React, { Component } from 'react';
import PriceTable from './PriceTable';
import './App.css';


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <PriceTable/>
        </header>
      </div>
    );
  }
}

export default App;
