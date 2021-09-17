import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Lobby from "../views/Lobby.vue";
import GameInit from "../views/GameInit.vue";
import GamePlay from "../views/GamePlay.vue";
import GameResult from "../views/GameResult.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: '/:room_id',
    name: 'Lobby',
    component: Lobby
  },
  {
    path: '/:room_id/init',
    name: 'GameInit',
    component: GameInit
  },
  {
    path: '/:room_id/play',
    name: 'GamePlay',
    component: GamePlay
  },
  {
    path: '/:room_id/result',
    name: 'GameResult',
    component: GameResult
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
