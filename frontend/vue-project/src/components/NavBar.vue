<template>
  <nav class="navbar">
    <div class="navbar-menu">
      <div class="navbar-items">
        <button v-if="!loggedInUser" class="navbar-item" @click="togglePopup('login')">Login</button> <!-- login button -->
        <button v-if="!loggedInUser" class="navbar-item" @click="togglePopup('signup')">Sign Up</button> <!-- logout button -->
        <div v-if="loggedInUser" class="navbar-item">{{ loggedInUser }}</div>
        <button @click="logout">Logout</button>
        <router-link to="/authors" class="navbar-item">Authors</router-link>
        <router-link to="/about" class="navbar-item">About</router-link>
        <router-link to="/contact" class="navbar-item">Contact</router-link>
      </div>
      <div v-if="showLoginMessage" class="login-message">{{ loginMessage }}</div> <!-- login message -->
      <div class="navbar-dropdown">
        <button class="navbar-item dropdown-button" @click="togglePopup('menu')">
          <span class="dropdown-icon"></span>
          <span class="dropdown-icon"></span>
          <span class="dropdown-icon"></span>
        </button>
        <div class="dropdown-menu" v-if="showPopup === 'menu'">
          <a class="dropdown-item" href="#" @click="checkLogin">Create Recipe</a>      <!-- checks if logged in and allows it to crate recipe-->
          <createRecipe v-if="showCreateRecipeForm" />       
        </div>
      </div>
    </div>
    <div class="login-popup" v-if="showPopup === 'login'">
      <div class="popup-window">
        <button class="close-button" @click="closePopup">X</button> <!-- button to close popups-->
        <h2>Login</h2>
        <form @submit.prevent="login" class="popup-form">
          <input type="text" v-model="username" placeholder="Username" class="input-field" required/>
          <input type="password" v-model="password" placeholder="Password" class="input-field" required/>
          <button type="submit" class="btn btn-primary">Log In</button>
        </form>
        <div class="login-message">{{ loginMessage }}</div> <!-- login message -->
        <div class="popup-options">
          <span>Don't have an account?</span>
          <button @click="togglePopup('signup')">Sign Up</button> <!-- singup button -->
        </div>
      </div>
    </div>
    <div class="signup-popup" v-if="showPopup === 'signup'"> <!-- singup popup -->
      <div class="popup-window">
        <button class="close-button" @click="closePopup">X</button>
        <h2>Sign Up</h2>
        <form @submit.prevent="signup" class="popup-form">
          <input type="text" v-model="username" placeholder="Username" class="input-field" required/> <!-- singup fields -->
          <input type="password" v-model="password" placeholder="Password" class="input-field" required/> <!-- singup fields -->
          <input type="text" v-model="fullname" placeholder="Full Name" class="input-field" required/> <!-- singup fields -->
          <button type="submit" class="btn btn-primary">Sign Up</button> <!-- singup button -->
        </form>
        <div class="message">{{ signupMessage }}</div> <!-- singup message -->
        <div class="popup-options">
          <span>Already have an account?</span> <!-- singup hardcoded message -->
          <button @click="togglePopup('login')">Log In</button> <!-- login popup -->
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import axios from 'axios';
import CreateRecipe from './CreateRecipe.vue'
export default {
  components: {
    CreateRecipe,
  },
  data() {
  return {
    showSignupMessage: false,
    signupMessage: '',
    showSuccessMessage: false,
    showPopup: null,
    username: "",
    password: "",
    fullname: "",
    loggedInUser: "",
    isLogged: false,
    showCreateRecipeForm: false,
    recipeName: "",
    category: "",
    prepTime: "",
    cookTime: "",
    ingredients: "",
    directions: "",
    image: null,
    showLoginMessage: false,
    loginMessage: ""
  };
},
  computed: {
    isLoggedIn() { // provides a way to check if a user is logged in
      return this.loggedInUser !== "";
    }
  },
  methods: {
    logout() {
      // logging out and resetting
      this.loginMessage = '';
      this.isLogged = false;
      this.loggedInUser = '';
      this.showCreateRecipeForm = false;
      this.$emit("isloggedout");
      fetch('api/logout');
      // Perform any additional actions required for logout
    },
    checkLogin(){ // when viewing the create recipe it checks if the user is logged in
      if(!this.isLogged){
        alert('You are not logged in');
    }
    else{
      
      if(!this.showCreateRecipeForm){this.showCreateRecipeForm = true;}
      else {this.showCreateRecipeForm = false;}
      
    }
    },
    togglePopup(popup) {//popups for login / recipe creation
      if (this.showPopup === popup) {
        this.hidePopup();
      } else {
        this.showPopup = popup;
      }
    },
    hidePopup() {
      this.showPopup = null;
    },
    login() {//functionality of the login post and get used to get responses + logged in in the server side
      this.logout();
      let url = 'api/login';
  let formData = new FormData();
  formData.append('email', this.username); 
  formData.append('password', this.password);
  axios.post(url, formData)
    .then(response => {
      console.log(response);
    })
    .catch(error => {
      console.error('Error:', error);
    });
  axios.get(url, {
    params: {
      email: this.username, //
      password: this.password,
    }})
  .then(response => {
    this.loginMessage = response.data.message;
    if (response.data.outputMessage === "success") { //if success returns the server response
      this.isLogged = true;
      this.$emit("islogged");
      this.loggedInUser = this.username;
      }
  })
  this.password = "";
},
    signup() { //signup functionality, same as the login
      let url = 'api/signup?email=' + this.username + '&password=' + this.password + '&fullname=' + this.fullname;
      axios.get(url)
        .then(response => {
          this.signupMessage = response.data.message;
          if (response.data.outputMessage === "success") {
            this.signupMessage = `Account created successfully! Welcome, ${this.username}! Please login now`;
          } 
          
          
        })
        .catch(error => {
          console.error('Error:', error);
        });
        
          this.password = '';
    },
    closePopup() {
      this.showPopup = null;
      this.signupMessage= "";
      this.username= "";
      this.password= "";
      this.loginMessage= "";
      this.fullname="";
    },
    createRecipe() {
      this.recipeName = "";
      this.category = "";
      this.prepTime = "";
      this.cookTime = "";
      this.ingredients = "";
      this.directions = "";
      this.image = null;
      this.showSuccessMessage = true;
      setTimeout(() => {
        this.showSuccessMessage = false;
      }, 2500);
    },
  },
  created() { //initialize the state of the component. check if there are any values stored in the localStorage for the keys 'loggedInUser' and 'isLogged and gets the values
    if (localStorage.getItem('loggedInUser')) {
      this.loggedInUser = localStorage.getItem('loggedInUser');
    }
    if (localStorage.getItem('isLogged')) {
      this.isLogged = localStorage.getItem('isLogged');
    }
  },
  watch: { // checks if the user is still logged in  
    loggedInUser(newVal) {
      if (newVal) {
        localStorage.setItem('loggedInUser', newVal);
      } else {
        localStorage.removeItem('loggedInUser'); //   // Remove 'loggedInUser' from localStorage
      }
    },
    isLogged(newVal) {
      if (newVal) {
        localStorage.setItem('isLogged', newVal);
      } else {
        localStorage.removeItem('isLogged');
      }
    }
  }
};
</script>
<style scoped>
 /* styles */
.navbar {
  background-color: #f2f2f2;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 9999;
} 
.navbar-menu {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1rem;
  position: relative;
}
.navbar-items {
  display: flex;
  align-items: center;
}
.navbar-item {
  margin-right: 1rem;
  text-decoration: none;
  color: #333;
  font-weight: bold;
  transition: all 0.3s ease;
}
.navbar-item:hover {
  text-decoration: underline;
}
.dropdown-button {
  margin-right: 1rem;
  background-color: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
}
.dropdown-icon {
  display: block;
  width: 20px;
  height: 2px;
  background-color: #333;
  margin-bottom: 4px;
  margin-top: 5px;
}
.dropdown-menu {
  position: absolute;
  top: 100%;
  display: none;
  background-color: #f2f2f2;
  padding: 2rem;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  z-index: 100;
  right: 0;
  width: 400px;
  transform: translateX(15%);
}
.dropdown-item {
  display: block;
  margin-bottom: 1rem;
  text-decoration: none;
  color: #333;
  transition: all 0.3s ease;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}


.dropdown-item:last-child {
  margin-bottom: 0;
}
.dropdown-item > span {
  display: inline-block;
  margin-right: 0.5rem;
}
.dropdown-item > span::before {
  content: "\25CF";
  margin-right: 0.3rem;
  font-size: 8px;
}
.create-recipe-form .input-field {
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}
.create-recipe-form .input-field {
  background-color: #ffffff;
  border: 1px solid #dddddd;
  color: #333333;
}
.navbar-dropdown .dropdown-menu {
  display: block;
}
.login-popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.popup-window {
  background-color: #f8f8f8;
  border-radius: 8px;
  padding: 2rem;
}
.popup-form {
  display: flex;
  flex-direction: column;
}
.popup-form .input-field {
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}
.popup-form button[type="submit"] {
  padding: 0.5rem 1rem;
  background-color: #5c6ac4;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease;
}
.popup-form button[type="submit"]:hover {
  background-color: #4c5aa6;
}
.message {
  margin-top: 1rem;
  color: #333;
  font-size: 14px;
}
.close-button {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #333;
}
</style>