import React, { Component } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import EvidenceList from './components/EvidenceList';
import EvidenceForm from './components/EvidenceForm';
import UserList from './components/UserList';
import AuthService from './services/AuthService';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: undefined
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({ currentUser: user });
    }
  }

  logOut = () => {
    AuthService.logout();
    this.setState({ currentUser: undefined });
  };

  render() {
    const { currentUser } = this.state;

    return (
      <Router>
        <div>
          <nav className="navbar glass-card" style={{margin: '10px', padding: '10px', display: 'flex', justifyContent: 'space-between'}}>
            <Link to={"/"} style={{color: 'white', textDecoration: 'none', fontWeight: 'bold'}}>
              ReportApp
            </Link>
            <div style={{display: 'flex', gap: '20px'}}>
              {currentUser ? (
                <>
                  <Link to={"/evidences"} style={{color: 'white'}}>Evidences</Link>
                  {currentUser.roles && currentUser.roles.includes('ROLE_ADMIN') && (
                    <Link to={"/users"} style={{color: 'white'}}>Users (Admin)</Link>
                  )}
                  <a href="/login" onClick={this.logOut} style={{color: 'white'}}>LogOut</a>
                </>
              ) : (
                <>
                  <Link to={"/login"} style={{color: 'white'}}>Login</Link>
                  <Link to={"/register"} style={{color: 'white'}}>Register</Link>
                </>
              )}
            </div>
          </nav>

          <div className="container mt-3">
            <Routes>
              <Route path="/" element={<Navigate to="/evidences" />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/evidences" element={<EvidenceList />} />
              <Route path="/evidences/create" element={<EvidenceForm />} />
              <Route path="/evidences/edit/:id" element={<EvidenceForm />} />
              <Route path="/users" element={<UserList />} />
            </Routes>
          </div>
        </div>
      </Router>
    );
  }
}

export default App;
