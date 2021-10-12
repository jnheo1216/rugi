import { createApp } from 'vue'
import App from './App.vue'

import router from './router'
import store from './store'
import PrimeVue from 'primevue/config'
//primevud에서 필요한 component를 import
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import Avatar from 'primevue/avatar'
import AvatarGroup from 'primevue/avatargroup'
import ScrollTop from 'primevue/scrolltop'
import ScrollPanel from 'primevue/scrollpanel'
import ColorPicker from 'primevue/colorpicker'
import Carousel from 'primevue/carousel';

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'primevue/resources/themes/saga-blue/theme.css'       //theme
// import 'primevue/resources/primevue.min.css'       //core css
import 'primeicons/primeicons.css'       //icons
import 'primeflex/primeflex.css'
const app = createApp(App)
app.use(router).use(store).use(PrimeVue)

// 가져온것들은 컴포넌트로 달아준다
app.component('Button', Button)
app.component('InputText', InputText)
app.component('Avatar', Avatar)
app.component('AvatarGroup', AvatarGroup)
app.component('ScrollTop', ScrollTop)
app.component('ScrollPanel', ScrollPanel)
app.component('ColorPicker', ColorPicker)
app.component('Carousel', Carousel)

app.mount('#app')

// 원본
// createApp(App).use(store).use(router).use(PrimeVue).mount('#app')
// App.component('Button', Button)
