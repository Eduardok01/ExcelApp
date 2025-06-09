<template>
  <div class="login-wrapper">
    <div class="login-box">
      <h2>Iniciar Sesión</h2>
      <form @submit.prevent="iniciarSesion" class="login-form">
        <label for="usuario">Usuario:</label>
        <input id="usuario" v-model="usuario" type="text" required />
        
        <label for="contrasena">Contraseña:</label>
        <input id="contrasena" v-model="contrasena" type="password" required />
        
        <button type="submit" class="btn-submit">Entrar</button>
        
        <p v-if="error" class="error-msg">{{ error }}</p>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      usuario: '',
      contrasena: '',
      error: ''
    }
  },
  methods: {
    iniciarSesion() {
      if (this.usuario === 'admin' && this.contrasena === '1234') {
        this.$router.push('/menu');
      } else {
        this.error = 'Credenciales inválidas';
      }
    }
  }
}
</script>

<style scoped>
.login-wrapper {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  /* background removed */
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  padding: 3rem 1rem;
}

.login-box {
  background: white;
  width: 100%;
  max-width: 480px;
  padding: 4rem 3rem;
  border-radius: 35px;
  /* Borde negro sutil */
  border: 1.5px solid #111;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.12);
  box-sizing: border-box;
  transition: box-shadow 0.3s ease;
}

.login-box:hover {
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.18);
}

h2 {
  text-align: center;
  color: #222;
  margin-bottom: 2.5rem;
  font-weight: 600;
  font-size: 2rem;
  letter-spacing: 0.05em;
}

.login-form {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #555;
  font-size: 1rem;
}

input {
  padding: 0.75rem 1rem;
  margin-bottom: 1.6rem;
  font-size: 1.05rem;
  border: 1.5px solid #bbb;
  border-radius: 12px;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input:focus {
  outline: none;
  border-color: #4a90e2;
  box-shadow: 0 0 8px rgba(74, 144, 226, 0.4);
}

.btn-submit {
  background-color: #4a90e2;
  border: none;
  padding: 0.85rem;
  font-size: 1.2rem;
  font-weight: 700;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  transition: background-color 0.25s ease;
  user-select: none;
}

.btn-submit:hover {
  background-color: #357abd;
}

.error-msg {
  margin-top: 1rem;
  color: #e74c3c;
  font-weight: 600;
  text-align: center;
  user-select: none;
  font-size: 1rem;
}
</style>