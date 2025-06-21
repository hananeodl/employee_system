package ucd.fs.gateway.security;

//@Configuration
//public class RedirectHttpToHttpsConfig {
//
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
//        return server -> {
//            Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//            connector.setScheme("http");
//            connector.setPort(8081);
//            connector.setSecure(false);
//            connector.setRedirectPort(8443);
//            server.addAdditionalTomcatConnectors(connector);
//        };
//    }
//
//}

//@Configuration
//public class HttpRedirectConfig {
//
//    @Bean
//    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
//        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
//
//        // Démarre un port HTTP (8081) qui redirige vers HTTPS (8443)
//        factory.addServerCustomizers(httpServer -> {
//            return httpServer
//                    .port(8081)
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

