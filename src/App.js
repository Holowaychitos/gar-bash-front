import React, { Component } from 'react';
import { getPoint } from './services/pointsService.js';
import Map from './Map';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {count: 1};
    getPoint()
  }
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <Map />
      </div>
    );
  }
}

export default App;
