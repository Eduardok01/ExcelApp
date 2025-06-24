import { createApp } from 'vue'
import App from './App.vue'
import router from './router'  // importa el router configurado

const app = createApp(App)

app.use(router)  // usa el router en la app

app.mount('#app')  // monta la app en el div con id="app"
