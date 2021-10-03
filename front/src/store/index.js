import { createStore } from "vuex";
import axios from "axios";
import router from "@/router";
import createPersistedState from "vuex-persistedstate";

export default createStore({
  state: {
    id: "",
    avatar: 0,
    nickname: "nickname",
    super: false,
    room: {},
    stompClient: ""
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
    SET_ROOM: function(state, data) {
      state.room = data;
    },
    SET_AVATAR: function(state, data) {
      state.avatar = data;
    },
    SET_STOMP_CLIENT: function(state, data) {
      state.stompClient = data;
    },
    SET_SUPER: function(state, data) {
      state.super = data;
    }
  },
  actions: {
    createUser: function(context) {
      axios({
        method: "post",
        url: "/user",
        //기본 아바타, 닉네임 설정
        data: {
          id: context.state.id ? context.state.id : null,
          avatar: context.state.avatar,
          nickname: context.state.nickname
        }
      })
        .then(res => {
          axios.defaults.headers.common["User-Id"] = res.data.data.id;
          console.log(axios.defaults.headers.common["User-Id"]);
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
          // room 정보는 여기서 저장하지 않는다. 실제 입장해서 받아오는 방 객체로 방을 저장한다
          router.push({ name: "Game", query: { room: res.data.data["id"] } });
        })
        .catch(err => {
          console.log("failed");
          console.log(err.response);
        });
    },
    // unSub: function() {
    //   console.log('유저 퇴장')
    //   axios({
    //     method: 'delete',
    //     url: '/room/user',
    //     params: {
    //       roomId: this.state.roomId
    //     }
    //   }).then((res) => {
    //     console.log(res.data)
    //   }).catch((err) => {
    //     console.log(err.response)
    //   })
    // },

    setNickName: function(context, data) {
      context.commit("SET_NICKNAME", data);
    },
    setAvatar: function(context, data) {
      context.commit("SET_AVATAR", data);
    },
    setStompClient: function(context, data) {
      context.commit("SET_STOMP_CLIENT", data);
    },
    setRoom: function(context, data) {
      context.commit("SET_ROOM", data);
    },
    setSuper: function(context, data) {
      context.commit("SET_SUPER", data === context.state.id);
    }
  },
  getters: {
    isRoomExist: function(state) {
      return Object.keys(state.room).length;
    }
  },
  modules: {},
  plugins: [createPersistedState()]
});
