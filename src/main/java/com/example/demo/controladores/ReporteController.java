package com.example.demo.controladores;

import com.example.demo.modelos.Venta;
import com.example.demo.repositorio.VentaRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReporteController {

    @Autowired
    private VentaRepository ventaRepository;
    
    
    @GetMapping("/ventas")
    public ResponseEntity<byte[]> generarReporteVentas(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) throws Exception {
        InputStream jasperStream = getClass().getResourceAsStream("/reportes/Simple_Blue.jasper");

        Map<String, Object> params = new HashMap<>();
        params.put("autor", "Sistema Loloyta");

        List<Venta> ventas;

        if (desde != null && hasta != null) {
            ventas = ventaRepository.findByFechaBetween(
                desde.atStartOfDay(),
                hasta.atTime(23, 59, 59)
            );
        } else {
            ventas = ventaRepository.findAll();
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ventas);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "reporte_ventas.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


   
}
