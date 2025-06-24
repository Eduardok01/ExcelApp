<template>
  <div class="formulario">
    <h1>Nueva Ficha de Viaje</h1>
    <form @submit.prevent="enviarFicha">

      <!-- Fechas -->
      <div class="fechas-flex">
        <div class="fecha-item">
          <label for="fechaIda">Fecha Ida:</label>
          <input id="fechaIda" v-model="ficha.fechaIda" type="date" required />
        </div>
        <div class="fecha-item">
          <label for="fechaVuelta">Fecha Vuelta:</label>
          <input id="fechaVuelta" v-model="ficha.fechaVuelta" type="date" />
        </div>
      </div>


      <!-- Conductor -->
      <fieldset>
        <legend>Conductor</legend>
        <div class="conductor-row">
          <div class="conductor-item">
            <label>Nombre:</label>
            <input v-model="ficha.conductor.nombre" type="text" placeholder="Nombre completo" required />
          </div>
          <div class="conductor-item">
            <label>RUT:</label>
            <input v-model="ficha.conductor.rut" type="text" placeholder="Ingrese rut del conductor" required />
          </div>
        </div>
      </fieldset>

      <!-- Camión -->
      <fieldset>
        <legend>Camión</legend>
        <div>
          <label>Patente:</label>
          <input v-model="ficha.camion.patente" type="text" placeholder="Ingrese Patente Vehículo" required />
        </div>
      </fieldset>

      <!-- Cargas Ida -->
      <fieldset>
        <legend>Cargas Ida</legend>
        <div
            v-for="(carga, index) in ficha.cargasIda"
            :key="'ida-' + index"
            class="item-array cargas-grid"
        >
          <label>Tipo de carga:</label>
          <input v-model="carga.tipoCarga" type="text" placeholder="¿Qué carga lleva?" required />
          <label>Kilos:</label>
          <input v-model.number="carga.kilos" type="number" min="0" step="0.01" @input="actualizarTotalCarga(carga)" required />
          <label>Precio por Kilo:</label>
          <input v-model.number="carga.precioPorKilo" type="number" min="0" step="0.01" @input="actualizarTotalCarga(carga)" required />
          <label>Total carga:</label>
          <input :value="carga.totalCarga.toFixed(2)" type="number" readonly />
          <button type="button" @click="quitarCarga('ida', index)">Eliminar</button>
        </div>
        <button type="button" @click="agregarCarga('ida')">Agregar carga ida</button>
      </fieldset>

      <!-- Cargas Vuelta -->
      <fieldset>
        <legend>Cargas Vuelta</legend>
        <div
            v-for="(carga, index) in ficha.cargasVuelta"
            :key="'vuelta-' + index"
            class="item-array cargas-grid"
        >
          <label>Tipo de carga:</label>
          <input v-model="carga.tipoCarga" placeholder="¿Qué carga trae?" type="text" />
          <label>Kilos:</label>
          <input v-model.number="carga.kilos" type="number" min="0" step="0.01" @input="actualizarTotalCarga(carga)" />
          <label>Precio por Kilo:</label>
          <input v-model.number="carga.precioPorKilo" type="number" min="0" step="0.01" @input="actualizarTotalCarga(carga)" />
          <label>Total carga:</label>
          <input :value="carga.totalCarga.toFixed(2)" type="number" readonly />
          <button type="button" @click="quitarCarga('vuelta', index)">Eliminar</button>
        </div>
        <button type="button" @click="agregarCarga('vuelta')">Agregar carga vuelta</button>
      </fieldset>

      <!-- Petróleo Ida -->
      <fieldset>
        <legend>Petróleo Ida</legend>
        <div class="flex-row">
          <div>
            <label>Litros:</label>
            <input v-model.number="ficha.petroleoIda.litros" type="number" min="0" step="0.01" @input="actualizarTotalPetroleo('ida')" />
          </div>
          <div>
            <label>Precio por litro:</label>
            <input v-model.number="ficha.petroleoIda.precioLitro" type="number" min="0" step="0.01" @input="actualizarTotalPetroleo('ida')" />
          </div>
          <div>
            <label>Total:</label>
            <input :value="ficha.petroleoIda.total.toFixed(2)" type="number" readonly />
          </div>
        </div>
      </fieldset>

      <!-- Petróleo Vuelta -->
      <fieldset>
        <legend>Petróleo Vuelta</legend>
        <div class="flex-row">
          <div>
            <label>Litros:</label>
            <input v-model.number="ficha.petroleoVuelta.litros" type="number" min="0" step="0.01" @input="actualizarTotalPetroleo('vuelta')" />
          </div>
          <div>
            <label>Precio por litro:</label>
            <input v-model.number="ficha.petroleoVuelta.precioLitro" type="number" min="0" step="0.01" @input="actualizarTotalPetroleo('vuelta')" />
          </div>
          <div>
            <label>Total:</label>
            <input :value="ficha.petroleoVuelta.total.toFixed(2)" type="number" readonly />
          </div>
        </div>
      </fieldset>

      <!-- Viáticos -->
      <fieldset>
        <legend>Viáticos</legend>
        <div
            v-for="(viatico, index) in ficha.viaticos"
            :key="index"
            class="item-array flex-row"
        >
          <div>
            <label>Monto viático:</label>
            <input v-model.number="viatico.monto" type="number" min="0" step="0.01" required />
          </div>
          <div>
            <label>Descripción:</label>
            <input v-model="viatico.descripcion" type="text" placeholder="¿Para qué es el viático?" required />
          </div>
          <button type="button" @click="quitarViatico(index)">Eliminar</button>
        </div>
        <button type="button" @click="agregarViatico">Agregar viático</button>
      </fieldset>

      <!-- Peajes -->
      <fieldset>
        <legend>Peajes</legend>
        <div
            v-for="(peaje, index) in ficha.peajes"
            :key="index"
            class="item-array flex-row"
        >
          <div>
            <label>Lugar Peaje:</label>
            <input v-model="peaje.nombre" placeholder="Ciudad del peaje" type="text" />
          </div>
          <div>
            <label>Costo:</label>
            <input v-model.number="peaje.costo" type="number" min="0" step="0.01" />
          </div>
          <button type="button" @click="quitarPeaje(index)">Eliminar</button>
        </div>
        <button type="button" @click="agregarPeaje">Agregar peaje</button>
      </fieldset>

      <!-- AdBlue -->
      <fieldset>
        <legend>AdBlue</legend>
        <div class="flex-row">
          <div>
            <label>Uso:</label>
            <input type="checkbox" v-model="ficha.adBlue.uso" />
          </div>
          <div>
            <label>Costo:</label>
            <input v-model.number="ficha.adBlue.costo" type="number" min="0" step="0.01" />
          </div>
        </div>
      </fieldset>

      <!-- Neumático -->
      <fieldset>
        <legend>Neumático</legend>
        <div class="flex-row">
          <div>
            <label>Cambio:</label>
            <input type="checkbox" v-model="ficha.neumatico.cambio" />
          </div>
          <div>
            <label>Detalle cambio:</label>
            <input v-model="ficha.neumatico.detalleCambio" placeholder="¿Qué neumático se cambió?" type="text" />
          </div>
          <div>
            <label>Costo:</label>
            <input v-model.number="ficha.neumatico.costo" type="number" min="0" step="0.01" />
          </div>
        </div>
      </fieldset>

      <!-- Totales -->
      <fieldset>
        <legend>Totales</legend>
        <div>
          <label>Ingresos Totales:</label>
          <input :value="ingresosTotales.toFixed(2)" type="number" readonly />
        </div>
        <div>
          <label>Ganancia Total:</label>
          <input :value="gananciaTotal.toFixed(2)" type="number" readonly />
        </div>
      </fieldset>

      <button type="submit">Enviar Ficha</button>
      <!-- Botón Guardar PDF -->
      <button type="button" @click="guardarPdf" style="margin-left: 10px;">Guardar PDF</button>
      <button type="button" @click="limpiarFicha" style="margin-left: 10px; background-color: #ef4444; color: white;">Limpiar Ficha</button>
      <button type="button" @click="volverMenu" style="margin-left: 10px; background-color: #03741b; color: white;">Volver al Menú</button>

    </form>



    <div v-if="mensaje" class="mensaje">{{ mensaje }}</div>
  </div>
</template>

<script setup>
import { reactive, ref, computed, watchEffect } from 'vue'
import { crearViaje } from '@/services/viajeService.js'
import axios from 'axios'

const fichaInicial = {
  fechaIda: '',
  fechaVuelta: '',
  conductor: { nombre: '', rut: '' },
  camion: { patente: '' },
  cargasIda: [],
  cargasVuelta: [],
  petroleoIda: { litros: 0, precioLitro: 0, total: 0 },
  petroleoVuelta: { litros: 0, precioLitro: 0, total: 0 },
  viaticos: [],
  peajes: [],
  adBlue: { uso: false, costo: 0 },
  neumatico: { cambio: false, detalleCambio: '', costo: 0 }
}


const ficha = reactive({
  fechaIda: '',
  fechaVuelta: '',
  conductor: { nombre: '', rut: '' },
  camion: { patente: '' },
  cargasIda: [],
  cargasVuelta: [],
  petroleoIda: { litros: 0, precioLitro: 0, total: 0 },
  petroleoVuelta: { litros: 0, precioLitro: 0, total: 0 },
  viaticos: [],
  peajes: [],
  adBlue: { uso: false, costo: 0 },
  neumatico: { cambio: false, detalleCambio: '', costo: 0 }
})

function limpiarFicha() {
  const copia = JSON.parse(JSON.stringify(fichaInicial))
  Object.keys(copia).forEach(key => {
    ficha[key] = copia[key]
  })
  mensaje.value = ''  // Opcional: limpia mensaje al limpiar ficha
}
import { useRouter } from 'vue-router'
const router = useRouter()

function volverMenu() {
  router.push('/menu')
}


const mensaje = ref('')

function agregarCarga(tipo) {
  const nuevaCarga = { tipoCarga: '', kilos: 0, precioPorKilo: 0, totalCarga: 0 }
  ficha[tipo === 'ida' ? 'cargasIda' : 'cargasVuelta'].push(nuevaCarga)
}

function quitarCarga(tipo, index) {
  ficha[tipo === 'ida' ? 'cargasIda' : 'cargasVuelta'].splice(index, 1)
}

function actualizarTotalCarga(carga) {
  carga.totalCarga = carga.kilos * carga.precioPorKilo
}

function actualizarTotalPetroleo(tipo) {
  ficha[`petroleo${capitalize(tipo)}`].total =
      ficha[`petroleo${capitalize(tipo)}`].litros *
      ficha[`petroleo${capitalize(tipo)}`].precioLitro
}

function capitalize(str) {
  return str.charAt(0).toUpperCase() + str.slice(1)
}

function agregarViatico() {
  ficha.viaticos.push({ monto: 0, descripcion: '' })
}

function quitarViatico(index) {
  ficha.viaticos.splice(index, 1)
}

function agregarPeaje() {
  ficha.peajes.push({ nombre: '', costo: 0 })
}

function quitarPeaje(index) {
  ficha.peajes.splice(index, 1)
}

const ingresosTotales = computed(() =>
    [...ficha.cargasIda, ...ficha.cargasVuelta].reduce((acc, c) => acc + c.totalCarga, 0)
)

const gananciaTotal = computed(() => {
  const gastos = [
    ...ficha.viaticos.map(v => v.monto),
    ...ficha.peajes.map(p => p.costo),
    ficha.petroleoIda.total,
    ficha.petroleoVuelta.total,
    ficha.adBlue.uso ? ficha.adBlue.costo : 0,
    ficha.neumatico.cambio ? ficha.neumatico.costo : 0
  ].reduce((acc, val) => acc + val, 0)

  return ingresosTotales.value - gastos
})

// Aquí la función para formatear fechas a "YYYY-MM-DD"
function formatFechaISO(fecha) {
  if (!fecha) return ''
  if (fecha instanceof Date) {
    const y = fecha.getFullYear()
    const m = String(fecha.getMonth() + 1).padStart(2, '0')
    const d = String(fecha.getDate()).padStart(2, '0')
    return `${y}-${m}-${d}`
  }
  if (typeof fecha === 'string') {
    // Extraemos solo la parte YYYY-MM-DD si viene con horas o zona
    return fecha.slice(0, 10)
  }
  return ''
}

// Este watchEffect se asegura que fechas siempre estén en formato correcto para el input date
watchEffect(() => {
  ficha.fechaIda = formatFechaISO(ficha.fechaIda)
  ficha.fechaVuelta = formatFechaISO(ficha.fechaVuelta)
})

async function enviarFicha() {
  try {
    await crearViaje(ficha) // Guarda el viaje en backend
    mensaje.value = 'Ficha enviada correctamente.'
  } catch (error) {
    mensaje.value = 'Error al enviar ficha: ' + (error.response?.data?.message || error.message)
  }
}

async function guardarPdf() {
  try {
    const response = await axios.post('http://localhost:8080/api/viajes/pdf', ficha, { responseType: 'blob' })
    const blob = new Blob([response.data], { type: 'application/pdf' })
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    const nombreConductor = ficha.conductor.nombre.trim().replace(/\s+/g, '_') || 'ficha_viaje';
    link.setAttribute('download', `${nombreConductor}.pdf`);

    //link.setAttribute('download', 'ficha_viaje.pdf')
    document.body.appendChild(link)
    link.click()
    link.remove()
    window.URL.revokeObjectURL(url)
  } catch (error) {
    mensaje.value = 'Error al generar PDF: ' + (error.response?.data?.message || error.message)
  }
}
</script>

<style scoped>
/* --- ESTILO GENERAL DE INPUTS --- */
input,
select,
textarea {
  background-color: white;
  color: black;
  border: 1px solid #ccc;
  padding: 6px 10px;
  border-radius: 4px;
  font-size: 1rem;
  box-sizing: border-box;
}

/* Icono calendario negro (WebKit: Chrome, Edge, Safari) */
input[type="date"]::-webkit-calendar-picker-indicator {
  filter: brightness(0);
  opacity: 1;
  cursor: pointer;
}

/* Firefox: fuerza modo claro */
input[type="date"] {
  color-scheme: light;
}

input:read-only {
  background-color: #f5f5f5;
}

/* --- FORMULARIO --- */
.formulario {
  max-width: 1000px;
  margin: auto;
  font-family: Georgia, sans-serif;
  background-color: white;
  color: black;
  padding: 20px;
  border-radius: 8px;
}

h1 {
  text-align: left;
  margin-bottom: 20px;
}

legend {
  color: #222;
}

button {
  margin-top: 5px;
  background-color: #2563eb;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #2563eb;
}

.mensaje {
  margin-top: 15px;
  font-weight: bold;
  color: green;
}

/* --- FILAS FLEXIBLES --- */
.flex-row {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  align-items: center;
}
.flex-row > div {
  display: flex;
  flex-direction: column;
}
.flex-row label {
  margin-bottom: 4px;
  font-weight: bold;
}

/* --- FECHAS --- */
.fecha-row {
  display: block;
  margin-bottom: 10px;
}

.fecha-row label {
  display: block;
  margin-bottom: 4px;
  font-weight: bold;
}

.fecha-row input[type="date"] {
  width: 160px;
  max-width: 160px;
  display: inline-block;
}

/* --- GRILLA DE CARGAS --- */
.cargas-grid {
  display: grid;
  grid-template-columns: 120px 200px 80px 1fr 1fr auto;
  gap: 10px;
  align-items: center;
}

.item-array {
  margin-bottom: 10px;
}

/* --- CONDUCTOR --- */
fieldset > div {
  display: flex;
  gap: 20px;
}

.conductor-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 8px;
  flex: 1;
}

.conductor-item label {
  min-width: 70px;
  font-weight: bold;
}

.conductor-item input {
  flex: 1;
  box-sizing: border-box;
}
</style>

<style>
body {
  background-color: white !important;
  margin: 0;
}

html {
  background-color: white !important;
}

.formulario fieldset > div,
.formulario > form > div {
  width: 100%;
}

.formulario > form > div {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 10px;
}

.formulario label {
  display: block;
  margin-bottom: 4px;
  font-weight: bold;
  text-align: left;
}

/* Achicar inputs de fecha */
.formulario > form > div > input[type="date"] {
  width: 160px !important;
  max-width: 160px !important;
  padding: 6px 10px;
  box-sizing: border-box;
}
</style>
