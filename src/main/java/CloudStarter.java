import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.ProxyServerService;

@SpringBootApplication
public class CloudStarter {

    public static void main(String[] args){
        ProxyServerService proxyServerService = new ProxyServerService();
        proxyServerService.startServer(8080);
        SpringApplication.run(CloudStarter.class, args);
    }
}
