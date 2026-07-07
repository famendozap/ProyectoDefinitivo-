package com.sushi.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
		System.out.println("================================================");
		System.out.println(" API Gateway Sushi iniciado correctamente");
		System.out.println(" URL base: http://localhost:8080");
		System.out.println("------------------------------------------------");
		System.out.println(" /usuarios/**, /auth/**, /roles/**, /usuario-rol/**  -> AUTENTICACION");
		System.out.println(" /inventario/**                                      -> INVENTARIO");
		System.out.println(" /sucursales/**                                      -> REGISTRO-SUCURSAL");
		System.out.println(" /pagos/**                                           -> PAGO");
		System.out.println(" /despachos/**                                       -> DESPACHO");
		System.out.println(" /certificaciones/**                                 -> CERTIFICACION");
		System.out.println(" /ventas/**                                          -> REGISTRO-VENTAS");
		System.out.println(" /resenas/**                                         -> RESENA");
		System.out.println(" /notificaciones/**                                  -> NOTIFICACION");
		System.out.println(" /tickets/**                                         -> SOPORTE");
		System.out.println("------------------------------------------------");
		System.out.println(" Eureka: http://localhost:8761");
		System.out.println("================================================");
	}

}
