package ucd.fs.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}

//Le Gateway va :
//
//intercepter toutes les requêtes
//
//valider le JWT
//
//propager les infos de l’utilisateur vers les microservices

// Fasse office de point d’entrée sécurisé avec HTTPS
//
// Gère l’authentification avec JWT
//
// Redirige HTTP vers HTTPS
//
//Fasse le routage vers les microservices enregistrés dans Eureka


