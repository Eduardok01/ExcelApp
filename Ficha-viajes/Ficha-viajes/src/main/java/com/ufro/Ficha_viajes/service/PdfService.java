package com.ufro.Ficha_viajes.service;

import com.ufro.Ficha_viajes.model.Viaje;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Genera un PDF a partir de la plantilla Thymeleaf y el objeto Viaje.
     * @param viaje Objeto Viaje con datos para la ficha
     * @return ByteArrayInputStream con contenido PDF
     */
    public ByteArrayInputStream generarFichaViajePdf(Viaje viaje) {
        Context context = new Context();
        context.setVariable("viaje", viaje);

        String htmlContent = templateEngine.process("ficha-viaje", context);

        // Reemplazar espacios no separables para evitar errores XML
        htmlContent = htmlContent.replace("&nbsp;", "&#160;");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ITextRenderer renderer = new ITextRenderer();

            // baseUrl apunta a la carpeta templates para recursos relativos (CSS, imágenes)
            String baseUrl = this.getClass().getResource("/templates/").toExternalForm();

            renderer.setDocumentFromString(htmlContent, baseUrl);
            renderer.layout();
            renderer.createPDF(out);
            renderer.finishPDF();

            return new ByteArrayInputStream(out.toByteArray());

        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar PDF", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                // Ignorar
            }
        }
    }

    /**
     * Guarda el HTML generado para inspección.
     * Útil para depurar el HTML antes de generar el PDF.
     * @param viaje Objeto Viaje
     * @param rutaArchivo Ruta del archivo donde guardar el HTML
     */
    public void guardarHtmlGenerado(Viaje viaje, String rutaArchivo) {
        Context context = new Context();
        context.setVariable("viaje", viaje);

        String htmlContent = templateEngine.process("ficha-viaje", context);

        try {
            Files.writeString(Path.of(rutaArchivo), htmlContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("HTML generado guardado en: " + rutaArchivo);
        } catch (Exception e) {
            System.err.println("Error guardando HTML generado: " + e.getMessage());
        }
    }
}
