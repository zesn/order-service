package com.zesn.karate;

import com.intuit.karate.junit5.Karate;

public class KarateTestRunner {

    @Karate.Test
    Karate testAll() {
        return Karate.run("classpath:com/zesn/karate/SampleTest.feature");
    }
}
