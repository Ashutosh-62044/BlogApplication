package com.blog.blogger.controller;

import com.blog.blogger.payload.Postdto;
import com.blog.blogger.service.PostService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

 //http://localhost:8080/api/posts
    private PostService postService;

    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody Postdto postdto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Postdto dto = postService.createPost(postdto);
        return  new ResponseEntity<>(dto , HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post is deleted ",HttpStatus.OK);
    }

    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Postdto>> getAllPosts(
            @RequestParam(name = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "3",required = false) int pageSize,
            @RequestParam(name="sortBy",defaultValue = "id" ,required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc",required = false) String sortDir
    ) {
        List<Postdto> PostDtos = postService.getAllPosts( pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(PostDtos,HttpStatus.OK) ;
    }

    // http://localhost:8080/api/posts?postId=1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Postdto> updatePost(@RequestParam long postId, @RequestBody Postdto postdto) {
        Postdto dto = postService.updatePost(postId, postdto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
