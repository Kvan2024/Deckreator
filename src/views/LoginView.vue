<template>
  <div class="log-reg-page">
    <div id="reg-users">
      <p> Registered User? Login below. </p>
      <div class="logreg-form">
        <form @submit.prevent="login">
          <input v-model="loginForm.username" required="true"  name="username"  type="text" placeholder="User ID*"></input><br>
          <input v-model="loginForm.password" required="true"  name="password"  type="password" placeholder="Password*"></input><br>
          <button id="logreg-button">Login</button>
          </form>
      </div>    
    </div>
      <div class="new-users">
        <p>New User? Just register below, it's quick and easy! </p>
        <div class="logreg-form">
        <form @submit.prevent="register">
          <input v-model="registerForm.username" required="true"  name="username"  type="text" placeholder="User ID*"></input><br>
          <input v-model="registerForm.password" required="true"  name="password"  type="password" placeholder="Password*"></input><br>
          <input v-model="registerForm.confirmPassword" required="true"  name="confirmPassword"  type="password" placeholder="Confirm Password*"></input><br>
          <button id="logreg-button">Register</button>
          </form>
        </div> 
      </div>
      <div>*Required field</div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'ROLE_USER'
      },
      error: ''
    };
  },
  methods: {
    ...mapActions(['loginUser', 'registerUser']),

    async login() {
      try {
        await this.loginUser({
          username: this.loginForm.username,
          password: this.loginForm.password
        });
        this.$router.push({ name: 'DeckView', params: { username: this.loginForm.username } });
      } catch (error) {
        console.error('Login failed:', error);
        this.error = 'Login failed. Please check your credentials.';
      }
    },

    async register() {
      try {
        await this.registerUser({
          username: this.registerForm.username,
          password: this.registerForm.password,
          confirmPassword: this.registerForm.confirmPassword,
          role: this.registerForm.role
        });
        console.log('Registration successful!');
      } catch (error) {
        console.error('Registration failed:', error);
        this.error = 'Registration failed. Try again.';
      }
    }
  }
}
</script>

<style scoped>
.log-reg-page {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-content: center;
  padding-bottom: 50px;
  gap: 50px;
}

#logreg-button{
  width: 75px;
  height: 20px;
  background-color: #344f31;
  border-radius:7px;
  border-color: #21d1d1;
  color: #2ce019;
}

.logreg-form input {
border: 1px solid #21521c;
}

input, textarea {
  color: #2ce019;
  background-color: black;
  border: 1px solid #2ce019;
  padding: 8px;
}

input:focus, textarea:focus {
  outline: none;
  border: 2px solid #21521c;
  box-shadow: 0 0 20px #00ffff;
}

.logreg-form input::placeholder{
background-color: black;
color: #2c7575;
padding: 2px;
}
</style>