package org;

import java.util.List;
import java.util.Optional;

import org.acme.Follower;
import org.acme.User;

import com.aayushatharva.brotli4j.encoder.Encoder;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
    boolean follows(User follower, User user){
        var params = Parameters.with("follower", follower).and("user",user).map();
        PanacheQuery<Follower> query= find("follower =:follower and user =:user ", params);
        Optional<Follower> result= query.firstResultOptional();
        return result.isPresent();
    }
    public List<Follower> findByUser(Long userId){
      PanacheQuery<Follower> query=find("user.id", userId);
        return query.list();
    }
    public void deleteByFollowerAndUser(Long followerId, long userId) {
        var params = Parameters.with("userId", userId).and("followerId", followerId).map();
        // Corrected the parameter name from ":followerdId" to ":followerId"
        delete("follower.id = :followerId and user.id = :userId", params);
    }
}