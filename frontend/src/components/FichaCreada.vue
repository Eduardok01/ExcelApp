<template>
  <div class="fichas-background">
    <div class="fichas-container" role="main" aria-label="Listado de fichas creadas">
      <h1>Fichas Creadas</h1>
      <p class="subtitulo">Filtra y selecciona una ficha para ver detalles</p>

      <div class="filtros">
        <input type="text" placeholder="Buscar por título..." v-model="busqueda" aria-label="Buscar por título" />
        <input type="date" v-model="fechaFiltro" aria-label="Filtrar por fecha de creación" />
        <input type="text" placeholder="Filtrar por autor..." v-model="autorFiltro" aria-label="Filtrar por autor" />
      </div>

      <ul class="lista-fichas" role="list">
        <li
            v-for="ficha in fichasFiltradas"
            :key="ficha.id"
            @click="verFicha(ficha.id)"
            tabindex="0"
            class="ficha-item"
            role="listitem"
            @keyup.enter="verFicha(ficha.id)"
        >
          <span class="titulo">{{ ficha.titulo }}</span>
          <span class="info">{{ ficha.fechaCreacion }} — {{ ficha.autor }}</span>
        </li>
      </ul>

      <button @click="volverMenu" class="btn btn-salir" aria-label="Volver al menú principal">
        ← Volver
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FichasCreadasView',
  data() {
    return {
      fichas: [
        { id: 1, titulo: 'Ficha 001', fechaCreacion: '2025-06-01', autor: 'Juan Pérez' },
        { id: 2, titulo: 'Ficha 002', fechaCreacion: '2025-06-05', autor: 'María Gómez' },
        { id: 3, titulo: 'Ficha 003', fechaCreacion: '2025-06-09', autor: 'Carlos Soto' }
      ],
      busqueda: '',
      fechaFiltro: '',
      autorFiltro: ''
    };
  },
  computed: {
    fichasFiltradas() {
      return this.fichas.filter(ficha => {
        const tituloMatch = ficha.titulo.toLowerCase().includes(this.busqueda.toLowerCase());
        const fechaMatch = this.fechaFiltro ? ficha.fechaCreacion === this.fechaFiltro : true;
        const autorMatch = ficha.autor.toLowerCase().includes(this.autorFiltro.toLowerCase());
        return tituloMatch && fechaMatch && autorMatch;
      });
    }
  },
  methods: {
    verFicha(id) {
      this.$router.push(`/detalle/${id}`);
    },
    volverMenu() {
      this.$router.push('/menu');
    }
  }
};
</script>

<style scoped>
.fichas-background {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 2rem 1rem 4rem;
  background: linear-gradient(135deg, #89f7fe 0%, #66a6ff 100%);
}

.fichas-container {
  background: white;
  padding: 2.5rem 3rem;
  border-radius: 20px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.12);
  width: 380px;
  max-width: 90vw;
  text-align: center;
  border: 1px solid #000;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

h1 {
  margin: 0;
  font-weight: 700;
  font-size: 2.2rem;
  color: #222;
}

.subtitulo {
  font-weight: 400;
  font-size: 1rem;
  color: #555;
  margin-top: -0.4rem;
  margin-bottom: 1.5rem;
}

.filtros {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1rem;
}

.filtros input {
  padding: 0.7rem 1rem;
  font-size: 1rem;
  border-radius: 10px;
  border: 1px solid #cbd5e0;
  background-color: #f7fafc;
  transition: all 0.2s ease;
  width: 100%;
}

.filtros input:focus {
  border-color: #3b82f6;
  outline: none;
  background-color: white;
}

.lista-fichas {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
  max-height: 400px;
  overflow-y: auto;
  border-top: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
}

.ficha-item {
  display: flex;
  justify-content: space-between;
  padding: 0.7rem 1rem;
  background-color: #e0e7ff;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  color: #1e40af;
  transition: background-color 0.25s ease;
  user-select: none;
}

.ficha-item:hover,
.ficha-item:focus {
  background-color: #3b82f6;
  color: white;
  outline: none;
}

.titulo {
  flex-grow: 1;
  text-align: left;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.info {
  margin-left: 1rem;
  font-weight: 400;
  font-size: 0.9rem;
  color: #1e3a8a;
  white-space: nowrap;
}

.btn-salir {
  background-color: #0d2a4d;
  box-shadow: 0 3px 6px rgba(239, 68, 68, 0.4);
  padding: 1rem;
  border: none;
  border-radius: 10px;
  font-weight: 600;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  margin-top: 1rem;
  width: 100%;
}

.btn-salir:hover,
.btn-salir:focus-visible {
  background-color: #0d2a4d;
  box-shadow: 0 6px 15px rgba(185, 28, 28, 0.5);
  outline: none;
}

/* Responsive */
@media (max-width: 420px) {
  .fichas-container {
    padding: 2rem 1.5rem;
    width: 95vw;
  }
  h1 {
    font-size: 1.8rem;
  }
  .filtros input {
    font-size: 0.9rem;
    padding: 0.6rem 0.8rem;
  }
  .ficha-item {
    font-size: 0.95rem;
    padding: 0.6rem 0.9rem;
  }
  .btn-salir {
    font-size: 1rem;
    padding: 0.9rem 1rem;
  }
}
</style>