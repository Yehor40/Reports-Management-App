import React, { Component } from 'react';
import AuthService from '../services/AuthService';

class RegisterPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      email: '',
      password: '',
      successful: false,
      message: ''
    };
  }

  onChangeValue = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleRegister = (e) => {
    e.preventDefault();
    this.setState({ message: '', successful: false });

    AuthService.register(this.state.name, this.state.email, this.state.password).then(
      (response) => {
        this.setState({ message: response.data, successful: true });
      },
      (error) => {
        const resMessage = (error.response && error.response.data && error.response.data.message) || error.message || error.toString();
        this.setState({ successful: false, message: resMessage });
      }
    );
  };

  render() {
    return (
      <div className="col-md-12">
        <div className="card glass-card" style={{maxWidth: '400px', margin: '40px auto', padding: '30px'}}>
          <h2 style={{textAlign: 'center', marginBottom: '20px'}}>Register</h2>
          <form onSubmit={this.handleRegister}>
            {!this.state.successful && (
              <div>
                <div className="form-group" style={{marginBottom: '15px'}}>
                  <label>Full Name</label>
                  <input
                    type="text"
                    className="form-control"
                    name="name"
                    value={this.state.name}
                    onChange={this.onChangeValue}
                    style={{width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc'}}
                  />
                </div>

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

                <button className="btn-premium" style={{width: '100%'}}>Sign Up</button>
              </div>
            )}

            {this.state.message && (
              <div className={this.state.successful ? "alert alert-success" : "alert alert-danger"} style={{marginTop: '15px', color: this.state.successful ? '#4ade80' : '#ff4d4d'}}>
                {this.state.message}
              </div>
            )}
          </form>
        </div>
      </div>
    );
  }
}

export default RegisterPage;
