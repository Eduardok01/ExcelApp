<template>
  <form class="formulario" @submit.prevent="guardarFicha">
    <h1>Ficha de Viaje</h1>

    <fieldset>
      <legend>Datos básicos</legend>
      <div class="form-group-inline">
        <div>
          <label for="fechaIda">Fecha Ida</label>
          <input type="date" id="fechaIda" v-model="ficha.fechaIda" required />
        </div>
        <div>
          <label for="fechaVuelta">Fecha Vuelta</label>
          <input type="date" id="fechaVuelta" v-model="ficha.fechaVuelta" required />
        </div>
      </div>
    </fieldset>

    <fieldset>
      <legend>Conductor</legend>
      <div class="form-group-inline">
        <div>
          <label for="conductor">Nombre Conductor</label>
          <input type="text" id="conductor" v-model="ficha.conductor" required />
        </div>
        <div>
          <label for="patente">Patente Camión</label>
          <input type="text" id="patente" v-model="ficha.patente" required />
        </div>
      </div>
    </fieldset>

    <fieldset>
      <legend>Cargas Ida</legend>
      <div v-for="(carga, idx) in ficha.cargasIda" :key="'cargaIda' + idx" class="item-array">
        <input type="text" placeholder="Descripción" v-model="carga.descripcion" required />
        <input type="number" placeholder="Peso (kg)" v-model.number="carga.peso" min="0" required />
        <button type="button" class="btn btn-danger" @click="quitarCargaIda(idx)">Quitar</button>
      </div>
      <button type="button" class="btn btn-primary" @click="agregarCargaIda">Agregar Carga Ida</button>
    </fieldset>

    <fieldset>
      <legend>Cargas Vuelta</legend>
      <div v-for="(carga, idx) in ficha.cargasVuelta" :key="'cargaVuelta' + idx" class="item-array">
        <input type="text" placeholder="Descripción" v-model="carga.descripcion" required />
        <input type="number" placeholder="Peso (kg)" v-model.number="carga.peso" min="0" required />
        <button type="button" class="btn btn-danger" @click="quitarCargaVuelta(idx)">Quitar</button>
      </div>
      <button type="button" class="btn btn-primary" @click="agregarCargaVuelta">Agregar Carga Vuelta</button>
    </fieldset>

    <fieldset>
      <legend>Petróleo</legend>
      <div class="form-group-inline" style="gap: 2rem;">
        <div>
          <label for="petroleoIda">Ida (litros)</label>
          <input type="number" id="petroleoIda" v-model.number="ficha.petroleo.ida" min="0" />
        </div>
        <div>
          <label for="petroleoVuelta">Vuelta (litros)</label>
          <input type="number" id="petroleoVuelta" v-model.number="ficha.petroleo.vuelta" min="0" />
        </div>
      </div>
    </fieldset>

    <fieldset>
      <legend>Viáticos</legend>
      <div v-for="(viatico, idx) in ficha.viaticos" :key="'viatico' + idx" class="item-array">
        <input type="text" placeholder="Descripción" v-model="viatico.descripcion" required />
        <input type="number" placeholder="Monto" v-model.number="viatico.monto" min="0" step="0.01" required />
        <button type="button" class="btn btn-danger" @click="quitarViatico(idx)">Quitar</button>
      </div>
      <button type="button" class="btn btn-primary" @click="agregarViatico">Agregar Viático</button>
    </fieldset>

    <fieldset>
      <legend>Peajes</legend>
      <div v-for="(peaje, idx) in ficha.peajes" :key="'peaje' + idx" class="item-array">
        <input type="text" placeholder="Descripción" v-model="peaje.descripcion" required />
        <input type="number" placeholder="Monto" v-model.number="peaje.monto" min="0" step="0.01" required />
        <button type="button" class="btn btn-danger" @click="quitarPeaje(idx)">Quitar</button>
      </div>
      <button type="button" class="btn btn-primary" @click="agregarPeaje">Agregar Peaje</button>
    </fieldset>

    <fieldset>
      <legend>AdBlue</legend>
      <div class="form-group-inline" style="align-items: center;">
        <label>
          <input type="checkbox" v-model="ficha.adBlue.uso" />
          Uso
        </label>
        <input
          type="number"
          placeholder="Costo"
          v-model.number="ficha.adBlue.costo"
          min="0"
          step="0.01"
          style="margin-left: 1rem; flex: 1;"
        />
      </div>
    </fieldset>

    <fieldset>
      <legend>Neumáticos</legend>
      <div v-for="(neumatico, idx) in ficha.neumaticos" :key="'neumatico' + idx" class="item-array">
        <label>
          <input type="checkbox" v-model="neumatico.cambio" />
          Cambio
        </label>
        <input type="text" placeholder="Detalle" v-model="neumatico.detalle" required />
        <input type="number" placeholder="Costo" v-model.number="neumatico.costo" min="0" step="0.01" required />
        <button type="button" class="btn btn-danger" @click="quitarNeumatico(idx)">Quitar</button>
      </div>
      <button type="button" class="btn btn-primary" @click="agregarNeumatico">Agregar Neumático</button>
    </fieldset>

    <fieldset>
      <legend>Kilometraje</legend>
      <div class="form-group-inline">
        <div>
          <label for="kilometrajeIda">Ida (km)</label>
          <input type="number" id="kilometrajeIda" v-model.number="ficha.kilometraje.ida" min="0" />
        </div>
        <div>
          <label for="kilometrajeVuelta">Vuelta (km)</label>
          <input type="number" id="kilometrajeVuelta" v-model.number="ficha.kilometraje.vuelta" min="0" />
        </div>
      </div>
    </fieldset>

    <fieldset>
      <legend>Totales</legend>
      <div class="form-group-inline">
        <div>
          <label for="ingresos">Ingresos</label>
          <input type="number" id="ingresos" v-model.number="ficha.totales.ingresos" min="0" step="0.01" />
        </div>
        <div>
          <label for="ganancias">Ganancias</label>
          <input type="number" id="ganancias" v-model.number="ficha.totales.ganancias" min="0" step="0.01" />
        </div>
      </div>
    </fieldset>

    <button type="submit" class="btn btn-submit">Guardar Ficha</button>
  </form>
</template>

<script>
export default {
  data() {
    return {
      ficha: {
        fechaIda: "",
        fechaVuelta: "",
        conductor: "",
        patente: "",
        cargasIda: [],
        cargasVuelta: [],
        petroleo: { ida: 0, vuelta: 0 },
        viaticos: [],
        peajes: [],
        adBlue: {
          uso: false,
          costo: 0,
        },
        neumaticos: [],
        kilometraje: { ida: 0, vuelta: 0 },
        totales: { ingresos: 0, ganancias: 0 },
      },
    };
  },
  methods: {
    agregarCargaIda() {
      this.ficha.cargasIda.push({ descripcion: "", peso: 0 });
    },
    quitarCargaIda(idx) {
      this.ficha.cargasIda.splice(idx, 1);
    },
    agregarCargaVuelta() {
      this.ficha.cargasVuelta.push({ descripcion: "", peso: 0 });
    },
    quitarCargaVuelta(idx) {
      this.ficha.cargasVuelta.splice(idx, 1);
    },
    agregarViatico() {
      this.ficha.viaticos.push({ descripcion: "", monto: 0 });
    },
    quitarViatico(idx) {
      this.ficha.viaticos.splice(idx, 1);
    },
    agregarPeaje() {
      this.ficha.peajes.push({ descripcion: "", monto: 0 });
    },
    quitarPeaje(idx) {
      this.ficha.peajes.splice(idx, 1);
    },
    agregarNeumatico() {
      this.ficha.neumaticos.push({ cambio: false, detalle: "", costo: 0 });
    },
    quitarNeumatico(idx) {
      this.ficha.neumaticos.splice(idx, 1);
    },
    guardarFicha() {
      console.log("Ficha guardada:", this.ficha);
      alert("Ficha guardada con éxito.");
    },
  },
};
</script>

<style scoped>
.formulario {
  max-width: 900px;
  margin: 1rem auto;
  padding: 1rem 2rem;
  background-color: #fefefe;
  border-radius: 8px;
  box-shadow: 0 0 8px rgb(0 0 0 / 0.1);
}

h1 {
  text-align: center;
  color: #3178c6;
  margin-bottom: 1rem;
}

fieldset {
  margin-bottom: 1.5rem;
  border: 1px solid #aaccee;
  border-radius: 5px;
  padding: 1rem 1.5rem;
}

legend {
  font-weight: bold;
  color: #2176bd;
  padding: 0 0.5rem;
}

.form-group-inline {
  display: flex;
  gap: 1.5rem;
  flex-wrap: wrap;
}

.item-array {
  display: flex;
  gap: 0.75rem;
  margin-bottom: 0.5rem;
  align-items: center;
}

input[type="text"],
input[type="number"],
input[type="date"] {
  padding: 0.3rem 0.5rem;
  border: 1px solid #ccc;
  border-radius: 3px;
  font-size: 0.9rem;
  min-width: 120px;
}

.btn {
  padding: 0.4rem 0.8rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
}

.btn-primary {
  background-color: #2d89ef;
  color: white;
}

.btn-danger {
  background-color: #d9534f;
  color: white;
}

.btn-submit {
  background-color: #2176bd;
  color: white;
  display: block;
  width: 100%;
  font-size: 1.1rem;
  margin-top: 1rem;
  padding: 0.75rem;
  border-radius: 6px;
}
</style>
