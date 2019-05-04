package com.syun.protobuf;



import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.annotations.Test;


/* 用于 PRC 传输
 * @description:
 * @program: netty-base
 * @author: syun
 * @create: 2019-05-04 18:19
 */
public class TestCore {

    @Test
    public void test() throws InvalidProtocolBufferException {
        DataInfo.Student stu = DataInfo.Student.newBuilder()
                .setName("张三").setAge(20).setAddress("yangzhou").build();
        byte[] data = stu.toByteArray();        //序列化
        DataInfo.Student student = DataInfo.Student.parseFrom(data);    //反序列化
        System.out.println(student.getAddress());
    }

}
