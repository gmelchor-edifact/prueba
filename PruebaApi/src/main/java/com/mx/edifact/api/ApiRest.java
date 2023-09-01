package com.mx.edifact.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("EdifactMx")
public class ApiRest {

	@PostMapping(value = "/facturaCFDI", produces = MediaType.APPLICATION_JSON_VALUE)
	public String postFacturaCFDI(@RequestBody String json) {
		Gson gsonResponce = new GsonBuilder().create();
		// Necesitamos separar cada número binario por espacio. Usamos split
        String[] binaryNumbers = json.split(" ");
        String text = "";

        // Los recorremos. En cada paso tenemos al número binario
        for (String currentBinary : binaryNumbers) {
            // Ahora convertimos ese binario a decimal
            int decimal = binaryToDecimal(currentBinary);
            // Obtenemos la letra que le corresponde a ese valor ASCII
            char letra = (char) decimal;
            text += letra;
        }
		System.out.println(text);
		return gsonResponce.toJson("Ok");
	}
	
	public static int binaryToDecimal(String binary) {
        // A este número le vamos a sumar cada valor binario
        int decimal = 0;
        int position = 0;
        // Recorrer la cadena...
        for (int x = binary.length() - 1; x >= 0; x--) {
            // Saber si es 1 o 0; primero asumimos que es 1 y abajo comprobamos
            short digit = 1;
            if (binary.charAt(x) == '0') {
                digit = 0;
            }

      /*
          Se multiplica el dígito por 2 elevado a la potencia
          según la posición; comenzando en 0, luego 1 y así
          sucesivamente
       */
            double multiplier = Math.pow(2, position);
            decimal += digit * multiplier;
            position++;
        }
        return decimal;
    }
}
