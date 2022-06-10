package com.mapto.api.app.file.repository;

import com.mapto.api.app.file.entity.File;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepository extends CrudRepository<File, Long>, FileRepositoryCustom {
}
