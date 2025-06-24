<template>
  <div class="lista-fichas">
    <h1>Fichas Creadas</h1>
    <button @click="volverMenu">Volver al Menú</button>

    <div v-if="loading">Cargando fichas...</div>
    <div v-else>
      <ul>
        <li v-for="ficha in fichas" :key="ficha.id">
          <!-- Aquí muestra los datos que quieras, ejemplo: -->
          <strong>{{ ficha.conductor.nombre }}</strong> - Fecha Ida: {{ ficha.fechaIda }}
          <button @click="verFicha(ficha.id)">Ver Detalle</button>
        </li>
      </ul>

      <div v-if="fichas.length === 0">No hay fichas creadas aún.</div>
    </div>

    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const fichas = ref([])
const loading = ref(true)
const error = ref('')
const router = useRouter()

// Función para traer las fichas desde backend
async function cargarFichas() {
  loading.value = true
  error.value = ''
  try {
    const response = await axios.get('http://localhost:8080/api/viajes') // Ajusta URL según tu backend
    fichas.value = response.data
  } catch (err) {
    error.value = 'Error al cargar fichas: ' + (err.response?.data?.message || err.message)
  } finally {
    loading.value = false
  }
}

// Al montar el componente, carga las fichas
onMounted(() => {
  cargarFichas()
})

// Navegar a detalles o edición (a crear después)
function verFicha(id) {
  router.push(`/fichaDetalle/${id}`)
}

// Volver al menú principal
function volverMenu() {
  router.push('/menu')
}
</script>

<style scoped>
.lista-fichas {
  max-width: 700px;
  margin: auto;
  padding: 1rem;
  font-family: Arial, sans-serif;
}

button {
  margin-left: 1rem;
  cursor: pointer;
}

.error {
  color: red;
  font-weight: bold;
}
</style>
