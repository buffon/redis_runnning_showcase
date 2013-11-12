package harry.resource;

import com.google.gson.Gson;
import harry.bean.ResultBean;
import harry.redisconn.RedisHandler;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: chenyehui
 * Date: 13-11-4
 * Time: 下午9:48
 * To change this template use File | Settings | File Templates.
 */

@Path("/user.do")
@Component
public class UserResource {

    public UserResource() {
        RedisHandler.initJedis(RedisHandler.IP,RedisHandler.PORT);
    }

    @POST
    @Produces("application/json")
    public Response setValue(@FormParam("key") String key, @FormParam("value") String value) {
        long begin = System.currentTimeMillis();
        return Response.status(200).entity(new Gson().toJson(new ResultBean(RedisHandler.setValue(key, value), String.valueOf(System.currentTimeMillis() - begin)))).build();
    }

    @GET
    @Produces("application/json")
    public Response getValue(@QueryParam("key") String key) {
        long begin = System.currentTimeMillis();
        return Response.status(200).entity(new Gson().toJson(new ResultBean(RedisHandler.getValue(key), String.valueOf(System.currentTimeMillis() - begin)))).build();
    }

    @GET
    @Path("/info.do")
    @Produces("application/json")
    public Response info() {
        return Response.status(200).entity(new Gson().toJson(new ResultBean(RedisHandler.collectinfo()))).build();
    }

    @GET
    @Path("/dbsize.do")
    @Produces("application/json")
    public Response dbsize() {
        return Response.status(200).entity(new Gson().toJson(new ResultBean(String.valueOf(RedisHandler.dbsize())))).build();
    }

    @POST
    @Path("/concurrent.do")
    @Produces("application/json")
    public Response con(@FormParam("number") int number,@FormParam("operation") int operation) {
        return Response.status(200).entity(new Gson().toJson(new ResultBean(String.valueOf(RedisHandler.concurrent(number,operation))))).build();
    }

    @POST
    @Path("/init.do")
    @Produces("application/json")
    public Response init(@FormParam("number") long number) {
        long begin = System.currentTimeMillis();
        return Response.status(200).entity(new Gson().toJson(new ResultBean(String.valueOf(RedisHandler.init(number)), String.valueOf(System.currentTimeMillis() - begin)))).build();
    }

    @POST
    @Path("/address.do")
    @Produces("application/json")
    public Response address(@FormParam("ip") String ip,@FormParam("port") String port) {
        long begin = System.currentTimeMillis();
        return Response.status(200).entity(new Gson().toJson(new ResultBean(String.valueOf(RedisHandler.initJedis(ip,Integer.parseInt(port))), String.valueOf(System.currentTimeMillis() - begin)))).build();
    }

}
