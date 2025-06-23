package ucd.fs.gateway.security;


import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class HttpRedirectConfig {
//
//    @Bean
//    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
//        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
//
//        factory.addServerCustomizers(httpServer -> {
//            return httpServer.port(8081)
//                    .handle((req, res) -> {
//                        String host = req.requestHeaders().get("Host");
//                        String redirectUrl = "https://" + (host != null ? host.split(":")[0] : "localhost") + ":8443" + req.uri();
//                        return res.status(301)
//                                .header("Location", redirectUrl)
//                                .send();
//                    });
//        });
//
//        return factory;
//    }
//}

//Cas	Que faire
//✅ Spring MVC	Utilise TomcatServletWebServerFactory et Connector
//✅ Spring WebFlux (Gateway)	Utilise NettyReactiveWebServerFactory avec redirection

