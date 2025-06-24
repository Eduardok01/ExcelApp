<template>
  <div class="fichas-creadas">
    <div class="contenedor">
      <h1>Fichas de Viaje Guardadas</h1>

      <div v-if="fichas.length === 0" class="mensaje-vacio">
        No hay fichas registradas aún.
      </div>

      <div v-else class="tabla">
        <table>
          <thead>
          <tr>
            <th>ID</th>
            <th>Conductor</th>
            <th>Patente</th>
            <th>Fecha Ida</th>
            <th>Fecha Vuelta</th>
            <th>Acciones</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="ficha in fichas" :key="ficha.id">
            <td>{{ ficha.id }}</td>
            <td>{{ ficha.conductor.nombre }}</td>
            <td>{{ ficha.camion.patente }}</td>
            <td>{{ ficha.fechaIda }}</td>
            <td>{{ ficha.fechaVuelta }}</td>
            <td>
              <button class="btn" @click="verDetalles(ficha.id)">Detalles</button>
              <button class="btn" @click="descargarPdf(ficha.id)">PDF</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <button class="btn volver" @click="volverMenu">Volver al Menú</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'FichasCreadasView',
  data() {
    return {
      fichas: []
    }
  },
  created() {
    this.obtenerFichas()
  },
  methods: {
    async obtenerFichas() {
      try {
        const res = await axios.get('http://localhost:8080/api/viajes')
        this.fichas = res.data
      } catch (err) {
        console.error('Error al obtener fichas:', err)
      }
    },
    verDetalles(id) {
      this.$router.push(`/detalleFicha/${id}`)
    },
    async descargarPdf(id) {
      try {
        // 1. Obtener los datos del viaje por ID
        const { data: ficha } = await axios.get(`http://localhost:8080/api/viajes/${id}`)

        // 2. Generar el PDF con esos datos
        const response = await axios.post('http://localhost:8080/api/viajes/pdf', ficha, {
          responseType: 'blob'
        })

        // 3. Preparar el nombre del archivo con el nombre del conductor
        const blob = new Blob([response.data], { type: 'application/pdf' })
        const url = window.URL.createObjectURL(blob)
        const link = document.createElement('a')
        const nombreConductor = ficha.conductor.nombre.trim().replace(/\s+/g, '_') || 'ficha_viaje'
        link.href = url
        link.setAttribute('download', `${nombreConductor}.pdf`)
        document.body.appendChild(link)
        link.click()
        link.remove()
        window.URL.revokeObjectURL(url)
      } catch (error) {
        console.error('Error al generar PDF con nombre del conductor:', error)
      }
    }
    ,

    volverMenu() {
      this.$router.push('/menu')
    }
  }
}
</script>

<style scoped>
.fichas-creadas {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #89f7fe 0%, #66a6ff 100%);
  font-family: 'Segoe UI', sans-serif;
  padding: 2rem;
}

.contenedor {
  background: white;
  padding: 3rem;
  border-radius: 20px;
  border: 1px solid #000;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.12);
  width: 100%;
  max-width: 900px;
}

h1 {
  font-size: 2rem;
  text-align: center;
  margin-bottom: 1.5rem;
}

.mensaje-vacio {
  text-align: center;
  color: #555;
  font-size: 1.2rem;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 2rem;
}

th, td {
  border: 1px solid #ccc;
  padding: 0.75rem;
  text-align: center;
}

th {
  background-color: #f0f4f8;
  font-weight: bold;
}

.btn {
  margin: 0 0.3rem;
  background-color: #3b82f6;
  color: white;
  padding: 0.5rem 0.8rem;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
}

.btn:hover {
  background-color: #2563eb;
}

.volver {
  margin-top: 1rem;
  background-color: #ef4444;
}

.volver:hover {
  background-color: #b91c1c;
}
</style>
