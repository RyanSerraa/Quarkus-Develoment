package org;

import java.time.LocalDateTime;
import java.util.List;

import org.acme.Posts;
import org.acme.User;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON) // Diz que vou estar consumindo uma info JSON
@Produces(MediaType.APPLICATION_JSON) //  Diz que vou enviar Json, produzir 
public class PostResource {
    private UserRepository userRepository;
    private PostRepository repository;
    @Inject
    public PostResource(UserRepository userRepository, PostRepository repository){
        this.userRepository= userRepository;
        this.repository=repository;

    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId")long userId, CreatePostRequest request){
      User user = userRepository.findById(userId);
      if (user==null){
        return Response.status(Response.Status.NOT_FOUND).build();

      }
      Posts post= new Posts();
      post.setDateTime(LocalDateTime.now());
      post.setText(request.getText());
      post.setUser(user);
      repository.persist(post);
      return Response.status(Response.Status.CREATED).build();

    }
    @GET
    public Response listPost(@PathParam("userId")long userId){
        User user = userRepository.findById(userId);
        if (user==null){
            return Response.status(Response.Status.NOT_FOUND).build();
    
          }
        PanacheQuery<Posts> query = repository.findAll();  // Make sure to call findAll() correctly
        List<Posts> posts = query.list();  // Convert the PanacheQuery to a List
        var postResponselist =posts.stream().map(post -> PostResponse.fromEntity(post)).toList();
        return Response.ok(postResponselist).build();
    }
}
