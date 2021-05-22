package dev.fumin.sample.eventdriven;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;

public class Main {

    public static void main(String[] args) throws Exception {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StrErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "INFO");

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        Server server = new Server(port);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        ProtectionDomain domain = Main.class.getProtectionDomain();
        URL warLocation = domain.getCodeSource().getLocation();
        webapp.setWar(warLocation.toExternalForm());

        Configuration.ClassList classList = Configuration.ClassList.setServerDefault(server);

        classList.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

        server.setHandler(webapp);
        server.start();
        server.join();
    }

}
