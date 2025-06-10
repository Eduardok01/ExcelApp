// src/services/viajeService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/viajes';

export const obtenerViajes = async () => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const crearViaje = async (viaje) => {
    const response = await axios.post(API_URL, viaje);
    return response.data;
};

// Puedes agregar más funciones para update, delete, etc. según necesites
