
package com.example.app;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/simple-greet")
public class SimpleGreetResource {

    @GET
    public String greet() {
        return "Hello World!";
    }
}
