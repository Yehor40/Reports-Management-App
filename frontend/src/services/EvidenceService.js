import axios from 'axios';
import AuthService from './AuthService';

const API_URL = '/api/evidences';

class EvidenceService {
    getEvidences() {
        return axios.get(API_URL, { headers: AuthService.getAuthHeader() });
    }

    getEvidence(id) {
        return axios.get(API_URL + '/' + id, { headers: AuthService.getAuthHeader() });
    }

    createEvidence(evidence) {
        return axios.post(API_URL, evidence, { headers: AuthService.getAuthHeader() });
    }

    updateEvidence(id, evidence) {
        return axios.put(API_URL + '/' + id, evidence, { headers: AuthService.getAuthHeader() });
    }

    deleteEvidence(id) {
        return axios.delete(API_URL + '/' + id, { headers: AuthService.getAuthHeader() });
    }

    exportMyReport() {
        return axios.get('/api/excel/download/my', { 
            headers: AuthService.getAuthHeader(),
            responseType: 'blob'
        });
    }

    exportAllReports() {
        return axios.get('/api/excel/download/all', { 
            headers: AuthService.getAuthHeader(),
            responseType: 'blob'
        });
    }

    exportSingle(id) {
        return axios.get('/api/excel/download/' + id, { 
            headers: AuthService.getAuthHeader(),
            responseType: 'blob'
        });
    }

    exportSelected(ids) {
        return axios.post('/api/excel/download/selected', ids, { 
            headers: AuthService.getAuthHeader(),
            responseType: 'blob'
        });
    }
}

export default new EvidenceService();
