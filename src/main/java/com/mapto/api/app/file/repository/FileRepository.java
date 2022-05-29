package com.mapto.api.app.file.repository;

import com.mapto.api.app.file.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
}
