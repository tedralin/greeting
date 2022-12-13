package com.example.greetings;

import com.example.greetings.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.lang.Integer.parseInt;

//  Spring has a number of built-in repositories
//                  Repository
//                      |
//                CRUDRepository   -- Create/Read/Update/Delete
//                      |
//                JPARepository    -- Java Persistence API, CRUD + Paging + Batch Deleting and other functions
// We have access to all the CRUD operations inherited from CrudRepository

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, String> {
    // we have access to built in methods lie save() for POST, findAll for GET
    // we can also write our own custom methods

        // Jpa will parse the names of these custom methods to look for "find" "by"
            // find - looking for something
            // By - the condition of what is being looked for
            // id - the variable (should match the variable nae we defined in the model constructor - case sensitive)
    Greeting findByid (int id);

//        Optional<Greeting> findByid(int id);


}
