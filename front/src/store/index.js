import { createStore } from "vuex";
import axios from "axios";
import router from "@/router";
import createPersistedState from "vuex-persistedstate";

export default createStore({
  state: {
    id: "",
    avatar: 1,
    nickname: "nickname",
    userlist: [],
    roomId: "",
    stompClient: "",
    message: {}
  },
  mutations: {
    SET_USERDATA: function(state, data) {
      state.id = data.id;
      state.avatar = data.avatar;
      state.nickname = data.nickname;
    },
    SET_NICKNAME: function(state, data) {
      state.nickname = data;
    },
    SET_ROOMID: function(state, data) {
      state.roomId = data;
    },
    SET_AVATAR: function(state, data) {
      state.avatar = data;
    },
    SET_STOMP_CLIENT: function(state, data) {
      state.stompClient = data;
    },
    SET_MESSAGE: function(state, data) {
      state.message = data;
    },
    SET_USERLIST: function(state, data) {
      state.userlist = data
    },
  },
  actions: {
    createUser: function(context) {
      axios({
        method: "post",
        url: "/user",
        //기본 아바타, 닉네임 설정
        data: {
          avatar: context.state.avatar,
          nickname: context.state.nickname
        }
      })
        .then(res => {
          axios.defaults.headers.common["User-Id"] = res.data.data.id;
          context.commit("SET_USERDATA", res.data.data);
        })
        .catch(err => {
          console.log(err.response);
        });
    },
    createRoom: function(context) {
      axios({
        method: "post",
        url: "/room",
        data: {
          avatar: context.state.avatar,
          nickname: context.state.nickname
        }
      })
        .then(res => {
          context.commit("SET_ROOMID", res.data.data.id);
          console.log(res);
          router.push({
            name: "Game",
            query: { room: res.data.data.id }
          });
        })
        .catch(err => {
          console.log("failed");
          console.log(err.response);
        });
    },
    setNickName: function(context, data) {
      context.commit("SET_NICKNAME", data);
    },
    setRoomId: function(context, data) {
      context.commit("SET_ROOMID", data);
    },
    setAvatar: function(context, data) {
      context.commit("SET_AVATAR", data);
    },
    setStompClient: function(context, data) {
      context.commit("SET_STOMP_CLIENT", data);
    },
    setMessage: function(context, data) {
      context.commit("SET_MESSAGE", data);
    },
    setUserList: function(context, data) {
      context.commit("SET_USERLIST", data);
    }
  },
  modules: {},
  plugins: [createPersistedState()]
});
