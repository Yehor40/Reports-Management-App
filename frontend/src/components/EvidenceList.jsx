import React, { Component } from 'react';
import EvidenceService from '../services/EvidenceService';

class EvidenceList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      evidences: [],
      message: ''
    };
  }

  componentDidMount() {
    this.refreshEvidences();
  }

  refreshEvidences = () => {
    EvidenceService.getEvidences().then(
      (response) => {
        this.setState({ evidences: response.data });
      },
      (error) => {
        this.setState({ message: 'Error fetching records' });
      }
    );
  };

  deleteEvidence = (id) => {
    if (window.confirm('Are you sure you want to delete this record?')) {
      EvidenceService.deleteEvidence(id).then(() => {
        this.refreshEvidences();
      });
    }
  };

  render() {
    return (
      <div className="container" style={{marginTop: '20px'}}>
        <div className="glass-card" style={{padding: '30px'}}>
          <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px'}}>
            <h2>Evidence Records</h2>
            <div style={{display: 'flex', gap: '10px'}}>
               <a href="/api/excel/download/my" className="btn-premium" style={{textDecoration: 'none'}}>Export My Report</a>
               <button className="btn-premium" onClick={() => window.location.href='/evidences/create'}>Add Record</button>
            </div>
          </div>
          
          <table className="table" style={{width: '100%', color: 'white', borderCollapse: 'collapse'}}>
            <thead>
              <tr style={{borderBottom: '1px solid rgba(255,255,255,0.1)'}}>
                <th style={{padding: '12px', textAlign: 'left'}}>Task Name</th>
                <th style={{padding: '12px', textAlign: 'left'}}>Project</th>
                <th style={{padding: '12px', textAlign: 'left'}}>Spent Time</th>
                <th style={{padding: '12px', textAlign: 'left'}}>State</th>
                <th style={{padding: '12px', textAlign: 'center'}}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {this.state.evidences.map(evidence => (
                <tr key={evidence.id} style={{borderBottom: '1px solid rgba(255,255,255,0.05)'}}>
                  <td style={{padding: '12px'}}>{evidence.taskName}</td>
                  <td style={{padding: '12px'}}>{evidence.project}</td>
                  <td style={{padding: '12px'}}>{evidence.timeSpent}h</td>
                  <td style={{padding: '12px'}}><span style={{background: 'rgba(99,102,241,0.2)', padding: '4px 8px', borderRadius: '4px'}}>{evidence.state}</span></td>
                  <td style={{padding: '12px', textAlign: 'center'}}>
                    <button onClick={() => this.deleteEvidence(evidence.id)} style={{background: 'none', border: 'none', color: '#ff4d4d', cursor: 'pointer', marginRight: '10px'}}>Delete</button>
                    <button onClick={() => window.location.href=`/evidences/edit/${evidence.id}`} style={{background: 'none', border: 'none', color: '#fbbf24', cursor: 'pointer'}}>Edit</button>
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

export default EvidenceList;
