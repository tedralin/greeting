package com.example.greetings;

import com.example.greetings.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static java.lang.Integer.parseInt;

@RestController
@CrossOrigin(origins  =  "http://localhost:3000")
public class GreetingController {

//    private ArrayList<Greeting> greetings = new ArrayList<>();

    // dependency injection
       // avoids us needing to make a new instance
    @Autowired
    GreetingRepository repository;


    @GetMapping("/greeting/{id}")
    public ResponseEntity<Optional<Greeting>> getGreetingById(@PathVariable String id) {
//        return greetings.stream()
//                .filter(greeting -> greeting.getId() == parseInt(id))
//                .findFirst()
//                .orElse(null);
        // what made up my response
            // status code
            // body - our actual greeting
            // headers - additional info re the request and response

        // ResponseEntity
            // we can configure our entire response using this
//        return repository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }

        @GetMapping("/greetings")
    public ResponseEntity<List<Greeting>> getAllGreetings() {
//        return greetings;
//        repository spring methods with data access (get and save)
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }


    @GetMapping("/random")
    public ResponseEntity<Greeting> getRandomGreeting() {
        // Random r = new Random();
        //Greeting randomGreeting = greetings.get(r.nextInt(greetings.size()));

        Random r = new Random();
        // refactor to get random greeting from database, not greetings array
        // .count to find number of entries in repository / .findById with repository.count() as argument
//         int index = 1 + r.nextInt((int) (repository.count()));
//         return ResponseEntity.status(HttpStatus.OK).body(repository.findByid(index));


        // .findAll (already written ^^) to get all the existing greetings
        List<Greeting> allGreetings = repository.findAll();
        Greeting randomGreeting = allGreetings.get(r.nextInt(allGreetings.size()));

        return ResponseEntity.status(HttpStatus.OK).body(randomGreeting);
    }

    @PostMapping("/greetings")
    public ResponseEntity<String> createGreeting(@RequestBody Greeting greeting) {
        // set the greetings id based on the greetings list
        // set the created by
//        greeting.setId(greetings.size() + 1);
//        greeting.setCreatedBy("Ollie");
//        greeting.setDateCreated(new Timestamp(System.currentTimeMillis()));
//        greetings.add(greeting);
        repository.save(greeting);
        return ResponseEntity.status(HttpStatus.OK).body("Greeting added");
    }

    // UPDATE route
    @PutMapping("/greetings/{id}")
    public String updateFullGreeting(@PathVariable int id, @RequestBody Greeting newGreeting){
//        Greeting updatedGreeting = greetings.get(id);
//        updatedGreeting.setGreeting(newGreeting.getGreeting());
//        updatedGreeting.setCreatedBy(newGreeting.getCreatedBy());
//        updatedGreeting.setOriginCountry(newGreeting.getOriginCountry());
        Greeting updatedGreeting = repository.findByid(id);
        if (newGreeting.getOriginCountry() != null)
            updatedGreeting.setOriginCountry(newGreeting.getOriginCountry());
        if (newGreeting.getGreeting() != null)
            updatedGreeting.setGreeting(newGreeting.getGreeting());
        if (newGreeting.getCreatedBy() != null)
            updatedGreeting.setCreatedBy(newGreeting.getCreatedBy());
        repository.save(updatedGreeting);
        return "Greeting with id: " + id + "changed to" + newGreeting;
    }

    // DELETE route
    @Transactional
    @DeleteMapping("/greetings/{id}")
    public String deleteGreeting(@PathVariable int id) {
//        greetings.remove(greetings.get(id));
        repository.delete(repository.findByid(id));
        return "Greeting with id: " + id + " deleted.";

    }
}
