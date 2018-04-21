package com.ror.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("rest")
public class RORRest {
	@GET
	@Path("/sample")
	public String sampleRest() {
		return "Rest Working Fine!!";
	}

}
