package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.model.Tag;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Tag
 * @author liqiwen
 * @since 1.0
 */
@RestController
@RequestMapping("api")
public class TagApi extends AbstractApi {


    private final TagService tagService;

    public TagApi(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("tag/saved")
    public ResponseEntity<Void> save(@RequestBody @Validated Tag tag) {
        tagService.save(tag);
        return ResponseEntity.ok().build();
    }



    @PutMapping("tag/{id}/update")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Tag tag) {

        tag.setId(id);

        tagService.update(tag);


        return ResponseEntity.ok().build();
    }



    @GetMapping("tags")
    public ResponseEntity<PageAggregate<Tag>> findPage(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        if(page == null || page < 1){
            page = 1;
        }
        return ResponseEntity.ok(tagService.findPage(page));
    }

    @DeleteMapping("tag/{id}/deleted")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        tagService.delete(id);
        return ResponseEntity.ok(id);
    }

}
