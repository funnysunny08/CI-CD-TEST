package sopt.org.springPractice.infrastructure;

import org.springframework.data.repository.Repository;
import sopt.org.springPractice.domain.Image;

public interface ImageRepository extends Repository<Image, Long> {
    // CREATE
    void save(Image image);
}
