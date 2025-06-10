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

export const descargarPdf = async (viajeId) => {
    const response = await axios.get(`api/viajes/pdf`, {
        responseType: 'blob' // Para recibir el archivo PDF como blob
    });

    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `ficha-viaje-${viajeId}.pdf`);
    document.body.appendChild(link);
    link.click();
    link.remove();
};
