import client.TimeClient;

/*
 * @description:
 * @program: nettydemo02
 * @author: syun
 * @create: 2018-09-12 14:31
 */
public class CoreTest02 {

    public static void main(String[] args) throws Exception {
        new TimeClient().connect(8080, "127.0.0.1");
    }
}
