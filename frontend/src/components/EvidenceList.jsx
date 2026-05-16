import React, { Component } from 'react';
import EvidenceService from '../services/EvidenceService';

class EvidenceList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      evidences: [],
      selectedIds: [],
      message: ''
    };
  }

  componentDidMount() {
    this.refreshEvidences();
  }

  toggleSelectAll = (e) => {
    if (e.target.checked) {
      this.setState({ selectedIds: this.state.evidences.map(ev => ev.id) });
    } else {
      this.setState({ selectedIds: [] });
    }
  };

  toggleSelect = (id) => {
    this.setState(prevState => {
      const selectedIds = prevState.selectedIds.includes(id)
        ? prevState.selectedIds.filter(sid => sid !== id)
        : [...prevState.selectedIds, id];
      return { selectedIds };
    });
  };

  handleDownload = (promise, filename) => {
    promise.then(response => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', filename);
      document.body.appendChild(link);
      link.click();
      link.remove();
    }).catch(error => {
      console.error('Download failed', error);
      alert('Download failed');
    });
  };

  exportSelected = () => {
    if (this.state.selectedIds.length === 0) {
      alert('Please select at least one record.');
      return;
    }
    this.handleDownload(EvidenceService.exportSelected(this.state.selectedIds), 'selected_reports.xlsx');
  };

  exportMyReport = () => {
    this.handleDownload(EvidenceService.exportMyReport(), 'my_report.xlsx');
  };

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
    const { evidences, selectedIds } = this.state;
    return (
      <div className="container" style={{marginTop: '20px'}}>
        <div className="glass-card" style={{padding: '30px'}}>
          <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px'}}>
            <h2>Evidence Records</h2>
            <div style={{display: 'flex', gap: '10px'}}>
               <button onClick={this.exportSelected} className="btn-premium">Export Selected</button>
               <button onClick={this.exportMyReport} className="btn-premium">Export My Report</button>
               <button className="btn-premium" onClick={() => window.location.href='/evidences/create'}>Add Record</button>
            </div>
          </div>
          
          <table className="table" style={{width: '100%', color: 'white', borderCollapse: 'collapse'}}>
            <thead>
              <tr style={{borderBottom: '1px solid rgba(255,255,255,0.1)'}}>
                <th style={{padding: '12px', textAlign: 'left'}}>
                  <input 
                    type="checkbox" 
                    onChange={this.toggleSelectAll} 
                    checked={selectedIds.length === evidences.length && evidences.length > 0} 
                  />
                </th>
                <th style={{padding: '12px', textAlign: 'left'}}>Task Name</th>
                <th style={{padding: '12px', textAlign: 'left'}}>Project</th>
                <th style={{padding: '12px', textAlign: 'left'}}>Spent Time</th>
                <th style={{padding: '12px', textAlign: 'left'}}>State</th>
                <th style={{padding: '12px', textAlign: 'center'}}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {evidences.map(evidence => (
                <tr key={evidence.id} style={{borderBottom: '1px solid rgba(255,255,255,0.05)'}}>
                  <td style={{padding: '12px'}}>
                    <input 
                      type="checkbox" 
                      checked={selectedIds.includes(evidence.id)} 
                      onChange={() => this.toggleSelect(evidence.id)} 
                    />
                  </td>
                  <td style={{padding: '12px'}}>{evidence.taskName}</td>
                  <td style={{padding: '12px'}}>{evidence.project}</td>
                  <td style={{padding: '12px'}}>{evidence.timeSpent}h</td>
                  <td style={{padding: '12px'}}><span style={{background: 'rgba(99,102,241,0.2)', padding: '4px 8px', borderRadius: '4px'}}>{evidence.state}</span></td>
                  <td style={{padding: '12px', textAlign: 'center'}}>
                    <button onClick={() => this.handleDownload(EvidenceService.exportSingle(evidence.id), `report_${evidence.id}.xlsx`)} style={{background: 'none', border: 'none', color: '#6366f1', cursor: 'pointer', marginRight: '10px'}}>Export</button>
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
