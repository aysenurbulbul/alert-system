import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch, NavLink} from "react-router-dom";
import { Menu } from "semantic-ui-react";
import RequestForm from './components/RequestForm';
import AlertList from './components/AlertList';
import Graph from "./components/Graph";
import EditForm from "./components/EditForm";

function App() {
  return (
      <Router>
          <div>
              <Menu inverted>
                  <Menu.Item
                      name='home'
                      as = {NavLink}
                      to = "/"
                  />
                  <Menu.Item
                      name='List'
                      as = {NavLink}
                      to = "/alerts"
                  />
              </Menu>
              <br/>
              <br/>
              <Switch>
                  <Route path="/" exact component={RequestForm} />
                  <Route path="/alerts/:name" component={Graph} />
                  <Route path="/edit/:name" component={EditForm} />
                  <Route path="/alerts" component={AlertList} />
              </Switch>

          </div>
      </Router>

  );
}

export default App;
