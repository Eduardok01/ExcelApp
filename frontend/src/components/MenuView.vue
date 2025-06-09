<template>
  <div class="menu-background">
    <div class="menu-container" role="main" aria-label="Menú principal">
      <h1>Menú Principal</h1>
      <p class="subtitulo">Selecciona una opción para continuar</p>

      <div class="botones-principales">
        <button @click="irA('fichaNueva')" class="btn">
          <span class="icon">📝</span>
          Nueva Ficha
        </button>
        <button @click="irA('fichasCreadas')" class="btn">
          <span class="icon">📋</span>
          Ver Fichas
        </button>
      </div>

      <button @click="cerrarSesion" class="btn btn-salir" aria-label="Cerrar sesión y salir">
        <span class="icon">🚪</span>
        Salir
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MenuView',
  methods: {
    irA(vista) {
      this.$router.push(`/${vista}`)
    },
    cerrarSesion() {
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.menu-background {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #89f7fe 0%, #66a6ff 100%);
  padding: 1rem;
}

.menu-container {
  background: white;
  padding: 3rem 3.5rem;
  border-radius: 20px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.12);
  width: 380px;
  max-width: 90vw;
  text-align: center;
  border: 1px solid #000;
  display: flex;
  flex-direction: column;
  gap: 2rem;
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
  margin-bottom: 1.8rem;
}

.botones-principales {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.7rem;
  padding: 1.1rem 1.3rem;
  font-size: 1.15rem;
  font-weight: 600;
  color: white;
  background-color: #3b82f6;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 3px 6px rgba(59, 130, 246, 0.4);
}

.btn:hover,
.btn:focus-visible {
  background-color: #2563eb;
  box-shadow: 0 6px 15px rgba(37, 99, 235, 0.5);
  outline: none;
}

.btn:focus-visible {
  outline: 3px solid #a5c8ff;
  outline-offset: 3px;
}

.btn-salir {
  background-color: #ef4444;
  box-shadow: 0 3px 6px rgba(239, 68, 68, 0.4);
  margin-top: 1rem;
}

.btn-salir:hover,
.btn-salir:focus-visible {
  background-color: #b91c1c;
  box-shadow: 0 6px 15px rgba(185, 28, 28, 0.5);
}

.icon {
  font-size: 1.35rem;
  user-select: none;
  line-height: 1;
}

/* Responsive */
@media (max-width: 420px) {
  .menu-container {
    padding: 2rem 1.5rem;
    width: 95vw;
  }
  h1 {
    font-size: 1.8rem;
  }
  .btn {
    font-size: 1rem;
    padding: 0.9rem 1rem;
  }
}
</style>