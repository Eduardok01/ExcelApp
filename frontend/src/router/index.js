import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/components/LoginView.vue'
import MenuView from '@/components/MenuView.vue'
import FichaViaje from "@/components/FichaViaje.vue";  // tu vista de creación ficha
import FichasCreadas from '@/components/FichasCreadas.vue'
import DetalleFicha from "@/components/DetalleFicha.vue"; // o donde lo guardes

const routes = [
    { path: '/', name: 'Login', component: LoginView },
    { path: '/menu', name: 'Menu', component: MenuView },
    { path: '/fichaNueva', name: 'FichaNueva', component: FichaViaje },
    { path: '/fichasCreadas', name: 'FichasCreadas', component: FichasCreadas },
    { path: '/detalleFicha/:id', name: 'DetalleFicha', component: DetalleFicha },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
