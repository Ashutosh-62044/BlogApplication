package com.blog.blogger.service.Impl;

import com.blog.blogger.entity.Post;
import com.blog.blogger.exception.ResourceNotFoundException;
import com.blog.blogger.payload.Postdto;
import com.blog.blogger.repository.PostRepository; // Assuming you have a PostRepository
import com.blog.blogger.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private  PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository)
    {
        this.postRepository = postRepository;
    }

    // Create new post and save in to database...
    @Override
    public Postdto createPost(Postdto postdto) {
        // Create a new Post entity and map fields from the Postdto
        Post post = new Post();
        post.setTitle(postdto.getTitle());
        post.setDescription(postdto.getDescription());
        post.setContent(postdto.getContent());

        // Save the Post entity to the database using the repository
        Post savepost = postRepository.save(post);

        Postdto dto = new Postdto();
        dto.setId(savepost.getId());
        dto.setTitle(savepost.getTitle());
        dto.setDescription(savepost.getDescription());
        dto.setContent(savepost.getContent());
        // dto.setMessage("Post is created.!");

        return dto;
    }

// Get all data and show in the view page...
    @Override
    public List<Postdto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir)
    {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy)
                    .ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<Postdto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return dtos;
    }

    Postdto mapToDto(Post post)
    {
      Postdto dto = new Postdto();
      dto.setId(post.getId());
      dto.setTitle(post.getTitle());
      dto.setContent(post.getContent());
      dto.setDescription(post.getDescription());
      return dto;
    }

  //Delete the data by id...

    @Override
    public void deleteById(long id)
    {

        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id:" +id)
        );
         postRepository.deleteById(id);
    }

    // Update the data of post...
    @Override
    public Postdto updatePost(long postId, Postdto postdto)
    {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId));

        post.setTitle(postdto.getTitle());
        post.setContent(postdto.getContent());
        post.setDescription(postdto.getDescription());

        Post savedPost = postRepository.save(post);

        Postdto dto = mapToDto(savedPost);

        return dto;
    }

}
