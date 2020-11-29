package grow.together.io.bookmarks.serviceImpl;

import grow.together.io.bookmarks.domain.*;
import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.dtoModel.PostDtoIn;
import grow.together.io.bookmarks.dtoModel.PostDtoOut;
import grow.together.io.bookmarks.errorHandler.BadRequestException;
import grow.together.io.bookmarks.errorHandler.ResourceNotFoundException;
import grow.together.io.bookmarks.repository.CategoryRepository;
import grow.together.io.bookmarks.repository.PostRepository;
import grow.together.io.bookmarks.service.PostService;
import grow.together.io.bookmarks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public DataResponse<PostDtoOut> getPostById(Long post_id) {
        Posts post = getById(post_id);

        return new DataResponse<>("User Load Successfully", HttpStatus.OK.value(), new PostDtoOut(post));
    }

    @Override
    public PageableResult<PostDtoOut> getAllPost(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        Page<Posts> posts = this.postRepository.findPosts(PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(PostDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public DataResponse<Void> createPostByUser(Long user_id, PostDtoIn postDtoIn) {

        Posts posts = new Posts();
        posts.setLink(postDtoIn.getLink());
        posts.setStatus(GroupStatus.valueOf(postDtoIn.getStatus()));

        List<Category> categories = postDtoIn.getCategory().stream()
                .map(s -> this.categoryRepository.findByName(s).orElseThrow(
                        () -> new ResourceNotFoundException("Category With Name " + postDtoIn.getCategory() + " Not Found")))
                .collect(Collectors.toList());

        User user = getUser(user_id);

        posts.setCategories(categories);
        posts.setUser(user);

        this.postRepository.save(posts);

        return new DataResponse<>("Post Created Successfully ", HttpStatus.CREATED.value());
    }

    @Override
    @Transactional
    public DataResponse<Void> updatePostByUser(Long user_id, Long post_id, PostDtoIn postDtoIn) {

        Posts posts = this.postRepository.findByPostIdAndUserId(post_id, user_id).orElseThrow(() -> new ResourceNotFoundException("Post Not Found with id " + post_id));

        posts.setStatus(GroupStatus.valueOf(postDtoIn.getStatus()));
        posts.setTitle(postDtoIn.getTitle());
        posts.setLink(postDtoIn.getLink());

        List<Category> categories = postDtoIn.getCategory().stream()
                .map(s -> this.categoryRepository.findByName(s).orElseThrow(
                        () -> new ResourceNotFoundException("Category With Name " + postDtoIn.getCategory() + " Not Found")))
                .collect(Collectors.toList());

        posts.setCategories(categories);

        this.postRepository.save(posts);

        return new DataResponse<>("Post Updated Successfully ", HttpStatus.CREATED.value());
    }

    @Override
    @Transactional
    public DataResponse<Void> deletePostByUser(Long user_id, Long post_id) {
        getUser(user_id);
        Posts posts = getById(post_id);
        posts.setDeleted(true);
        this.postRepository.save(posts);
        return new DataResponse<>("Post Deleted Successfully ", HttpStatus.NO_CONTENT.value());
    }

    @Override
    public PageableResult<PostDtoOut> getAllPostByUserId(Long user_id, int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        User user = getUser(user_id);

        Page<Posts> posts = this.postRepository.findPostsByUserId(user_id, PageRequest.of(page - 1, size));
        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(PostDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public DataResponse<PostDtoOut> getPostByUserIdAndPostId(Long user_id, Long post_id) {
        Posts post = this.postRepository.findByPostIdAndUserId(post_id, user_id)
                .orElseThrow(() -> new ResourceNotFoundException("No result Found For Post Id :" + post_id + " And User Id " + user_id));

        return new DataResponse<>("Post Load Successfully", HttpStatus.OK.value(), new PostDtoOut(post));
    }

    @Override
    public DataResponse<Void> updateVueOrLike(Long id, String status) {

        Posts post = this.getById(id);

        if (status.equals(VueOrLike.LIKE.name())) {
            post.setLike(post.getLike() + 1);
        } else if (status.equals(VueOrLike.VUE.name())) {
            post.setVue(post.getVue() + 1);
        } else {
            throw new BadRequestException("The Value Enter is Not a Valid value");
        }
        return new DataResponse<>("Status Updated successfully", HttpStatus.OK.value());
    }

    @Override
    public PageableResult<PostDtoOut> getAllPostByAdmin(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        Page<Posts> posts = this.postRepository.findPostsByAdmin(PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(PostDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<PostDtoOut> getMostLikePost(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        Page<Posts> posts = this.postRepository.findMostLikePosts(PageRequest.of(page - 1, size));
        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(PostDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<PostDtoOut> getRecentPost(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        Page<Posts> posts = this.postRepository.findRecentPosts(PageRequest.of(page - 1, size));
        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(PostDtoOut::new).collect(Collectors.toList()));
    }

    Posts getById(Long post_id) {
        return this.postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post Not Found With id " + post_id));
    }


    User getUser(Long user_id) {
        return this.userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User Not Found With User Id " + user_id));
    }
}
