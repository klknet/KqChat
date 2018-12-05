package com.konglk.ims.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by konglk on 2018/12/3.
 */
public class FuncTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        System.out.println(list.stream().map(s -> s.equals("a") ? "A" : s).collect(Collectors.toList()));
        System.out.println(list);
    }
}
