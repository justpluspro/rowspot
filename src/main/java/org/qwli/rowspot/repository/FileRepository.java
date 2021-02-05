package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.UserUploadFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<UserUploadFile, Long> {


    @Query("select sum(fileSize) from File where userId = ?1")
    Optional<Long> findTotalSizeByUser(Long userId);
}
