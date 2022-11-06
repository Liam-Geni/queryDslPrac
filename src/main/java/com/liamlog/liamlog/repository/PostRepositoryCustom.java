package com.liamlog.liamlog.repository;

import com.liamlog.liamlog.domain.Post;
import com.liamlog.liamlog.dto.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
