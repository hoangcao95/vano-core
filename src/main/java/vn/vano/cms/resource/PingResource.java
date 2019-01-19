package vn.vano.cms.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path(value = "/ping")
@Produces(value = { MediaType.APPLICATION_JSON })
@Consumes(value = { MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
public class PingResource {

    private static final Logger logger = LoggerFactory.getLogger(PingResource.class);
    @Context
    private HttpServletRequest request;
    @Context
    private HttpHeaders httpHeaders;

    @GET
    public Response ping() {
        return Response.ok("API call success").type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
