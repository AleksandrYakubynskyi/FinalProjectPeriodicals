package com.project.service;

import com.project.entity.Publication;
import com.project.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PublicationService {
    void addPublication(Publication publication);

    List<Publication> getAllPublications(Map<String, String[]> paramMap);

    Optional<Publication> searchPublicationByName(String name);

    List<Publication> getPublicationForUser(String id);

    List<Publication> getPublicationForUserWithParams(String id, Map<String, String[]> paramMap);

    Optional<Publication> getPublicationById(String id);

    void setPublicationForUser(User user, Publication publication);

    void removePublication(String id);

    void removePublicationForUser(String publicationId, String userId);

    void updatePublication(Publication publication);

    int getNumberOfPublication(Map<String, String[]> paramMap);

    int getNumberOfPublicationForUser(User user);
}
