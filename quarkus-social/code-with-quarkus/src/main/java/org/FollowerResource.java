package org;

import org.acme.Follower;
import org.acme.User;

import com.aayushatharva.brotli4j.decoder.DecoderJNI;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {
    FollowerRepository repository;
    UserRepository user;
    public FollowerResource(FollowerRepository repository, UserRepository user){
        this.repository=repository;
        this.user=user;
    }
    @PUT
    @Transactional
    public Response updateFollower(@PathParam("userId") long userId, FollowerRequest request) {
        // Retrieve the user using the UserRepository
        User user = this.user.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found.").build();
        }
    
        // Retrieve the follower user using the UserRepository
        User follower = this.user.findById(request.getFollowerId());
        if (follower == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Follower user not found.").build();
        }
    
        // Check if the follower already follows the user
        boolean follows = repository.follows(follower, user);
        if (follows) {
            return Response.status(Response.Status.CONFLICT).entity("Follower already exists.").build();
        }
    
        // Create a new Follower entity and set the user and follower
        Follower entity = new Follower();
        entity.setUser(user);
        entity.setFollower(follower);
    
        // Persist the follower entity
        repository.persist(entity);
    
        return Response.ok(entity).build();
    }
    
    
    @GET
    public  Response listFollower(@PathParam("userId") long UserId){
        User user = this.user.findById(UserId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found.").build();
        }
        var list= repository.findByUser(UserId);
        FollowerPerUserResponse responseObject= new FollowerPerUserResponse(null, null);
        responseObject.setFollowerCount(list.size());
        var followerlist=list.stream().map(FollowerResponse::new).toList();
        responseObject.setContent(followerlist);
        return Response.ok(responseObject).build();
    }
    @DELETE
    @Transactional
    public  Response deleteFollower(@PathParam("userId") long UserId, @QueryParam("followerId") Long followerId){
        User user = this.user.findById(UserId);
        if(user ==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        repository.deleteByFollowerAndUser(followerId, UserId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
