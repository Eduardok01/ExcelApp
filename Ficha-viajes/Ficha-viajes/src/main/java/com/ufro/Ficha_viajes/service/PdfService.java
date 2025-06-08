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

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayInputStream generarFichaViajePdf(Viaje viaje) {
        Context context = new Context();
        context.setVariable("viaje", viaje);

        String htmlContent = templateEngine.process("ficha-viaje", context);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar PDF (DocumentException)", e);
        } catch (IOException e) {
            throw new RuntimeException("Error de IO al generar PDF", e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error inesperado al generar PDF", e);
        }
    }
}
