import { createRouter as _createRouter, createWebHistory } from 'vue-router';
import MainView from '../views/MainView.vue';
import DeckView from '../views/DeckView.vue';
import CardSearchView from '../views/CardSearchView.vue';
import CreateView from '../views/CreateView.vue';
import LoginView from '../views/LoginView.vue';
import DeckDetail from '../views/DeckDetail.vue';

const routes = [
    {
      path: "/",
      name: "MainView",
      component: MainView
    },
    {
      path: "/users/:username/decks",
      name: "DeckView",
      component: DeckView
    },
    {
      path: '/users/:username/decks/:id',
      name: 'DeckDetail',
      component: DeckDetail
    },
    {
        path: "/newdeck",
        name: "CreateView",
        component: CreateView
    },
    {
        path: "/login",
        name: "LoginView",
        component: LoginView
    },
    {
        path: "/Cardsearch",
        name: "CardSearchView",
        component: CardSearchView
    },
  ];


export function createRouter () {
    return _createRouter({
      history: createWebHistory(),
      routes: routes
    })
  }