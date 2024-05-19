import { reactive } from 'vue';

export const authState = reactive({
  isAuthorized: !!localStorage.getItem('jwtToken'),
  user: JSON.parse(localStorage.getItem('user')),
  login(user, token) {
    this.isAuthorized = true;
    this.user = user;
    localStorage.setItem('jwtToken', token);
    localStorage.setItem('user', JSON.stringify(user));
  },
  logout() {
    this.isAuthorized = false;
    this.user = null;
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('user');
  }
});
