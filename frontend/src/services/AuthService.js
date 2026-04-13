import axios from 'axios';

const API_URL = '/api/auth/';

class AuthService {
    login(email, password) {
        return axios
            .post(API_URL + 'login', { email, password })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(name, email, password) {
        return axios.post(API_URL + 'register', {
            name,
            email,
            password
        });
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }

    getAuthHeader() {
        const user = this.getCurrentUser();
        if (user && user.token) {
            return { Authorization: 'Bearer ' + user.token };
        } else {
            return {};
        }
    }
}

export default new AuthService();
