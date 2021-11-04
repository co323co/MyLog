
package com.mylog.dao;

import com.mylog.dto.post.selectpost.SelectPostInput;
import com.mylog.dto.post.selectpost.SelectPostOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PostRepositoryCustom {
    Page<SelectPostOutput> findByDynamicQuery(SelectPostInput selectCodiInput, Pageable pageable);
}
