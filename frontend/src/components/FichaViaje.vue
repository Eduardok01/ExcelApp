<template>
  <div class="formulario">
    <h1>Ficha de Viaje</h1>
    <form @submit.prevent="enviarFicha">
      <!-- Fechas -->
      <div>
        <label>Fecha Ida:</label>
        <input v-model="ficha.fechaIda" type="date" required />
      </div>
      <div>
        <label>Fecha Vuelta:</label>
        <input v-model="ficha.fechaVuelta" type="date" />
      </div>

      <!-- Conductor -->
      <fieldset>
        <legend>Conductor</legend>
        <div>
          <label>Nombre:</label>
          <input v-model="ficha.conductor.nombre" type="text" required />
        </div>
        <div>
          <label>RUT:</label>
          <input v-model="ficha.conductor.rut" type="text" required />
        </div>
      </fieldset>

      <!-- Camión -->
      <fieldset>
        <legend>Camión</legend>
        <div>
          <label>Patente:</label>
          <input v-model="ficha.camion.patente" type="text" required />
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
          <input v-model="carga.tipoCarga" type="text" required />
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
          <input v-model="carga.tipoCarga" type="text" />
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
        <div>
          <label>Litros:</label>
          <input v-model.number="ficha.petroleoIda.litros" type="number" min="0" step="0.01" @input="actualizarTotalPetroleo('ida')" />
          <label>Precio por litro:</label>
          <input v-model.number="ficha.petroleoIda.precioLitro" type="number" min="0" step="0.01" @input="actualizarTotalPetroleo('ida')" />
          <label>Total:</label>
          <input :value="ficha.petroleoIda.total.toFixed(2)" type="number" readonly />
        </div>
      </fieldset>

      <!-- Petróleo Vuelta -->
      <fieldset>
        <legend>Petróleo Vuelta</legend>
        <div>
          <label>Litros:</label>
          <input v-model.number="ficha.petroleoVuelta.litros" type="number" min="0" step="0.01" @input="actualizarTotalPetroleo('vuelta')" />
          <label>Precio por litro:</label>
          <input v-model.number="ficha.petroleoVuelta.precioLitro" type="number" min="0" step="0.01" @input="actualizarTotalPetroleo('vuelta')" />
          <label>Total:</label>
          <input :value="ficha.petroleoVuelta.total.toFixed(2)" type="number" readonly />
        </div>
      </fieldset>

      <!-- Viáticos -->
      <fieldset>
        <legend>Viáticos</legend>
        <div v-for="(viatico, index) in ficha.viaticos" :key="index" class="item-array">
          <label>Monto:</label>
          <input v-model.number="viatico.monto" type="number" min="0" step="0.01" required />
          <label>Descripción:</label>
          <input v-model="viatico.descripcion" type="text" required />
          <button type="button" @click="quitarViatico(index)">Eliminar</button>
        </div>
        <button type="button" @click="agregarViatico">Agregar viático</button>
      </fieldset>

      <!-- Peajes -->
      <fieldset>
        <legend>Peajes</legend>
        <div v-for="(peaje, index) in ficha.peajes" :key="index" class="item-array">
          <label>Nombre:</label>
          <input v-model="peaje.nombre" type="text" />
          <label>Costo:</label>
          <input v-model.number="peaje.costo" type="number" min="0" step="0.01" />
          <button type="button" @click="quitarPeaje(index)">Eliminar</button>
        </div>
        <button type="button" @click="agregarPeaje">Agregar peaje</button>
      </fieldset>

      <!-- AdBlue -->
      <fieldset>
        <legend>AdBlue</legend>
        <div>
          <label>Uso:</label>
          <input type="checkbox" v-model="ficha.adBlue.uso" />
          <label>Costo:</label>
          <input v-model.number="ficha.adBlue.costo" type="number" min="0" step="0.01" />
        </div>
      </fieldset>

      <!-- Neumático -->
      <fieldset>
        <legend>Neumático</legend>
        <div>
          <label>Cambio:</label>
          <input type="checkbox" v-model="ficha.neumatico.cambio" />
          <label>Detalle cambio:</label>
          <input v-model="ficha.neumatico.detalleCambio" type="text" />
          <label>Costo:</label>
          <input v-model.number="ficha.neumatico.costo" type="number" min="0" step="0.01" />
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
    </form>

    <div v-if="mensaje" class="mensaje">{{ mensaje }}</div>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { crearViaje, descargarPdf } from '@/services/viajeService.js'
import axios from 'axios'

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

async function enviarFicha() {
  try {
    const creado = await crearViaje(ficha) // Guarda el viaje en backend
    mensaje.value = 'Ficha enviada correctamente.'
    /* await descargarPdf*/          // Descarga PDF usando el ID retornado
  } catch (error) {
    mensaje.value =
        'Error al enviar ficha: ' + (error.response?.data?.message || error.message)
  }
}


// Nueva función para descargar el PDF
async function guardarPdf() {
  try {
    // Aquí asumo que la API backend recibe el objeto ficha y devuelve el PDF
    const response = await axios.post('http://localhost:8080/api/viajes/pdf', ficha, { responseType: 'blob' })


    const blob = new Blob([response.data], { type: 'application/pdf' })
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', 'ficha_viaje.pdf')
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
.formulario {
  max-width: 800px;
  margin: auto;
  font-family: Arial, sans-serif;
  background-color: white; /* Fondo blanco */
  color: black; /* Texto negro por defecto */
  padding: 20px;
  border-radius: 8px;
}

h1, legend {
  color: #0074B8; /* Azul del encabezado */
}

fieldset {
  margin-bottom: 20px;
  padding: 10px;
  border: 2px solid #0074B8; /* Bordes con azul personalizado */
  border-radius: 5px;
}

.cargas-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr) auto;
  gap: 10px;
  align-items: center;
}

.item-array {
  margin-bottom: 10px;
}

button {
  margin-top: 5px;
  background-color: #0074B8;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #005f95;
}

.mensaje {
  margin-top: 15px;
  font-weight: bold;
  color: green;
}
</style>
