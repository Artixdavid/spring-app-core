package com.app.core.models.services;

import java.util.List;

import com.app.core.models.entity.Status;

public interface IStatusService  {

	public Status findById(Long id);
	
	public List<Status> findAll();
}
