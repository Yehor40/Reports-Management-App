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
      department: '',
      estTime: 0,
      timeSpent: 0,
      charge: 0,
      total: 0,
      state: 'TODO',
      month: '',
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
    this.setState({ [e.target.name]: e.target.value });
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
                    <label>Spent Time (h)</label>
                    <input type="number" name="timeSpent" className="form-control" value={this.state.timeSpent} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}} />
                </div>
                <div style={{flex: 1}}>
                    <label>State</label>
                    <select name="state" className="form-control" value={this.state.state} onChange={this.onChangeValue} style={{width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px'}}>
                        <option value="TODO">TODO</option>
                        <option value="IN_PROGRESS">IN PROGRESS</option>
                        <option value="DONE">DONE</option>
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
