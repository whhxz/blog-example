package com.whhxz.blogexample.genericsget.file;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbsFileHandle<T> {
    protected Class<T> childClazz;

    public AbsFileHandle() {
        Type type = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        //noinspection unchecked
        childClazz = (Class<T>) params[0];
    }

    public void start(String filepath) {
        //文件转对象
        //preview(t);
    }

    protected abstract void preview(T t);

    public static void main(String[] args) {
        File1Handle handle = new File1Handle();
        System.out.println(handle.childClazz);
    }
}
