package com.github.pbetkier.spockdemo

import com.alibaba.testable.core.annotation.MockNew
import com.alibaba.testable.core.annotation.MockInvoke
import com.github.pbetkier.spockdemo.model.SpockBox
import spock.lang.Shared
import spock.lang.Specification

import static com.alibaba.testable.core.matcher.InvocationVerifier.verifyInvoked;

class DemoSpockTest extends Specification {

    @Shared
    def demoSpock = new DemoSpock()

    static class Mock {
        @MockNew
        SpockBox createBox() {
            SpockBox box = new SpockBox()
            box.put("mock zero")
            return box
        }

        @MockInvoke(targetMethod = "put")
        void putBox(SpockBox self, String data) {
            self.put("mock " + data)
        }
    }

    def "should get a box of numbers"() {
        given:
        def box = demoSpock.createBoxOfNum()

        expect:
        box.size() == 4
        box.pop() == "mock 3"
        box.pop() == "mock 2"
        box.pop() == "mock 1"
        box.pop() == "mock zero"
        verifyInvoked("createBox").withTimes(1)
        verifyInvoked("putBox").withInOrder("1").withInOrder("2").withInOrder("3")
    }

}
