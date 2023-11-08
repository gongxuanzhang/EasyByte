package org.gongxuanzhang.easybyte.example;

import org.gongxuanzhang.easybyte.core.DynamicByteBuffer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class HowToUse {

    public static void main(String[] args) {
        DynamicByteBuffer allocate = DynamicByteBuffer.allocate();
        allocate.appendString("hello world");
        allocate.appendCollection(helloList());
        allocate.appendMap(helloMap());

        String helloWorld = allocate.getString();
        List<String> list = allocate.getCollection(String.class);
        Map<String, String> map = allocate.getMap(String.class, String.class);
        System.out.println(helloWorld); //  hello world
        System.out.println(list);    //  [hello, world]
        System.out.println(map);   //  {hello=world}
    }

    private static List<String> helloList() {
        return Arrays.asList("hello", "world");
    }

    private static Map<String, String> helloMap() {
        return Collections.singletonMap("hello", "world");
    }
}
