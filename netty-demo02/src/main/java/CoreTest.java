import server.TimeServer;

/*
 * @description:
 * @program: netty-demo01
 * @author: syun
 * @create: 2018-09-12 13:42
 */
public class CoreTest {


    public static void main(String[] args) {
        try {
            new TimeServer().bind(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
