import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

public class FileTransferRoute extends RouteBuilder {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new FileTransferRoute());
        main.run();
    }

    @Override
    public void configure() throws Exception {
        from("file:/home/luisp/Documents/IntegracionSistemas/camel-lab/input?noop=true")
                .filter(header("CamelFileName").endsWith(".csv"))
                .filter(header("CamelFileName").contains("ventas"))
                .filter(header("CamelFileLastModified").isGreaterThanOrEqualTo(simple("${date:now:yyyyMMdd000000000}")))
                .log("Procesando archivo: ${file:name} | Fecha y hora: ${date:now:dd-MM-yyyy HH:mm:ss} ")
                .convertBodyTo(String.class)
                .transform().simple("${body.toUpperCase()}")
                .to("file:/home/luisp/Documents/IntegracionSistemas/camel-lab/output");

        from("file:/home/luisp/Documents/IntegracionSistemas/camel-lab/output")
                .log("Archivando archivo: ${file:name}")
                .to("file:/home/luisp/Documents/IntegracionSistemas/camel-lab/archived");
    }
}
