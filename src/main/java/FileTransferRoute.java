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
                .log("Procesando archivo: ${file:name}")
                .convertBodyTo(String.class)
                .transform().simple("${body.toUpperCase()}")
                .to("file:/home/luisp/Documents/IntegracionSistemas/camel-lab/output");
    }
}
