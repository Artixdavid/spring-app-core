package com.app.core.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.app.core.models.entity.Status;

public interface IStatusDao extends CrudRepository<Status, Long> {

}
