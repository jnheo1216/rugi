import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  state: {
    id: '',
    avatar: 1,
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
    }
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
    }
  },
  modules: {
  },
  plugins: [createPersistedState()]
})
