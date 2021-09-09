import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Lobby from '../views/Lobby.vue'
import GameInit from '../views/GameInit.vue'
import GamePlay from '../views/GamePlay.vue'
import GameResult from '../views/GameResult.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/Lobby',
    name: 'Lobby',
    component: Lobby
  },
  {
    path: '/GameInit',
    name: 'GameInit',
    component: GameInit
  },
  {
    path: '/GamePlay',
    name: 'GamePlay',
    component: GamePlay
  },
  {
    path: '/GameResult',
    name: 'GameResult',
    component: GameResult
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
