import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  state: {
    id: '',
    avatar: '',
    nickname: 'nickname',
    roomId: ''
  },
  mutations: {
    SET_USERDATA: function (state, data) {
      state.id = data.id
      state.avatar = data.avatar
      state.nickname = data.nickname
    },
    SET_NICKNAME: function (state, data) {
      state.nickname = data
    },
    SET_ROOMID: function (state, data) {
      state.roomId = data
    },
    SET_AVATAR: function (state, data) {
      state.avatar = data
    },
  },
  actions: {
    setUserData: function(context, data) {
      context.commit('SET_USERDATA', data)
    },
    setNickName: function(context, data) {
      context.commit('SET_NICKNAME', data)
    },
    setRoomId: function(context, data) {
      context.commit('SET_ROOMID', data)
    },
    setAvatar: function(context, data) {
      context.commit('SET_AVATAR', data)
    }
  },
  modules: {
  },
  plugins: [createPersistedState()]
})
