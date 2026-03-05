package org.mifos.dummy.foo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DummyService {
    String greet(String message) {
        return "Hello: " + message;
    }
}
