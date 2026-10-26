package ma.ensa.pharmaciestock.controllers.rest;

import ma.ensa.pharmaciestock.entities.Tag;
import ma.ensa.pharmaciestock.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/tags", "/tags"})
@CrossOrigin(origins = "*")
public class TagRestController {

    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * GET /api/tags -> lister tous les tags
     * Status HTTP: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    /**
     * GET /api/tags/{id} -> afficher un tag par ID
     * Status HTTP: 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    /**
     * POST /api/tags -> ajouter un tag
     * Status HTTP: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag saved = tagService.saveTag(tag);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * PUT /api/tags/{id} -> modifier un tag
     * Status HTTP: 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tag) {
        Tag updated = tagService.updateTag(id, tag);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/tags/{id} -> supprimer un tag
     * Status HTTP: 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
