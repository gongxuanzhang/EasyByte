package org.gongxuanzhang.easybyte.core;

import java.util.Objects;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class Dummy implements ByteWrapper {

    private Integer age;

    private String name;

    public Dummy() {

    }

    public Dummy(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Dummy(int age) {
        this.age = age;
    }

    public Dummy(String name) {
        this.name = name;
    }

    @Override
    public byte[] toBytes() {
        DynamicByteBuffer byteBuffer = DynamicByteBuffer.allocate();
        byteBuffer.appendInt(Objects.requireNonNullElse(this.age, -1));
        byteBuffer.appendString(this.name);
        return byteBuffer.toBytes();
    }


    public Integer getAge() {
        return age;
    }

    public Dummy setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getName() {
        return name;
    }

    public Dummy setName(String name) {
        this.name = name;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dummy dummy = (Dummy) o;
        return Objects.equals(age, dummy.age) && Objects.equals(name, dummy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
}
