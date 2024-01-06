package com.blog.blogger.service;

import com.blog.blogger.payload.Postdto;

import java.util.List;


public interface PostService {


     Postdto updatePost(long postId, Postdto postdto);


    List<Postdto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


    public Postdto createPost(Postdto postdto);

    void deleteById(long id);
}
