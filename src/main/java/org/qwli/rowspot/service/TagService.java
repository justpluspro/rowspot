package org.qwli.rowspot.service;


import org.qwli.rowspot.Message;
import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.ArticleTag;
import org.qwli.rowspot.model.Tag;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.repository.ArticleTagRepository;
import org.qwli.rowspot.repository.TagRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author qwli7
 * TagService 业务
 */
@Service
public class TagService extends AbstractService<Tag, Tag> {


    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;

    public TagService(TagRepository tagRepository, ArticleTagRepository articleTagRepository) {
        this.tagRepository = tagRepository;
        this.articleTagRepository = articleTagRepository;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void save(Tag tag) throws BizException {
        final String name = tag.getName();
        tagRepository.findByName(name).ifPresent(e -> {
            throw new BizException(new Message("tagName.exists", "标签已经存在"));
        });

        tagRepository.findByAlias(tag.getAlias()).ifPresent(e -> {
            throw new BizException(new Message("tagAlias.exists", "标签已经存在"));
        });

        tag.setCreateAt(new Date());
        tag.setModifyAt(new Date());
        String description = tag.getDescription();
        if(!StringUtils.hasText(description)) {
            tag.setDescription("");
        }
        tag.setIcon("");
        tagRepository.save(tag);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void delete(Long id) throws BizException {
        final Tag tag = tagRepository.findById(id).orElseThrow(()
                -> new BizException(new Message("tag.notExists", "标签不存在")));
        ArticleTag articleTag = new ArticleTag();
        articleTag.setTagId(tag.getId());
        articleTagRepository.delete(articleTag);
        tagRepository.deleteById(tag.getId());
    }



    @Transactional(readOnly = true)
    public PageAggregate<Tag> findPage(Integer page) {

        PageRequest pageRequest = PageRequest.of(page-1, 10);

        final long count = tagRepository.count();
        if (count == 0) {
            return new PageAggregate<>(new ArrayList<>(), 0, 0);
        }

        final Page<Tag> tags = tagRepository.findAll(pageRequest);

        if(tags.getContent().isEmpty()) {

            return new PageAggregate<>(new ArrayList<>(), 0, 0);

        }
        return new PageAggregate<>(tags.getContent(), page, tags.getTotalPages());
    }
}
