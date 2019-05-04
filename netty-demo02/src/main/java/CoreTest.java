import client.TimeClient;
import org.testng.annotations.Test;
import server.TimeServer;

/*
 * @description:
 * @program: netty-demo01
 * @author: syun
 * @create: 2018-09-12 13:42
 */
public class CoreTest {


    @Test
    public void startServer() throws Exception {
        new TimeServer().bind(8080);

    }

    @Test
    public void startClient() throws Exception {
        new TimeClient().connect(8080, "127.0.0.1");
    }

//    public static void main(String[] args) {
//        try {
//            new TimeServer().bind(8080);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
