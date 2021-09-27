import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  state: {
    id: '',
    avatar: 'avatar/1',
    nickname: 'nickname'
  },
  mutations: {
    SET_USERDATA: function (state, data) {
      state.id = data.id
      state.avatar = data.avatar
      state.nickname = data.nickname
    },
    SET_NICKNAME: function (state, data) {
      state.nickname = data
    }
  },
  actions: {
    setUserData: function(context, data) {
      context.commit('SET_USERDATA', data)
    },
    setNickName: function(context, data) {
      context.commit('SET_NICKNAME', data)
    }
  },
  modules: {
  },
  plugins: [createPersistedState()]
})
