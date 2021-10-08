import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
// 수정해야함 router에 있으면 안댐
import Lobby from "@/components/game/Lobby.vue";
import Game from "@/views/Game.vue"
// import GameInit from "../views/GameInit.vue";
// import GamePlay from "../views/GamePlay.vue";
import GamePlay from "@/components/game/GamePlay.vue"

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/game",
    name: "Game",
    component: Game
  },
  {
    path: "/lobby",
    name: "Lobby",
    component: Lobby
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: { name: "Home"}
  },
  {
    path: '/gameplay',
    name: 'GamePlay',
    component: GamePlay
  },
  // {
  //   path: '/:room_id/init',
  //   name: 'GameInit',
  //   component: GameInit
  // },
  // {
  //   path: '/:room_id/play',
  //   name: 'GamePlay',
  //   component: GamePlay
  // },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
