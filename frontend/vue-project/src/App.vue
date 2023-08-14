<script>
import HelloWorld from './components/HelloWorld.vue'
import SearchRecipe from './components/SearchRecipe.vue'
import NavBar from './components/NavBar.vue'
export default { 
  components: {
    HelloWorld,
    SearchRecipe,
    NavBar
  },
  data() {
    return {
      userLoggedIn: false,
      
    };
  },
  methods: {
    userLogged() {
      //dismiss the selected recipe
      this.userLoggedIn = true;
    },
    isloggedout() {
      //dismiss the selected recipe
      this.userLoggedIn = false;
    }
  }
};
</script>

<template>
  
  <header>
    <div class="wrapper">
      <HelloWorld msg="We are smarter than AI." />
    </div>
  </header>

  <main>
    <SearchRecipe :userLoggedIn="userLoggedIn"/>
    <NavBar @islogged="userLogged" @isloggedout="isloggedout"/>
  </main>
  
</template>

<style scoped>

header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
    
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
