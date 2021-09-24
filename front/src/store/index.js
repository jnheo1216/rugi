import { createStore } from 'vuex'

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
    }
  },
  actions: {
    setUserData: function(context, data) {
      context.commit('SET_USERDATA', data)
    }
  },
  modules: {
  }
})
