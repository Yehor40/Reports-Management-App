import React, { Component } from 'react';
import EvidenceService from '../services/EvidenceService';

class EvidenceForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: null,
      taskName: '',
      project: '',
      orderNum: '',
      department: 'Interns',
      estTime: 0,
      timeSpent: 0,
      charge: 0,
      total: 0,
      state: 'TODO',
      month: 'January',
      message: ''
    };
  }

  componentDidMount() {
    // Check if we are in edit mode (simple approach without hooks since we are in class components outside of router context in this example)
    const path = window.location.pathname;
    if (path.includes('/edit/')) {
        const id = path.split('/').pop();
        EvidenceService.getEvidence(id).then(response => {
            this.setState({ ...response.data });
        });
    }
  }

  onChangeValue = (e) => {
    const { name, value } = e.target;
    this.setState({ [name]: value }, () => {
        if (name === 'timeSpent' || name === 'charge') {
            const timeSpent = parseFloat(this.state.timeSpent) || 0;
            const charge = parseFloat(this.state.charge) || 0;
            this.setState({ total: timeSpent * charge });
        }
    });
  };

  saveEvidence = (e) => {
    e.preventDefault();
    const evidence = { ...this.state };
    
    if (this.state.id) {
        EvidenceService.updateEvidence(this.state.id, evidence).then(() => {
            window.location.href = '/evidences';
        });
    } else {
        EvidenceService.createEvidence(evidence).then(() => {
            window.location.href = '/evidences';
        });
    }
  };

  render() {
    return (
      <div className="container" style={{marginTop: '20px'}}>
        <div className="card glass-card" style={{padding: '30px', maxWidth: '600px', margin: '0 auto'}}>
          <h2 style={{marginBottom: '20px'}}>{this.state.id ? 'Edit Record' : 'Create Record'}</h2>
          <form onSubmit={this.saveEvidence}>
            <div className="form-group" style={{marginBottom: '15px'}}>
              <label>Task Name</label>
              <input type="text" name="taskName" className="form-control" value={this.state.taskName} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}} />
            </div>
            
            <div className="form-group" style={{marginBottom: '15px'}}>
              <label>Project Name</label>
              <input type="text" name="project" className="form-control" value={this.state.project} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}} />
            </div>

            <div style={{display: 'flex', gap: '20px', marginBottom: '15px'}}>
                <div style={{flex: 1}}>
                    <label>Order Num</label>
                    <input type="text" name="orderNum" className="form-control" value={this.state.orderNum} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}} />
                </div>
                <div style={{flex: 1}}>
                    <label>Department</label>
                    <select name="department" className="form-control" value={this.state.department} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}}>
                        <option value="Interns">Interns</option>
                        <option value="Marketing">Marketing</option>
                        <option value="Development">Development</option>
                        <option value="Personal">Personal</option>
                        <option value="Legal">Legal</option>
                        <option value="Finance">Finance</option>
                        <option value="Trade">Trade</option>
                        <option value="Front office">Front office</option>
                        <option value="IT">IT</option>
                        <option value="Coaching">Coaching</option>
                    </select>
                </div>
            </div>

            <div style={{display: 'flex', gap: '20px', marginBottom: '15px'}}>
                <div style={{flex: 1}}>
                    <label>Est Time (h)</label>
                    <input type="number" name="estTime" className="form-control" value={this.state.estTime} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}} />
                </div>
                <div style={{flex: 1}}>
                    <label>Spent Time (h)</label>
                    <input type="number" name="timeSpent" className="form-control" value={this.state.timeSpent} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}} />
                </div>
            </div>

            <div style={{display: 'flex', gap: '20px', marginBottom: '15px'}}>
                <div style={{flex: 1}}>
                    <label>Charge</label>
                    <input type="number" name="charge" className="form-control" value={this.state.charge} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}} />
                </div>
                <div style={{flex: 1}}>
                    <label>Total</label>
                    <input type="number" name="total" className="form-control" value={this.state.total} readOnly style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px', backgroundColor: '#f0f0f0', cursor: 'not-allowed'}} />
                </div>
            </div>

            <div style={{display: 'flex', gap: '20px', marginBottom: '15px'}}>
                <div style={{flex: 1}}>
                    <label>State</label>
                    <select name="state" className="form-control" value={this.state.state} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}}>
                        <option value="TODO">TODO</option>
                        <option value="IN_PROGRESS">IN PROGRESS</option>
                        <option value="DONE">DONE</option>
                    </select>
                </div>
                <div style={{flex: 1}}>
                    <label>Month</label>
                    <select name="month" className="form-control" value={this.state.month} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}}>
                        <option value="January">January</option>
                        <option value="February">February</option>
                        <option value="March">March</option>
                        <option value="April">April</option>
                        <option value="May">May</option>
                        <option value="June">June</option>
                        <option value="July">July</option>
                        <option value="August">August</option>
                        <option value="September">September</option>
                        <option value="October">October</option>
                        <option value="November">November</option>
                        <option value="December">December</option>
                    </select>
                </div>
            </div>

            <button type="submit" className="btn-premium" style={{width: '100%', marginTop: '10px'}}>Save Evidence</button>
            <button type="button" className="btn-secondary" onClick={() => window.location.href='/evidences'} style={{width: '100%', marginTop: '10px', background: 'transparent', color: 'white', border: '1px solid white', padding: '10px', borderRadius: '8px', cursor: 'pointer'}}>Cancel</button>
          </form>
        </div>
      </div>
    );
  }
}

export default EvidenceForm;
