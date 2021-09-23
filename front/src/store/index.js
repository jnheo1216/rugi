import { createStore } from 'vuex'

export default createStore({
  state: {
    id: undefined,
    avatar: undefined,
    nickname: undefined
  },
  mutations: {
    SET_USERDATA: function (state, data) {
      console.log('here', data)
      state.id = data.id
      console.log('hi', state.id)
      // status.avatar = data.avatar
      // status.nickname = data.nickname
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
