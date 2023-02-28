package com.project.service;

import com.project.dao.PublicationDao;
import com.project.entity.Publication;
import com.project.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DefaultPublicationService implements PublicationService {
    private final PublicationDao publicationDao;

    public DefaultPublicationService(PublicationDao publicationDao) {
        this.publicationDao = publicationDao;
    }

    @Override
    public void addPublication(Publication publication) {
        publication.setId(UUID.randomUUID().toString());
        publicationDao.addPublication(publication);
    }

    @Override
    public List<Publication> getAllPublications(Map<String, String[]> paramMap) {
        return publicationDao.getAllPublications(paramMap);
    }

    @Override
    public Optional<Publication> searchPublicationByName(String name) {
        return publicationDao.searchPublicationByName(name);
    }

    @Override
    public List<Publication> getPublicationForUser(String id) {
        return publicationDao.getPublicationForUser(id);
    }

    @Override
    public List<Publication> getPublicationForUserWithParams(String id, Map<String, String[]> paramMap) {
        return publicationDao.getPublicationForUserWithParams(id, paramMap);
    }

    @Override
    public Optional<Publication> getPublicationById(String id) {
        return publicationDao.getPublicationById(id);
    }

    @Override
    public void setPublicationForUser(User user, Publication publication) {
        publicationDao.setPublicationForUser(user, publication);
    }

    @Override
    public void removePublication(String id) {
        publicationDao.removePublication(id);
    }

    @Override
    public void removePublicationForUser(String publicationId, String userId) {
        publicationDao.removePublicationForUser(publicationId, userId);
    }

    @Override
    public void updatePublication(Publication publication) {
        publicationDao.updatePublication(publication);
    }

    @Override
    public int getNumberOfPublication(Map<String, String[]> paramMap) {
        return publicationDao.getNumberOfPublication(paramMap);
    }

    @Override
    public int getNumberOfPublicationForUser(User user) {
        return publicationDao.getNumberOfPublicationForUser(user);
    }
}
