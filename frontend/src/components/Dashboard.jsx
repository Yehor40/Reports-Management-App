import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import AuthService from '../services/AuthService';

class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: undefined,
      isAdmin: false
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        isAdmin: user.roles && user.roles.includes('ROLE_ADMIN')
      });
    } else {
        // If not logged in, you could redirect to /login or let App.jsx handle it
        window.location.href = '/login';
    }
  }

  render() {
    const { currentUser, isAdmin } = this.state;

    if (!currentUser) {
        return null; // Or a loading spinner
    }

    return (
      <div className="container" style={{marginTop: '40px'}}>
        <div style={{textAlign: 'center', marginBottom: '40px'}}>
            <h1 style={{fontSize: '3rem', fontWeight: 'bold', marginBottom: '10px'}}>
                Welcome back, {currentUser.name || currentUser.username || 'User'}!
            </h1>
            <p style={{fontSize: '1.2rem', color: '#aaa'}}>Here is your overview for today.</p>
        </div>

        <div style={{display: 'flex', gap: '30px', justifyContent: 'center', flexWrap: 'wrap'}}>
            {/* Manage Evidences Card (For everyone) */}
            <Link to="/evidences" style={{textDecoration: 'none', color: 'inherit'}}>
                <div className="glass-card" style={{
                    padding: '40px', 
                    borderRadius: '20px', 
                    width: '350px', 
                    textAlign: 'center',
                    border: '1px solid rgba(99, 102, 241, 0.4)',
                    boxShadow: '0 0 20px rgba(99, 102, 241, 0.2)',
                    transition: 'all 0.3s ease',
                    cursor: 'pointer'
                }}
                onMouseEnter={(e) => {
                    e.currentTarget.style.transform = 'translateY(-5px)';
                    e.currentTarget.style.boxShadow = '0 0 30px rgba(99, 102, 241, 0.5)';
                }}
                onMouseLeave={(e) => {
                    e.currentTarget.style.transform = 'translateY(0)';
                    e.currentTarget.style.boxShadow = '0 0 20px rgba(99, 102, 241, 0.2)';
                }}>
                    <div style={{fontSize: '3rem', marginBottom: '20px', color: '#818cf8'}}>📁</div>
                    <h2 style={{fontWeight: 'bold', marginBottom: '15px'}}>Manage Evidences</h2>
                    <p style={{color: '#aaa', fontSize: '1.1rem'}}>View, upload, categorize, and organize your files.</p>
                </div>
            </Link>

            {/* Manage Users Card (Only for Admins) */}
            {isAdmin && (
                <Link to="/users" style={{textDecoration: 'none', color: 'inherit'}}>
                    <div className="glass-card" style={{
                        padding: '40px', 
                        borderRadius: '20px', 
                        width: '350px', 
                        textAlign: 'center',
                        border: '1px solid rgba(16, 185, 129, 0.4)',
                        boxShadow: '0 0 20px rgba(16, 185, 129, 0.2)',
                        transition: 'all 0.3s ease',
                        cursor: 'pointer'
                    }}
                    onMouseEnter={(e) => {
                        e.currentTarget.style.transform = 'translateY(-5px)';
                        e.currentTarget.style.boxShadow = '0 0 30px rgba(16, 185, 129, 0.5)';
                    }}
                    onMouseLeave={(e) => {
                        e.currentTarget.style.transform = 'translateY(0)';
                        e.currentTarget.style.boxShadow = '0 0 20px rgba(16, 185, 129, 0.2)';
                    }}>
                        <div style={{fontSize: '3rem', marginBottom: '20px', color: '#34d399'}}>👥</div>
                        <h2 style={{fontWeight: 'bold', marginBottom: '15px'}}>Manage Users</h2>
                        <p style={{color: '#aaa', fontSize: '1.1rem'}}>Control access, view user activity, and manage roles.</p>
                    </div>
                </Link>
            )}
        </div>
      </div>
    );
  }
}

export default Dashboard;
