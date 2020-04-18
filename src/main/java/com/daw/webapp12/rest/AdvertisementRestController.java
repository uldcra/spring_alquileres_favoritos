package com.daw.webapp12.rest;

import com.daw.webapp12.entity.*;
import com.daw.webapp12.repository.AdvertisementRepository;
import com.daw.webapp12.repository.CommentRepository;
import com.daw.webapp12.security.UserComponent;
import com.daw.webapp12.service.AdvertisementService;
import com.daw.webapp12.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/advertisements")
public class AdvertisementRestController {

    @Autowired
    AdvertisementService advertisementService;
    @Autowired
    AdvertisementRepository advertisementRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserComponent userComponent;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/list")
    public List<Advertisement> list() {
		List<Advertisement> ads = advertisementService.findAll();
		return ads;
		}
    
    @GetMapping("/")
    public List<Advertisement> allAdvertisement(/*@RequestParam("id") long idAdver, */@RequestParam(value="page") int page,@RequestParam(value="number") int number) {
    //    Users users = userService.findById(idAdver);
    //     List<Advertisement> myAds = users.getMyAdvertisements();
    //     return myAds;
    	System.out.println("userComponent.getLoggedUser().getName(): "+ userComponent.getLoggedUser().getName());
        Users user = userService.findByName(userComponent.getLoggedUser().getName()).get();
        List<Advertisement> myAds = user.getMyAdvertisements(page,number);
        if(myAds != null){
            return myAds;
        }else{
            return null;
        }
    }
    // public List<Advertisement> allAdvertisement(@RequestParam("id") long id) {
    //     Users users = userService.findById(id);
    //     List<Advertisement> myAds = users.getMyAdvertisements();
    //     return myAds;
    // }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Advertisement uploadsAdvertisement(@RequestParam String type,@RequestParam String property, @RequestParam Integer rooms,
                                                    @RequestParam Integer bathrooms, @RequestParam Integer squareMeters,@RequestParam String location,
                                                    @RequestParam String address, @RequestParam double price) {
        Advertisement ads = new Advertisement(type, property, rooms, bathrooms,squareMeters, location, address, price);
        String userName = userComponent.getLoggedUser().getName();
        Optional<Users> user = userService.findByName(userName);
        user.get().addMyAdvertisement(ads);
        ads.setImages(new ArrayList<String>());
        advertisementService.addAdvertisement(ads);
        userService.addUser(user.get());

        return ads;
        //Users users = userService.findById(id);
        /*Users users = userComponent.getLoggedUser();
        List<Advertisement> myAds = users.getMyAdvertisements();
        advertisementRepository.save(anuncios);
        myAds.add(anuncios);
        return myAds;*/
    }

    @PutMapping("/{id}")
    public ResponseEntity<Advertisement> editAdvertisement(@PathVariable long id, @RequestBody Advertisement newAdvertisement){
        Users user = userService.findByName(userComponent.getLoggedUser().getName()).get();
        if(user.getMyAdvertisements().contains(advertisementService.findById(id))) {
            Advertisement advertisement = advertisementService.findById(id);
            if (advertisement != null) {
                newAdvertisement.setId((int) id);
                advertisementService.addAdvertisement(newAdvertisement);
                return new ResponseEntity<>(newAdvertisement, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/{id}")
    public Advertisement getAdvertisement(@PathVariable long id) {
        Advertisement myAds = advertisementService.findById(id);
        return myAds;
    }

    @GetMapping("/recommended")
    public ResponseEntity<List<Advertisement>> recommendeds() {
		List<Advertisement> recommendeds = new ArrayList<Advertisement>();
		advertisementService.recommendeds(recommendeds);
		if(recommendeds.size()>0){
            return new ResponseEntity<>(recommendeds, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/graph")
    public ResponseEntity<List<Advertisement>> graphValues() {
		List list = advertisementService.graphValues();
		if(list.size()>0){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> advertisementComments(@PathVariable long id, @RequestParam(value="page") int page,@RequestParam(value="number") int number) {
        Advertisement advert = advertisementService.findById(id);
        List<Comment> comments = advert.getComments(page, number);
        if(comments != null){
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment blogUpload(@PathVariable long id, @RequestBody Comment comment){
        Advertisement advert = advertisementService.findById(id);
        if(userComponent.getLoggedUser()!=null){
            Users user = userService.findByName(userComponent.getLoggedUser().getName()).get();
            comment.setAuthor(user.getName());
        } 
        commentRepository.save(comment);
        advert.getComments().add(comment);
        advertisementService.addAdvertisement(advert);
        return comment;
    }

    @DeleteMapping("/{id}/comments/{idComment}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long id, @PathVariable long idComment){
        Comment comment = commentRepository.findById(idComment).get();
        if(comment != null){
            if(comment.getAuthor().equals(userComponent.getLoggedUser().getName())){
                Advertisement advert = advertisementService.findById(id);
                advert.deleteComment(idComment);
                advertisementService.addAdvertisement(advert);
                commentRepository.deleteById(idComment);
                return new ResponseEntity<>(comment, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}