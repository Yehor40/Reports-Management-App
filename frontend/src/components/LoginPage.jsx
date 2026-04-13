import React, { Component } from 'react';
import AuthService from '../services/AuthService';

class LoginPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      loading: false,
      message: ''
    };
  }

  onChangeValue = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleLogin = (e) => {
    e.preventDefault();
    this.setState({ loading: true, message: '' });

    AuthService.login(this.state.email, this.state.password).then(
      () => {
        window.location.href = '/evidences';
      },
      (error) => {
        const resMessage = (error.response && error.response.data && error.response.data.message) || error.message || error.toString();
        this.setState({ loading: false, message: resMessage });
      }
    );
  };

  render() {
    return (
      <div className="col-md-12">
        <div className="card glass-card" style={{maxWidth: '400px', margin: '40px auto', padding: '30px'}}>
          <h2 style={{textAlign: 'center', marginBottom: '20px'}}>Login</h2>
          <form onSubmit={this.handleLogin}>
            <div className="form-group" style={{marginBottom: '15px'}}>
              <label>Email</label>
              <input
                type="text"
                className="form-control"
                name="email"
                value={this.state.email}
                onChange={this.onChangeValue}
                style={{width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc'}}
              />
            </div>

            <div className="form-group" style={{marginBottom: '20px'}}>
              <label>Password</label>
              <input
                type="password"
                className="form-control"
                name="password"
                value={this.state.password}
                onChange={this.onChangeValue}
                style={{width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc'}}
              />
            </div>

            <button className="btn-premium" style={{width: '100%'}} disabled={this.state.loading}>
              {this.state.loading && <span className="spinner-border spinner-border-sm"></span>}
              <span>Login</span>
            </button>

            {this.state.message && (
              <div className="alert alert-danger" style={{marginTop: '15px', color: '#ff4d4d'}}>
                {this.state.message}
              </div>
            )}
          </form>
        </div>
      </div>
    );
  }
}

export default LoginPage;
