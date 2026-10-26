package ma.ensa.pharmaciestock.services;

import ma.ensa.pharmaciestock.entities.Tag;
import ma.ensa.pharmaciestock.exception.ResourceNotFoundException;
import ma.ensa.pharmaciestock.repositories.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag non trouvé avec l'id: " + id));
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, Tag details) {
        Tag existing = getTagById(id);
        existing.setLibelle(details.getLibelle());
        return tagRepository.save(existing);
    }

    public void deleteTag(Long id) {
        Tag existing = getTagById(id);
        tagRepository.delete(existing);
    }
}
