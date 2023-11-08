<p align="center">
 <img  src="image/logo.png" align="center" alt="GitHub Readme Stats" />
 <h2 align="center"></h2>
</p>

[![Java 8+](https://img.shields.io/badge/java-8+-4c7e9f.svg)](http://java.oracle.com)
[![Apache License 2](https://img.shields.io/badge/license-APL2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

[中文文档](./README_zh.md)

## What is EasyByte?

EasyByte is a ByteBuffer operate library. Simple,easy to use

## Why EasyByte?

- JDK's native ByteBuffer that after allocate can't update capacity, `EasyByte` can create a dynamic ByteBuffer.
- Strings are something we need to add frequently but ByteBuffer only support byte array.
- If you have a container like List,Map,must design a format policy，`EasyByte` can easy to use.
- Annoyed by the read and write mode of the native ByteBuffer. ByteBuffer created from `EasyByte` is read-write separated.




## Some API

[source,java,indent=0]
----

import org.gongxuanzhang.easybyte.core.DynamicByteBuffer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

----






## Contributing

If you like EasyByte, star it please.

Please feel free to submit an [issue](https://github.com/gongxuanzhang/EasyByte/issues/new).

Fork and [PR](https://github.com/gongxuanzhang/EasyByte/pulls) are always welcomed.

