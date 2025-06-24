<template>
  <div v-if="cargando">Cargando ficha...</div>
  <div v-else-if="error">{{ error }}</div>
  <div v-else class="detalle-ficha">
    <h2>Detalle Ficha de Viaje</h2>
    <form>
      <label>Nombre Conductor:</label>
      <input type="text" v-model="ficha.conductor.nombre" disabled />

      <label>RUT Conductor:</label>
      <input type="text" v-model="ficha.conductor.rut" disabled />

      <label>Fecha Ida:</label>
      <input type="date" v-model="ficha.fechaIda" disabled />

      <label>Fecha Vuelta:</label>
      <input type="date" v-model="ficha.fechaVuelta" disabled />

      <!-- Aquí puedes agregar más campos para mostrar todos los datos que necesites -->

      <button @click.prevent="volverALaLista">Volver a la lista de fichas</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'DetalleFicha',
  data() {
    return {
      ficha: null,
      cargando: true,
      error: null
    }
  },
  async mounted() {
    const id = this.$route.params.id
    try {
      const response = await axios.get(`http://localhost:8080/api/viajes/${id}`)
      this.ficha = response.data
    } catch (e) {
      this.error = 'No se pudo cargar la ficha.'
    } finally {
      this.cargando = false
    }
  },
  methods: {
    volverALaLista() {
      this.$router.push('/fichasCreadas')
    }
  }
}
</script>

<style scoped>
.detalle-ficha {
  max-width: 700px;
  margin: 1rem auto;
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0,0,0,0.1);
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}

label {
  display: block;
  margin-top: 1rem;
  font-weight: 600;
}

input {
  width: 100%;
  padding: 0.4rem 0.6rem;
  font-size: 1rem;
  border-radius: 6px;
  border: 1px solid #ccc;
  background-color: #f8f8f8;
  color: #333;
}

button {
  margin-top: 2rem;
  padding: 0.6rem 1.2rem;
  background-color: #3b82f6;
  color: white;
  font-weight: 600;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #2563eb;
}
</style>
