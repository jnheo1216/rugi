import { createApp } from 'vue'
import App from './App.vue'

import router from './router'
import store from './store'
import PrimeVue from 'primevue/config'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'

import 'primevue/resources/themes/saga-blue/theme.css'       //theme
import 'primevue/resources/primevue.min.css'       //core css
import 'primeicons/primeicons.css'       //icons

const app = createApp(App)
app.use(router).use(store).use(PrimeVue)

app.component('Button', Button)
app.component('InputText', InputText)

app.mount('#app')

// 원본
// createApp(App).use(store).use(router).use(PrimeVue).mount('#app')
// App.component('Button', Button)
