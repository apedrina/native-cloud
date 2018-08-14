package com.alissonpedrina.resources;

import com.alissonpedrina.model.User;
import com.alissonpedrina.service.UserService;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j
@RestController
public class ClientController {

  @Autowired
  UserService userService;  //Service which will do all data retrieval/manipulation work


  //-------------------Retrieve All Users--------------------------------------------------------

  @RequestMapping(value = "/user/", method = RequestMethod.GET)
  public ResponseEntity<List<User>> listAllUsers() {
    List<User> users = userService.findAllUsers();
    if (users.isEmpty()) {
      return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
    }
    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
  }


  //-------------------Retrieve Single User--------------------------------------------------------

  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<User> getUser(@PathVariable("id") long id) {
    log.info("Fetching User with id " + id);
    User user = userService.findById(id);
    if (user == null) {
      log.debug("User with id " + id + " not found");
      return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }


  //-------------------Create a User--------------------------------------------------------

  @RequestMapping(value = "/user/", method = RequestMethod.POST)
  public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
    log.debug("Creating User " + user.getName());

    if (userService.isUserExist(user)) {
      log.info("A User with name " + user.getName() + " already exist");
      return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    userService.saveUser(user);

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }


  //------------------- Update a User --------------------------------------------------------

  @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
  public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
    log.info("Updating User " + id);

    User currentUser = userService.findById(id);

    if (currentUser == null) {
      log.debug("User with id " + id + " not found");
      return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    currentUser.setName(user.getName());
    currentUser.setAge(user.getAge());
    currentUser.setSalary(user.getSalary());

    userService.updateUser(currentUser);
    return new ResponseEntity<User>(currentUser, HttpStatus.OK);
  }

  //------------------- Delete a User --------------------------------------------------------

  @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
    log.info("Fetching & Deleting User with id " + id);

    User user = userService.findById(id);
    if (user == null) {
      log.debug("Unable to delete. User with id " + id + " not found");
      return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    userService.deleteUserById(id);
    return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
  }


  //------------------- Delete All Users --------------------------------------------------------

  @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
  public ResponseEntity<User> deleteAllUsers() {
    log.info("Deleting All Users");

    userService.deleteAllUsers();
    return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
  }

}