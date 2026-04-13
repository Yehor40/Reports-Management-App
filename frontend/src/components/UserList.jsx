import React, { Component } from 'react';
import UserService from '../services/UserService';

class UserList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      message: ''
    };
  }

  componentDidMount() {
    this.refreshUsers();
  }

  refreshUsers = () => {
    UserService.getUsers().then(
      (response) => {
        this.setState({ users: response.data });
      },
      (error) => {
        this.setState({ message: 'Error fetching users (Admin only)' });
      }
    );
  };

  deleteUser = (id) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      UserService.deleteUser(id).then(() => {
        this.refreshUsers();
      });
    }
  };

  render() {
    return (
      <div className="container" style={{marginTop: '20px'}}>
        <div className="glass-card" style={{padding: '30px'}}>
          <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px'}}>
            <h2>Registered Users</h2>
            <button className="btn-premium" onClick={() => window.location.href='/users/create'}>Invite User</button>
          </div>
          
          {this.state.message && <div className="alert alert-danger">{this.state.message}</div>}

          <table className="table" style={{width: '100%', color: 'white', borderCollapse: 'collapse'}}>
            <thead>
              <tr style={{borderBottom: '1px solid rgba(255,255,255,0.1)'}}>
                <th style={{padding: '12px', textAlign: 'left'}}>ID</th>
                <th style={{padding: '12px', textAlign: 'left'}}>Name</th>
                <th style={{padding: '12px', textAlign: 'left'}}>Email</th>
                <th style={{padding: '12px', textAlign: 'center'}}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {this.state.users.map(user => (
                <tr key={user.id} style={{borderBottom: '1px solid rgba(255,255,255,0.05)'}}>
                  <td style={{padding: '12px'}}>{user.id}</td>
                  <td style={{padding: '12px'}}>{user.name}</td>
                  <td style={{padding: '12px'}}>{user.email}</td>
                  <td style={{padding: '12px', textAlign: 'center'}}>
                    <button onClick={() => this.deleteUser(user.id)} style={{background: 'none', border: 'none', color: '#ff4d4d', cursor: 'pointer'}}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default UserList;
