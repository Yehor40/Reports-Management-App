import axios from 'axios';
import AuthService from './AuthService';

const API_URL = '/api/users';

class UserService {
    getUsers() {
        return axios.get(API_URL, { headers: AuthService.getAuthHeader() });
    }

    getUser(id) {
        return axios.get(API_URL + '/' + id, { headers: AuthService.getAuthHeader() });
    }

    createUser(user) {
        return axios.post(API_URL, user, { headers: AuthService.getAuthHeader() });
    }

    updateUser(id, user) {
        return axios.put(API_URL + '/' + id, user, { headers: AuthService.getAuthHeader() });
    }

    deleteUser(id) {
        return axios.delete(API_URL + '/' + id, { headers: AuthService.getAuthHeader() });
    }
}

export default new UserService();
