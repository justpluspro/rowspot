package org.qwli.rowspot.service;


import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.Tag;
import org.qwli.rowspot.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qwli7
 * TagService 业务
 */
@Service
public class TagService extends AbstractService<Tag, Tag> {


    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void save(Tag tag) {
        tagRepository.save(tag);
    }
}
