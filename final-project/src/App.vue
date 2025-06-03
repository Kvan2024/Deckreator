<template>
  <div class="app">
    <div id="header">
      <header id="header" v-on:click="$router.push({name: 'MainView'})">
        <h1>Welcome to MM's Splendiferous Most Magnificent Totally Completely <br>
            Finished Deck Creator App For Magic The Gathering.</h1>
      </header>
    </div>
    <div id="content">
      <div id="nav">
          <div class="home">
            <router-link v-bind:to="{ name: 'MainView' }"><button id = "home-button" class="home-menu-button">Home</button></router-link>
          </div>  
          <div class="deck">
            <button id = "deck-button" class="menu-button">Decks</button>
            <router-link v-if="isLoggedIn" :to="{ name: 'DeckView', params: { username: getUsername } }">
            <button id="view-button" class="submenu-button">View</button>
            </router-link>
            <router-link v-else :to="{ name: 'LoginView' }">
            <button id="view-button" class="submenu-button">View</button>
            </router-link>
            <router-link v-if="isLoggedIn" :to="{ name: 'CreateView', params: { username: getUsername } }">
            <button id="view-button" class="submenu-button">Create</button>
            </router-link>
            <router-link v-else :to="{ name: 'LoginView' }">
            <button id="view-button" class="submenu-button">Create</button>
            </router-link>
          </div>
          <div class="cards">
            <button id = "card-button" class="menu-button">Cards</button>
            <router-link v-bind:to="{ name: 'CardSearchView' }"><button id = "cardsearch-button" class="submenu-button">All Search</button></router-link>
          </div>
        <div class="homereg">
          <router-link v-if="!isLoggedIn" :to="{ name: 'LoginView', params: { username: getUsername } }">
          <button id="login-button" class="submenu-button">Login</button></router-link>
          <button v-else id="login-button" class="submenu-button" @click="handleLogout">Logout</button>
        </div>
      </div>
      <div id="main">
          <main>
          <!-- Main website view -->
          <router-view />
        </main>
      </div>
    </div>
    <div id="footer">
      ©Kvan Designs 2025
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  computed: {
    ...mapGetters(['isLoggedIn', 'getUsername'])
  },
  methods: {
  ...mapActions(['logout']),
  handleLogout() {
    this.logout();
    this.$router.push({ name: 'MainView' });
  }
}
}
</script>

<style>

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: black;
}
a {
  text-decoration: none;
  color: #2ce019;
}
a:visited {
  color: #344f31;
}
#app {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

#content {
  display: grid;
  grid-template-columns: 17% 83%;
  grid-template-areas: 
  "nav main";
  flex: 1;
  height: 75vh;
}

#header {
  position: fixed;
  background-color: black;
  padding: 10px;
  height: 150px;
  width: 100%;
  font-family: 'Amarante', sans-serif;
  color: #2ce019;
  text-align: center;
}

#footer {
  position: fixed;
  padding-top: 160px;
  font-family: 'Amarante', sans-serif;
  color: #2ce019;
  text-align: start;
  align-content: end;
  height: 50px;
}

#nav {
  position: fixed;
  grid-area: nav;
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  row-gap: 80px;
  flex:1;
  background-color: black;
  height: 75vh;
  width: 250px;
  border-style: solid;
  border-radius: 10px;
  border-color: #2ce019;
  top: 160px;
  padding-top: 20px;
  margin-left:20px;
}
.home-menu-button {
  width: 175px;
  height: 40px;
  background-color: #344f31;
  border-radius: 10px;
  border-color: #2ce019;
  color: #2ce019; 
  animation: borderPulse 5s infinite;
  transition: border-color 0.3s ease-in-out;
}
.deck {
  display: grid;
  grid-template-columns: auto;
  grid-template-rows: auto auto auto auto auto;
  grid-template-areas: 
  "deck"
  "view"
  "edit"
  "create"
  "delete";
  row-gap: 10px;
  justify-items: center;
}
#deck-button {
  grid-area: deck;
}
#view-button {
  grid-area: view;
}
#edit-button {
  grid-area: edit;
}
#create-button {
  grid-area: create;
}
#delete-button {
  grid-area: delete;
}

.cards {
  display: grid;
  grid-template-columns: auto;
  grid-template-rows: auto auto;
  grid-template-areas: 
  "card"
  "search";
  row-gap: 10px;
  justify-items: center;
}
#cardsearch-button {
  grid-area: search;
}

.homereg {
  padding-top: 180px;
}

#main {
  padding-top: 160px;
  grid-area: main;
  background-color: black;
  height: 100%;
  color: #2ce019;
  align-content: center;
  justify-items: center;
}

.menu-button {
  width: 175px;
  height: 40px;
  background-color: #344f31;
  border-radius: 10px;
  border-color: #2ce019;
  color: #2ce019;
}

.submenu-button {
  width: 100px;
  height: 20px;
  background-color: #344f31;
  border-radius: 10px;
  border-color: #2ce019;
  color: #2ce019;
}

.submenu-button:hover {
  background-color: #2aa11f;
}


@media screen and (max-width: 425px) {
  #header {
  display: none;
}
  #content {
    display: flex;
    flex-direction: column;
    height: auto;
  }

 #nav {
  position: relative;
  order: -1;
  width: 100%;
  height: auto;
  margin-left: 0;
  border-radius: 0;
  padding: 10px;
  flex-direction: row;
  flex-wrap: wrap;
  row-gap: 10px;
  column-gap: 10px;
  justify-content: center;
  align-items: center;
  border-style: none;
  background-color: #111;
}

  .deck, .cards {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .home-menu-button,
  .menu-button,
  .submenu-button {
    width: 90%;
    margin: 5px 0;
  }

  #main {
    padding-top: 20px;
    width: 100%;
  }

  #header {
    position: static;
    height: auto;
    font-size: 1rem;
  }

  #footer {
    position: static;
    padding-top: 20px;
    text-align: center;
    font-size: 0.9rem;
  }
}

@keyframes borderPulse {
  0% {
    border-color: #2ce019;
  }
  25% {
    border-color: #00ffff;
  }
  50% {
    border-color: #2ce019;
  }
  75% {
    border-color: #00ffff;
  }
  100% {
    border-color: #2ce019;
  }
}

</style>