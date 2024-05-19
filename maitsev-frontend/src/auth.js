import { reactive } from 'vue';
import { jwtDecode } from 'jwt-decode';

export const authState = reactive({
  isAuthorized: !!localStorage.getItem('jwtToken'),
  user: localStorage.getItem('jwtToken') ? jwtDecode(localStorage.getItem('jwtToken')) : null,
  login(token) {
    this.isAuthorized = true;
    const decodedToken = jwtDecode(token);
    this.user = { id: decodedToken.userId, ...decodedToken };
    localStorage.setItem('jwtToken', token);
  },
  logout() {
    this.isAuthorized = false;
    this.user = null;
    localStorage.removeItem('jwtToken');
  }
});
