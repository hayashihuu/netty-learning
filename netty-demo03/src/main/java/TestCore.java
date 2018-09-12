import client.EchoClient;
import org.testng.annotations.Test;
import server.EchoServer;
import server.EchoServer02;

/*
 * @description:
 * @program: nettydemo03
 * @author: syun
 * @create: 2018-09-12 15:16
 */
public class TestCore {

    @Test
    public void timeServerTest() throws Exception {
        new EchoServer().bind(8080);
    }

    @Test
    public void timeClientTest() throws Exception {
        new EchoClient().connect(8080, "127.0.0.1");
    }


    @Test
    public void testService02() {

        try {
            new EchoServer02().bind(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
