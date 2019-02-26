package com.app.core.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.core.models.dao.IStatusDao;
import com.app.core.models.entity.Status;

@Service
public class StatusServiceImpl implements IStatusService {
	
	@Autowired
	private IStatusDao statusDao;

	@Override
	public Status findById(Long id) {
		return (Status) statusDao.findById(id).orElse(null);
	}

	@Override
	public List<Status> findAll() {
		return (List<Status>) statusDao.findAll();
	}

}
